package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addToyFormController implements Initializable {
    private static final FileChooser fileChooser = new FileChooser();
    public Button closeButton;
    public TextField name;
    public ImageView imageView;
    public Button addToy;
    public ComboBox<Integer> toyDropRatePercent;
    public Image image;
    public Text number;
    int count = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createPercentCombobox();
        number.setText(String.valueOf(count));
    }

    private void createPercentCombobox(){
        if (mainFormController.sumOfToyDropRate>=100){
            ((Stage) this.closeButton.getScene().getWindow()).close();
        }
        ArrayList<Integer> percent = new ArrayList<>();
        for (int i = 1; i <= 100 - mainFormController.sumOfToyDropRate ; i++) {
            percent.add(i);
        }
        ObservableList<Integer> langs = FXCollections.observableArrayList(percent);
        toyDropRatePercent.setItems(langs);
        toyDropRatePercent.setValue(1);
    }

    public void close(ActionEvent actionEvent) {
        ((Stage) this.closeButton.getScene().getWindow()).close();
    }
    public static File openFileChooser(String description, String... extensions){
        fileChooser.setInitialDirectory(new File(new File(".").getAbsolutePath()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extensions));
        File file = fileChooser.showOpenDialog(Main.primaryStage);
        System.out.println(file);
        return file;
    }

    public void loadImage(ActionEvent actionEvent) throws FileNotFoundException {
        File imageFile = openFileChooser("Image File", "*.png", "*.jpg");
        if (imageFile.length() <= 0) {
            System.out.println(imageFile.length());
//            throw new IllegalArgumentException();
        }
        InputStream inputStream = new FileInputStream(imageFile);
        image = new Image(inputStream);
        imageView.setImage(image);
    }

    public void addToy(ActionEvent actionEvent) {
        if (mainFormController.sumOfToyDropRate>=100){
            ((Stage) this.closeButton.getScene().getWindow()).close();
        }
        try{
            Toy toy = new Toy(name.getText(), count, toyDropRatePercent.getValue(), image );
            mainFormController.getCustomTilePane().getChildren().add(toy);
            mainFormController.sumOfToyDropRate = mainFormController.sumOfToyDropRate + toyDropRatePercent.getValue();
            createPercentCombobox();
//            ((Stage) this.closeButton.getScene().getWindow()).close();
//            не закрываем чтобы добавлять игрушки не по одной)
        } catch (Exception ignored){}

    }


    public void increaseTheNumber(ActionEvent actionEvent) {
        countOperation(1);
        displayCount();
    }

    public void reduceTheNumber(ActionEvent actionEvent) {
        countOperation(-1);
        displayCount();
    }

    public void countOperation(int i){
        count = count + i;
        if(count<1){
            count = 1;
        }
    }
    private void displayCount(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                number.setText(String.valueOf(count));
            }
        });
    }
}
