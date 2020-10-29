import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import  java.util.*;

public class MainClass {

   private static DBManagerSQL database = new DBManagerSQL();
   private static DBManagerLevel dbManagerLevel = new DBManagerLevel();


    public static void printBookList()
    {
        ArrayList<book> bookList = new ArrayList<>();
        //bookList = database.ReadList();
        bookList = dbManagerLevel.ReadList();

        System.out.println("LISTA DEI LIBRI:");

        for (int i = 0; i < bookList.size(); i++)
        {
            System.out.println(bookList.get(i).getIdbook() +" "+bookList.get(i).getTitle());
        }
    }

    public static void printpublisherList()
    {
        ArrayList<Publisher> publisherList = new ArrayList<>();
       // publisherList = database.ReadListPublisher();
        publisherList = dbManagerLevel.ReadListPublisher();
        System.out.println("LISTA DEI PUBLISHER");

        for (int i = 0; i < publisherList.size(); i++)
        {
            System.out.println(publisherList.get(i).getIdpublisher() +" "+publisherList.get(i).getName());
        }
    }

    public static void printAuthorList()
    {
        ArrayList<Author> authorsList = new ArrayList<>();
        //authorsList = database.ReadListAuthor();
        authorsList = dbManagerLevel.ReadListAuthor();
        System.out.println("LISTA DEGLI AUTORI:");

        for (int i = 0; i < authorsList.size(); i++)
        {
            System.out.println(authorsList.get(i).getIdauthor() +" "+authorsList.get(i).getLastname());
        }
    }

    public static void printBookdetails(int a)
    {
        System.out.println(dbManagerLevel.getBookFromId(a).getIdbook() +"\nTitle:"+dbManagerLevel.getBookFromId(a).getTitle() +"\nPrice:"+dbManagerLevel.getBookFromId(a).getPrice()+
                "\nCategory:"+dbManagerLevel.getBookFromId(a).getCategory() +"\nNumber of pages:"+ dbManagerLevel.getBookFromId(a).getNumpages()+
                "\nQuantity: "+dbManagerLevel.getBookFromId(a).getQuantity()+/*"\n Publisher ID: "+ dbManagerLevel.getBookFromId(a).getPub_id()+*/
                "\nPublication Year: "+dbManagerLevel.getBookFromId(a).getPub_Year()+/*"\nID Author: "+ dbManagerLevel.getAuthorFromBookId(a).getIdauthor()+*/
                "\nAuthor name: "+ dbManagerLevel.getAuthorFromBookId(a).getLastname() +/*"\nAuthor surname: "+dbManagerLevel.getAuthorFromBookId(a).getLastname()+*/
                "\nPublisher name: "+dbManagerLevel.getPublisherFromBookId(a).getName()/*"\nPublisher location: "+ dbManagerLevel.getPublisherFromBookId(a).getLocation()*/) ;

    }

    public static void printAuthordetails(int a)
    {
        System.out.println((dbManagerLevel.viewAuthor(a)).getIdauthor() +" "+dbManagerLevel.viewAuthor(a).getFirstname()+" "+dbManagerLevel.viewAuthor(a).getLastname()+" "+dbManagerLevel.viewAuthor(a).getBiography());
    }

    public static void printPublisherdetails(int a)
    {
        System.out.println(dbManagerLevel.viewPublisher(a).getIdpublisher()+" "+dbManagerLevel.viewPublisher(a).getName()+" "+dbManagerLevel.viewPublisher(a).getLocation());
    }


