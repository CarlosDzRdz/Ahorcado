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
    private String head1 = "", body1 = " ", lefta1 = " ", righta1 = " ", leftf1 = " ", rightf1 = " ";
    private String head, body, lefta, righta, leftf, rightf;
    String [] tablero;

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

    public Tablero() {
        this.observadores = new ArrayList<>();
        this.intentosRestantes = 6;
        Random random = new Random();
        this.palabraSecreta = PALABRAS[random.nextInt(PALABRAS.length)];
        this.espacios = new StringBuilder("_".repeat(palabraSecreta.length()));
        this.oportunidades = 0;
        this.letrasIngresadas = new StringBuilder(); // Inicializamos como StringBuilder
        this.head = "O"; this.body = "|"; this.lefta = "\\"; this.righta = "/"; this.leftf = "\\"; this.rightf = "/";
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

        // Actualiza el estado acumulado en función de las oportunidades
        switch (oportunidades) {
            case 1 -> head1 = tablero[0];
            case 2 -> body1 = tablero[1];
            case 3 -> lefta1 = tablero[2];
            case 4 -> righta1 = tablero[3];
            case 5 -> leftf1 = tablero[4];
            case 6 -> rightf1 = tablero[5];
        }

        // Imprime el estado acumulado del monito
        System.out.println("      ~");
        System.out.println("      |");
        System.out.println("      " + head1);
        System.out.println("     " + righta1 + body1 + lefta1);
        System.out.println("     " + rightf1 + " " + leftf1);

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

}
