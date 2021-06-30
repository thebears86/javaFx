package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToSceneOne(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources.fxml")));

        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.show();
    }

    public void switchToSceneTwo(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample2.fxml")));

        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.show();
    }




}
