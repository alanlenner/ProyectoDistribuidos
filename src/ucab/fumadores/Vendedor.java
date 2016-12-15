package ucab.fumadores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Vendedor{
	
	private Socket socket = null;

	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader dataIn = null;
		PrintWriter dataOut = null;
		XML xml = new XML();
		Vendedor vendedor = new Vendedor();
		
		try{
			socket = new Socket("127.0.0.1", 50006);
			//socket = new Socket("JAMS", 50006);
			
			dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			
		} catch (UnknownHostException e) {  
			System.err.println("No conozco al host: " + "127.0.0.1");
        } catch (IOException e) {
            System.err.println("Error de E/S1 para la conexion con: " + "127.0.0.1");
        }catch (NullPointerException e) {
            System.err.println("Error de E/S2 para la conexion con: " + "127.0.0.1");
        }
		
		if (socket != null && dataOut != null && dataIn != null) {
			
			// En el momento de la conexion se envia un mensaje notificando la misma.
			dataOut.println("conexionVendedor&");	
			
			while(true){
				
				boolean escuchando = true;
				String[] entrada = {"","",""};
				int[] ingredientes = {0,0};
				
				try {
					
					while(escuchando == true){
						
						/*
						 *	Posibles mensajes entrantes:
						 *	1. [ okVendedor ]
						 *	2. [ ingPuestos , horaServidor , Ingrediente1 e Ingrediente2 ]
						 *	3. [ solicitud , horaServidor , Ingrediente solicitado ]
						 */
						entrada = dataIn.readLine().split("&");
						
						/*
						 *  "ingPuestos": El servidor ha recibido la solicitud del cliente de poner
						 *  dos ingredientes y ha sumado una (1) unidad a la cantidad total de cada
						 *  banco de correspondiente indicando la hora en la que se realizo la accion;
						 *  el cliente imprime en el XML los datos pertinentes de la accion realizada
						 *  y se da un tiempo de 5 segundos de descanso al vendedor.
						 *  
						 *  "okVendedor": El servidor envia un mensaje de confirmacion conexion y el
						 *  cliente responde con la primera peticion de "poner ingredientes". 
						 *  OJO: este mensaje solo se recibe una vez!
						 */
						if (entrada[0].equals("ingPuestos")){
							xml.imprimir("traza_vendedor", entrada[1], "Vendedor", "Ha puesto "+ entrada[2]);
							//Thread.sleep(10000);
							escuchando = false;
							
						} else if (entrada[0].equals("okVendedor")){
							ingredientes = vendedor.ingredientesAPoner();
							dataOut.println("vendedor&" + ingredientes[0] + "&" + ingredientes[1]);
							escuchando = false;
							
						} else if (entrada[0].equals("solicitud")){
							xml.imprimir("traza_vendedor", entrada[1], "Vendedor", "Le han solicitado "+entrada[2]+" al vendedor");
							escuchando = false;
						}
						
					}
					
					/*
					 * Manda al servidor un mensaje con los ingredientes que se pondran.
					 * 
					 * Solo se pasara por alto en el momento de confirmacion de conexion, de resto
					 * SIEMPRE se ejecutara.
					 * 
					 * Mensaje: "vendedor&ingrediente1&ingrediente2"
					 * 	
					 * -Los mensajes se separaran con el simbolo "&".
					 * -El primer bloque del mensaje es para que el servidor pueda filtrar.
					 */
					if(!entrada[0].equals("okVendedor")){
						ingredientes = vendedor.ingredientesAPoner();
						dataOut.println("vendedor&" + ingredientes[0] + "&" + ingredientes[1]);
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
		}
			
	}
	
	/*
	 * Constructor de la clase.
	 */
	public Vendedor(){
		//
	}
	
	/*
	 * Devuelve un arreglo con los dos ingredientes que se agregaran.
	 * 
	 * La seleccion de ingredientes es totalmente aleatoria.
	 */
	public int[] ingredientesAPoner(){
		Random random = new Random();
		Random random2 = new Random();
		
		int[] ingredientes = {0,0};
		
		ingredientes[0] = random.nextInt(3) + 1;
		ingredientes[1] = random2.nextInt(3) + 1;
		
		return ingredientes;
	}
		
}
