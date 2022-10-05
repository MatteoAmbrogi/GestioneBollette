import java.io.*;
import java.net.*;
import java.nio.file.*;
/**
 *
 * @author Matteo
 */
public class LogDiNavigazione {

    private static final Validatore val = new Validatore();// (01)
    private static final String fileDiLog = "EventiDiNavigazione.txt";
    private static final int portaServerLog = 8080;
            
    public static void main(String[] args) {
        
        while(true)
        {
            try(
                ServerSocket servs = new ServerSocket(portaServerLog);
                Socket sd = servs.accept();
                DataInputStream din = new DataInputStream(sd.getInputStream());
            )
            {
                String x = din.readUTF();
                String y = "\n";
                System.out.println(x);
                Files.write(Paths.get("evento.xml"), x.getBytes());
                
                if(!val.valida("evento.xml", "evento.xsd"))
                    System.out.println("Errore di validazione dell'evento ricevuto");
                
                Files.write(Paths.get(fileDiLog), x.getBytes(), StandardOpenOption.APPEND);// (02)
                Files.write(Paths.get(fileDiLog), y.getBytes(), StandardOpenOption.APPEND);
            
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }   
    }    
}
/*
// (01)
Oggetto di tipo Validatore per poter validare l'evento che e' stato inviato al server

// (02)
Apertura in Append del file di log dove verranno inseriti in modo incrementale gli eventi che arrivano al server
https://docs.oracle.com/javase/7/docs/api/java/nio/file/StandardOpenOption.html

*/