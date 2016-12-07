package ucab.fumadores;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Principal{

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
		
		
		BancoThread banco1 = new BancoThread(1);
		BancoThread banco2 = new BancoThread(2);
		BancoThread banco3 = new BancoThread(3);

		/*
		banco1.start();
		banco2.start();
		banco3.start();
		*/
		
		try {
			ServerSocket s = new ServerSocket(50006);
			
			int i = 1;
			
			while(true){
				Socket ss = s.accept();
				System.out.println("\nRecibiendo conexion numero" + i + "\n");
				new ManejoHilos(ss, i, banco1, banco2, banco3).start();
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}



