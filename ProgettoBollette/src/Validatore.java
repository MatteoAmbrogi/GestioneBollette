import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 *
 * @author Matteo
 */
public class Validatore {
    
    public boolean valida(String xml, String xsd){
        try{
            
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File(xml));
            Schema s = sf.newSchema(new StreamSource(new File(xsd)));
            s.newValidator().validate(new DOMSource(d));
            return true;
        
        } catch (Exception e)
        {
            if(e instanceof SAXException)
                System.out.println("Errore di validazione: " + e.getMessage());
            else{
                System.out.println(e.getMessage());
            } 
            return false;
        }
    }    
}
