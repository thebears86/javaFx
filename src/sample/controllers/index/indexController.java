package sample.controllers.index;

import animatefx.animation.ZoomIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

public class indexController {


    public AnchorPane archRoot;
    public ImageView btnBack;
    public Pane pnlSignIn;
    public StackPane pnlStack;
    public Pane pnlSignUp;
    public Button btnSignUp;
    public Circle btnClose;
    public Button btnSignIn;


    public void login(MouseEvent event){
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Home.fxml")));
            Stage primaryStage = new Stage();
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

            primaryStage.setTitle("KeepToo SMSys");
            primaryStage.getIcons().add(new Image("/resources/icons/icon.png"));
            primaryStage.setScene(new Scene(root));

            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleMouseEvent(MouseEvent event) {
        if(event.getSource() == btnClose) {
            new animatefx.animation.FadeOut(archRoot).play();
            System.exit(0);
        }
        if(event.getSource().equals(btnBack)) {
            new ZoomIn(pnlSignIn).setSpeed(2L).play();
            pnlSignIn.toFront();
        }
    }

    public void handleButtonAction(ActionEvent event) {
        if(event.getSource().equals(btnSignUp)) {
            new ZoomIn(pnlSignUp).setSpeed(2L)
                    .play();
            pnlSignUp.toFront();
        }
    }
}
