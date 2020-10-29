public class Author {

    private int idauthor;
    private String firstname;
    private String lastname;
    private String biography;

    //COSTRUTTORE

    public Author(String lastname)
    {
        this.lastname = lastname;
    }

    public Author(int idauthor,String lastname)
    {
        this(lastname);
        this.idauthor = idauthor;
    }


    public Author(int idauthor, String firstname, String lastname, String biography)
    {
        this(idauthor,lastname);
        this.firstname = firstname;
        this.biography = biography;
    }


    public void setIdauthor(int idauthor) {
        this.idauthor = idauthor;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getIdauthor() {
        return idauthor;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBiography() {
        return biography;
    }
}
