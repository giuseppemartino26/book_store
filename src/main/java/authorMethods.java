import java.sql.*;

public class authorMethods {

    static Connection MyConn = null; //Interface for establishing a connection
    static PreparedStatement PrepareStat = null;

    private static void log(String string) {
        System.out.println(string);
    }

    public static void makeJDBCConnection() {

        try {
            MyConn = DriverManager.getConnection("jdbc:mysql://localhost/book_store?" + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=CET", "root", "root");

        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();

        }

    }

    public static void closeConnection(){
        try {
            MyConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAuthor(String firstname, String lastname, String biography) {

        try {
            String insertQueryStatement = "INSERT  INTO  author(firstname,lastname,biography)  VALUES  (?,?,?)";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setString(1, firstname);
            PrepareStat.setString(2, lastname);
            PrepareStat.setString(3, biography);

            PrepareStat.executeUpdate();
            log(lastname + " added successfully");
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeAuthor(int idAuthor) {

        try {
            String query = "DELETE FROM Author WHERE idAuthor = ?";
            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat.setInt(1, idAuthor);


            PrepareStat.execute();

            MyConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void ReadListAuthor() {

        try {

            String getQueryStatement = "SELECT * FROM author ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);

            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idAuthor = rs.getInt("idauthor");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                // Simply Print the results
                System.out.format("id: %d - %s %s\n", idAuthor, firstname, lastname);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAuthor(int id) {
        try {

            String getQueryStatement = "select idauthor,firstname,lastname,biography from author where  idauthor = ?";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            PrepareStat.setInt(1, id);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = PrepareStat.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                int idauthor = rs.getInt("idauthor");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String biography = rs.getString("biography");


                // Simply Print the results
                System.out.format("%d - %s - %s -%s\n", idauthor, firstname, lastname, biography);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static boolean check(int idauthor) {
            int conto = 0;
            try {

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
            return false;

        }





}
