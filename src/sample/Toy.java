package sample;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Toy extends ImageView {
    private String name;
    private int count;
    private int toyDropRatePercent;
    private Image image;
    private boolean reverse = false;
    private final DropShadow aqua = new DropShadow( 20, Color.AQUA );
    private final DropShadow red = new DropShadow( 20, Color.RED );
    private final DropShadow gold = new DropShadow( 50, Color.GOLD );

    public Toy(String name, int count, int toyDropRatePercent, Image image) {
        super(image);
        this.name = name;
        this.count = count;
        this.toyDropRatePercent = toyDropRatePercent;
        this.image = image;
    }

    public void reversStyle(){
        if(reverse){
            this.setStyle("-fx-rotate: -3");
            this.setEffect(aqua);
            reverse = false;
        } else {
            this.setStyle("-fx-rotate: 3");
            this.setEffect(red);
            reverse = true;
        }
    }

    public int getCount() {
        return count;
    }

    public void reduceCount() {
        this.count = count - 1;
    }



    public void setStyleDefault(){
            this.setStyle("-fx-rotate: 0");
            this.setEffect(aqua);
    }

    public void setWinStyle(int rotate){
        this.setStyle("-fx-rotate: " + rotate);
        this.setEffect(gold);
    }

    public int getToyDropRatePercent() {
        return toyDropRatePercent;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "name='" + name + '\'' +
                '}';
    }
}
