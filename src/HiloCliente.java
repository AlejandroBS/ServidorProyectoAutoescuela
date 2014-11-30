
import java.io.File;
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
    
    public HiloCliente(Socket socket, ObjectInputStream entrada, ObjectOutputStream salida){
        this.socket=socket;
        this.entrada=entrada;
        this.salida=salida;                
    }
    
    @Override
    public void run() {
        try {
            int eleccion = entrada.readInt();
            //quiere una lista de los ficheros test
            if(eleccion==1){
                File f = new File ("data");
                File[] lista = f.listFiles();
                
                for(int i = 0;i<lista.length;i++){
                    if(lista[i].isDirectory()){
                        salida.writeBoolean(true);System.out.println(lista[i].getName());
                        salida.flush();
                        salida.writeUTF(lista[i].getName());salida.flush();
                    }
                }
                salida.writeBoolean(false);
                salida.flush();
                
            }
            
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
