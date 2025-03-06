package Negocio;

import DAO.Dao;
import Presentacion.Obeserver.Observer;
import Presentacion.Obeserver.Tablero;

public class LogDb implements Observer {
    private Dao accesodatos;

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    private String ErrMsg;

    public LogDb(Dao accesodatos) {
        this.accesodatos = accesodatos;
    }

    public LogDb() {
        this(new Dao());
    }

    @Override
    public void update(Tablero tablero) {
        try {
            accesodatos.add(tablero.getPalabraSecreta(), tablero.getLetrasIngresadas().toString(), tablero.getOportunidades(), tablero.getTablero());
        } catch (Exception e) {
            setErrMsg(accesodatos.getErrMsg());
        }
    }

    public void add (){

    }

    public void showRun(){
        try{
            accesodatos.showData();
        } catch (Exception e) {
            setErrMsg(accesodatos.getErrMsg());
        }
    }

}
