package it.unipi.dii.inginf.lsdb.library.author;

import java.sql.*;
import java.util.ArrayList;

public class AuthorDBSQL implements AuthorDB {

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

    //ritorna l'autore di un libro il cui id lo passo come parametro
    public Author getAuthorFromBookId(int id) {
        Author author = null;
        try {
            makeJDBCConnection();
            String getQueryStatement = "SELECT * FROM author A inner join book_has_author BH ON A.idauthor = BH.author_idauthor WHERE BH.book_idbook = ?";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            PrepareStat.setInt(1, id);

            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idauthor = rs.getInt("idauthor");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String biography = rs.getString("biography");

                author = new Author(idauthor, firstname, lastname, biography);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return author;

    }

    public boolean check(int idauthor) {
        int conto = 0;
        try {
            makeJDBCConnection();

            String getQueryStatement = "select count(*) as conto from author where idauthor =?";
            PrepareStat = MyConn.prepareStatement(getQueryStatement);

            PrepareStat.setInt(1, idauthor);
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

    public void addAuthor(Author newauthor) {

        try {
            makeJDBCConnection();
            String insertQueryStatement = "INSERT  INTO  author(idauthor,firstname,lastname,biography)  VALUES  (?,?,?,?)";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);

            PrepareStat.setInt(1, newauthor.getIdauthor());
            PrepareStat.setString(2, newauthor.getFirstname());
            PrepareStat.setString(3, newauthor.getLastname());
            PrepareStat.setString(4, newauthor.getBiography());

            PrepareStat.executeUpdate();
            log(newauthor.getLastname() + " added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public ArrayList<Author> ReadListAuthor() {

        ArrayList<Author> AuthorsList = new ArrayList<>();

        try {
            makeJDBCConnection();

            String getQueryStatement = "SELECT * FROM  author ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idauthor = rs.getInt("idauthor");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String biography = rs.getString("biography");


                Author newAuthor= new Author (idauthor,firstname, lastname, biography);

                AuthorsList.add(newAuthor);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return AuthorsList;

    }

    public Author viewAuthor(int idAuthor)
    {
        Author author = null;
        try {
            makeJDBCConnection();
            String query = "Select * from author where idauthor = ?";

            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat.setInt(1,idAuthor);

            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next())
            {
                int idauthor = rs.getInt("idauthor");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String biography = rs.getString("biography");

                author = new Author(idauthor,firstname,lastname,biography);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return author;
    }

    public void removeAuthor(int idauthor) {

        try {
            makeJDBCConnection();
            String query = "SET foreign_key_checks = 0 ";
            String query2 = "DELETE FROM Author where idauthor = ?";

            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat = MyConn.prepareStatement(query2);

            PrepareStat.setInt(1, idauthor);


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

}
