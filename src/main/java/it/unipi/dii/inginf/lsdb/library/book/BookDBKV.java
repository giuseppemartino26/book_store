package it.unipi.dii.inginf.lsdb.library.book;

import org.iq80.leveldb.*;
import org.iq80.leveldb.DBIterator;

import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BookDBKV implements BookDB{

    private DB db = null;

    private void openDB()
    {
        Options options = new Options();
        options.createIfMissing(true);
        try {
            db = factory.open(new File("example"),options);
        }catch (IOException ioe){ closeDB();}
    }

    private void closeDB()
    {
        try {
            if (db != null) db.close();
        }catch (IOException ioe){ioe.printStackTrace();}

    }

    public void putValue(String key, String value)
    {
        db.put(bytes(key),bytes(value));
    }

    public String getValue(String key)
    {
        return asString(db.get(bytes(key)));
    }

    public void deleteValue(String key)
    {
        db.delete(bytes(key));
    }

    public  void fillDB()
    {
        openDB();

        putValue("author:1:firstname","elena");
        putValue("author:1:lastname","ferrante");
        putValue("author:1:biography","vivo");

        putValue("author:3:firstname","Dante");
        putValue("author:3:lastname","Alighieri");
        putValue("author:3:biography","vivo");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:title","Divina Commedia");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:price","44");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:category","storico");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:numpages","1000");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:quantity","2");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:pub_id","3");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:publication_YEAR","2020");
        putValue("publisher:5:namep","LaLepre");
        putValue("publisher:3:namep","LaLepre2");
        putValue("publisher:5:location","milano");
        putValue("book_has_author:5","Dante Alighieri");
        putValue("book_publisher:5","LaLepre");


        closeDB();
    }

    public ArrayList<book> ReadBookList()
    {
        ArrayList<book> bookList = new ArrayList<>();
        int idbook = 0;
        String title = null;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();

            if (asString(key).contains("title"))
            {
                String[] parts = asString(key).split(":");
                idbook = Integer.parseInt(parts[1]);
                title = getValue(asString(key));
                book newBook = new book(idbook,title);
                bookList.add(newBook);
            }

            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();

        return bookList;
    }

    public book getBookFromId(int id) {
        book newBook = null;
        int idbook=0,pub_id=0;
        String title=null,category=null;
        int numpages=0;
        int quantity = 0;
        int pub_year=0;
        float price = 0;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext()) {
            byte[] key = iterator.peekNext().getKey();


            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":title"))
            {
                //  idbook = Character.getNumericValue(asString(key).charAt(5)); //idbook is the fifth char of the key
                String[] parts = asString(key).split(":");
                idbook = Integer.parseInt(parts[1]);
                title = getValue(asString(key));
            }
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":price"))
            {
                price = Float.parseFloat(getValue(asString(key)));
            }
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":category"))
            {
                category = getValue(asString(key));
            }
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":numpages"))
            {
                numpages = Integer.parseInt(getValue(asString(key)));
            }
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":quantity"))
            {
                quantity =Integer.parseInt(getValue(asString(key)));
            }
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":pub_id"))
            {
                pub_id =Integer.parseInt(getValue(asString(key)));
            }
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":publication_YEAR"))
            {
                pub_year =Integer.parseInt(getValue(asString(key)));
            }

            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
        newBook = new book(idbook, title, price, category, numpages, quantity, pub_id, pub_year);
        //System.out.println(newBook.getQuantity());
        return newBook;


    }

    public void increaseQuantity(int id)
    {
        int oldquantity =0;
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":quantity"))
            {
                oldquantity = Integer.parseInt(getValue(asString(key)));
                deleteValue(asString(key));
                putValue(asString(key),String.valueOf(oldquantity+1));
            }
            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
    }

    public void decreaseQuantity(int id)
    {
        int oldquantity =0;
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":quantity"))
            {
                oldquantity = Integer.parseInt(getValue(asString(key)));
                deleteValue(asString(key));
                putValue(asString(key),String.valueOf(oldquantity-1));
            }
            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
    }

    public void updateQuantity(int num_copies,int id)
    {
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id + ":quantity"))
            {
                deleteValue(asString(key));
                putValue(asString(key),String.valueOf(num_copies));
            }
            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
    }

    public void removeBook(int id)
    {
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ id))
            {
                deleteValue(asString(key));
            }
            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
    }

    public boolean checkbook(int idbook)
    {
        int conto = 0;
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("it.unipi.dii.inginf.lsdb.library.book.book:"+ idbook))
            {
                conto++;
            }
            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
        if (conto > 0) {
            return true;
        }
        else return false;


    }

    public void addBook(book newBook)
    {
        openDB();
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:"+newBook.getIdbook()+":title",newBook.getTitle());
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:"+newBook.getIdbook()+":price", String.valueOf(newBook.getPrice()));
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:"+newBook.getIdbook()+":category",newBook.getCategory());
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:" +newBook.getIdbook()+":numpages", String.valueOf(newBook.getNumpages()));
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:" +newBook.getIdbook()+":quantity", String.valueOf(newBook.getQuantity()));
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:" +newBook.getIdbook()+":pub_id", String.valueOf(newBook.getPub_id()));
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:" +newBook.getIdbook()+":publication_YEAR", String.valueOf(newBook.getPub_Year()));

        closeDB();

    }

    public void fillbookhasauthor(int bookid, int idauthor,int idpub)
    {
        openDB();
        putValue("book_has_author:"+bookid+":",getValue("author:"+idauthor+":lastname"));
        putValue("book_publisher:"+bookid+":",getValue("publisher:"+idpub+":name"));
        closeDB();
    }

}
