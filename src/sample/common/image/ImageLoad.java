package sample.common.image;

import com.sun.javafx.iio.ImageStorage;
import javafx.scene.image.Image;

public interface ImageLoad {

    Image loadImage(String path);

    void getType(String path);

    String getSourceFrom(String path);

}
