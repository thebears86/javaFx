package sample.controllers.replace;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class content3Controller {
    @FXML public Button changedBtn;

    public void changedBtnAction(MouseEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("좆까");
        alert.setContentText("테스트");
        alert.showAndWait();

    }


}
