package it.unipi.dii.inginf.lsdb.library.persistence;

public class DBManagerFactory
{

    private DBManagerFactory(){}
    public static DBManagerFactory create(){return new DBManagerFactory();} //ritorna un'istanza di se stessa

    public DBManager getService(DBManagerType type) //crea un'istanza della classe SQL o KV
    {
        return DBManagerType.getService(type);
    }

}
