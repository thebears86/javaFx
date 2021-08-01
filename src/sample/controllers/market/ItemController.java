package sample.controllers.market;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.model.market.Fruit;

import java.io.InputStream;
import java.util.Objects;

public class ItemController {
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView img;

    private final static String defaultImagePath = "/icons/marketImg/zg3n4gcd.jpg";

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(fruit);
    }

    private Fruit fruit;
    private MyListener myListener;

    public void setData(Fruit fruit, MyListener myListener) {
        this.fruit = fruit;
        this.myListener = myListener;
        nameLabel.setText(fruit.getName());
        priceLabel.setText(MarketController.CURRENCY + fruit.getPrice());

        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(fruit.getImgSrc())));
            img.setImage(image);

        } catch (Exception e){
            Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(defaultImagePath)));
            img.setImage(defaultImage);
        }


    }
}
