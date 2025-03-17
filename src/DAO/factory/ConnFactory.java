package DAO.factory;

public interface ConnFactory {
    Conn getConnection();
    public static Conn obtenerConexion(String dbSeleccionada) {
        if ("MySQL".equalsIgnoreCase(dbSeleccionada)) {
            return JMysql.getInstance();
        } else if ("SQLite".equalsIgnoreCase(dbSeleccionada)) {
            return JSqlite.getInstance();
        }
        return null;
    }
}
