package ucab.fumadores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
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

public class Vendedor implements Serializable{
	
	private Socket socket = null;
	private BufferedReader brIn = null;
	private PrintWriter pwOut = null;
	private DataInputStream disIn = new DataInputStream(System.in);
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private BancoThread banco;
	
	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader brIn = null;
		PrintWriter pwOut = null;
		DataInputStream disIn = new DataInputStream(System.in);
		
		Vendedor vendedor = new Vendedor();
		
		try{
			
			socket = new Socket("127.0.0.1", 50006);
			//socket = new Socket("190.38.247.224", 50006);
			brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pwOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            
            vendedor.setSocket(socket);
            vendedor.setBufferedReader(brIn);
            vendedor.setPrintWriter(pwOut);
              
		} catch (UnknownHostException e) {  
			System.err.println("No conozco al host: " + "127.0.0.1");
        } catch (IOException e) {
            System.err.println("Error de E/S1 para la conexion con: " + "127.0.0.1");
        }catch (NullPointerException e) {
            System.err.println("Error de E/S2 para la conexion con: " + "127.0.0.1");
        }
		
		while(true){
		
			vendedor.ponerIngrediente();
			
		}
		
	}
	
	
	public Vendedor(){
		//
	}
	
	public void setBufferedReader(BufferedReader br){
		this.brIn = br;
	}
	
	public void setPrintWriter(PrintWriter pw){
		this.pwOut = pw;
	}
	
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	
	public void ponerIngrediente(){
		
		try {
			
			this.ois = new ObjectInputStream(this.socket.getInputStream());
			
			this.banco = (BancoThread)this.ois.readObject();
			
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		this.banco.agregarItem();
		
		try {
			
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.oos.writeObject(this.banco);
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		    	
		System.out.println("VENDEDOR agrego a "+this.banco.getTipo());
			
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		    
	}
		
}
