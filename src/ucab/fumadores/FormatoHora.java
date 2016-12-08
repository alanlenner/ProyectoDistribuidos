package ucab.fumadores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoHora {
	//Obtiene la hora del sistema y la convierte al formato HH:mm:ss
	public static String horaActual(){
		DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		Date fecha = new Date();
		return formatoHora.format(fecha);
	}

}
