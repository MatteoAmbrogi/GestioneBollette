import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.*;
/**
 *
 * @author Matteo
 */
public class ArchivioBollette {
    
    private InfoDataBase idb = new InfoDataBase();// (01)
    
    private String indirizzo = "jdbc:mysql://" + idb.ipDBMS + ":" + idb.portaDBMS + "/bolletta";
    private String utente = idb.userDBMS;
    private String password = idb.passwordDBMS;
    
    
    // (02)
    public List<Bolletta> caricaBollette()
    {
        List<Bolletta> lTabella = new ArrayList<Bolletta>();
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                Statement st = con.createStatement();
            )
        {
            ResultSet rs = st.executeQuery("SELECT * FROM bollette");
            while(rs.next())
                lTabella.add(new Bolletta(rs.getString("fornitura"), rs.getDouble("importo"), rs.getDate("datainizio").toLocalDate(), rs.getDate("datafine").toLocalDate()));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return lTabella;
    }
    
    
    public List<PieChart.Data> caricaBollettePerGrafico()
    {
        List<PieChart.Data> lGrafico = new ArrayList<PieChart.Data>();
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                Statement st = con.createStatement();
            )
        {
            ResultSet rs = st.executeQuery("SELECT fornitura, SUM(importo) AS importoTot  FROM bollette GROUP BY fornitura");
            while(rs.next())
                lGrafico.add(new PieChart.Data( rs.getString("fornitura"), rs.getDouble("importoTot") ) );
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return lGrafico;
    }
    
    public double calcolaCostoTot()
    {
        double costoTot = 0;
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                Statement st = con.createStatement();
            )
        {
            ResultSet rs = st.executeQuery("SELECT SUM(importo) AS importoTot  FROM bollette");
            while(rs.next())
                costoTot =rs.getDouble("importoTot");
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        
        return Math.round(costoTot * 100.0) / 100.0;
    }
    
    public double calcolaCostoMed()
    {
        double costoMed = 0;
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                Statement st = con.createStatement();
            )
        {
            ResultSet rs = st.executeQuery("SELECT AVG(importo) AS importoMed  FROM bollette");
            while(rs.next())
                costoMed =rs.getDouble("importoMed");
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return Math.round(costoMed * 100.0) / 100.0;
    }
    
    
    // (03)
    public List<Bolletta> caricaBollettePeriodo(Date inizio, Date fine)
    {
        List<Bolletta> lTabella = new ArrayList<Bolletta>();
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                PreparedStatement ps = con.prepareStatement("SELECT * FROM bollette where datainizio >= ? and datafine <= ? ");
            )
        {
            ps.setDate(1, inizio);
            ps.setDate(2, fine);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                lTabella.add(new Bolletta(rs.getString("fornitura"), rs.getDouble("importo"), rs.getDate("datainizio").toLocalDate(), rs.getDate("datafine").toLocalDate()));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return lTabella;
    }
    
    
    public List<PieChart.Data> caricaBollettePerGraficoPeriodo(Date inizio, Date fine)
    {
        List<PieChart.Data> lGrafico = new ArrayList<PieChart.Data>();
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                PreparedStatement ps = con.prepareStatement("SELECT fornitura, SUM(importo) AS importoTot  FROM bollette WHERE datainizio >= ? and datafine <= ?  GROUP BY fornitura");
            )
        {
            ps.setDate(1, inizio);
            ps.setDate(2, fine);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                lGrafico.add(new PieChart.Data( rs.getString("fornitura"), rs.getDouble("importoTot") ) );
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return lGrafico;
    }
    
    
    public double calcolaCostoTotPeriodo(Date inizio, Date fine)
    {
        double costoTot = 0;
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                PreparedStatement ps = con.prepareStatement("SELECT SUM(importo) AS importoTot  FROM bollette WHERE datainizio >= ? and datafine <= ?");
            )
        {
            ps.setDate(1, inizio);
            ps.setDate(2, fine);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                costoTot =rs.getDouble("importoTot");
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return Math.round(costoTot * 100.0) / 100.0;
    }
    
    public double calcolaCostoMedPeriodo(Date inizio, Date fine)
    {
        double costoMed = 0;
        
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                PreparedStatement ps = con.prepareStatement("SELECT AVG(importo) AS importoMed  FROM bollette WHERE datainizio >= ? and datafine <= ?");
            )
        {
            ps.setDate(1, inizio);
            ps.setDate(2, fine);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                costoMed =rs.getDouble("importoMed");
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        
        return Math.round(costoMed * 100.0) / 100.0;
    }
    

    // (04)
    public void inserisciInDbms(String fornitore, double importo, Date inizio, Date fine)
    {
        
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                PreparedStatement ps = con.prepareStatement("INSERT INTO bollette VALUES (?, ?, ?, ?)");
            )
        {
            ps.setString(1, fornitore);
            ps.setDouble(2, importo);
            ps.setDate(3, inizio);
            ps.setDate(4, fine);
            
            System.out.println("righe inserite:" + ps.executeUpdate());
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        
    }
    
    // (05)
    public void eliminaInDbms(String fornitore, double importo, Date inizio, Date fine)
    {
        try(
                Connection con = DriverManager.getConnection(indirizzo, utente, password);
                PreparedStatement ps = con.prepareStatement("DELETE FROM bollette WHERE fornitura = ? AND importo = ? AND datainizio = ? AND datafine = ?");
            )
        {
            ps.setString(1, fornitore);
            ps.setDouble(2, importo);
            ps.setDate(3, inizio);
            ps.setDate(4, fine);
            
            System.out.println("righe eliminate:" + ps.executeUpdate());
        } catch (SQLException e) {System.err.println(e.getMessage());}
    }
    
}
/*
// (01)
Preleva dall' xml i parametri di configurazione relativi al data base

// (02)
Funzioni per l'esecuzione delle Query che prelevano tutte le Bollette presenti nel data base

// (03)
Funzioni per l'esecuzione delle Query che prelevano solo le Bollette che fanno parte del periodo
selezionato nei date picker prima di premere il bottone visualizza

// (04)
Funzione per l'esecuzione della Query di inserimento della nuova bolletta nel data base

// (05)
Funzione per l'esecuzione della Query di eliminazione della bolletta selezionata dal data base

*/