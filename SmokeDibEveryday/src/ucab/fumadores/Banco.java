package ucab.fumadores;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unchecked")
//i put the suppress warning in because of the converting between classes in the array
public class Banco {

    //Variables for storing ITEMSs(Weed,Blunt,Lighter)
    private ArrayList allItems  = new ArrayList();
    private ArrayList ItemsVendedor = new ArrayList();

    public Banco(){
    	//adds items
        allItems .add("Weed");
        allItems .add("Blunt");
        allItems .add("Lighter");
    }
    
	//gets two random item from the list
    public void getRandom(){
        Random random = new Random();
        // clears monitor array
        ItemsVendedor.clear();
        //copies what in the array list to another array

        ArrayList copyAllElements = (ArrayList) allItems .clone();
        //picks an item and adds it to the monitors items
        int ITEMS1 = random.nextInt(copyAllElements.size());
        ItemsVendedor.add(copyAllElements.get(ITEMS1));

        copyAllElements.remove(ITEMS1);
        //picks an item and adds it to the monitors items        
        int ITEMS2 = random.nextInt(copyAllElements.size());
        ItemsVendedor.add(copyAllElements.get(ITEMS2));
    }
    
    //to check if the table is empty so you don't get any errors with arrays
    public boolean isEmpty(){
        return (ItemsVendedor.size() == 0);
    }
    
    //gets the items and notifies the other threads
    public synchronized String getItemsVendedor(){
        notifyAll();
        return ItemsVendedor.toString();
    }
    
    //this gets the item and pairs it with the correct thread
    public synchronized String getItemsFumador(int x){
        try {
            this.wait();
        } catch (Exception e) {
        	
        }
        return (String) allItems .get(x);
    }
    
   //checking if the smoker has the same items as the monitor
    public boolean hasIngredient(String ITEMSName){
        return (ItemsVendedor.contains(ITEMSName));
    }

    public synchronized void pause()
    {
        try {
            this.wait();
        } catch (Exception e) {
        	
        }
    }
    
}
