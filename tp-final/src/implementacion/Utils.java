package implementacion;

public class Utils {
    public static class Fecha {

        public static boolean fechaValida(int anio,int mes,int dia){
            if (anio < 1900 || anio > 2100) return false;
            if (mes < 1 || mes > 12) return false;
            if (dia < 1 || dia > diasEnMes(anio, mes)) return false;
            return true;
        }
        private static int diasEnMes(int anio, int mes) {
            int[] diasMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (mes == 2 && esBisiesto(anio)) {
                return 29;
            }
            return diasMes[mes - 1];
        }
        
        private static boolean esBisiesto(int anio) {
            return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
        }
    }

    public static class Periodo { 

        public static String periodo(int anio, int mes) {
            return String.valueOf(anio) + String.format("%02d", mes);
        }

        public static String periodo(String anio, String mes){
            return anio + String.format("%02d", Integer.parseInt(mes));
        }
        
        public static int[] descomponerPeriodo(String periodo) {
            if (periodo == null || periodo.length() != 6) {
                return null;
            }
            
            int anio = Integer.parseInt(periodo.substring(0, 4));
            int mes = Integer.parseInt(periodo.substring(4, 6));
            return new int[]{anio, mes};
        }
        public static int obtenerMes(String periodo){
            return descomponerPeriodo(periodo)[1];
        }
        public static int obtenerAnio(String periodo){
            return descomponerPeriodo(periodo)[0];
        }
    }
}
