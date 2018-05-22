package negocio;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Irdevelo
 */
public class Utileria {

    public static String cifrarContrasena(String contrasena) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(contrasena.getBytes());
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {
            stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }

    public static boolean validarCorreo(String correo) {
        String formatoCorreo = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern patron = Pattern.compile(formatoCorreo);
        Matcher matcher = patron.matcher(correo);
        return matcher.matches();
    }

    public static Date convertirFechaNacimiento(LocalDate fechaNacimiento) {
        return java.sql.Date.valueOf(fechaNacimiento);
    }

    public static LocalDate mostrarFechaNacimiento(Date fechaNacimiento) {
        java.sql.Date fechaSql = new java.sql.Date(fechaNacimiento.getTime());
        LocalDate fecha = fechaSql.toLocalDate();
        return fecha;
    }

    public static LocalDate mostrarFecha(Date fechaNacimiento) {
        java.sql.Date fechaSql = new java.sql.Date(fechaNacimiento.getTime());
        LocalDate fecha = fechaSql.toLocalDate();
        return fecha;
    }

    public static String convertirMeses(int mes) {
        String mesConvertido;

        Map<Integer, String> meses = new HashMap<>();
        meses.put(0, "enero");
        meses.put(1, "febrero");
        meses.put(2, "marzo");
        meses.put(3, "abril");
        meses.put(4, "mayo");
        meses.put(5, "junio");
        meses.put(6, "julio");
        meses.put(7, "agosto");
        meses.put(8, "septiembre");
        meses.put(9, "octubre");
        meses.put(10, "noviembre");
        meses.put(11, "diciembre");

        mesConvertido = meses.get(mes);

        return mesConvertido;
    }

    public static Date convertirFecha(LocalDate fecha) {
        return java.sql.Date.valueOf(fecha);
    }

    public static String convertirDia(String diaIngles) {
        String diaEspañol = null;

        switch (diaIngles) {
            case "MONDAY":
                diaEspañol = "Lunes";
                break;

            case "TUESDAY":
                diaEspañol = "Martes";
                break;

            case "WEDNESDAY":
                diaEspañol = "Miercoles";
                break;

            case "THURSDAY":
                diaEspañol = "Jueves";
                break;

            case "FRIDAY":
                diaEspañol = "Viernes";
                break;

            case "SATURDAY":
                diaEspañol = "Sabado";
                break;

            case "SUNDAY":
                diaEspañol = "Domingo";
                break;

        }

        return diaEspañol;
    }

}
