package it.unipi.dii.inginf.lsdb.library.persistence;

import it.unipi.dii.inginf.lsdb.library.entities.Author;
import it.unipi.dii.inginf.lsdb.library.entities.Publisher;
import it.unipi.dii.inginf.lsdb.library.entities.book;

import java.util.ArrayList;

public interface DBManager {

    ArrayList<book> ReadList();
    book getBookFromId(int id);
    Author getAuthorFromBookId(int id);
    Publisher getPublisherFromBookId(int id);
    void removeBook(int idBook);
    void increaseQuantity(int id);
    void decreaseQuantity(int id);
    void updateQuantity(int num_copies, int id);
    boolean checkbook(int idbook);
    boolean check(int idauthor);
    boolean checkpub(int idpublisher);
    void addBook(book newbook);
    void addAuthor(Author newauthor);
    void addPublisher(Publisher newpublisher);
    void fillbookhasauthor(int bookid, int idauthor,int idpub);
    void fillbookhasauthor(int bookid, int idauthor);
    ArrayList<Publisher> ReadListPublisher();
    ArrayList<Author> ReadListAuthor();
    Author viewAuthor(int idAuthor);
    Publisher viewPublisher(int idPublisher);
    void removeAuthor(int idauthor);
    void removePublisher(int idpublisher);
}
