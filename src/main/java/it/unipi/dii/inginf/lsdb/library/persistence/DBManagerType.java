package it.unipi.dii.inginf.lsdb.library.persistence;

public enum DBManagerType {
    SQL, KV;

    protected static DBManager getService (DBManagerType type)
    {
        switch (type)
        {
            case KV:
                return new DBManagerLevel();
            case SQL:
                return new DBManagerSQL();
        }
        return null;
    }
}
