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
		private String tipoFumador;
		private String ingPuesto;
		private String ingEncontrado;
		private int[] carrito = {0,0,0};
		private FormatoHora hora = new FormatoHora();
		private XML xml = new XML();
		//private Traza traza = new Traza();
		
		//Comentar..
		public ManejoHilos(Socket socket, int nroConexion, BancoThread banco1, BancoThread banco2, BancoThread banco3){
			this.banco1 = banco1;
			this.banco2 = banco2;
			this.banco3 = banco3;
			this.socket = socket;
			this.nroConexion = nroConexion;
		}
		
		//Comentar..
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
						this.ingPuesto = ponerIngrediente(bancoAgregar);
						//dataOut.println("imprimir&"+hora.horaActual()+"&Vendedor&Puso "+ this.ingPuesto);
					}	
					
					if (!line.equals("VendedorAct") && !line.equals("ok&")){
						
						this.tipoFumador = (line.split("&"))[0];
						this.carrito[0] = Integer.parseInt((line.split("&"))[1]);
						this.carrito[1] = Integer.parseInt((line.split("&"))[2]);
						this.carrito[2] = Integer.parseInt((line.split("&"))[3]);
					
						if (tipoFumador.equals("FumadorTabaco")){
							//dataOut.println("imprimir&"+hora.horaActual()+"&FumadorTabaco&Comenzo a buscar!");
							//dataOut.flush();
							buscarIngrediente(1);
							//dataOut.println("imprimir&"+hora.horaActual()+"&FumadorTabaco&Encontro "+this.ingEncontrado);
							//dataOut.flush();				
							System.out.println("FumadorTabaco tiene: papel("+carrito[1]+") y fosforo("+carrito[2]+")");
							
						} else if (tipoFumador.equals("FumadorPapel")){
							//dataOut.println("imprimir&"+hora.horaActual()+"&FumadorPapel&Comenzo a buscar!");
							//dataOut.flush();
							buscarIngrediente(2);
							//dataOut.println("imprimir&"+hora.horaActual()+"&FumadorPapel&Encontro "+this.ingEncontrado);
							//dataOut.flush();
							System.out.println("FumadorPapel tiene: tabaco("+carrito[0]+") y fosforo("+carrito[2]+")");
							
						} else if (tipoFumador.equals("FumadorFosforos")){
							//dataOut.println("imprimir&"+hora.horaActual()+"&FumadorFosforos&Comenzo a buscar!");
							//dataOut.flush();
							buscarIngrediente(3);
							//dataOut.println("imprimir&"+hora.horaActual()+"&FumadorFosforos&Encontro "+this.ingEncontrado);
							//dataOut.flush();
							System.out.println("FumadorFosforos tiene: tabaco("+carrito[0]+") y papel("+carrito[1]+")");
						}
						
						if (carritoLleno()){
							//dataOut.println("imprimir&"+hora.horaActual()+"&"+this.tipoFumador+"&Ha comenzado a fumar!");
							//dataOut.flush();
							fumar();
							resetCarrito();
						}
						
						//xml.imprimir("Fumadores", hora.horaActual(), tipoFumador, "Tiene: Tabaco("+carrito[0]+"), Papel("+carrito[1]+") y fosforo("+carrito[2]+")");
						//dataOut.println("imprimir&"+hora.horaActual()+"&"+this.tipoFumador+
						//		"&Tiene: Tabaco("+carrito[0]+"), Papel("+carrito[1]+") y fosforo("+carrito[2]+")");
						//dataOut.flush();
						dataOut.println(this.carrito[0]+"&"+this.carrito[1]+"&"+this.carrito[2]);
						//dataOut.flush();
						
					}
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Comentar..
		public synchronized String ponerIngrediente(int banco){
			
			if (banco == 1){
				this.banco1.agregarItem();
				System.out.println("El vendedor puso TABACO");
				System.out.println("------------------------");
				//xml.imprimir("Vendedor", hora.horaActual(), "Vendedor", "El vendedor puso TABACO");
				stockBancos();
				return "TABACO";
			} else if (banco == 2){
				this.banco2.agregarItem();
				System.out.println("El vendedor puso PAPEL");
				System.out.println("------------------------");
				//xml.imprimir("Vendedor", hora.horaActual(), "Vendedor", "El vendedor puso PAPEL");
				stockBancos();
				return "PAPEL";
			} else if (banco == 3){
				this.banco3.agregarItem();
				System.out.println("El vendedor puso FOSFOROS");
				System.out.println("------------------------");
				//xml.imprimir("Vendedor", hora.horaActual(), "Vendedor", "El vendedor puso FOSFOROS");
				stockBancos();
				return "FOSFOROS";
			} else {
				return "";
			}
			
		}
		
		//Comentar..
		public void stockBancos(){
			
			System.out.println("STOCK DE BANCOS:");
			System.out.println("Tabaco - " + this.banco1.getCantidad());
			System.out.println("Papel - " + this.banco2.getCantidad());
			System.out.println("Fosforos - " + this.banco3.getCantidad());
			System.out.println("\n");
			
		}
		
		//Comentar
		public synchronized  void buscarIngrediente(int ingredientePropio)
		{
			int encontro = 0;
			//xml.imprimir("Fumadores", hora.horaActual(), tipoFumador,"Comenzo a buscar!");
			
			while (encontro != 1){
				encontro = buscarEnBancos(ingredientePropio);
			}
			
		}
		
		//Comentar
		public synchronized int buscarEnBancos(int ingredientePropio){
				
			int entroEnBanco = 0;
			
			if((ingredientePropio != this.banco1.getTipo()) && (this.banco1.vacio() == 0) && (this.carrito[0] == 0)){
				this.banco1.quitarItem();
				this.carrito[0] = 1;
				this.ingEncontrado = "TABACO";
				//xml.imprimir("Fumadores", hora.horaActual(), tipoFumador,"Encontro Tabaco..");
				entroEnBanco = 1;
			} else if ((ingredientePropio != this.banco2.getTipo()) && (this.banco2.vacio() == 0) && (this.carrito[1] == 0)){
				this.banco2.quitarItem();
				this.carrito[1] = 1;
				this.ingEncontrado = "PAPEL";
				//xml.imprimir("Fumadores", hora.horaActual(), tipoFumador,"Encontro Papel..");
				entroEnBanco = 1;
			} else if ((ingredientePropio != this.banco3.getTipo()) && (this.banco3.vacio() == 0) && (this.carrito[2] == 0)){
				this.banco3.quitarItem();
				this.carrito[2] = 1;
				this.ingEncontrado = "FOSFORO";
				//xml.imprimir("Fumadores", hora.horaActual(), tipoFumador,"Encontro Fosforo..");
				entroEnBanco = 1;
			}
			
			return entroEnBanco;
			
		}

		//Comentar
		public boolean carritoLleno(){
			
			if((this.carrito[0] == 1) && (this.carrito[1] == 1) && (this.carrito[2] == 1)){
				return true;
			} else {
				return false;	
			}
			
		}
		
		public void resetCarrito(){
			this.carrito[0] = 0;
			this.carrito[1] = 0;
			this.carrito[2] = 0;
			
			if(this.tipoFumador.equals("FumadorTabaco")){
				this.carrito[0] = 1;
			} else if (this.tipoFumador.equals("FumadorPapel")){
				this.carrito[1] = 1;
			} else if(this.tipoFumador.equals("FumadorFosforo")){
				this.carrito[2] = 1;
			}
		}
		
		public void fumar(){
			
			try {
				
				System.out.println(":):):):):):):):):):):):):):):):):):):):):):):):):):):):):):):):)");
				System.out.println("//////////////////////////////////////////////////////////////\n\n");
				System.out.println("\t\tEnrolando el TABACO...");
				System.out.println("\t\tPrendiendo el CIGARRO...");
				System.out.println("\t\tRELAX :)\n\n");
				System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
				System.out.println(":):):):):):):):):):):):):):):):):):):):):):):):):):):):):):):):)\n\n");
				//xml.imprimir("Fumadores", hora.horaActual(), tipoFumador, "Ha comenzado a fumar!");
				
				Thread.sleep(10000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
