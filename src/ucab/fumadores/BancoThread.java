package ucab.fumadores;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unchecked")
//i put the suppress warning in because of the converting between classes in the array
public class BancoThread extends Thread implements Serializable{
	
	private int cantidad;
	private int tipo;
	private long initialTime;
	//Constructor de la clase
	
	public BancoThread(int tipo){
		this.tipo= tipo;
		this.cantidad = 0;
	}
	
	
	
	@Override
	public void run() {
	
		while (true){
			System.out.println("Hola soy el banco "+ this.tipo + " Mi cantidad es "+ this.cantidad);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Obtiene la cantidad de un banco solicitado, retorna la cantidad si y solo si el tipo del banco es el consultado
	// Retorna -1 en caso de no ser el banco consultado.
	public int getCantidad()
	{
		return this.cantidad;
	}
	
	public int vacio()
	{
		if (this.cantidad == 0)
		return 1;
		else 
			return 0;
	}

	
	public synchronized void agregarItem(){
		this.cantidad++;
	}
	
	public synchronized void quitarItem(){
		this.cantidad--;
	}
	
	public int getTipo()
	{
		return this.tipo;
	}
	
}
