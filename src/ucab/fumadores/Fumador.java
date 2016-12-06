package ucab.fumadores;

class Fumador{
	
	public static void main(String[] args) {
		Fumador cliente1 = new Fumador(1);
		Fumador cliente2 = new Fumador(2);
		Fumador cliente3 = new Fumador(3);
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
