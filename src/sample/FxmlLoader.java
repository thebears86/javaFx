package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FxmlLoader {

    private Pane view;


    public Pane getPage(model.FxmlScene fxmlScene){

        URL url = Objects.requireNonNull(getClass().getResource(fxmlScene.getUrl()));

        try {
            view = new FXMLLoader().load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }



}
