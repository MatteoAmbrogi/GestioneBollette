import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Matteo
 */

// (01)
class Evento{
    public String nomeApplicazione;
    public String indirizzoIP;
    public String timestamp;
    public String nomeEvento;
    
    public Evento(String nomeEvento)
    {
        this.nomeApplicazione = "Gestione Bollette";
        this.indirizzoIP = "localhost";
        this.timestamp = new Date().toString();
        this.nomeEvento = nomeEvento;
    }
}


public class EventoDiNavigazione {
    
    private InfoServerLog isl = new InfoServerLog();// (02)
    private String ip = isl.ipServerLog;
    private int porta = isl.portaServerLog;
    
    public void inviaEvento(String nomeEvento){
        
        Evento ev = new Evento(nomeEvento);
        XStream xs = new XStream();
        String x = xs.toXML(ev);
        
        try(
            DataOutputStream dout = 
            new DataOutputStream((new Socket(ip,porta)).getOutputStream());
        )
        {
            dout.writeUTF(x);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
/*
// (01)
Classe evento dove il costruttore riceve il nome dell'evento e inserisce automaticamente il timestamp in formato stringa

// (02)
Preleva dall'xml i parametri di configurazione relativi al server di log

*/