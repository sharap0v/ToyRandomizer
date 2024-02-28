package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class mainFormController implements Initializable {
    public static mainFormController mainFormController;
//    private static final FileChooser fileChooser = new FileChooser();
    public static int sumOfToyDropRate = 0;
    private static TilePane customTilePane;
    public Pane toysPane;
    public Button playButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainFormController = this;
        customTilePane = new TilePane();
        customTilePane.setPadding(new Insets(10, 10, 10, 10));
        customTilePane.setHgap(10);
        customTilePane.setVgap(10);
        toysPane.getChildren().add(customTilePane);
        ScrollPane sp = new ScrollPane(customTilePane);
        sp.setFitToWidth(true);
    }

    public void play(ActionEvent actionEvent) throws InterruptedException {
        if (customTilePane.getChildren().size()<2) {
            return;
        }
        int chanceCount = 0;
        for (int i = 0; i < customTilePane.getChildren().size(); i++) {
            chanceCount = chanceCount + ((Toy)customTilePane.getChildren().get(i)).getToyDropRatePercent();
            //защита от дурака если сумма процентов всех предметов не 100

        }
        Random random = new Random();


        int index = random.nextInt(chanceCount);

        for (int i = 0; i < customTilePane.getChildren().size(); i++) { // Ищем элемент, которому принадлежит этот индекс
            index = index -  ((Toy)customTilePane.getChildren().get(i)).getToyDropRatePercent();
            if(index < 0) {
                index = i;
                break;
            }
        }

        playButton.setVisible(false);
        int delay = 60;
        Timeline timeline = new Timeline();
        timeline.setCycleCount(6000/delay);
        final int[] i = {0,0};
        KeyFrame keyFrame = new KeyFrame(Duration.millis(delay),
                event -> Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(i[0] >=customTilePane.getChildren().size()) {
                            i[0] = 0;
                        }
                        ((Toy)customTilePane.getChildren().get(i[0])).reversStyle();
//                        customTilePane.getChildren().get(i[1]).setEffect(new DropShadow( 20, Color.AQUA ));

                        try {
                            Thread.sleep(((Toy)customTilePane.getChildren().get(i[0])).getToyDropRatePercent());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        customTilePane.getChildren().get(i[0]).setEffect(new DropShadow( 20, Color.RED ));
                        i[1] = i[0];
                        i[0]++;
                    }
                })
        );
        int finalIndex = index;
        timeline.setOnFinished(event -> Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i1 = 0; i1 < customTilePane.getChildren().size(); i1++) {
                    if(i1!=finalIndex){
                        ((Toy)customTilePane.getChildren().get(i1)).setStyleDefault();
                    }
                }
                int winDelay = 10;
                Timeline winTimeline = new Timeline();
                winTimeline.setCycleCount(3600/winDelay);
                final int[] rotate = {360};
                KeyFrame winKeyFrame = new KeyFrame(Duration.millis(winDelay),
                        event -> Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ((Toy)customTilePane.getChildren().get(finalIndex)).setWinStyle(rotate[0]);
                                rotate[0] = rotate[0] -1;
                            }
                        })
                );
                winTimeline.getKeyFrames().add(winKeyFrame);
                winTimeline.play();
                winTimeline.setOnFinished(event -> Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ((Toy)customTilePane.getChildren().get(finalIndex)).setStyleDefault();
                        ((Toy)customTilePane.getChildren().get(finalIndex)).reduceCount();
                        UT.writeFile(((Toy)customTilePane.getChildren().get(finalIndex)));
                        if(((Toy)customTilePane.getChildren().get(finalIndex)).getCount()<=0) {
                            sumOfToyDropRate = sumOfToyDropRate - ((Toy)customTilePane.getChildren().get(finalIndex)).getToyDropRatePercent();
                            customTilePane.getChildren().remove(customTilePane.getChildren().get(finalIndex));
                        }
                        playButton.setVisible(true);
                    }
                }));
            }
        }));
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void openAddToyForm(ActionEvent actionEvent) throws IOException {
        createStage("/fxml/addToyForm.fxml", "/css/dark-theme.css");
    }

    public void save(ActionEvent actionEvent) {
    }


    public static void createStage(String fxml, String css) throws IOException {
        if(sumOfToyDropRate>=100) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(mainFormController.class.getResource(fxml));
        Parent root = fxmlLoader.load();
        root.getStylesheets().add(mainFormController.class.getResource(css).toExternalForm());
        Stage stage = new Stage();
        Scene scene = new Scene(root, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage);
        stage.setX(Main.primaryStage.getX() + 200);
        stage.setY(Main.primaryStage.getY() + 100);
        stage.setResizable(false);
        stage.show();
    }

    public static TilePane getCustomTilePane() {
        return customTilePane;
    }
}
