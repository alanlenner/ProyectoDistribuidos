package ucab.fumadores;

class Principal {

	public static void main(String[] args) {
		//creates a new table called table to put data into
		Banco banco = new Banco();
		//creates a monitor to control all of the threads
		Vendedor vendedor = new Vendedor(banco);
    	//starts the agent Monitors
		vendedor.start();

		//prints a pot leaf, i thought this was funny because i heard another student did the same also.
		System.out.println("     LETS GET High On Potenuse");
		System.out.println("                        W     ");                       
		System.out.println("                       WWW   ");       
		System.out.println("                       WWW    ");      
		System.out.println("                      WWWWW    ");    
		System.out.println("                W     WWWWW     W  ");
		System.out.println("                WWW   WWWWW   WWW "); 
		System.out.println("                 WWW  WWWWW  WWW  "); 
		System.out.println("                  WWW  WWW  WWW   "); 
		System.out.println("                   WWW WWW WWW    ");  
		System.out.println("                     WWWWWWW      ");  
		System.out.println("                  WWWW  |  WWWW   "); 
		System.out.println("                        |         "); 
		System.out.println("                        | ");
		System.out.println("");

		// creates 3 smoker thread from the monitor, so the threads can wake up the monitor
		for (int i = 0; i < 3; i++){
			//inserts the variables into the smokers class
			Fumador FumadorThread = new Fumador(banco, i, "Smoker " + Integer.toString(i+1), vendedor);
			//starts all of the smoker threads
			FumadorThread.start();
		}
    }
	
}



