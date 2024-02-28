package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainForm.fxml"));
        root.getStylesheets().add(getClass().getResource("/css/dark-theme.css").toExternalForm());
        primaryStage.setTitle("ToysRandomizer");
        primaryStage.setScene(new Scene(root, 300, 275));
        Main.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
