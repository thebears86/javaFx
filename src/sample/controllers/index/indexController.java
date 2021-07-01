package sample.controllers.index;

import animatefx.animation.ZoomIn;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class indexController {


    public AnchorPane archRoot;
    public ImageView btnBack;
    public Pane pnlSignIn;
    public StackPane pnlStack;
    public Pane pnlSignUp;
    public Button btnSignUp;
    public Circle btnClose;
    public Button btnSignIn;


    public void login(ActionEvent event){

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
