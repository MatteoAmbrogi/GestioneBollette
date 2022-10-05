import java.sql.*;
import java.time.*;
import javafx.collections.*;
/**
 *
 * @author Matteo
 */
public class BottoniDiInterfaccia {
    
    
    private ArchivioBollette ab = new ArchivioBollette();
    private EventoDiNavigazione edn = new EventoDiNavigazione();
    private GestioneBollette gb;// (01)
    
    
    public BottoniDiInterfaccia(GestioneBollette gb)
    {
        this.gb=gb;
    }
    
    
    public void avvioInterfaccia()
    {
        edn.inviaEvento("AVVIO");// (02)
        datiDiAvvio();
        gb.tabella.setOnMouseClicked((i)->
        {
           edn.inviaEvento("SELEZIONE");
        });
        
    }
    
    
    public void terminazioneSisitema(){
        edn.inviaEvento("TERMINE");// (02)
    }
    
    
    private void azzeraCampiInserimento()
    {
        gb.comboBox.setValue(null);// (03)
        gb.textField.setText(null);// (04)
        gb.datePickerIn.setValue(LocalDate.now().minusDays(gb.numGiorniDefault));
        gb.datePickerFn.setValue(LocalDate.now());
    }
    
    
    private void datiDiAvvio()
    {
        
        gb.tabella.setItems(FXCollections.observableList(ab.caricaBollette()));
        
        gb.grafico.setData(FXCollections.observableList(ab.caricaBollettePerGrafico()));
        
        gb.valoreCostoTotale.setText(String.valueOf(ab.calcolaCostoTot()));
        gb.valoreCostoMedio.setText(String.valueOf(ab.calcolaCostoMed()));
        
    }
    
    
    public void eventoBottoneVisualizza()
    {
        edn.inviaEvento("VISUALIZZA");// (02)
        // (05)
        Date sqlDataInizio = Date.valueOf(gb.datePickerIn.getValue());
        Date sqlDataFine = Date.valueOf(gb.datePickerFn.getValue());
        
        gb.tabella.setItems(FXCollections.observableList( ab.caricaBollettePeriodo(sqlDataInizio, sqlDataFine)));
        
        gb.grafico.setData(FXCollections.observableList( ab.caricaBollettePerGraficoPeriodo(sqlDataInizio, sqlDataFine)));
        
        gb.valoreCostoTotale.setText(String.valueOf(ab.calcolaCostoTotPeriodo(sqlDataInizio, sqlDataFine)));
        
        gb.valoreCostoMedio.setText(String.valueOf(ab.calcolaCostoMedPeriodo(sqlDataInizio, sqlDataFine)));
        
        azzeraCampiInserimento();
        
    }
    
    
    public void eventoBottoneInserisci()
    {
        edn.inviaEvento("INSERISCI");// (02)
        
        String fornitura = gb.comboBox.getValue();
        double importo = Double.valueOf(gb.textField.getText());
        Date sqlDataInizio = Date.valueOf(gb.datePickerIn.getValue());
        Date sqlDataFine = Date.valueOf(gb.datePickerFn.getValue());
        
        ab.inserisciInDbms(fornitura, importo, sqlDataInizio, sqlDataFine);
        
        datiDiAvvio();
        
        azzeraCampiInserimento();
    }
    
    
    public void eventoBottoneElimina()
    {
        edn.inviaEvento("ELIMINA");// (02)
        
        
        Bolletta b = gb.tabella.getSelectionModel().getSelectedItem();// (06)
        gb.tabella.getSelectionModel().clearSelection();// (07)
        
        
        String fornitura = b.getFornitura();
        double importo = b.getImporto();
        Date sqlDataInizio = Date.valueOf(b.getDataInizio());
        Date sqlDataFine = Date.valueOf(b.getDataFine());
        
        ab.eliminaInDbms(fornitura, importo, sqlDataInizio, sqlDataFine);
        
        datiDiAvvio();
    }
    
}
/*
// (01)
Istanza dell'interfaccia grafica per poter prelevare le informazioni e per poterle inserire

// (02)
Invio l'evento al server di log attraverso la classe EventoDiNavigazione

// (03)
Reinserisce il testo di Prompt della Combo Box

// (04)
Reinserisce il testo di Prompt della Text Field

// (05)
Trasformo le date local date dei date picker in Date per sql

// (06)
Prelevo la Bolletta selezionata dalla tabella

// (07)
Cancella la selezione 
*/