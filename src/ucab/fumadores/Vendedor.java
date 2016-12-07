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
import java.util.Random;

public class Vendedor{
	
	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader brIn = null;
		PrintWriter pwOut = null;
		DataInputStream disIn = new DataInputStream(System.in);
		
		Vendedor vendedor = new Vendedor();
		
		try{
			
			socket = new Socket("127.0.0.1", 50006);
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
	
	private BancoThread banco1;
	private BancoThread banco2;
	private BancoThread banco3;
	private int bancoAgregar;
		
	public Vendedor(){
		//
	}
	
	public void pruebaVendedor(){
		       
		Random random = new Random();
		
		while (true){
			this.bancoAgregar = random.nextInt(3) + 1;
			
			if(bancoAgregar == 1 ){
				banco1.agregarItem();
			} else if (bancoAgregar == 2){
				banco2.agregarItem();
			} else if (bancoAgregar == 3){
				banco3.agregarItem();
			}
			    
			System.out.println("VENDEDOR agrego: "+ this.bancoAgregar);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		    
	}
	
}
