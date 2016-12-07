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

class Fumador{
	
	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader brIn = null;
		PrintWriter pwOut = null;
		DataInputStream disIn = new DataInputStream(System.in);
		
		Fumador cliente1 = new Fumador(1);
		
		try{
			
			//socket = new Socket("127.0.0.1", 50006);
			socket = new Socket("190.38.247.224", 50006);
			brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pwOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); 
			
		} catch (UnknownHostException e) {  
			System.err.println("No conozco al host: " + "127.0.0.1");
        } catch (IOException e) {
            System.err.println("Error de E/S para la conexion con: " + "127.0.0.1");
        }catch (NullPointerException e) {
            System.err.println("Error de E/S para la conexion con: " + "127.0.0.1");
        }
		
	}	
	
	private int tipo;
	
	public Fumador(int tipo){
		this.tipo = tipo;
	}
	
	public void fumar(){
		
		System.out.println("Fumador"+this.tipo+" esta fumando.");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int solicitarItem(BancoThread banco)
	{
		
		if (banco.getTipo() != this.tipo)
			return 1;
		else
			return 0;
	}

}
