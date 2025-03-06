package DAO;

import DAO.factory.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.Objects;

public class Dao {

    private  ConnFactory connFactory; // objeeto patter demo
    private  Conn conexion;
    private String ErrMsg;

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    private ConnFactory getConnectionFactory(Database db) {
        if (db == Database.JMysql) {
            return new MysqlConnectionFactory();
        }
        return null;
    } // End connFactory

    private void star(){
        try{
            conexion = connFactory.getConnection();
            conexion.conectar();
            //conexion.Desconectar();
        } catch (Exception e) {
            setErrMsg(conexion.getErrMsg());
        }
    }

    private void close(){
        try{
            conexion.Desconectar();
        } catch (Exception e) {
            setErrMsg(conexion.getErrMsg());
        }
    }

    public void add(String palabraAdivinada, String letras, int intentos, String[] tablero) {
        try {
            Gson gson = new Gson();
            JsonArray jsonArray = new JsonArray();

            // Asegurarnos de que solo las posiciones necesarias estén llenas
            for (int i = 0; i < tablero.length; i++) {
                if (tablero[i] == null) { // Si no se ha usado, guardar como vacío
                    jsonArray.add("");
                } else {
                    jsonArray.add(tablero[i]); // Si ya hay una parte, guardarla
                }
            }

            conexion.add(palabraAdivinada, letras, intentos, gson.toJson(jsonArray));
        } catch (Exception e) {
            setErrMsg(e.getMessage());
        }
    }


    public Dao(Database db) {
        connFactory = getConnectionFactory(db);
        star();
    }

    public Dao() {
        this(Database.JMysql);
    }

    public void showData(){
        try{
            conexion.query("");
        } catch (Exception e) {
            setErrMsg(conexion.getErrMsg());
        }
    }

}
