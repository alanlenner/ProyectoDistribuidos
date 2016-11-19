package ucab.fumadores;

class Fumador extends Thread {

	//Initializes all of the variables
    private Banco banco = new Banco();
    private String ITEMS;
    private int ITEMNUMBER;
    private Vendedor vendedor;

    public Fumador(Banco newBanco, int newITEM, String NAME, Vendedor newVendedor){
    	//initializing all of the variables
        ITEMNUMBER = newITEM;
        this.banco = newBanco;
        setName(NAME);
        vendedor = newVendedor;
    }

    @Override
    public void run(){ 
    	//while true, basically run this script forever
        while(true){
            //gets the item from the table and puts it into items
            ITEMS = banco.getItemsFumador(ITEMNUMBER);
            // if the table doesn't has an ingredient and the table is not empty
            if (!banco.hasIngredient(ITEMS) && !banco.isEmpty()){
            	//print out the thread with he missing item
            	System.out.println("-----------------------------------");
            	System.out.println("Hey I am " + getName() + " and i have the " + ITEMS + " your missing.\n");
            	System.out.println("-----------------------------------");
            	try {
            		//tell the thread to smoke
            		HighOnPotenuse();
            		// prints that the thread is going to let another read do the same operation
            		System.out.println(getName() + " is going to let someone else Get High On Potenuse.");
                    // the thread tells the monitor to continue
            		vendedor.wake();
                } catch (Exception e) {
                	
                }
            }
        }
    } 

    public synchronized void HighOnPotenuse() throws Exception{
    	//this prints what each thread is doing when it matches up with the monitor
        System.out.println(getName() + " rolls up a doobie.");
        Thread.sleep(100);
        System.out.println(getName() + " starts blowing smoke clouds");
        Thread.sleep(100);
        System.out.println(getName() + " is officially High On Potenuse :)");

    }
    
}
