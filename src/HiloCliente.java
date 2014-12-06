
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alejandro
 */
public class HiloCliente implements Runnable {

    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public HiloCliente(Socket socket, ObjectInputStream entrada, ObjectOutputStream salida) {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
    }

    @Override
    public void run() {
        try {
            int eleccion = entrada.readInt();
            //quiere una lista de los ficheros test
            if (eleccion == 1) {
                File f = new File("data");
                File[] lista = f.listFiles();

                for (int i = 0; i < lista.length; i++) {
                    if (lista[i].isDirectory()) {
                        salida.writeBoolean(true);
                        System.out.println(lista[i].getName());
                        salida.flush();
                        salida.writeUTF(lista[i].getName());
                        salida.flush();
                    }
                }
                salida.writeBoolean(false);
                salida.flush();

            }
            //quiere descargar un fichero
            if (eleccion == 2) {
                String fichero = entrada.readUTF();
                int tamano = 5096;
                byte[] buffer = new byte[tamano];
                int leido = 0;
                
                comprimirFichero(fichero);

                File f = new File("data" + File.separator + fichero+".zip");
                
                FileInputStream fis = new FileInputStream(f);
                if (f.exists()) {
                    salida.writeInt(tamano);
                    salida.flush();
                    
                    salida.writeLong(f.length());
                    salida.flush();
                    while ((leido = fis.read(buffer)) > 0) {

                        salida.writeInt(leido);
                        salida.flush();
                        salida.writeObject(buffer);
                        salida.flush();
                        entrada.readBoolean();
                        buffer = new byte[tamano];
                    }
                    salida.writeInt(0);
                    salida.flush();

                    fis.close();
                    socket.close();

                }

            }

        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private boolean comprimirFichero(String fichero){
        Compressor comp = new Compressor("data", fichero + ".zip");
        comp.compress("data" + File.separator + fichero, fichero);
        comp.close();
        return true;
    }

}
