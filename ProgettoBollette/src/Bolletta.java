import java.time.*;
import javafx.beans.property.*;
/**
 *
 * @author Matteo
 */
public class Bolletta{
    
    private final SimpleStringProperty fonritura;
    private final SimpleDoubleProperty importo;
    private final SimpleObjectProperty<LocalDate> dataInizio;
    private final SimpleObjectProperty<LocalDate> dataFine;
    
    public Bolletta(String forn, double imp, LocalDate di, LocalDate df) 
    {
            this.fonritura = new SimpleStringProperty(forn);
            this.importo = new SimpleDoubleProperty(imp);
            this.dataInizio = new SimpleObjectProperty(di);
            this.dataFine = new SimpleObjectProperty(df);
            
    }
    
    public String getFornitura()
    {
        return this.fonritura.get();
    }
    public double getImporto()
    {
        return this.importo.get();
    }
    public LocalDate getDataInizio()
    {
        return this.dataInizio.get();
    }
    public LocalDate getDataFine()
    {
        return this.dataFine.get();
    }

}
