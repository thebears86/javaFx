package sample.controllers.exception;

import javafx.scene.control.Alert;

import java.beans.ExceptionListener;


public class ExceptionController implements ExceptionListener {

    /**
     * This method is called when a recoverable exception has
     * been caught.
     *
     * @param e The exception that was caught.
     */
    @Override
    public void exceptionThrown(Exception e) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getLocalizedMessage());
        alert.showAndWait();





    }
}
