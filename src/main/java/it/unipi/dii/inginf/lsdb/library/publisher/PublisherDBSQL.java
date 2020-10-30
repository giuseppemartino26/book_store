package it.unipi.dii.inginf.lsdb.library.publisher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class PublisherDBSQL {


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

