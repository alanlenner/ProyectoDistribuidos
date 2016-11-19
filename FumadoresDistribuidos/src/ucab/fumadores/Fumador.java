package ucab.fumadores;
import java.util.*;

public class Fumador extends Thread {
	
	private Random random = new Random();
	private Banco banco;
	private int id;
	
	public Fumador(int id, Banco banco){
		this.id = id;
		this.banco = banco;
	}
	
	public void run(){
		
		while (true){
			try{
				//fumador esta fumando.
				banco.fumar(id);
				
				Thread.sleep(random.nextInt(300));
				
				//fumador descansa.
				banco.salir(id);
				
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}

}
