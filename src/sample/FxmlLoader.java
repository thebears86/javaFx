package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FxmlLoader {

    private Pane view;

    public Pane getPage(model.FxmlScene fxmlScene){

        FXMLLoader loader = new FXMLLoader();
        URL url = Objects.requireNonNull(getClass().getResource(fxmlScene.getUrl()));
        loader.setLocation(url);
        try {
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }




}
