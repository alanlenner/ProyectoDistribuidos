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
	
	//Comentar
	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader dataIn = null;
		PrintWriter dataOut = null;
		XML xml = new XML();
		
		//Cambiar numero..
		Fumador fumador = new Fumador(3);
		
		try{
			socket = new Socket("127.0.0.1", 50006);
			//socket = new Socket("190.38.247.224", 50006);
			
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
			
			while(true){
				
				try{	
					if (fumador.tipo == 1){
						dataOut.println("FumadorTabaco&"+fumador.ingredientes[0]+"&"+fumador.ingredientes[1]+"&"+fumador.ingredientes[2]);
					} else if(fumador.tipo == 2){
						dataOut.println("FumadorPapel&"+fumador.ingredientes[0]+"&"+fumador.ingredientes[1]+"&"+fumador.ingredientes[2]);
					} else if(fumador.tipo == 3){
						dataOut.println("FumadorFosforos&"+fumador.ingredientes[0]+"&"+fumador.ingredientes[1]+"&"+fumador.ingredientes[2]);
					}
						
					String[] nuevos = dataIn.readLine().split("&");
					
					if(!nuevos[0].equals("imprimir")){
						//[0] = Tabaco
						fumador.ingredientes[0] = Integer.parseInt(nuevos[0]);
						//[1] = Papel
						fumador.ingredientes[1] = Integer.parseInt(nuevos[1]);
						//[2] = Fosforo
						fumador.ingredientes[2] = Integer.parseInt(nuevos[2]);
					} else {
						//(fuente, hora, responsable, accion)
						xml.imprimir("traza_fumadores", nuevos[1], nuevos[2], nuevos[3]);
					}
					
					Thread.sleep(10000);
						
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}	
	
	//Comentar..
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
	
}
