package ucab.fumadores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;


public class ManejoHilos extends Thread{
	
		private BancoThread banco1;
		private BancoThread banco2;
		private BancoThread banco3;
		private Socket socket;
		private int nroConexion;
		private int bancoAgregar;
		
		//
		public ManejoHilos(Socket socket, int nroConexion, BancoThread banco1, BancoThread banco2, BancoThread banco3){
			this.banco1 = banco1;
			this.banco2 = banco2;
			this.banco3 = banco3;
			this.socket = socket;
			this.nroConexion = nroConexion;
			
		}
		
		@Override
		public void run(){
			BufferedReader dataIn = null;
			PrintWriter dataOut = null;
			
			try {
				dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            dataOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

				Random random = new Random();
				
				while (true){
					String line = dataIn.readLine();
					
					if (line.equals("VendedorAct")){
						bancoAgregar = random.nextInt(3) + 1;
						ponerIngrediente(bancoAgregar);
					} else if (line == "FumadorTabaco"){
						//
					} else if (line == "FumadorPapel"){
						//
					} else if (line == "FumadorFosforos"){
						//
					}
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//
		public void ponerIngrediente(int banco){
			
			if (banco == 1){
				this.banco1.agregarItem();
				System.out.println("El vendedor puso TABACO");
				System.out.println("////////////////");
				stockBancos();
			} else if (banco == 2){
				this.banco2.agregarItem();
				System.out.println("El vendedor puso PAPEL");
				System.out.println("////////////////");
				stockBancos();
			} else if (banco == 3){
				this.banco3.agregarItem();
				System.out.println("El vendedor puso FOSFOROS");
				System.out.println("////////////////");
				stockBancos();
			}
			
		}
		
		public void stockBancos(){
			
			System.out.println("STOCK DE BANCOS:");
			System.out.println("Tabaco - " + this.banco1.getCantidad());
			System.out.println("Papel - " + this.banco2.getCantidad());
			System.out.println("Fosforos - " + this.banco3.getCantidad());
			System.out.println("\n");
			
		}
}
