package Presentacion.Obeserver;

import Presentacion.utilis.Utilis;

import javax.swing.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Tablero {
    private List<Observer> observadores;
    private String palabraSecreta;
    private StringBuilder espacios;
    private int intentosRestantes;
    private static final String[] PALABRAS = {"Mexico", "Java", "Docker", "GitHub", "SpringBoot", "BaseDatos", "Servidor", "Internet", "Framework", "Seguridad"};
    private int oportunidades;
    private StringBuilder letrasIngresadas;
    private String head, body, lefta, righta, leftf, rightf;
    String [] tablero;

    public Tablero() {
        this.observadores = new ArrayList<>();
        this.intentosRestantes = 6;
        Random random = new Random();
        this.palabraSecreta = PALABRAS[random.nextInt(PALABRAS.length)];
        this.espacios = new StringBuilder("_".repeat(palabraSecreta.length()));
        this.oportunidades = 0;
        this.letrasIngresadas = new StringBuilder();
        this.head = " "; this.body = " "; this.lefta = " "; this.righta = " "; this.leftf = " "; this.rightf = " ";
        this.tablero = new String[] {head, body, lefta, righta, leftf, rightf};
    }

    public void attach(Observer observer) {
        observadores.add(observer);
    }

    private void notificarObservadores(String estado) {
        for (Observer observer : observadores) {
            observer.update(this);
        }
    }

    public void dibujarMonito() {
        switch (oportunidades) {
            case 1 -> tablero[0] = "O"; // Cabeza
            case 2 -> tablero[1] = "|"; // Cuerpo
            case 3 -> tablero[2] = "/"; // Brazo izquierdo (de frente)
            case 4 -> tablero[3] = "\\"; // Brazo derecho (de frente)
            case 5 -> tablero[4] = "/"; // Pierna izquierda (de frente)
            case 6 -> tablero[5] = "\\"; // Pierna derecha (de frente)
        }

        // Imprime el estado actual del monito
        System.out.println("      ~");
        System.out.println("      |");
        System.out.println("      " + tablero[0]);
        System.out.println("     " + tablero[2] + tablero[1] + tablero[3]);
        System.out.println("     " + tablero[4] + " " + tablero[5]);

        // Muestra los espacios de la palabra
        for (int i = 0; i < espacios.length(); i++) {
            System.out.print(" " + espacios.charAt(i));
        }
        System.out.println();
    }

    public void play() {

        dibujarMonito();

        Scanner sc = new Scanner(System.in);

        while (intentosRestantes > 0 && espacios.toString().contains("_")) {
            System.out.println("\nIntentos restantes: " + intentosRestantes);
            System.out.println("Ingrese una letra: ");
            char letra = sc.next().charAt(0);

            // Verificamos si la letra ya fue ingresada usando StringBuilder
            if (letrasIngresadas.toString().indexOf(letra) >= 0) {
                intentosRestantes--;
                oportunidades++;
            } else {
                letrasIngresadas.append(letra); // Agregar la letra al StringBuilder
                boolean letraValida = Utilis.validarLetra(palabraSecreta, espacios, letra);

                if (!letraValida) {
                    oportunidades++;
                    intentosRestantes--;
                }
            }

            dibujarMonito();
            notificarObservadores("Palabra actual: " + espacios);
        }
    }

    public void winner() {
        if (!espacios.toString().contains("_")) {
            System.out.println("\n¡Ganaste! La palabra era: " + palabraSecreta);
        } else if (intentosRestantes == 0) {
            System.out.println("\n¡Perdiste! La palabra era: " + palabraSecreta);
        }
    }

    public String[] getTablero() {
        return tablero;
    }

    public void setTablero(String[] tablero) {
        this.tablero = tablero;
    }

    public int getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(int oportunidades) {
        this.oportunidades = oportunidades;
    }

    public StringBuilder getLetrasIngresadas() {
        return letrasIngresadas;
    }

    public void setLetrasIngresadas(StringBuilder letrasIngresadas) {
        this.letrasIngresadas = letrasIngresadas;
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    public void setPalabraSecreta(String palabraSecreta) {
        this.palabraSecreta = palabraSecreta;
    }

}