    public static void main(String[] args) {

        dbManagerLevel.fillDB();

        while (true) {
        System.out.println("Welcome to your library application \nChoose an option:\n 1: View list of all the books\n 2: View list of all the authors" +
                "\n 3: View the list of all publishers\n 4: Add book\n 5: Add author\n 6: Add publisher\n");


      //  dbManagerLevel.getBookFromId(5);


            Scanner keyboard = new Scanner(System.in);
            int option = keyboard.nextInt();

          /*  System.out.println("vai");
           int x = keyboard.nextInt();
           dbManagerLevel.getSurnameAuthorFromBookId(x); */

            InputStreamReader leggistringa = new InputStreamReader(System.in);
            BufferedReader buffertastiera = new BufferedReader(leggistringa);

            if (option == 1) {

                printBookList(); //stampo la lista di libri

                System.out.println("Select the ID number of the book that you want to view, or click 0 to return to choose another option:");
                int a = keyboard.nextInt();
                if (a == 0) {continue;}

                printBookdetails(a); //stampo tutti i dettagli del libro selezionato

                System.out.println("Choose:\n 1 (DELETE BOOK)\n 2 (SET QUANTITY)\n 3(INCREASE QUANTITY)\n 4 (DECREASE QUANTITY)\n 5 (VIEW AUTHOR)\n 6 (VIEW PUBLISHER) 0(RETURN TO THE MAIN PAGE\n");
                int b = keyboard.nextInt();
                if (b == 1) {
                    dbManagerLevel.removeBook(a);
                    System.out.println("Book deleted");
                }
                else if (b == 2){
                    System.out.println("Insert the new quantity");
                    int c = keyboard.nextInt();
                    dbManagerLevel.updateQuantity(c,a);
                }
                else if (b == 3){
                    //database.increaseQuantity(a);
                    dbManagerLevel.increaseQuantity(a);
                }
                else if (b == 4){
                   // database.decreaseQuantity(a);
                    dbManagerLevel.decreaseQuantity(a);
                }/*
                else if (b == 5){
                    authorMethods.makeJDBCConnection();
                    System.out.println("Insert the id of the author you want to view");
                    int x = keyboard.nextInt();
                    authorMethods.viewAuthor(x);
                    authorMethods.closeConnection();
                }
                else if (b == 6){
                    publisherMethods.makeJDBCConnection();
                    System.out.println("Insert the id of the publisher you want to view");
                    int y = keyboard.nextInt();
                    publisherMethods.viewPublisher(y);
                    publisherMethods.closeConnection();
                } */
                else if (b == 0){
                    continue;
                }

                else {System.out.println("ERROR");
                continue;
                }



        }

        if (option == 2) {

            printAuthorList();

            System.out.println("Select the ID number of the author that you want to view, or click 0 to return to choose another option:");
            int a = keyboard.nextInt();
            if (a == 0){ continue;}
            printAuthordetails(a);

            System.out.println("Click 1 if you want to delete the author, 0 to return to the home page");
            int b = keyboard.nextInt();
            if (b == 0){ continue;}
            if (b == 1){
               // database.removeAuthor(a);
                dbManagerLevel.removeAuthor(a);
            }else { System.out.println("ERROR");}
        }

        if (option == 3) {

            printpublisherList();

            System.out.println("Insert the publisher id that you want to view or press 0 to return to the home page");

            int a = keyboard.nextInt();
            if (a == 0){continue;}
            printPublisherdetails(a);

            System.out.println("Clicl 1 if you want to delete the selected publisher, 0 to return to the home page");
            int b = keyboard.nextInt();

            if (b == 1){
            //database.removePublisher(a);
            dbManagerLevel.removePublisher(a);
            }

            if (b == 0)
            {
                continue;
            }
        }


        if (option == 4) {

            System.out.println("Insert the id of the book");
            int idbook = keyboard.nextInt();

            if(dbManagerLevel.checkbook(idbook))
            {
                System.out.println("ERROR: the book ID is used. Select another id and retry to insert the book\n");
                continue;
            }

            System.out.println("Insert the title");
            String title = null;
            try {
                title = buffertastiera.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Insert the id of the author");
            int idauthor = keyboard.nextInt();


            if(!dbManagerLevel.check(idauthor))
            {
                System.out.println("ERROR, the author is not present, please insert the author before");
                continue;
            }

            System.out.println("Insert the price");
            float price = keyboard.nextFloat();

            System.out.println("Insert the category");
            String category = keyboard.next();

            System.out.println("Insert the quantity");
            int quantity = keyboard.nextInt();

            System.out.println("Insert the publication year");
            int year = keyboard.nextInt();

            System.out.println("Insert the number of the pages");
            int numpages = keyboard.nextInt();

            System.out.println("Insert the id of the publisher");
            int id_pub = keyboard.nextInt();
            if(!dbManagerLevel.checkpub(id_pub))
            {
                System.out.println("ERROR, the author is not present, please insert the author before");
                continue;
            }

            book booktoadd = new book(idbook,title,price,category,numpages,quantity,id_pub,year);
           // database.addBook(booktoadd);
            dbManagerLevel.addBook(booktoadd);
            //database.fillbookhasauthor(idbook,idauthor);
            dbManagerLevel.fillbookhasauthor(idbook,idauthor,id_pub);

        }


        if (option == 5) {

            System.out.println("Insert the id of the author");
            int idauthor = keyboard.nextInt();

            System.out.println("Insert name:");
            String firstname = keyboard.next();

            System.out.println("Insert surname:");
            String lastname = keyboard.next();

            System.out.println("Write a short biography:");
            String biography = keyboard.next();

            Author authortoadd = new Author(idauthor,firstname,lastname,biography);
           // database.addAuthor(authortoadd);
            dbManagerLevel.addAuthor(authortoadd);


        }

        if (option == 6) {

            System.out.println("Insert the id of the publisher");
            int idpublisher = keyboard.nextInt();

            System.out.println("Insert name:");
            String name = keyboard.next();

            System.out.println("Insert location:");
            String location = keyboard.next();

            Publisher publishertoadd = new Publisher(idpublisher,name,location);
           // database.addPublisher(publishertoadd);
            dbManagerLevel.addPublisher(publishertoadd);

        }
    }
}
}
