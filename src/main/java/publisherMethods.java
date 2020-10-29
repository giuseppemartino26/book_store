import java.sql.*;

public class publisherMethods {

    static Connection MyConn = null;
    static PreparedStatement PrepareStat = null;


    public static void log(String string) {
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


    public static void addPublisher( String name, String location) {

        try {
            String insertQueryStatement = "INSERT  INTO  Publisher(name,location)  VALUES  (?,?)";

            PrepareStat = MyConn.prepareStatement(insertQueryStatement);
            PrepareStat.setString(1, name);
            PrepareStat.setString(2, location);

            PrepareStat.executeUpdate();
            log(name + " added successfully");
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removePublisher(int idPublisher) {

        try {
            String query = "DELETE FROM Publisher WHERE idPublisher = ?";
            PrepareStat = MyConn.prepareStatement(query);
            PrepareStat.setInt(1, idPublisher);


            PrepareStat.execute();

            MyConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void ReadListPublisher() {



        try {

            String getQueryStatement = "SELECT * FROM Publisher ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);

            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idPublisher = rs.getInt("idPublisher");
                String name = rs.getString("name");
                String location = rs.getString("location");

                System.out.format("id:%d - %s, %s\n", idPublisher, name, location);
            }

        } catch (

                SQLException e) {
            e.printStackTrace();
        }

    }
    public static void viewPublisher(int id){

        try {
            String getQueryStatement = "SELECT * FROM publisher WHERE idpublisher = ? ";

            PrepareStat = MyConn.prepareStatement(getQueryStatement);
            PrepareStat.setInt(1, id);
            ResultSet rs = PrepareStat.executeQuery();

            while (rs.next()) {
                int idpublisher = rs.getInt("idpublisher");
                String location = rs.getString("location");
                String name = rs.getString("name");



                System.out.format("%d, %s, %s\n", idpublisher, name, location);
            }

        } catch (

                SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean check(int idpublisher) {
        int conto = 0;
        try {

            String getQueryStatement = "select count(*) as conto from publisher where idpublisher =?";
            PrepareStat = MyConn.prepareStatement(getQueryStatement);

            PrepareStat.setInt(1,idpublisher);
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

