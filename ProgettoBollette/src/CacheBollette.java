import java.io.*;
import java.time.*;
/**
 *
 * @author Matteo
 */
class InformazioniDiInserimento implements Serializable{
    public int indexFornitore;
    public String importo;
    public LocalDate inizio;
    public LocalDate fine;
    
    public InformazioniDiInserimento()
    {
        this.indexFornitore = 0;
        this.importo = "";
        this.inizio = null;
        this.fine = null;
    }
}

public class CacheBollette {
    
    private String fileCache = "./myfiles/bollette.bin";
    private GestioneBollette gb;// (01)
    
    public CacheBollette(GestioneBollette gb)
    {
        this.gb=gb;
    }
    
    
    public void conservaBolletta()
    {
        
        InformazioniDiInserimento idi = new InformazioniDiInserimento();
        idi.indexFornitore = gb.comboBox.getSelectionModel().getSelectedIndex();// (02)
        idi.importo = gb.textField.getText();
        idi.inizio = gb.datePickerIn.getValue();
        idi.fine = gb.datePickerFn.getValue();
        
        try (   FileOutputStream fout= new FileOutputStream(fileCache);
                ObjectOutputStream oout = new ObjectOutputStream(fout); )
        {
            
            oout.writeObject(idi);
            
        }catch (IOException ex)
        {
            System.out.println("Errore: impossibile conservare cache");
        }
        
    }
    
    
    public void prelevaBolletta()
    {
        
        InformazioniDiInserimento idi = new InformazioniDiInserimento();
        
        try(    FileInputStream fin = new FileInputStream(fileCache);
                ObjectInputStream oin = new ObjectInputStream(fin); )
        {
            
            idi = (InformazioniDiInserimento) oin.readObject();
            
        }catch(IOException | ClassNotFoundException ex )
        {
            return;
        }
        
        gb.comboBox.getSelectionModel().select(idi.indexFornitore);
        gb.textField.setText(idi.importo);
        gb.datePickerIn.setValue(idi.inizio);
        gb.datePickerFn.setValue(idi.fine);
    }
}

/*
// (01)
Istanza dell'interfaccia grafica per poter prelevare le informazioni e per poterle inserire

// (02)
Prelevo l'indice selezionato dalla combo box

*/