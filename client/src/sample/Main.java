package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ClientController;
import sample.MainPane;

public class Main extends Application {
    ClientController cc = new ClientController();
    //    private LoginDialog loginDialog = new LoginDialog();
    /*
    public static MainPane mainPane = new MainPane();
    public LoginDialog loginDialogs;
    private static Stage stage;
    public static Stage getStage() { return stage;}
    */
    private static BorderPane root = new BorderPane();
    private static VBox topBox = new VBox();
    private LoginDialog loginDialogs = new LoginDialog();
    private static TabPane tabPane = new TabPane();
    public static Stage stage;




    //    private Scene scene1, scene2;


    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;






        if (cc.getIDFromFile() < 1 ) {
            primaryStage.setTitle("Registrer bruker");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(loginDialogs));
            primaryStage.show();

        } else {
            tabPane.getTabs().addAll(new SokeTab(), new MatchTab());
            topBox.getChildren().addAll(tabPane);
            root.setTop(topBox);
            primaryStage.setTitle("<3");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, 550, 400));
            primaryStage.show();
        }

    }

    public static void changeSceneToMainPane() {
        tabPane.getTabs().addAll(new SokeTab(), new MatchTab());
        topBox.getChildren().addAll(tabPane);
        root.setTop(topBox);
        Scene newScene = new Scene(root, 550, 400);
        stage.setScene(newScene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);


    }
}
