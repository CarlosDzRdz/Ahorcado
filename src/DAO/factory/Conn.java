package DAO.factory;

public interface Conn {
    void conectar();
    void Desconectar();
    void setErrMsg(String msg);
    String getErrMsg();

    void query(String sql);
    void add(String estado, String json);
    void delete();
    void update();
}
