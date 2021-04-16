package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.ClientController;

import java.util.ArrayList;
import java.util.List;

public class LoginDialog extends BorderPane {

    private ClientController cc = new ClientController();

    private GridPane gridPane;
    private TextField tFNavn;
    private TextField tFBosted;
    private TextField tFAlder;
    private TextField tFKjonn;
    private TextField tFTlf;
    private Label lbInfo;

    private ToggleGroup tgKjonn;
    private RadioButton rbMann;
    private RadioButton rbKvinne;

    private HBox hbBot;
    private Button btReg;
    private Button btAvslutt;

    private ListView<String> interesser;



    public LoginDialog() {

        this.setPrefWidth(300);

        tgKjonn = new ToggleGroup();
        VBox vBkjonn = new VBox();
        vBkjonn.setSpacing(5);
        rbMann = new RadioButton("Mann");
        rbMann.setToggleGroup(tgKjonn);
        rbKvinne = new RadioButton("Kvinne");
        rbKvinne.setToggleGroup(tgKjonn);


        vBkjonn.getChildren().addAll(rbMann,rbKvinne);



        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25,25,25,25));

        Text scenetitle = new Text("Registrer bruker");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(scenetitle, 0,0,2,1);

        lbInfo = new Label();
        lbInfo.setFont(Font.font("Consolas", FontWeight.NORMAL, 10));
        lbInfo.setTextFill(Color.RED);
        gridPane.add(lbInfo, 0,1,2,1);

        tFNavn = new TextField();
        tFNavn.setPromptText("Navn");
        gridPane.add(tFNavn, 0,2);

        tFBosted = new TextField();
        tFBosted.setPromptText("Bosted");
        gridPane.add(tFBosted,0,3);

        tFAlder = new TextField();
        tFAlder.setPromptText("Alder");
        gridPane.add(tFAlder, 0, 4);

        tFTlf = new TextField();
        tFTlf.setPromptText("Telefon");
        gridPane.add(tFTlf,0,5);


        gridPane.add(vBkjonn, 0,6);



        scenetitle = new Text("Velg interesser (ctrl + click): ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        gridPane.add(scenetitle, 0,7,2,1);


        hbBot = new HBox();
        btReg = new Button("Registrer");
        btAvslutt = new Button("Avslutt");
        hbBot.getChildren().addAll(btReg, btAvslutt);
        hbBot.setPadding(new Insets(10));
        hbBot.setSpacing(5);

        interesser = new ListView<>();

        btReg.setOnAction(e -> registrerBruker());

        btAvslutt.setOnAction(e -> System.exit(0));


        this.setTop(gridPane);
        this.setCenter(interesseBox());
        this.setBottom(hbBot);
    }

    private void registrerBruker() {

        String brukerNavn = "";
        String bosted = "";
        int alder = -1;
        String tlf = "";
        String kjønn = "";

        if (tFNavn.getText().length() <= 0) {
            lbInfo.setText("Må fylle ut navn");
        } else {
            brukerNavn = tFNavn.getText();
        }

        if (tFBosted.getText().length() <= 0) {
            lbInfo.setText("Bosted mangler");
        } else {
            bosted = tFBosted.getText();
        }

        if (tFAlder.getText().length() <= 0) {
            lbInfo.setText("My fylle ut alder");
        }
        if(tFAlder.getText().matches("[0-9]+") )    {
            alder = Integer.parseInt(tFAlder.getText());
        } else {
            alder  = -1;
            lbInfo.setText("Ulovlig verdi i alder");
        }


        if (tFTlf.getText().length() <= 0) {
            lbInfo.setText("Må fylle ut telefon");
        } else {
            tlf = tFTlf.getText();
        }

        if (tgKjonn.getSelectedToggle() == rbMann) {
            kjønn = "Mann";
        } else if (tgKjonn.getSelectedToggle() == rbKvinne) {
            kjønn = "Kvinne";
        } else {
            lbInfo.setText("Velg kjønn");
        }



        ObservableList<String> selectedInter = interesser.getSelectionModel().getSelectedItems();
        if (selectedInter.isEmpty()) {
            lbInfo.setText("Velg minst 1 interesse");
        }
        List<String> interesseArr = new ArrayList<>(selectedInter);


        if (brukerNavn.length() > 0  && bosted.length() > 0 && alder > 0 && tlf.length() > 0 && kjønn.length() > 0 && interesseArr.size() > 0) {
            System.out.println(brukerNavn + " " + bosted + " " + alder + " " + tlf + " " + kjønn + " " + interesseArr);
            lbInfo.setText("");

            // ClientController.....
            int value = cc.register(brukerNavn, kjønn, alder, interesseArr, bosted, tlf);
            if(value > 0) {
                Main.changeSceneToMainPane();
            }


        }







    }



    private ListView<String> interesseBox() {
//        ListView<String> interesser = new ListView<>();
//        interesser.setMaxHeight(100);
        interesser.setPrefWidth(50);
        interesser.setPrefHeight(100);
        interesser.setMaxHeight(100);


        ObservableList<String> ol = FXCollections.observableArrayList(
                "Ski",
                "Jogging",
                "Netflix",
                "Chille",
                "Stupe kråke"
        );

        interesser.setItems(ol);
        interesser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

//        interesser.getSelectionModel().selectedItemProperty().addListener(
//                (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
//            ObservableList<String> selectedItems = interesser.getSelectionModel().getSelectedItems();
//
//
//        });

        return interesser;
    }
}

