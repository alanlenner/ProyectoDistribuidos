package ucab.fumadores;

public class Principal {
	
	public static void main(String[] args) {
		
		Banco banco = new Banco();
		Vendedor vendedor = new Vendedor(banco);
		Fumador[] fumador = new Fumador[3];
		
		for (int i = 0; i < fumador.length; i++){
			fumador[i] = new Fumador(i,banco);
		}
		
		vendedor.start();
		
		for (int i = 0; i < fumador.length; i++){
			fumador[i].start();
		}
		
	}
	
}
