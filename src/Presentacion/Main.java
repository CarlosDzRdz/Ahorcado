package Presentacion;

import DAO.Dao;
import DAO.factory.Conn;
import DAO.factory.JMysql;
import Negocio.LogDb;
import Presentacion.Obeserver.Jugador;
import Presentacion.Obeserver.Tablero;
import Presentacion.juego.Juego;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Dao dao = new Dao();
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        LogDb negocio = new LogDb();
        Juego Juego = new Juego();

        //JMysql.getInstance().conectar();
        //JMysql.getInstance().Desconectar();

        //Aqui creamos un objeto de tipo juego para jugar al ahorcado


        Juego.jugar();
        tablero.attach(jugador);
        tablero.attach(negocio);
        tablero.play();

    }//Fin main
}