package it.unipi.dii.inginf.lsdb.library.persistence;

import it.unipi.dii.inginf.lsdb.library.entities.Author;
import it.unipi.dii.inginf.lsdb.library.entities.book;
import it.unipi.dii.inginf.lsdb.library.entities.Publisher;
import org.iq80.leveldb.*;
import org.iq80.leveldb.DBIterator;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

 class DBManagerLevel implements DBManager {

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

    public ArrayList<book> ReadList()
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

    public ArrayList<Author> ReadListAuthor()
    {
        ArrayList<Author> authorList = new ArrayList<>();
        int idauthor = 0;
        String lastname = null;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();

            if (asString(key).contains("lastname"))
            {
                String[] parts = asString(key).split(":");
                idauthor = Integer.parseInt(parts[1]);
                lastname = getValue(asString(key));
                Author author = new Author(idauthor,lastname);
                authorList.add(author);
            }

            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();

        return authorList;
    }

    public ArrayList<Publisher> ReadListPublisher()
    {
        ArrayList<Publisher> publisherList = new ArrayList<>();
        int idpublisher = 0;
        String name = null;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();

            if (asString(key).contains("publisher") && asString(key).contains("namep"))
            {
               // idpublisher = Character.getNumericValue(asString(key).charAt(10));
                String[] parts = asString(key).split(":");
                idpublisher = Integer.parseInt(parts[1]);
                name = getValue(asString(key));

                Publisher publisher = new Publisher(idpublisher,name);
                publisherList.add(publisher);
            }

            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();

        return publisherList;
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

    public Author getAuthorFromBookId(int id)
    {
        Author author = null;

        String name = null;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext()) {
            byte[] key = iterator.peekNext().getKey();

            if (asString(key).contains("book_has_author:"+ id))
            {
                name = getValue(asString(key));
              //  System.out.println("get it.unipi.dii.inginf.lsdb.library.author.Author: "+getValue(asString(key)));
            }
            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
        author = new Author(name);
        return author;
    }

    public Publisher getPublisherFromBookId(int id)
    {
        Publisher publisher = null;

        String name = null;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext()) {
            byte[] key = iterator.peekNext().getKey();

            if (asString(key).contains("book_publisher:"+ id))
            {
                name = getValue(asString(key));
            }
            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
        publisher = new Publisher(name);
        return publisher;
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

    public boolean check(int idbook)
    {
        int conto = 0;
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("author:"+ idbook))
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

    public boolean checkpub(int idbook)
    {
        int conto = 0;
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("publisher:"+ idbook))
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

    public void addAuthor(Author newAuthor)
    {
        openDB();

        putValue("author:"+newAuthor.getIdauthor()+":firstname",newAuthor.getFirstname());
        putValue("author:"+newAuthor.getIdauthor()+":lastname",newAuthor.getLastname());
        putValue("author"+newAuthor.getIdauthor()+":biography",newAuthor.getBiography());

        closeDB();
    }

    public void addPublisher(Publisher newPublisher)
    {
        openDB();

        putValue("publisher:"+newPublisher.getIdpublisher()+":namep",newPublisher.getName());
        putValue("publisher:"+newPublisher.getIdpublisher()+":location",newPublisher.getLocation());

        closeDB();
    }


    public void fillbookhasauthor(int bookid, int idauthor,int idpub)
    {
        openDB();
        putValue("book_has_author:"+bookid+":",getValue("author:"+idauthor+":lastname"));
        putValue("book_publisher:"+bookid+":",getValue("publisher:"+idpub+":name"));
        closeDB();
    }
    public void fillbookhasauthor(int bookid, int idauthor){}

    public Author viewAuthor(int id)
    {
        Author author = null;
        String name = null;
        String surname = null;
        String biography = null;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext()) {
            byte[] key = iterator.peekNext().getKey();

            if (asString(key).contains("author:"+ id+":firstname"))
            {
                name = getValue(asString(key));
            }

            if (asString(key).contains("author:"+ id+":lastname"))
            {
                surname = getValue(asString(key));
            }

            if (asString(key).contains("author:"+ id+":biography"))
            {
                biography = getValue(asString(key));
            }

            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
        author = new Author(id,name,surname,biography);
        return author;
    }


    public Publisher viewPublisher(int id)
    {
        Publisher publisher = null;
        String name = null;
        String location = null;

        openDB();

        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext()) {
            byte[] key = iterator.peekNext().getKey();

            if (asString(key).contains("publisher:"+ id+":namep"))
            {
                name = getValue(asString(key));
            }

            if (asString(key).contains("publisher:"+ id+":location"))
            {
                location = getValue(asString(key));
            }

            iterator.next();
        }
        try {
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeDB();
        publisher = new Publisher(id,name,location);
        return publisher;
    }


    public void removeAuthor(int id)
    {
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("author:"+ id))
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


    public void removePublisher(int id)
    {
        openDB();
        DBIterator iterator = db.iterator();
        iterator.seekToFirst(); //start from the first key

        while (iterator.hasNext())
        {
            byte[] key = iterator.peekNext().getKey();
            if (asString(key).contains("publisher:"+ id))
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




}







