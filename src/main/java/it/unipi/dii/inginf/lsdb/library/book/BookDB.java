package it.unipi.dii.inginf.lsdb.library.book;

import java.sql.*;
import java.util.ArrayList;

public interface BookDB {

    ArrayList<book> ReadBookList();
    book getBookFromId(int id);
    void removeBook(int idBook);
    void increaseQuantity(int id);
    void decreaseQuantity(int id);
    void updateQuantity(int num_copies, int id);
    boolean checkbook(int idbook);
    public void addBook(book newbook);


}
