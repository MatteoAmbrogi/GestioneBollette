import com.thoughtworks.xstream.*;
import java.nio.file.*;
/**
 *
 * @author Matteo
 */
public class FileConfigurazione {
    
    public Parametri par;
    private String fileXML = "conf.xml";
    private String fileXSD = "conf.xsd";
    private Validatore val = new Validatore();// (01)
    
    public void deserializzaXML(){
        
        if(!val.valida(fileXML,fileXSD))
            System.out.println("Errore di validazione del file di configurazione");
        
        XStream xs = new XStream();
        xs.alias("String", String.class);
        String x = null;
        try
        {
            x = new String(Files.readAllBytes(Paths.get(fileXML)));
        } 
        catch (Exception e) {}
        par = (Parametri)xs.fromXML(x);
    }
    
    // (02)
    public ParametriStilistici getParametriStilistici()
    {
        deserializzaXML();
        return par.parametriStilistici;
    }
    public InfoDataBase getInfoDataBase()
    {
        deserializzaXML();
        return par.infoDataBase;
    }
    public InfoServerLog getInfoServerLog()
    {
        deserializzaXML();
        return par.infoServerLog;
    }
    
}
/*
// (01)
Oggetto di tipo Validatore per poter validare il file xml di configurazione

// (02)
Funzioni per restituire i diversi tipi di parametri alle varie classi che ne fanno richiesta

*/