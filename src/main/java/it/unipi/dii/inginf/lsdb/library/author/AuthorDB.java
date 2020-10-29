package it.unipi.dii.inginf.lsdb.library.author;

import java.util.ArrayList;


public interface AuthorDB {

    Author getAuthorFromBookId(int id);
    boolean check(int idauthor);
    void addAuthor(Author newauthor);
    ArrayList<Author> ReadListAuthor();
    Author viewAuthor(int idAuthor);
    void removeAuthor(int idauthor);

}
