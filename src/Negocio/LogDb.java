package Negocio;

import DAO.factory.JMysql;
import DAO.factory.JSqlite;
import DAO.factory.Conn;
import Presentacion.Obeserver.Observer;
import Presentacion.Obeserver.Tablero;
import com.google.gson.Gson;

import javax.swing.*;

public class LogDb implements Observer {
    private Conn database;
    private String errMsg;

    public LogDb() {
        this.database = seleccionarBaseDeDatos();
        this.database.conectar();
        this.database.delete(); // Borrar datos al iniciar el juego
    }

    private Conn seleccionarBaseDeDatos() {
        String[] opciones = {"MySQL", "SQLite", "Cancelar"};

        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Qué base de datos desea utilizar?",
                "Bases de datos disponibles",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == 2) { // Cancelar
            System.out.println("El usuario canceló la selección.");
            System.exit(0);
        }

        return (seleccion == 0) ? JMysql.getInstance() : JSqlite.getInstance();
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public void update(Tablero tablero) {
        try {
            Gson gson = new Gson();
            String tableroJson = gson.toJson(tablero.getTablero());

            database.add(
                    tablero.getPalabraSecreta(),
                    tablero.getLetrasIngresadas().toString(),
                    tablero.getOportunidades(),
                    tableroJson
            );
        } catch (Exception e) {
            setErrMsg(database.getErrMsg());
        }
    }

    public void showRun() {
        try {
            database.query("SELECT * FROM tablero");
        } catch (Exception e) {
            setErrMsg(database.getErrMsg());
        }
    }
}
