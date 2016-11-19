package ucab.fumadores;

public class Vendedor extends Thread {

	//initiates the variables
    private Banco banco;

    public Vendedor(Banco newBanco){
        banco = newBanco;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            	
            }
            banco.getRandom();
            // this tells the smoker threads to look at the table
            //this prints the greeting message and what items the monitor has
            System.out.println("\nHey, Do You Want to Get High On Potenuse...\nWell The Problem is I Only Have " + banco.getItemsVendedor());
            // pause the agent while one SMOKERS thread is running
            pause();
        }
    }

    public synchronized void wake(){
        try{
            notify();
        } catch(Exception e){
        	
        }
    }

    public synchronized void pause(){
        try{
            this.wait();
        } catch (Exception e) {
        	
        }
    }
    
}
