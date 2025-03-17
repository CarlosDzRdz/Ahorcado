package Presentacion;

import Negocio.LogDb;
import Presentacion.Obeserver.Jugador;
import Presentacion.Obeserver.Tablero;

public class Main {
    static Tablero tablero = new Tablero();

    public static void main(String[] args) throws InterruptedException {
        boolean seguirJugando = true;

        while (seguirJugando) {
            iniciarJuego();
            tablero.play();
            seguirJugando = menuFinPartida();
        }

        System.out.println("¡Gracias por jugar!");
        System.exit(0);
    }

    private static void iniciarJuego() throws InterruptedException {
        tablero = new Tablero();

        // LogDb maneja internamente la base de datos y la limpieza
        LogDb negocio = new LogDb();

        // Conectar los observadores
        Jugador jugador = new Jugador();
        tablero.attach(jugador);
        tablero.attach(negocio);
    }

    private static boolean menuFinPartida() {
        String[] opciones = {"Jugar de nuevo", "Salir"};

        int seleccion = javax.swing.JOptionPane.showOptionDialog(
                null,
                "¿Qué desea hacer?",
                "Fin de la partida",
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        return seleccion == 0;
    }
}
