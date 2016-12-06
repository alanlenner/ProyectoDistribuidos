package ucab.fumadores;

import java.util.Random;

public class Vendedor{
	
	public static void main(String[] args) {
		
		Vendedor vendedor = new Vendedor(banco1, banco2, banco3);
		
		vendedor.pruebaVendedor();
	
	}
	
	private BancoThread banco1;
	private BancoThread banco2;
	private BancoThread banco3;
	private int bancoAgregar;
		
	public Vendedor(BancoThread banco1, BancoThread banco2, BancoThread banco3){
		this.banco1 = banco1;
		this.banco2 = banco2;
		this.banco3 = banco3;
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
