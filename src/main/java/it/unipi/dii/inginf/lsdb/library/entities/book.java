package it.unipi.dii.inginf.lsdb.library.entities;

public class book  {

    private int idbook;
    private String title;
    private float price;
    private String category;
    private int numpages;
    private int quantity;
    private int pub_id;
    private int pub_Year;


    /* COSTRUTTORE */


    public book(int idbook, String title){
        this.idbook = idbook;
        this.title = title;
    }

    public book(int idbook, String title, float price, String category, int numpages, int quantity, int pub_id, int pub_Year ){
        this(idbook, title);
        this.price = price;
        this.category = category;
        this.numpages = numpages;
        this.quantity = quantity;
        this.pub_id = pub_id;
        this.pub_Year = pub_Year;

    }

    //METODI SETTER
    public void setTitle(String title){ this.title = title;}
    public void setPrice(float price){this.price = price;}
    public void setCategory(String category){this.category = category;}
    public void setNumpages(int numpages){this.numpages = numpages;}
    public void setQuantity(int quantity){this.quantity = quantity;}
    public void setPub_id(int pub_id){this.pub_id = pub_id;}
    public void setPub_Year(int pub_year){this.pub_Year = pub_year;}
    public void setIdbook(int idbook){this.idbook = idbook;}

    //METODI GETTER
    public String getTitle(){return title;}
    public float getPrice(){return price;}
    public String getCategory(){return category;}
    public int getNumpages(){return numpages;}
    public int getQuantity(){return quantity;}
    public int getPub_id(){return pub_id;}
    public int getPub_Year() { return pub_Year;}
    public int getIdbook() {return idbook;}

}
