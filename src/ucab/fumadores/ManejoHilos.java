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
	private String tipoFumador;
	private String[] ingPuestos = {"",""};
	private String ingEncontrado;
	private int[] carrito = {0,0,0};
	private FormatoHora hora = new FormatoHora();
	private boolean monitorBanco1 = false;
	private boolean monitorBanco2 = false;
	private boolean monitorBanco3 = false;
		
	/*
	 *  Constructor de la clase.
	 *  
	 *  Recibe el socket, los tres bancos y el numero de la conexion.
	 */
	public ManejoHilos(Socket socket, BancoThread banco1, BancoThread banco2, BancoThread banco3){
		this.banco1 = banco1;
		this.banco2 = banco2;
		this.banco3 = banco3;
		this.socket = socket;
	}
		
	//Comentar..
	@Override
	public void run(){
		BufferedReader dataIn = null;
		PrintWriter dataOut = null;
		
		try {
			dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dataOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				
			while (true){
				String entradaRaw = dataIn.readLine();	
				if (entradaRaw != null){	
					String[] entrada = entradaRaw.split("&");
						
					/*
					 * Logica del Vendedor
					 */
					if (entrada[0].equals("conexionVendedor")){	
						dataOut.println("okVendedor&");
						
					} else if (entrada[0].equals("vendedor")){		
						//Se pone UN (1) ingrediente a la vez.
						ponerIngrediente(Integer.parseInt(entrada[1]));
						ponerIngrediente(Integer.parseInt(entrada[2]));
					
						dataOut.println("ingPuestos&"+hora.horaActual()+"&"+this.ingPuestos[0]+" y "+this.ingPuestos[1]);
							
						resetIngPuestos();
					}
						
					/*
					 * Logica del Fumador
					 */
					if (entrada[0].equals("conexionFumador")){	
						dataOut.println("okFumador&");
							
					} else if (entrada[0].equals("fumador")){		
						setTipoFumador(entrada[1]);
						setCarritoFumador(this.tipoFumador, entrada[2], entrada[3]);
						buscarIngrediente(this.tipoFumador);
						dataOut.println("ingEncontrado&"+hora.horaActual()+"&"+this.tipoFumador+"&"+this.ingEncontrado);
						
					} else if(entrada[0].equals("solicitud")){
						//
					}
					
					if (!entrada[0].equals("conexionVendedor")){
						stockBancos();
					}
				}
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//Comentar
	public synchronized void ponerIngrediente(int banco){
			
		try{
			
			while (zonaBloqueada(banco)){
				System.out.println("Banco "+banco+" bloqueado para poner");
				wait();
				System.out.println("Vendedor salio de bloqueo");
			}
				
			if (banco == 1){
				bloquearZona(banco);
				this.banco1.agregarItem();
				if (this.ingPuestos[0].equals("")){
					this.ingPuestos[0] = "Tabaco";
				} else {
					this.ingPuestos[1] = "Tabaco";
				}
				Thread.sleep(15000);
				desbloquearZona(banco);
				notifyAll();
				
			} else if (banco == 2){
				bloquearZona(banco);
				this.banco2.agregarItem();			
				if (this.ingPuestos[0].equals("")){
					this.ingPuestos[0] = "Papel";
				} else {
					this.ingPuestos[1] = "Papel";
				}
				Thread.sleep(15000);
				desbloquearZona(banco);
				notifyAll();
				
			} else if (banco == 3){
				bloquearZona(banco);
				this.banco3.agregarItem();
				if (this.ingPuestos[0].equals("")){
					this.ingPuestos[0] = "Fosforo";
				} else {
					this.ingPuestos[1] = "Fosforo";
				}
				Thread.sleep(15000);
				desbloquearZona(banco);
				notifyAll();
			}
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Comentar
	public synchronized  void buscarIngrediente(String tipo){
			
		int encontro = 0;
		int ingredientePropio = 0;
		
		if (tipo.equals("FumadorTABACO")){
			ingredientePropio = 1;
		} else if (tipo.equals("FumadorPAPEL")){
			ingredientePropio = 2;
		} else if (tipo.equals("FumadorFOSFOROS")){
			ingredientePropio = 3;
		}
		
		while (encontro != 1){
			encontro = buscarEnBancos(ingredientePropio);
		}		
	}
				
	//Comentar
	public synchronized int buscarEnBancos(int ingredientePropio){
		
		int entroEnBanco = 0;
		int[] bancosABuscar = {0,0};
		try{
			
			bancosABuscar = bancosABuscar(ingredientePropio);
			while(zonaBloqueada(bancosABuscar[0]) && zonaBloqueada(bancosABuscar[1])){
				System.out.println("Bancos para buscar bloqueados");
				wait();
				System.out.println("Bancos para buscar DESBLOQUEADOS");
			}
			
			if((ingredientePropio != this.banco1.getTipo()) && (this.banco1.vacio() == 0) &&
					(this.carrito[0] == 0) && !zonaBloqueada(1)){
				bloquearZona(1);
				this.banco1.quitarItem();
				this.carrito[0] = 1;
				this.ingEncontrado = "TABACO";
				entroEnBanco = 1;
				Thread.sleep(15000);
				desbloquearZona(1);
				notifyAll();
				
			} else if ((ingredientePropio != this.banco2.getTipo()) && (this.banco2.vacio() == 0) &&
					(this.carrito[1] == 0) && !zonaBloqueada(2)){
				bloquearZona(2);
				this.banco2.quitarItem();
				this.carrito[1] = 1;
				this.ingEncontrado = "PAPEL";
				entroEnBanco = 1;
				Thread.sleep(15000);
				desbloquearZona(2);
				notifyAll();
				
			} else if ((ingredientePropio != this.banco3.getTipo()) && (this.banco3.vacio() == 0) &&
					(this.carrito[2] == 0) && !zonaBloqueada(3)){
				bloquearZona(3);
				this.banco3.quitarItem();
				this.carrito[2] = 1;
				this.ingEncontrado = "FOSFOROS";
				entroEnBanco = 1;
				Thread.sleep(15000);
				desbloquearZona(3);
				notifyAll();
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}	
		
		return entroEnBanco;
	}
	
	//Comentar..
	public void stockBancos(){
		
		System.out.println(" _______________________________________________");
		System.out.println("|                                               |");
		System.out.println("|                   STOCK BANCOS                |");
		System.out.println("|_______________________________________________|");
		System.out.println("|     TABACO    |       PAPEL     |   FOSFOROS  |");
		System.out.println("|_______________|_________________|_____________|");
		System.out.println("|       "+this.banco1.getCantidad()+
				"       |        "+this.banco2.getCantidad()+
				"        |      "+this.banco3.getCantidad()+"      |");
		System.out.println("|_______________|_________________|_____________|\n\n");
			
	}
		
	//Comentar
	public void resetIngPuestos(){
			
		this.ingPuestos[0] = "";
		this.ingPuestos[1] = "";
			
	}
		
	//Comentar
	public void setTipoFumador(String tipo){
			
		if (tipo.equals("1")){
			this.tipoFumador = "FumadorTABACO";
			
		} else if (tipo.equals("2")){
			this.tipoFumador = "FumadorPAPEL";
			
		} else if (tipo.equals("3")){
			this.tipoFumador = "FumadorFOSFOROS";
			
		}
		
	}
		
	//Comentar
	public void setCarritoFumador(String tipo, String ing1, String ing2){
			
		if (tipo.equals("FumadorTABACO")){
			this.carrito[0] = 1;
			this.carrito[1] = Integer.parseInt(ing1);
			this.carrito[2] = Integer.parseInt(ing2);
			
		} else if (tipo.equals("FumadorPAPEL")){
			this.carrito[0] = Integer.parseInt(ing1);
			this.carrito[1] = 1;
			this.carrito[2] = Integer.parseInt(ing2);
			
		} else if (tipo.equals("FumadorFOSFOROS")){
			this.carrito[0] = Integer.parseInt(ing1);
			this.carrito[1] = Integer.parseInt(ing2);
			this.carrito[2] = 1;	
		}	
	}
		
	//Comentar
	public int[] bancosABuscar(int ingrediente){
		
		int[] bancos = {0,0};
		
		if (ingrediente == 1){
			bancos[0] = 2;
			bancos[1] = 3;
		} else if (ingrediente == 2){
			bancos[0] = 1;
			bancos[1] = 3;
		} else if (ingrediente == 3){
			bancos[0] = 1;
			bancos[1] = 2;
		}
		
		return bancos;
	}

	//Comentar
	public void bloquearZona(int banco){
		System.out.println("'bout to block dat zone thou! ZONE: "+ banco);
		if (banco == 1){
			this.monitorBanco1 = true;
		} else if (banco == 2){
			this.monitorBanco2 = true;
		} else if (banco == 3){
			this.monitorBanco3 = true;
		}
	}
	
	//Comentar
	public void desbloquearZona(int banco){
		System.out.println("'bout to unlock dat zone to keep smoking braw. ZONE: "+banco);
		if (banco == 1){
			this.monitorBanco1 = false;
		} else if (banco == 2){
			this.monitorBanco2 = false;
		} else if (banco == 3){
			this.monitorBanco3 = false;
		}
	}
	
	//Comentar
	public boolean zonaBloqueada(int banco){
		
		boolean bloqueo = false;
		
		if (banco == 1){
			bloqueo = this.monitorBanco1;
		} else if (banco == 2){
			bloqueo = this.monitorBanco2;
		} else if (banco == 3){
			bloqueo = this.monitorBanco3;
		}
		
		return bloqueo;
	}
	
}
