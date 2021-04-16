package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import model.Bruker;
import model.BrukerMedCount;
import model.ClientController;


import java.util.ArrayList;
import java.util.List;

public class SokeTab extends Tab {
    private ClientController cc = new ClientController();
    private BorderPane content;
    private HBox topBox;

    private ToggleGroup tgKjonn;
    private RadioButton rbMann;
    private RadioButton rbKvinne;

    private TextField tfAlder1;
    private TextField tfAlder2;
    private TextField tfAntall;

    private Button btSok;
    private Label lblInfo;


    private ListView<BrukerMedCount> lvBrukere;


    public SokeTab() {

        this.setText("Søk etter kjærleiken");
        this.closableProperty().setValue(false);

        content = new BorderPane();
        topBox = topBox();
        lvBrukere = new ListView<>();

        content.setTop(topBox);
        content.setCenter(lvBrukere);
        this.setContent(content);


        btSok.setOnAction(e -> {
            queryPartnerMatch();
        });


    }



    private HBox topBox() {

        lblInfo = new Label();
        lblInfo.setFont(Font.font("Consolas", FontWeight.NORMAL, 8));
        lblInfo.setTextFill(Color.RED);



        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);

        VBox vBKjonn = new VBox();
        vBKjonn.setSpacing(10);
        vBKjonn.setPadding(new Insets(10));
        tgKjonn = new ToggleGroup();
        rbMann = new RadioButton("Mann");
        rbMann.setSelected(true);
        rbMann.setToggleGroup(tgKjonn);
        rbKvinne = new RadioButton("Kvinne");
        rbKvinne.setToggleGroup(tgKjonn);

        vBKjonn.getChildren().addAll(new Label("Søker etter: "), rbMann,rbKvinne);

        HBox hBAlder = new HBox();
        hBAlder.setPadding(new Insets(10));
        hBAlder.setSpacing(10);
        tfAlder1 = new TextField();
        tfAlder1.setPrefWidth(50);
        tfAlder1.setPromptText("min");
        tfAlder2 = new TextField();
        tfAlder2.setPrefWidth(50);
        tfAlder2.setPromptText("max");
        tfAntall = new TextField();
        tfAntall.setPrefWidth(50);
        tfAntall.setPromptText("Antall");

        hBAlder.getChildren().addAll(new Label("Alder:"), tfAlder1, new Label("mellom"), tfAlder2, new Label("Ant søkeres: "), tfAntall);

        VBox vBSok = new VBox();
        vBSok.setSpacing(10);
        vBSok.setPadding(new Insets(10));
        btSok = new Button("Søk");

        vBSok.getChildren().addAll(lblInfo, new Label("   "), btSok);


        hBox.getChildren().addAll(vBKjonn, hBAlder,vBSok);
        return hBox;
    }


    private void queryPartnerMatch() {

        String kjønn = "";
        if (tgKjonn.getSelectedToggle() == rbMann) {
            kjønn = "Mann";
        } else if (tgKjonn.getSelectedToggle() == rbKvinne) {
            kjønn = "Kvinne";
        }
        int minAlder = Integer.parseInt(tfAlder1.getText());
        int maxAlder = Integer.parseInt(tfAlder2.getText());
        int antall = Integer.parseInt(tfAntall.getText());


        if (minAlder > maxAlder) {
            lblInfo.setText("Min alder må\n være > enn max");
            lvBrukere.setItems(null);
            return;
        } else if (minAlder <=0) {
            lblInfo.setText("Alder > enn 0");
            lvBrukere.setItems(null);
            return;
        }
        lblInfo.setText("");


        ArrayList<BrukerMedCount> brRetur = cc.personSøk(cc.getIDFromFile(), kjønn, minAlder, maxAlder, antall);

        ObservableList<BrukerMedCount> brukere = FXCollections.observableArrayList(brRetur);

        lvBrukere = new ListView<>(brukere);
        lvBrukere.setCellFactory(params -> new ListCell<>(){
            @Override
            protected void updateItem(BrukerMedCount bmc, boolean b) {
                super.updateItem(bmc, b);
                if (b || bmc == null) {
                    setText(null);
                } else {
                    setText(bmc.getBruker().getKjønn() + " " + bmc.getBruker().getAlder() + " fra " + bmc.getBruker().getBosted() + " - Felles interesser: " + bmc.getCount());
                }
            }
        });

        content.setCenter(lvBrukere);

        lvBrukere.setOnMouseClicked(e -> openTabForMatch());

    }


    private void openTabForMatch() {

        //.. BrukerId + person for å sette inn i Stalkers tabell!
        int brukerId = cc.getIDFromFile();
        int partnerId = lvBrukere.getSelectionModel().getSelectedItem().getBruker().getId();

        //.. Sette brukerinfo for den brukeren du klikker på
        Bruker b = cc.requestInformation(brukerId, partnerId);
        //.. Sette ListView i MatchTab med brukere fra Query (Stalkers)
        List<Bruker> brukere = cc.visInteresserte(brukerId);

        // Begge er fucked
        if(brukere == null && b == null) {
            return;
        }
        // Ingen har sett på profilen men han klikket på person
        else if(brukere == null && b != null) {
            MatchTab.hentBrukerInfo(b);
            getTabPane().getSelectionModel().select(1);
        } else {
            MatchTab.hentBrukerInfo(b);
            MatchTab.hentStalkers(brukere);
            getTabPane().getSelectionModel().select(1);
        }







        // Kall på stalkers, sjekk om de er null,
        //
    }






}

