package DAO.factory;

public interface Conn {
    void conectar();
    void Desconectar();
    void setErrMsg(String msg);
    String getErrMsg();

    void query(String sql);
    void add(String palabraAdivinada, String letras, int intentos, String tablero);
    void delete();
    void update();
}
