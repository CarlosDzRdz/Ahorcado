package Presentacion.utilis;

public class Utilis {
    public static boolean validarLetra(String palabraSecreta, StringBuilder espacios, char letra) {
        boolean encontrado = false;
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (Character.toLowerCase(letra) == Character.toLowerCase(palabraSecreta.charAt(i))) {
                encontrado = true;
                if (espacios.charAt(i) == '_') {
                    espacios.setCharAt(i, Character.toUpperCase(letra));
                }
            }
        }
        return encontrado;
    }
}
