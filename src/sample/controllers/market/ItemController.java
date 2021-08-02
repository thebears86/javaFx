package sample.controllers.market;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.common.image.ImageLoad;
import sample.common.image.ImageLoadService;
import sample.defaultConfig.DefaultConfig;
import sample.model.market.Fruit;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class ItemController{
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView img;



    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(mouseEvent, fruit);
    }

    private final ImageLoad imageLoad = new ImageLoadService();

    private Fruit fruit;
    private MyListener myListener;

    public void setData(Fruit fruit, MyListener myListener) {
        this.fruit = fruit;
        this.myListener = myListener;
        nameLabel.setText(fruit.getName());
        priceLabel.setText(DefaultConfig.CURRENCY + fruit.getPrice());

        img.setImage(imageLoad.loadImage(fruit.getImgSrc()));
    }
/*
    private Image readGifFromInnerCode(String path) throws IOException{
        final int readLimit = 16 * 1024;
        try(InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream(path) , readLimit)){
            return new Image(inputStream);
        }
    }

    private Image readGifFromLocalDisk(String path) throws IOException{
        final int readLimit = 16 * 1024;
        FileInputStream fis = new FileInputStream(path);
        try(InputStream inputStream = new BufferedInputStream(fis , readLimit)){
            return new Image(inputStream);
        }
    }

    private Image readGifFromUrl(String path) throws IOException{
        final int readLimit = 16 * 1024;
        URLConnection connection = null;

        try {
            connection = new URL(path).openConnection();
            connection.setRequestProperty("User-Agent" , "Wget/1.13.4(linux-gnu)");
            InputStream inputStream = connection.getInputStream();
            return new Image(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }



    }*/

}
