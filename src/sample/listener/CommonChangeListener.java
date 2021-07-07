package sample.listener;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class CommonChangeListener {

    public CommonChangeListener() {

    }

    public static final ChangeListener<LocalDate> isPast = (observableValue, oldValue, newValue) -> {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("changeListener alert");
        alert.showAndWait();
    };




}
