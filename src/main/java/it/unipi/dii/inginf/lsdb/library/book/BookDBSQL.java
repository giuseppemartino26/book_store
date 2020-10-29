package it.unipi.dii.inginf.lsdb.library.book;

import java.sql.*;
import java.util.ArrayList;

public class BookDBSQL implements BookDB {

    private Connection MyConn = null; //Interface for establishing a connection
    private PreparedStatement PrepareStat = null;

    void log(String string) {
        System.out.println(string);
    }

    public void makeJDBCConnection() {

        try {
            MyConn = DriverManager.getConnection("jdbc:mysql://localhost/book_store?" + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=CET", "root", "root");

        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    public void closeConnection() {
        try {
            MyConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<book> ReadBookList() {
        ArrayList<book> bookList = new ArrayList<>();

        try {
            makeJDBCConnection();

            String getQueryStatement = "SELECT * FROM  book ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idbook = rs.getInt("idbook");
                String title = rs.getString("title");

                book newBook = new book(idbook, title);
                bookList.add(newBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return bookList;
    }

    public book getBookFromId(int id) {
        book newBook = null;
        try {
            makeJDBCConnection();
            String getQueryStatement = "SELECT * FROM book WHERE idbook = ?";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            PrepareStat.setInt(1, id);

            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idbook = rs.getInt("idbook");
                String title = rs.getString("title");
                float price = rs.getFloat("price");
                String category = rs.getString("category");
                int numpages = rs.getInt("numpages");
                int quantity = rs.getInt("quantity");
                int pubid = rs.getInt("pub_id");
                int pubyear = rs.getInt("publicationYEAR");

                newBook = new book(idbook, title, price, category, numpages, quantity, pubid, pubyear);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return newBook;

    }

    public void removeBook(int idBook) {

        try {
            makeJDBCConnection();
            String query = "SET foreign_key_checks = 0 ";
            String query2 = "DELETE FROM book where idbook = ?";

            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat = MyConn.prepareStatement(query2);

            PrepareStat.setInt(1, idBook);


            PrepareStat.executeUpdate(query);
            PrepareStat.execute();

            String queryback = "SET foreign_key_checks = 1 ";
            PrepareStat = MyConn.prepareStatement(queryback);
            PrepareStat.executeUpdate(queryback);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

    }

    public void increaseQuantity(int id) {
        try {
            makeJDBCConnection();
            String insertQueryStatement = "UPDATE BOOK SET quantity = quantity + 1 WHERE idbook = ?";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, id);

            PrepareStat.executeUpdate();
            log("increased");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

    }

    public void decreaseQuantity(int id) {
        try {
            makeJDBCConnection();
            String insertQueryStatement = "UPDATE BOOK SET quantity = quantity - 1 WHERE idbook = ?";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, id);

            PrepareStat.executeUpdate();
            log("decreased");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

    }

    public void updateQuantity(int num_copies, int id) {

        try {
            makeJDBCConnection();
            String insertQueryStatement = "UPDATE BOOK SET quantity = ? WHERE idbook = ?";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, num_copies);
            PrepareStat.setInt(2, id);

            PrepareStat.executeUpdate();
            log("Quantity updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public boolean checkbook(int idbook) {
        int conto = 0;
        try {
            makeJDBCConnection();

            String getQueryStatement = "select count(*) as conto from book where idbook =?";
            PrepareStat = MyConn.prepareStatement(getQueryStatement);

            PrepareStat.setInt(1, idbook);
            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                conto = rs.getInt("conto");
            }
            if (conto > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;

    }

    public void addBook(book newbook) {

        try {
            makeJDBCConnection();
            String insertQueryStatement = "INSERT  INTO  book(idbook,title,price,category,numpages,quantity,pub_id,publicationYEAR)  VALUES  (?,?,?,?,?,?,?,?)";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);

            PrepareStat.setInt(1, newbook.getIdbook());
            PrepareStat.setString(2, newbook.getTitle());
            PrepareStat.setFloat(3, newbook.getPrice());
            PrepareStat.setString(4, newbook.getCategory());
            PrepareStat.setInt(5, newbook.getNumpages());
            PrepareStat.setInt(6, newbook.getQuantity());
            PrepareStat.setInt(7, newbook.getPub_id());
            PrepareStat.setInt(8, newbook.getPub_Year());

            PrepareStat.executeUpdate();
            log(newbook.getTitle() + " added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void fillbookhasauthor(int idbook, int idauthor) {
        try {

            makeJDBCConnection();
            String query = "SET foreign_key_checks = 0 ";
            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat.executeUpdate(query);

            String getQueryStatement2 = "insert into book_has_author values(?,?)";
            PrepareStat = MyConn.prepareStatement(getQueryStatement2);
            PrepareStat.setInt(1, idbook);
            PrepareStat.setInt(2, idauthor);
            PrepareStat.executeUpdate();

            String queryback = "SET foreign_key_checks = 1 ";
            PrepareStat = MyConn.prepareStatement(queryback);
            PrepareStat.executeUpdate(queryback);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
