
package Presentacion.Obeserver;

public class Jugador implements Observer {

    @Override
    public void update (Tablero tablero) {
        tablero.winner();
    }
}

/*
package Presentacion.Obeserver;

public class Jugador implements Observer {
    @Override
    public void actualizar(String estado) {
        //System.out.println("Estado actualizado: " + estado);
    }
}
*/