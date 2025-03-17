package Presentacion;

import Negocio.LogDb;
import Presentacion.Obeserver.Jugador;
import Presentacion.Obeserver.Tablero;

import javax.swing.*;

public class Main {
    static Tablero tablero;

    public static void main(String[] args) throws InterruptedException {
        boolean seguirJugando = true;

        while (seguirJugando) {
            // Preguntar qué base de datos usar y pasar la opción como parámetro
            String dbSeleccionada = seleccionarBaseDeDatos();
            if (dbSeleccionada == null) {
                System.out.println("No se seleccionó una base de datos válida. Saliendo del juego.");
                System.exit(0);
            }

            iniciarJuego(dbSeleccionada);
            tablero.play();
            seguirJugando = menuFinPartida();
        }

        System.out.println("¡Gracias por jugar!");
        System.exit(0);
    }

    private static void iniciarJuego(String dbSeleccionada) throws InterruptedException {
        tablero = new Tablero();

        // Crear instancia de LogDb y pasar la base de datos seleccionada
        LogDb negocio = new LogDb(dbSeleccionada);

        // Obtener la conexión desde la capa de negocio
        negocio.inicializarConexion();

        // Conectar los observadores
        Jugador jugador = new Jugador();
        tablero.attach(jugador);
        tablero.attach(negocio);
    }

    private static String seleccionarBaseDeDatos() {
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

        switch (seleccion) {
            case 0:
                return "MySQL";
            case 1:
                return "SQLite";
            default:
                return null;
        }
    }

    private static boolean menuFinPartida() {
        String[] opciones = {"Jugar de nuevo", "Salir"};

        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Qué desea hacer?",
                "Fin de la partida",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        return seleccion == 0;
    }
}
