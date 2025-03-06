package DAO.factory;

public class MysqlConnectionFactory implements ConnFactory{

    @Override
    public Conn getConnection() {
        return JMysql.getInstance();
    }
}
