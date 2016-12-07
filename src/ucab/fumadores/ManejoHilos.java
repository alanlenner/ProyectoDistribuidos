package ucab.fumadores;

import java.net.Socket;

public class ManejoHilos extends Thread{
	
		private BancoThread banco1;
		private BancoThread banco2;
		private BancoThread banco3;
		private Socket socket;
		private int nroConexion;

		public ManejoHilos(Socket socket, int nroConexion, BancoThread banco1, BancoThread banco2, BancoThread banco3){
			this.banco1 = banco1;
			this.banco2 = banco2;
			this.banco3 = banco3;
			this.socket = socket;
			this.nroConexion = nroConexion;
		}
		
		public void Run(){
			//
		}
}
