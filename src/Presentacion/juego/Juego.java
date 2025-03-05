package Presentacion.juego;

import Presentacion.utilis.Utilis;

import java.util.Scanner;

public class Juego {

    private Scanner sc = new Scanner(System.in);

    public void jugar() {


        StringBuilder espacios = new StringBuilder();
        StringBuilder palabras = new StringBuilder("Mexico");

        String entrada, head = "", body = " ", lefta = " ", righta = " ", leftf = " ", rightf = " ";
        char letra = ' ';
        int oportunidades = 0, aciertos = 0;
        boolean encontrado;

        espacios.setLength(palabras.length());

        for (int i = 0; i < espacios.length(); i++) {
            espacios.setCharAt(i, '_');
        }

        while(oportunidades <= 6 && aciertos != palabras.length()) {
            Utilis.limpiarPantalla();

            System.out.println("Oportunidades restantes: " + (6 - oportunidades));

            //Thread.sleep(1000);

            System.out.println("      ~");
            System.out.println("      |");
            System.out.println("      " + head);
            System.out.println("     "+righta+body+lefta);
            System.out.println("     "+rightf+" "+leftf);

            for (int i = 0; i < espacios.length(); i++) {
                System.out.print(" "+espacios.charAt(i));
            }

            System.out.println();
            System.out.println("Ingrese la letra: ");

            do{
                entrada = sc.nextLine();
            }while(entrada.isEmpty());

            letra = entrada.charAt(0);
            encontrado = false;

            // Comprobar si la letra está en la palabra
            for (int i = 0; i < palabras.length(); i++) {
                if (Character.toLowerCase(letra) == Character.toLowerCase(palabras.charAt(i))) {
                    encontrado = true;
                    // Solo actualizar espacios y aciertos si la letra aún no ha sido revelada
                    if (espacios.charAt(i) == '_') {
                        espacios.setCharAt(i, Character.toUpperCase(letra));
                        aciertos++; // Incrementar aciertos solo si es una nueva coincidencia
                    }
                }
            }

            if (!encontrado) {
                oportunidades++;

                switch (oportunidades) {
                    case 1 -> head = "O";

                    case 2 -> body = "|";

                    case 3 -> lefta = "\\";

                    case 4 -> righta = "/";

                    case 5 -> leftf = "\\";

                    case 6 -> rightf = "/";
                }
            }
        }

        if(palabras.toString().equalsIgnoreCase(espacios.toString())) {
            System.out.println("¡Ganaste! La palabra es: " + palabras);
        }else{
            System.out.println("¡Perdiste! La palabra era: " + palabras);
        }
    }//fin jugar
}//fin clase jugar
