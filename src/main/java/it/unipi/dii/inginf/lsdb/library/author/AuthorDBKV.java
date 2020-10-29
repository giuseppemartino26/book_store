package it.unipi.dii.inginf.lsdb.library.author;

import org.iq80.leveldb.*;
import org.iq80.leveldb.DBIterator;

import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorDBKV implements AuthorDB {

    private DB db = null;

    private void openDB() {
        Options options = new Options();
        options.createIfMissing(true);
        try {
            db = factory.open(new File("example"), options);
        } catch (IOException ioe) {
            closeDB();
        }
    }

    private void closeDB() {
        try {
            if (db != null) db.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void putValue(String key, String value) {
        db.put(bytes(key), bytes(value));
    }

    public String getValue(String key) {
        return asString(db.get(bytes(key)));
    }

    public void deleteValue(String key) {
        db.delete(bytes(key));
    }

    public void fillDB() {
        openDB();

        putValue("author:1:firstname", "elena");
        putValue("author:1:lastname", "ferrante");
        putValue("author:1:biography", "vivo");

        putValue("author:3:firstname", "Dante");
        putValue("author:3:lastname", "Alighieri");
        putValue("author:3:biography", "vivo");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:title", "Divina Commedia");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:price", "44");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:category", "storico");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:numpages", "1000");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:quantity", "2");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:pub_id", "3");
        putValue("it.unipi.dii.inginf.lsdb.library.book.book:5:publication_YEAR", "2020");
        putValue("publisher:5:namep", "LaLepre");
        putValue("publisher:3:namep", "LaLepre2");
        putValue("publisher:5:location", "milano");
        putValue("book_has_author:5", "Dante Alighieri");
        putValue("book_publisher:5", "LaLepre");


        closeDB();
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

    public void addAuthor(Author newAuthor)
    {
        openDB();

        putValue("author:"+newAuthor.getIdauthor()+":firstname",newAuthor.getFirstname());
        putValue("author:"+newAuthor.getIdauthor()+":lastname",newAuthor.getLastname());
        putValue("author"+newAuthor.getIdauthor()+":biography",newAuthor.getBiography());

        closeDB();
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


}