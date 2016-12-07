package ucab.fumadores;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;


public class ManejoHilos extends Thread{
	
		private BancoThread banco1;
		private BancoThread banco2;
		private BancoThread banco3;
		private Socket socket;
		private int nroConexion;
		
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		private int bancoAgregar;
		
		public ManejoHilos(Socket socket, int nroConexion, BancoThread banco1, BancoThread banco2, BancoThread banco3){
			this.banco1 = banco1;
			this.banco2 = banco2;
			this.banco3 = banco3;
			this.socket = socket;
			this.nroConexion = nroConexion;
			
		}
		
		@Override
		public void run(){
			
			try {
				
				System.out.println("Entra en manejo hilos");
				
				Random random = new Random();
				
				while (true){
					
					this.bancoAgregar = random.nextInt(3) + 1;
					this.oos = new ObjectOutputStream(this.socket.getOutputStream());
					
					if(this.bancoAgregar == 1){	
						
						this.oos.writeObject(this.banco1);
						this.oos.flush();
						this.ois = new ObjectInputStream(this.socket.getInputStream());
						this.banco1 = (BancoThread)this.ois.readObject();
						
					} else if (this.bancoAgregar == 2){
						
						this.oos.writeObject(this.banco2);
						this.oos.flush();
						this.ois = new ObjectInputStream(this.socket.getInputStream());
						this.banco2 = (BancoThread)this.ois.readObject();
						
					} else if (this.bancoAgregar == 3){
						
						this.oos.writeObject(this.banco3);
						this.oos.flush();
						this.ois = new ObjectInputStream(this.socket.getInputStream());
						this.banco3 = (BancoThread)this.ois.readObject();
					}
					
					this.oos.close();
					Thread.sleep(2000);
					
					
				}
				
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
}
