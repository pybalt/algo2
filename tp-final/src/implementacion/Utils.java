package implementacion;

class Utils {
    class Fecha {

        static boolean fechaValida(int anio,int mes,int dia){
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

        private static String anioActual(){
            return String.valueOf(java.time.Year.now().getValue());
        }
    }

    static class Periodo { 

        static String periodo(int anio, int mes) {
            return String.valueOf(anio) + String.format("%02d", mes);
        }

        static String periodo(String anio, String mes){
            return anio + String.format("%02d", Integer.parseInt(mes));
        }
        
        static int[] descomponerPeriodo(String periodo) {
            if (periodo == null || periodo.length() != 6) {
                throw new IllegalArgumentException("Formato periodo inválido");
            }
            
            int anio = Integer.parseInt(periodo.substring(0, 4));
            int mes = Integer.parseInt(periodo.substring(4, 6));
            return new int[]{anio, mes};
        }
    }
}
