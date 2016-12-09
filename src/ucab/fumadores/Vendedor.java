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

	//Comentar..
	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader dataIn = null;
		PrintWriter dataOut = null;
		XML xml = new XML();
		
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
				
				int i = 0;
				
				try {
					
					if(i>0){
						dataOut.println("VendedorAct");	
						
						String[] entrada = dataIn.readLine().split("&");
						
						if(entrada[0].equals("imprimir")){
							//(fuente, hora, responsable, accion)
							xml.imprimir("traza_Vendedor", entrada[1], entrada[2], entrada[3]);
							
							dataOut.println("ok&");
						}	
						
					} else{
						dataOut.println("VendedorAct");
					}
					
					Thread.sleep(5000);
					i++;
					
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
	
	public Vendedor(){
		//
	}
		
}
