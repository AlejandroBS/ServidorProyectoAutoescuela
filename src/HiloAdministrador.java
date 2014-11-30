import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloAdministrador implements Runnable {

    private ObjectInputStream entrada = null;
    private ObjectOutputStream salida = null;
    private Socket socket = null;

    public HiloAdministrador(Socket socket, ObjectInputStream entrada, ObjectOutputStream salida) {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
    }

    public void run() {
        int eleccion = 0;
        try {
            eleccion = entrada.readInt();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (eleccion == 1) {
            // si es 1 significa que se van a listar los ficheros del servidor 
            File f = new File("data/");
        }
        if (eleccion == 2) {
			// si es 2 significa que se va a descargar un fichero

        }
        if (eleccion == 3) {
            try {
                        // si es 3 significa que se va a subir un fichero
                // el cliente administrador manda el nombre del fichero, por lo cual se borrara si ya existe
                System.out.println("Eleccion 3");
                String nombreFichero = entrada.readUTF();
                File f = new File("data" + File.separator + nombreFichero+".zip");


                leerFicheroDeCliente(nombreFichero);
                
                
                descomprimirFichero(nombreFichero);
                socket.close();
                
                System.out.println("SE ACABÓ");
            } catch (IOException ex) {
                Logger.getLogger(HiloAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (eleccion == 0) {
            // si es 0 finaliza la conexion
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    this.finalize();
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean leerFicheroDeCliente(String nombreFichero) throws IOException {
        File f = new File("data" + File.separator + nombreFichero+".zip");
        int tamano = entrada.readInt();
        byte[] buffer = new byte[tamano];
        int leido = 0;
        FileOutputStream fos = new FileOutputStream(f);
        
        while((leido=entrada.readInt())>0 ){
            try {
                
                buffer = (byte[]) entrada.readObject();
                /*for(int i = 0;i<leido;i++){
                    System.out.println(i+" // "+buffer[i]);
                }*/
                fos.write(buffer,0,leido);
                salida.writeBoolean(true);
                salida.flush();
                
                buffer = new byte[tamano];
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fos.flush();
        fos.close();
        return true;
    }

    public boolean descomprimirFichero(String nombreFichero) {

        File f = new File("data" + File.separator + nombreFichero+".zip");

        Decompressor decomp;
        decomp = new Decompressor("data" + File.separator + nombreFichero + ".zip", "data/");
        decomp.unZip();
        f.delete();
        return true;

    }

}
