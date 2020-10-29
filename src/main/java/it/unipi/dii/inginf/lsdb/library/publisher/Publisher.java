package it.unipi.dii.inginf.lsdb.library.publisher;

public class Publisher {
    private  int idpublisher;
    private String name;
    private String location;

    public  Publisher(String name)
    {
        this.name = name;
    }

    public  Publisher(int idpublisher,String name)
    {
        this(name);
        this.idpublisher = idpublisher;
    }

    public Publisher(int idpublisher, String name, String location)
    {
        this(idpublisher,name);
        this.location = location;
    }


    public int getIdpublisher() {
        return idpublisher;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

}
