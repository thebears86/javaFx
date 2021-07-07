package sample.controllers.replace;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.listener.CommonChangeListener;
import sample.listener.TextFieldListener;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class content1Controller implements Initializable {

    @FXML public Button changedBtn;
    @FXML public DatePicker datePicker;
    @FXML public TextField textField;
    @FXML public ToggleButton toggle;
    @FXML public MenuButton menuBtn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        datePicker.setValue(LocalDate.now());

        /*
         * datepicker property listener
         */

        datePicker.valueProperty().addListener(CommonChangeListener.isPast);
        textField.textProperty().addListener(new TextFieldListener(this.textField));

        MenuItem menuItem = new MenuItem();
        menuItem.setText("AddCustom");
        menuItem.setDisable(true);

        menuBtn.getItems().add(menuItem);

    }

    public void changedBtnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("좆까");
        alert.setContentText("테스트");
        alert.showAndWait();

    }


}
