package sample.listener;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class CommonChangeListener {

    public CommonChangeListener() {

    }

    public static final ChangeListener<LocalDate> isPast = (observableValue, oldValue, newValue) -> {
        System.out.println("observableValue = " + observableValue + ", oldValue = " + oldValue + ", newValue = " + newValue);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("changeListener alert");
        alert.showAndWait();
    };






}
