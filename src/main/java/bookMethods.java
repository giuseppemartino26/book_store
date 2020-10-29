import java.sql.*;

public class bookMethods {

    static Connection MyConn = null; //Interface for establishing a connection
    static PreparedStatement PrepareStat = null;

    public bookMethods() {};


    private static void log(String string) {
        System.out.println(string);
    }

    public static void makeJDBCConnection() {

        try {
            MyConn = DriverManager.getConnection("jdbc:mysql://localhost/book_store?" + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=CET", "root", "root");

        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    public static void closeConnection(){
        try {
            MyConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBook( int idbook, String title, float price, String category, int numPages, int quantity,int publisher_idPublisher,int publicationYear) {

        try {
            String insertQueryStatement = "INSERT  INTO  book(idbook,title,price,category,numpages,quantity,pub_id,publicationYEAR)  VALUES  (?,?,?,?,?,?,?,?)";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);

            PrepareStat.setInt(1,idbook);
            PrepareStat.setString(2, title);
            PrepareStat.setFloat(3, price);
            PrepareStat.setString(4, category);
            PrepareStat.setInt(5, numPages);
            PrepareStat.setInt(6, quantity);
            PrepareStat.setInt(7, publisher_idPublisher);
            PrepareStat.setInt(8,publicationYear );

            PrepareStat.executeUpdate();
            log(title + " added successfully");
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeBook(int idBook) {

        try {
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

    }

    public static void updateQuantity(int num_copies, int id) {

        try {
            String insertQueryStatement = "UPDATE BOOK SET quantity = ? WHERE idbook = ?";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, num_copies);
            PrepareStat.setInt(2, id);

            PrepareStat.executeUpdate();
            log("Quantity updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void increaseQuantity(int id) {
        try {
            String insertQueryStatement = "UPDATE BOOK SET quantity = quantity + 1 WHERE idbook = ?";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, id);

            PrepareStat.executeUpdate();
            log("increased");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void decreaseQuantity(int id) {
        try {
            String insertQueryStatement = "UPDATE BOOK SET quantity = quantity - 1 WHERE idbook = ?";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, id);

            PrepareStat.executeUpdate();
            log("decreased");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void ReadList() {



        try {
            // MySQL Select Query Tutorial
            String getQueryStatement = "SELECT * FROM  book ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = PrepareStat.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                int idbook = rs.getInt("idbook");
                String title = rs.getString("title");



                // Simply Print the results
                System.out.format("id:%d - %s\n", idbook, title);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewBook(int id){
        try {

            String getQueryStatement = "SELECT B.idbook, B.title, B.price, B.category, B.numpages, B.quantity, B.publicationYEAR, A.idauthor, A.firstname, A.lastname, P.idpublisher, P.name \n" +
                    "FROM book_has_author BH inner join book B on BH.book_idbook = B.idbook inner join author A on BH.author_idauthor = A.idauthor inner join publisher P on B.pub_id = P.idpublisher WHERE  B.idbook = ?";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            PrepareStat.setInt(1,id);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = PrepareStat.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                int idbook = rs.getInt("idbook");
                String title = rs.getString("title");
                float price = rs.getFloat("price");
                String category = rs.getString("category");
                int numpages = rs.getInt("numpages");
                int quantity = rs.getInt("quantity");
                int pubyear = rs.getInt("publicationYEAR");
                int idauthor = rs.getInt("idauthor");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int idpublisher = rs.getInt("idpublisher");
                String name = rs.getString("name");


                // Simply Print the results
                System.out.format("idbook:%d\nTITLE: %s\nPRICE: %.2f\n CATEGORY:%s\nNUMBER OF PAGES: %d\nQUANTITY: %d\nPUBLICATION YEAR: %d\nID AUTHOR:%d\nAUTHOR NAME" +
                        "%s\nAUTHOR SURNAME: %s\nID PUBLISHER: %d\nPUBLISHER NAME: %s\n", idbook, title,price,category,numpages,quantity,pubyear,idauthor,firstname,lastname,idpublisher,name);
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void  fillbookhasauthor(int idbook, int idauthor){
        try {

            String query = "SET foreign_key_checks = 0 ";
            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat.executeUpdate(query);

            String getQueryStatement2 = "insert into book_has_author values(?,?)";
            PrepareStat = MyConn.prepareStatement(getQueryStatement2);
            PrepareStat.setInt(1,idbook);
            PrepareStat.setInt(2,idauthor);
            PrepareStat.executeUpdate();

            String queryback = "SET foreign_key_checks = 1 ";
            PrepareStat = MyConn.prepareStatement(queryback);
            PrepareStat.executeUpdate(queryback);



        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static boolean checkbook(int idbook) {
        int conto = 0;
        try {

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
        return false;

    }


}
