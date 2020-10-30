package it.unipi.dii.inginf.lsdb.library.publisher;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;

import java.io.File;
import java.io.IOException;

import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import java.util.ArrayList;


public class PublisherDBKV implements PublisherDB{

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

    public void addPublisher(Publisher newPublisher)
    {
        openDB();

        putValue("publisher:"+newPublisher.getIdpublisher()+":namep",newPublisher.getName());
        putValue("publisher:"+newPublisher.getIdpublisher()+":location",newPublisher.getLocation());

        closeDB();
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
