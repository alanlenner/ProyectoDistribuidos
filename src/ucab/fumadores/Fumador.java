package ucab.fumadores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Fumador{
	
	private Socket socket = null;
	private int tipo;
	//[tabaco, papel, fosforos]
	private int[] ingredientes = {0,0,0};
	
	
	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader dataIn = null;
		PrintWriter dataOut = null;
		XML xml = new XML();
		
		//Cambiar numero para c/cliente
		Fumador fumador = new Fumador(1);
		
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
			dataOut.println("conexionFumador&");
			
			while(true){
				
				boolean escuchando = true;
				String[] entrada = {"","","",""};
				String ingEncontrado = "";
				
				try	{	
					
					while(escuchando == true){
						
						/*
						 *	Posibles mensajes entrantes:
						 *	1. [ okFumador ]
						 *	2. [ ingEncontrado , horaServidor , fumador, Ingrediente ]
						 */
						entrada = dataIn.readLine().split("&");
						/*
						 *  "ingEncontrado": se recibe un mensaje con el ingrediente encontrado y
						 *  se procede a imprimir en la traza el evento ocurrido, si luego de
						 *  actualizar las cantidades del carrito, este se encuentra LLENO
						 *  se procede a fumar.
						 *  
						 *  "okFumador": El servidor envia un mensaje de confirmacion conexion y el
						 *  cliente responde con la primera peticion de "busqueda de ingredientes". 
						 *  OJO: este mensaje solo se recibe una vez!
						 */
						if(entrada[0].equals("ingEncontrado")){
							
							ingEncontrado = entrada[3];
							fumador.actualizarCarrito(ingEncontrado);
							xml.imprimir("traza_fumador", entrada[1], entrada[2], "Encontro "+entrada[3]+
									" || Tabaco("+fumador.ingredientes[0]+"); "
											+ "Papel("+fumador.ingredientes[1]+"); "
													+ "Fosforo("+fumador.ingredientes[2]+");");
							
							if (fumador.carritoLleno()){
								xml.imprimir("traza_fumador", entrada[1], entrada[2], "Esta fumando");
								fumador.fumar();
								Thread.sleep(15000);
								fumador.resetCarrito();
							}
							
							escuchando = false;
							
						} else if(entrada[0].equals("okFumador")){
							
							if (fumador.tipo == 1){
								dataOut.println("fumador&"+fumador.tipo+"&"+fumador.ingredientes[1]+"&"+fumador.ingredientes[2]);
								
							} else if(fumador.tipo == 2){
								dataOut.println("fumador&"+fumador.tipo+"&"+fumador.ingredientes[0]+"&"+fumador.ingredientes[2]);
								
							} else if(fumador.tipo == 3){
								dataOut.println("fumador&"+fumador.tipo+"&"+fumador.ingredientes[0]+"&"+fumador.ingredientes[1]);
							}
							
							escuchando = false;
							
						}
			
					}
					
					/*
					 * Manda al servidor un mensaje con el tipo de fumador y los ingredientes 
					 * a buscar.
					 * 
					 * Solo se pasara por alto en el momento de confirmacion de conexion, de
					 *  resto SIEMPRE se ejecutara.
					 * 
					 * Mensaje: "fumador&tipo&ingrediente1&ingrediente2"
					 * 	
					 * -Los mensajes se separaran con el simbolo "&".
					 * -El primer bloque del mensaje es para que el servidor pueda filtrar.
					 */
					if(!entrada[0].equals("okFumador")){
						
						if (fumador.tipo == 1){
							dataOut.println("fumador&"+fumador.tipo+"&"+fumador.ingredientes[1]+"&"+fumador.ingredientes[2]);
							
						} else if(fumador.tipo == 2){
							dataOut.println("fumador&"+fumador.tipo+"&"+fumador.ingredientes[0]+"&"+fumador.ingredientes[2]);
							
						} else if(fumador.tipo == 3){
							dataOut.println("fumador&"+fumador.tipo+"&"+fumador.ingredientes[0]+"&"+fumador.ingredientes[1]);
						}
						
					}
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		
	}	
	
	
	public Fumador(int tipo){
		this.tipo = tipo;
		
		if (tipo == 1){
			this.ingredientes[0] = 1;
		} else if (tipo == 2){
			this.ingredientes[1] = 1;
		} else if (tipo == 3){
			this.ingredientes[2] = 1;
		}
		
	}
	
	//Actualiza las cantidades del carrito.
	public void actualizarCarrito(String encontrado){
		
		if(encontrado.equals("TABACO")){
			this.ingredientes[0] = 1;
		} else if(encontrado.equals("PAPEL")){
			this.ingredientes[1] = 1;
		} else if(encontrado.equals("FOSFOROS")){
			this.ingredientes[2] = 1;
		}
		
	}
	
	//Resetea a 0 las cantidades del carrito exceptuando el ingrediente propio.
	public void resetCarrito(){
		this.ingredientes[0] = 0;
		this.ingredientes[1] = 0;
		this.ingredientes[2] = 0;
		
		if (this.tipo == 1){
			this.ingredientes[0] = 1;
		} else if (this.tipo == 2){
			this.ingredientes[1] = 1;
		} else if (this.tipo == 3){
			this.ingredientes[2] = 1;
		}
		
	}
	
	//Indica si el fumador posee los 3 ingredientes necesarios para fumar.
	public boolean carritoLleno(){
				
		if((this.ingredientes[0] == 1) && (this.ingredientes[1] == 1) && (this.ingredientes[2] == 1)){
			return true;
		} else {
			return false;	
		}
		
	}
	
	//Metodo que indica que el fumador esta fumando vlr.
	public void fumar(){
				
		System.out.println("Enrolando el TABACO...");
		System.out.println("Prendiendo el CIGARRO...");
		System.out.println("RELAX :)\n\n");
				
	}
	
	
}
