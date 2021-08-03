package sample.common.image;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;
import sample.Main;
import sample.defaultConfig.DefaultConfig;

import java.beans.JavaBean;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ImageLoadService implements ImageLoad{

    private final int readLimit = 16 * 1024;

    @Override
    public Image loadImage(String path){

        try {
            if(path.contains("http")){

                    return readGifFromUrl(path);

            }else{

                if(path.contains("gif")){
                    return readGifFromLocalDisk(path);
                }else{
                    return getImage(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Image(DefaultConfig.defaultImagePath);
        }
    }

    private Image readGifFromLocalDisk(String path) throws IOException{

        Path path1 = Paths.get(path);



        if(path1.isAbsolute()){
            return readGifFromAbsolutePath(path);
        }else{
            return readGifFromRelativePath(path);
        }
    }


    private Image readGifFromAbsolutePath(String path) throws IOException{
        FileInputStream fis = new FileInputStream(path);
        try(InputStream inputStream = new BufferedInputStream(fis , readLimit)){

            return getImage(inputStream);
        }
    }

    private Image getImage(InputStream inputStream) {
        Image image = new Image(inputStream);
        image.progressProperty().addListener((observable, oldValue, newValue) -> System.out.println("Progress: " + Math.rint(newValue.doubleValue() * 100) + "%"));
        return image;
    }

    private Image readGifFromRelativePath(String path) throws IOException{
        try(InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream(path)), readLimit)){
            return getImage(inputStream);
        }
    }



    private Image readGifFromUrl(String path) throws IOException{

        URLConnection connection;

        try {
            connection = new URL(path).openConnection();
            connection.setRequestProperty("User-Agent" , "Wget/1.13.4(linux-gnu)");
            connection.setReadTimeout(30000);
            InputStream inputStream = connection.getInputStream();
            return getImage(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }

    }

}
