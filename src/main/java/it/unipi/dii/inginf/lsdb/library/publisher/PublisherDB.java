package it.unipi.dii.inginf.lsdb.library.publisher;

import java.util.ArrayList;

public interface PublisherDB {

    Publisher getPublisherFromBookId(int id);
    boolean checkpub(int idpublisher);
    void addPublisher(Publisher newpublisher);
    ArrayList<Publisher> ReadListPublisher();
    Publisher viewPublisher(int idPublisher);
    void removePublisher(int idpublisher);
}
