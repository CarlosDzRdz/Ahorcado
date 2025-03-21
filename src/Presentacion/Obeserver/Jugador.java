
package Presentacion.Obeserver;

public class Jugador implements Observer {

    @Override
    public void update (Tablero tablero) {
        tablero.winner();
    }
}
