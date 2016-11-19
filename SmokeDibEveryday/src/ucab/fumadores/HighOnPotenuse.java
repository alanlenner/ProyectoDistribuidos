package ucab.fumadores;

import java.util.ArrayList;
import java.util.Random;

class HighOnPotenuse {

	public static void main(String[] args) {
		//creates a new table called table to put data into
		table Table = new table();
		//creates a monitor to control all of the threads
		agent Monitor = new agent(Table);
    	//starts the agent Monitors
		Monitor.start();

		//prints a pot leaf, i thought this was funny because i heard another student did the same also
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
			SMOKERS SMOKERSThread = new SMOKERS(Table, i, "Smoker " + Integer.toString(i+1), Monitor);
			//starts all of the smoker threads
			SMOKERSThread.start();
		}
    }
}


class SMOKERS extends Thread {
	//intilizes all of the variables
    private table Table = new table();
    private String ITEMS;
    private int ITEMNUMBER;
    private agent Monitor;

    public SMOKERS(table newTable, int newITEM, String NAME, agent newMonitor){
    	//initilizing all of the variables
        ITEMNUMBER = newITEM;
        this.Table = newTable;
        setName(NAME);
        Monitor = newMonitor;
    }

    @Override
    public void run(){ 
    	//while true, basically run this script forever
        while(true){
            //gets the item from the table and puts it into items
            ITEMS = Table.getSmokerItems(ITEMNUMBER);
            // if the table doesnt has an ingredient and the table is not empty
            if (!Table.hasIngredient(ITEMS) && !Table.isEmpty()){
            	//print out the thread witht he missing item
            	System.out.println("-----------------------------------");
            	System.out.println("Hey I am " + getName() + " and i have the " + ITEMS + " your missing.\n");
            	System.out.println("-----------------------------------");
            	try {
            		//tell the thread to smoke
            		HighOnPotenuse();
            		// prints that the thread is going to let another read do the same operation
            		System.out.println(getName() + " is going to let someone else Get High On Potenuse.");
                    // the thread tells the monitor to continue
            		Monitor.wake();
                } catch (Exception e) {
                	
                }
            }
        }
    } 

    public synchronized void HighOnPotenuse() throws Exception{
    	//this prints what each thread is doing when it matches up witht hte monitor
        System.out.println(getName() + " rolls up a doobie.");
        Thread.sleep(100);
        System.out.println(getName() + " starts blowing smoke clouds");
        Thread.sleep(100);
        System.out.println(getName() + " is officially High On Potenuse");

    }
    
}


class agent extends Thread {
	//initiates the variables
    private table Table;

    public agent(table newTable){
        Table = newTable;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            	
            }
            Table.getRandom();
            // this tells the smoker threads to look at the table
            //this prints the greeting message and what items the monitor has
            System.out.println("\nHey, Do You Want to Get High On Potenuse...\nWell The Problem is I Only Have " + Table.getMonitorItems());
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


@SuppressWarnings("unchecked")
// i put the suppress warning in because of the converting between classes in the array
class table {

    //Variables for storing ITEMSs(Weed,Blunt,Lighter)
    private ArrayList allItems  = new ArrayList();
    private ArrayList MonitorItems = new ArrayList();

    public table(){
    	//adds items
        allItems .add("Weed");
        allItems .add("Blunt");
        allItems .add("Lighter");
    }
    
	//gets two random item from the list
    public void getRandom(){
        Random random = new Random();
        // clears monitor array
        MonitorItems.clear();
        //copies what in the array list to another array

        ArrayList copyAllElements = (ArrayList) allItems .clone();
        //picks an item and adds it to the monitors items
        int ITEMS1 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(ITEMS1));

        copyAllElements.remove(ITEMS1);
        //picks an item and adds it to the monitors items        
        int ITEMS2 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(ITEMS2));
    }
    
    //to check if the table is empty so you don't get any errors with arrays
    public boolean isEmpty(){
        return (MonitorItems.size() == 0);
    }
    
    //gets the items and notifies the other threads
    public synchronized String getMonitorItems(){
        notifyAll();
        return MonitorItems.toString();
    }
    
    //this gets the item and pairs it with the correct thread
    public synchronized String getSmokerItems(int x){
        try {
            this.wait();
        } catch (Exception e) {
        	
        }
        return (String) allItems .get(x);
    }
    
   //checking if the smoker has the same items as the monitor
    public boolean hasIngredient(String ITEMSName){
        return (MonitorItems.contains(ITEMSName));
    }

    public synchronized void pause()
    {
        try {
            this.wait();
        } catch (Exception e) {
        	
        }
    }
    
}