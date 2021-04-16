package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Bruker;


import java.util.List;

public class MatchTab extends Tab {


    private BorderPane content;
    private GridPane gridPane;

    private static ListView<Bruker> lvBrukere = new ListView<>();
    private VBox vBbruker;

    private static Label lblNavn;
    private static Label lblKjonn;
    private static Label lblAlder;
    private static Label lblTlf;
    private static Label lblInter;


    public MatchTab() {
        content = new BorderPane();
        content.setCenter(lvBrukere);

        this.setText("Mine match");
        this.closableProperty().setValue(false);

        lblNavn = new Label();
        lblKjonn = new Label();
        lblAlder = new Label();
        lblTlf = new Label();
        lblInter = new Label();



        vBbruker = new VBox();
        vBbruker.setSpacing(10);
        vBbruker.setPadding(new Insets(10));

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));

        Text scenetitle = new Text("Bruker info");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        gridPane.add(scenetitle, 0,0,2,1);

        gridPane.add(new Label("Navn: "),0,1);
        gridPane.add(lblNavn,1,1);

        gridPane.add(new Label("Kjønn: "), 0,2);
        gridPane.add(lblKjonn,1,2);

        gridPane.add(new Label("Alder: "), 0,3);
        gridPane.add(lblAlder,1,3);

        gridPane.add(new Label("Telefon: "),0,4);
        gridPane.add(lblTlf, 1,4);

        gridPane.add(new Label("Interesser: "),0,5);
        gridPane.add(lblInter,1,5);

        scenetitle = new Text("\n\n\nMine stalkers");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        gridPane.add(scenetitle, 2,6,2,6);


        content.setTop(gridPane);
        this.setContent(content);
    }


    public static void hentBrukerInfo(Bruker bruker) {

        System.out.println(bruker);
        lblNavn.setText(bruker.getNavn());
        lblKjonn.setText(bruker.getKjønn());
        lblAlder.setText(""+bruker.getAlder());
        lblTlf.setText(bruker.getTelefonummer());

        StringBuilder sb = new StringBuilder();
        for (String s : bruker.getInteresser()) {
            sb.append(s).append(" ");
        }
        lblInter.setText(sb.toString());
    }


    public static void hentStalkers(List<Bruker> brukereStalkers) {

        //System.out.println(brukereStalkers);

        ObservableList<Bruker> brukere = FXCollections.observableArrayList(brukereStalkers);
        lvBrukere.setItems(brukere);
        lvBrukere.setCellFactory(params -> new ListCell<>(){
            @Override
            protected void updateItem(Bruker bruker, boolean b) {
                super.updateItem(bruker, b);
                if (b || bruker == null) {
                    setText(null);
                } else {
                    setText(bruker.getNavn() + " " + bruker.getAlder() + " fra " + bruker.getBosted());
                }
            }
        });


        lvBrukere.setOnMouseClicked(mouseEvent -> {
            lblNavn.setText(lvBrukere.getSelectionModel().getSelectedItem().getNavn());
            lblKjonn.setText(lvBrukere.getSelectionModel().getSelectedItem().getKjønn());
            lblAlder.setText("" + lvBrukere.getSelectionModel().getSelectedItem().getAlder());
            lblTlf.setText(lvBrukere.getSelectionModel().getSelectedItem().getTelefonummer());

            StringBuilder sb = new StringBuilder();
            for (String s : lvBrukere.getSelectionModel().getSelectedItem().getInteresser()) {
                sb.append(s).append(" ");
            }
            lblInter.setText(sb.toString());
        });
    }

}

