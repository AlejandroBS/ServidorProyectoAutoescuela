import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Servidor {
	
	public static ArrayList<Integer> idsAdmin = new ArrayList<Integer>();
	public static ArrayList<Integer> idsUser = new ArrayList<Integer>();
	public static ArrayList<HiloAdministrador> hilosAdmin = new ArrayList<HiloAdministrador>();
	
	//public static ArrayList<HiloCliente> hilosCliente = new ArrayList<HiloCliente>();
	
	public static int asignarId(boolean admin){
		int id= 1;
		if(admin==true){
			for( ; idsAdmin.contains(id) ; id++);
		}
		else{
			id = 10000;
			for( ; idsUser.contains(id) ; id++);			
		}
		return id;
	}
	
	public void guardarIds() throws IOException{
		
		File f = new File("ids.dat");
		FileOutputStream fos = new FileOutputStream(f);
		/*fos.write(idsAdmin.size());
		for(int i = 0; i<idsAdmin.size();i++){
			fos.write(idsAdmin.get(i));
		}*/
		fos.write(idsUser.size());
		for(int i = 0;i<idsUser.size();i++){
			fos.write(idsUser.get(i));
		}
		fos.close();
		
	}
	
	public void leerIds() throws IOException{
		
		File f = new File("ids.dat");
		FileInputStream fis = new FileInputStream(f);
		
		int sizeAdmin = 0, sizeUser = 0;
		/*sizeAdmin = fis.read();
		int id = 0;
		for(int i = 0; i<sizeAdmin; i++){
			idsAdmin.add(fis.read());
		}*/
		sizeUser = fis.read();
		for(int i = 0; i<sizeUser; i++){
			idsUser.add(fis.read());
		}
		fis.close();
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Socket socket;
		ServerSocket serverSocket;
		int id = 0;
		ObjectOutputStream salida;
		ObjectInputStream entrada;
		
		HiloAdministrador hiloAdmin = null;
                HiloCliente hiloCliente = null;
		//HiloCliente hiloCliente = null;
		
		serverSocket = new ServerSocket(8400);
		while(true){
			System.out.println("Esperando peticiones...");
			socket = serverSocket.accept();
                        System.out.println("Se acepto nueva peticion de "+socket.getRemoteSocketAddress());
			salida = new ObjectOutputStream(socket.getOutputStream());
			entrada = new ObjectInputStream(socket.getInputStream());
			
			id = entrada.readInt();
			/*if(id == 0){
				id = asignarId(entrada.readBoolean());
				salida.writeInt(id);
			}
			if(id<1000){
				//new HiloAdministrador(socket).run();
				//new HiloAdministrador(null).run();
			}
			if(id>=1000){
				
			}
			*/if(id == -1){
                            System.out.println("la id es -1");
				// si entra aqui es un administrador
				hiloAdmin = new HiloAdministrador(socket,entrada,salida);
				//hilosAdmin.add(hiloAdmin);
				hiloAdmin.run();
			}
                        System.out.println(id);
			if(id>0){
                            System.out.println("la id es >0");
				// si entra aqui es un usuario
				hiloCliente = new HiloCliente(socket,entrada,salida);
                                hiloCliente.run();			
			}
		}
	}
}
