import java.time.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
/**
 *
 * @author Matteo
 */
public class GestioneBollette extends Application {
    
    
    private Button bottoneInserisci;
    private Button bottoneVisualizza;
    private Button bottoneElimina;
    private Label costoTotale;
    private Label costoMedio;
    private Label titolo;
    private HBox tabGrafBox = new HBox();
    private HBox inerimentoBox = new HBox();
    private HBox bottoniBox = new HBox();
    private HBox labelCostoTotBox = new HBox();
    private HBox labelCostoMedBox = new HBox();
    private VBox vbox = new VBox();
    
    private ParametriStilistici ps = new ParametriStilistici(); // (01)
    private BottoniDiInterfaccia bdi = new BottoniDiInterfaccia(this);
    private CacheBollette cb = new CacheBollette(this);
    // (02)
    public TableView<Bolletta> tabella = new TableView<>();
    public PieChart grafico = new PieChart();// (03)
    public ComboBox<String> comboBox = new ComboBox<>();//(04)
    public TextField textField = new TextField();
    public DatePicker datePickerIn = new DatePicker();//(05)
    public DatePicker datePickerFn = new DatePicker();
    public Label valoreCostoTotale = new Label();
    public Label valoreCostoMedio = new Label();
    
    public int numGiorniDefault = ps.numGiorniDefault; 
    
    
    
    public void start(Stage stage) {
        
        titolo = new Label("Gestione Bollette");
        titolo.setFont(Font.font(ps.font, FontWeight.BOLD, 30));
        
        
        creaTabellaGrafico();
        creaInserimento();
        creaBottoni();
        creaLabel();
        
        bdi.avvioInterfaccia();
        azioniBottoni();
        
        vbox.getChildren().addAll(titolo,tabGrafBox,inerimentoBox, bottoniBox, labelCostoTotBox,labelCostoMedBox);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10,10,10,10));
        
        stage.setOnCloseRequest((WindowEvent we)->{chiusuraInterfaccia();});
        
        Group root = new Group(vbox);
        
        Scene scene = new Scene(root, 800, 590, Color.web(ps.coloreSfondo));
        
        stage.setTitle("Progetto Ambrogi");
        stage.setScene(scene);
        stage.show();
        
        cb.prelevaBolletta();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void creaTabellaGrafico() {
        
        TableColumn col1 = new TableColumn("Fornitura");
        col1.setCellValueFactory(new PropertyValueFactory<>("fornitura"));
        col1.setMinWidth(100);
        
        TableColumn col2 = new TableColumn("Importo");
        col2.setCellValueFactory(new PropertyValueFactory<>("importo"));
        col2.setMinWidth(100);
        
        TableColumn col3 = new TableColumn("Data Inizio");
        col3.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
        col3.setPrefWidth(100);
        
        TableColumn col4 = new TableColumn("Data Fine");
        col4.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
        col4.setPrefWidth(100);
        
        tabella.getColumns().addAll(col1,col2,col3,col4);
        
        tabella.setMinWidth(400);
        tabella.setMaxHeight(400);
        
        tabella.setPlaceholder(new Label("Nessuna bolletta presente"));// (06)
        
        grafico.setMaxWidth(400);
        grafico.setMaxHeight(400);
        
        tabGrafBox.getChildren().addAll(tabella,grafico);
        tabGrafBox.setAlignment(Pos.CENTER);
        
    }

    
    private void creaBottoni() {
        
        bottoneInserisci = new Button("Inserisci");
        bottoneVisualizza = new Button("Visualizza");
        bottoneElimina = new Button("Elimina");
        
        bottoniBox.getChildren().addAll(bottoneInserisci, bottoneVisualizza, bottoneElimina);
        bottoniBox.setAlignment(Pos.CENTER_LEFT);
        bottoniBox.setSpacing(10);
        
    }

    
    private void creaLabel() {
        
        costoTotale = new Label("Costo Totale:");
        costoTotale.setFont(Font.font(ps.font, ps.dimensioneTesto));
        costoMedio = new Label("Costo Medio:");
        costoMedio.setFont(Font.font(ps.font, ps.dimensioneTesto));
        
        valoreCostoTotale.setFont(Font.font(ps.font, ps.dimensioneTesto));
        valoreCostoMedio.setFont(Font.font(ps.font, ps.dimensioneTesto));
        
        
        labelCostoTotBox.getChildren().addAll(costoTotale,valoreCostoTotale);
        labelCostoTotBox.setSpacing(5);
        
        labelCostoMedBox.getChildren().addAll(costoMedio,valoreCostoMedio);
        labelCostoMedBox.setSpacing(5);
        
    }

    
    private void creaInserimento() {
        
        for(int i = 0; i<ps.fornitura.length; i++)
        {
            comboBox.getItems().add(ps.fornitura[i]);// (07)
        }
        comboBox.setPromptText("Fornitura");
        comboBox.setPrefWidth(100);
        
        
        textField.setPromptText("Importo");
        textField.setPrefWidth(100);
        
        
        datePickerIn.setValue(LocalDate.now().minusDays(ps.numGiorniDefault));// (08)
        datePickerIn.setPrefWidth(100);
        
        datePickerFn.setValue(LocalDate.now());
        datePickerFn.setPrefWidth(100);
        
        inerimentoBox.getChildren().addAll(comboBox,textField,datePickerIn,datePickerFn);
        inerimentoBox.setSpacing(1);
        
    }

    
    private void chiusuraInterfaccia(){
        cb.conservaBolletta();
        bdi.terminazioneSisitema();
    }
    
    
    private void azioniBottoni() {
        
        bottoneVisualizza.setOnAction((ActionEvent ev)->{
            bdi.eventoBottoneVisualizza();});
        
        bottoneInserisci.setOnAction((ActionEvent ev)->{
            bdi.eventoBottoneInserisci();});
        
        bottoneElimina.setOnAction((ActionEvent ev)->{
            bdi.eventoBottoneElimina();});
        
    }
    
}


/*
// (01)
Preleva dall'xml i parametri di configurazione dell'iterfaccia grafica

// (02)
Parametri pubblici per poter essere acceduti liberamente dalle classi CacheBollette e BottoniDiInterfaccia
cosi che possano prlevare o settare i dati

// (03)
PieChart permette di creare un grafico a torata
https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/PieChart.html

// (04)
ComboBox permette di creare un menu a tendina
https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ComboBox.html

// (05)
DatePicker permette di inserire la data attraverso un calendario, la data dei datePicker e' in formato LocalDate
https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html

// (06)
Mette la scritta impostata quando la tabella e' vuota

// (07)
Inserisce tutti i tipi di fornitura nella comboBox prelevati dal file xml

// (08)
Imposto come data di default del datePicker di inizio periodo, la data attuale sottraendo un numero N (di default) 
di giorni impostato nel file di configurazione xml
https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html

*/