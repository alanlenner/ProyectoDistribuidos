package ucab.fumadores;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unchecked")
//i put the suppress warning in because of the converting between classes in the array
public class BancoThread extends Thread {
	
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
		//
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
