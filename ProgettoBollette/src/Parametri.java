/**
 *
 * @author Matteo
 */


class ParametriStilistici{
    public String[] fornitura = new String[5];
    public String font;
    public int dimensioneTesto;
    public String coloreSfondo;
    public int numGiorniDefault;
    
    public ParametriStilistici(){
        FileConfigurazione fc = new FileConfigurazione();// (01)
        ParametriStilistici ps = fc.getParametriStilistici();
        this.fornitura = ps.fornitura;
        this.font = ps.font;
        this.dimensioneTesto = ps.dimensioneTesto;
        this.coloreSfondo = ps.coloreSfondo;
        this.numGiorniDefault = ps.numGiorniDefault;
    }
    
}

class InfoDataBase{
    public String portaDBMS;
    public String ipDBMS;
    public String userDBMS;
    public String passwordDBMS;
    
    public InfoDataBase(){
        FileConfigurazione fc = new FileConfigurazione();// (01)
        InfoDataBase idb = fc.getInfoDataBase();
        this.portaDBMS = idb.portaDBMS;
        this.ipDBMS = idb.ipDBMS;
        this.userDBMS = idb.userDBMS;
        this.passwordDBMS = idb.passwordDBMS;
    }
    
}

class InfoServerLog{
    public String ipServerLog;
    public int portaServerLog;
    
    public InfoServerLog(){
        FileConfigurazione fc = new FileConfigurazione();// (01)
        InfoServerLog  isl = fc.getInfoServerLog();
        this.ipServerLog = isl.ipServerLog;
        this.portaServerLog = isl.portaServerLog;
    }
    
}

class Parametri { // (02)
    public ParametriStilistici parametriStilistici;
    public InfoDataBase infoDataBase;
    public InfoServerLog infoServerLog;
}

/*
// (01)
Attraverso la classe FileDiConfigurazione vado ad prelevare i dati di configurazione dal file xml e successivamente li inserisco nei vari campi della classe 

// (02)
Classe che e' usata da FileDiConfigurazione per prelevare dal file xml tutti i parametri delle varie componenti del progetto

*/

