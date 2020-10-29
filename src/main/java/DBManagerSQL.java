import it.unipi.dii.inginf.lsdb.library.author.Author;
import it.unipi.dii.inginf.lsdb.library.book.book;
import it.unipi.dii.inginf.lsdb.library.publisher.Publisher;

import java.sql.*;
import java.util.ArrayList;


public class DBManagerSQL {

    private Connection MyConn = null; //Interface for establishing a connection
    private PreparedStatement PrepareStat = null;

    public DBManagerSQL() {
    }

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

    /* Metodo che ritorna una lista di libri del DB */
    public ArrayList<book> ReadList()
    {

        ArrayList<book> bookList = new ArrayList<>();

        try {
            makeJDBCConnection();

            String getQueryStatement = "SELECT * FROM  book ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idbook = rs.getInt("idbook");
                String title = rs.getString("title");

                book newBook = new book(idbook,title);
                bookList.add(newBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return bookList;
    }


    //Ritorna un libro con un determinato id
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

    //ritorna il publisher di un libro il cui id lo passo come parametro
    public Publisher getPublisherFromBookId(int id)
    {
        Publisher publisher = null;
        try {
            makeJDBCConnection();
            String getQueryStatement = "SELECT * FROM publisher P inner join book B ON B.pub_id = P.idpublisher WHERE B.idbook = ?";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            PrepareStat.setInt(1, id);

            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int ipublisher = rs.getInt("idpublisher");
                String name = rs.getString("name");
                String location = rs.getString("location");

                publisher = new Publisher(ipublisher, name, location);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return publisher;
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

    public boolean checkpub(int idpublisher) {
        int conto = 0;
        try {
            makeJDBCConnection();

            String getQueryStatement = "select count(*) as conto from publisher where idpublisher =?";
            PrepareStat = MyConn.prepareStatement(getQueryStatement);

            PrepareStat.setInt(1, idpublisher);
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

    public void addPublisher(Publisher newpublisher) {

        try {
            makeJDBCConnection();
            String insertQueryStatement = "INSERT  INTO  publisher(idpublisher,name,location)  VALUES  (?,?,?)";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);

            PrepareStat.setInt(1, newpublisher.getIdpublisher());
            PrepareStat.setString(2, newpublisher.getName());
            PrepareStat.setString(3, newpublisher.getLocation());


            PrepareStat.executeUpdate();
            log(newpublisher.getName() + " added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    //METODO CHE RIEMPIE LA TABELLA book_has_author
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
        closeConnection();
    }


    public ArrayList<Publisher> ReadListPublisher() {

        ArrayList<Publisher> PublisherList = new ArrayList<>();

        try {
            makeJDBCConnection();

            String getQueryStatement = "SELECT * FROM  publisher ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idpublisher = rs.getInt("idpublisher");
                String name = rs.getString("name");
                String location = rs.getString("location");



                Publisher newAPublisher= new Publisher (idpublisher,name,location);

                PublisherList.add(newAPublisher);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return PublisherList;

    }


    /*---METODO CHE RITORNA UNA LISTA DI AUTHOR --ROSSELLA*/
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

    public Publisher viewPublisher(int idPublisher)
    {
        Publisher publisher = null;
        try {
            makeJDBCConnection();
            String query = "Select * from publisher where idpublisher = ?";

            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat.setInt(1,idPublisher);

            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next())
            {
                int idpublisher = rs.getInt("idpublisher");
                String name = rs.getString("name");
                String location = rs.getString("location");

                publisher = new Publisher(idpublisher,name,location);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return publisher;
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

    public void removePublisher(int idpublisher) {

        try {
            makeJDBCConnection();
            String query = "SET foreign_key_checks = 0 ";
            String query2 = "DELETE FROM Publisher where idpublisher = ?";

            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat = MyConn.prepareStatement(query2);

            PrepareStat.setInt(1, idpublisher);


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
