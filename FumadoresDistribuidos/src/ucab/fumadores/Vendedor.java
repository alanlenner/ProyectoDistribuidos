package ucab.fumadores;
import java.util.*;

public class Vendedor extends Thread{

	private Random random = new Random();
	private Banco banco;
	
	public Vendedor(Banco banco){
		this.banco = banco;
	}
	
	public void run(){
		
		while (true){
			int ingrediente = random.nextInt(3);
			
			try{
				Thread.sleep(random.nextInt(300));
				banco.colocarIngredientes(ingrediente);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	
}
