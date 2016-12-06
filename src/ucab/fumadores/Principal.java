package ucab.fumadores;

public class Principal implements Runnable{

	public static void main(String[] args) {
		
		System.out.println("");
		System.out.println(" Proyecto Fumadores - Sistemas Distribuidos UCAB");
		System.out.println("");
		System.out.println("");
		System.out.println(" a,  8a");
		System.out.println(" `8, `8)                            ,adPPRg,");
		System.out.println("  8)  ]8                        ,ad888888888b");
		System.out.println(" ,8' ,8'                    ,gPPR888888888888");
		System.out.println(",8' ,8'                 ,ad8''   `Y888888888P");
		System.out.println("8)  8)              ,ad8''        (8888888''");
		System.out.println("8,  8,          ,ad8''            d888''");
		System.out.println("`8, `8,     ,ad8''            ,ad8''");
		System.out.println(" `8, `' ,ad8''            ,ad8''");
		System.out.println("    ,gPPR8b           ,ad8''");
		System.out.println("   dP:::::Yb      ,ad8''");
		System.out.println("   8):::::(8  ,ad8''");
		System.out.println("   Yb:;;;:d888''               Merchán Javier");
		System.out.println("    '8ggg8P'                   Muñoz Alan");
		System.out.println("");
		
		// Tiempo inicial de referencia
		//long initialTime = System.currentTimeMillis();
		BancoThread banco1 = new BancoThread(1);
		BancoThread banco2 = new BancoThread(2);
		BancoThread banco3 = new BancoThread(3);

		banco1.start();
		banco2.start();
		banco3.start();
		
	}

	@Override
	public void run() {
		//
	}
	
}



