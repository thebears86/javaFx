package sample.controllers.market;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import sample.defaultConfig.DefaultConfig;
import sample.model.market.Fruit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class MarketController implements Initializable {



    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private final List<Fruit> fruits = new ArrayList<>();
    private MyListener myListener;

    private List<Fruit> getData() {
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;

        fruit = new Fruit();
        fruit.setName("Kiwi");
        fruit.setPrice(2.99);
        //TODO Handle Url Image
        //fruit.setImgSrc("http://file3.instiz.net/data/file3/2018/02/26/8/c/d/8cdfb988a83db43892488e3abc85d081.jpg");
        fruit.setImgSrc("/icons/marketImg/kiwi.png");
        fruit.setColor("6A7324");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Coconut");
        fruit.setPrice(3.99);
        fruit.setImgSrc("/icons/marketImg/coconut.png");
        fruit.setColor("A7745B");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Peach");
        fruit.setPrice(1.50);
        fruit.setImgSrc("/icons/marketImg/peach.png");
        fruit.setColor("F16C31");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Grapes");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/icons/marketImg/grapes.png");
        fruit.setColor("291D36");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Watermelon");
        fruit.setPrice(4.99);
        fruit.setImgSrc("/icons/marketImg/watermelon.png");
        fruit.setColor("22371D");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Orange");
        fruit.setPrice(2.99);
        fruit.setImgSrc("/icons/marketImg/orange.png");
        fruit.setColor("FB5D03");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("StrawBerry");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/icons/marketImg/strawberry.png");
        fruit.setColor("80080C");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Mango");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/icons/marketImg/mango.png");
        fruit.setColor("FFB605");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Cherry");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/icons/marketImg/cherry.png");
        fruit.setColor("5F060E");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Banana");
        fruit.setPrice(1.99);
        fruit.setImgSrc("/icons/marketImg/banana.png");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        return fruits;
    }

    private void setChosenFruit(Fruit fruit) {
        fruitNameLable.setText(fruit.getName());
        fruitPriceLabel.setText(DefaultConfig.CURRENCY + fruit.getPrice());
        InputStream resourceAsStream = getClass().getResourceAsStream(fruit.getImgSrc());
        Image image;

        if(null == resourceAsStream ){
            image = new Image(resourceAsStream);
        }else{
            image = new Image(resourceAsStream);
        }



        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        fruits.addAll(getData());
        if (fruits.size() > 0) {
            setChosenFruit(fruits.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Fruit fruit) {
                    setChosenFruit(fruit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (Fruit fruit : fruits) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/market/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruit, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
