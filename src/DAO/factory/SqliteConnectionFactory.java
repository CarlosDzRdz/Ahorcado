package DAO.factory;

public class SqliteConnectionFactory implements ConnFactory{

    @Override
    public Conn getConnection() {
        return JSqlite.getInstance();
    }
}
