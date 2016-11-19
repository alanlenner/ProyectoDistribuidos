package ucab.fumadores;

public class Banco {

	private boolean fumando=false;
	private boolean ingredienteEnMesa=false;
	private int ingrediente;
	
	public synchronized void colocarIngredientes(int ingrediente) throws InterruptedException{
		
		while(ingredienteEnMesa || fumando)
			wait();
		this.ingrediente = ingrediente;
		ingredienteEnMesa = true;
		System.out.println("El vendedor no pone ingrediente "+ingrediente);
		notifyAll();

	}
	
	public synchronized void fumar(int id) throws InterruptedException{
		while(!ingredienteEnMesa || fumando || ingrediente!=id)
			wait();
		System.out.println("Fumador "+id+" empieza a fumar.");
		ingredienteEnMesa = false;
		fumando = true;
	}
	
	public synchronized void salir(int id){
		fumando = false;
		System.out.println("Fumador "+id+" termina de fumar.");
		notifyAll();
	}
	
}
