package sample.controllers.replace;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class layoutController implements Initializable {

    @FXML public Button menu;
    @FXML public Button btn1;
    @FXML public Button btn2;
    @FXML public Button btn3;

    @FXML public AnchorPane leftMenu;
    @FXML public Button show;
    @FXML public Button hide;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideLeftMenu();
    }

    public void hideBtnClick(MouseEvent actionEvent) {
        hideLeftMenu();
    }

    public void showBtnClick(MouseEvent actionEvent) {
        showLeftMenu();
    }

    public void topMenuBtnClick(MouseEvent actionEvent) {

        if(show.isVisible()){
            showLeftMenu();
        }else{
            hideLeftMenu();
        }

    }



    private void showLeftMenu() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.millis(300L));
        slide.setNode(leftMenu);

        slide.setToX(0);
        slide.play();

        leftMenu.setTranslateX(-200);

        slide.setOnFinished(slideEvent -> {
            hide.setVisible(true);
            show.setVisible(false);

        });
    }




    private void hideLeftMenu() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.millis(300L));
        slide.setNode(leftMenu);

        slide.setToX(-200);
        slide.play();

        leftMenu.setTranslateX(0);

        slide.setOnFinished(slideEvent -> {
            hide.setVisible(false);
            show.setVisible(true);
        });
    }
}
