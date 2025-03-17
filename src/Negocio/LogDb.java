package Negocio;

import DAO.factory.Conn;
import DAO.factory.ConnFactory;
import Presentacion.Obeserver.Observer;
import Presentacion.Obeserver.Tablero;
import com.google.gson.Gson;

public class LogDb implements Observer {
    private Conn database;
    private String errMsg;
    private String dbSeleccionada; // Variable para almacenar la base de datos elegida

    public LogDb(String dbSeleccionada) {
        this.dbSeleccionada = dbSeleccionada;
    }

    public void inicializarConexion() {
        // Solicitar la conexión a la capa DAO
        this.database = ConnFactory.obtenerConexion(dbSeleccionada);

        // Conectar y limpiar la base de datos
        if (database != null) {
            database.conectar();
            database.summary();
            database.delete();  // Se borra el contenido de la tabla
        }
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public void update(Tablero tablero) {
        if (database == null) return;

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
        if (database == null) return;

        try {
            database.query("SELECT * FROM tablero");
        } catch (Exception e) {
            setErrMsg(database.getErrMsg());
        }
    }
}
