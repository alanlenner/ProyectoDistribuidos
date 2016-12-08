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
	
	//Comentar..
	@Override
	public void run() {
		System.out.println("Banco " + this.tipo + " iniciado.");
	}
	
	// Obtiene la cantidad de un banco solicitado, retorna la cantidad si y solo si el tipo del banco es el consultado
	// Retorna -1 en caso de no ser el banco consultado.
	public int getCantidad()
	{
		return this.cantidad;
	}
	
	//Comentar..
	public int vacio()
	{
		if (this.cantidad == 0)
			return 1;
		else 
			return 0;
	}

	//Comentar..
	public synchronized void agregarItem(){
		this.cantidad++;
	}
	
	//Comentar..
	public synchronized void quitarItem(){
		this.cantidad--;
	}
	
	//Comentar..
	public int getTipo()
	{
		return this.tipo;
	}
	
}
