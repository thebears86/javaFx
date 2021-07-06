package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FxmlScene;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML public Button btnDashboard;
    @FXML public Button btnStudents;
    @FXML public Button btn_Timetable;
    @FXML public Button btnSettings;
    @FXML public Button btnUpdate;
    @FXML public Button btnClasses;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private AnchorPane content;


    @FXML
    private void handleButtonClicks(ActionEvent mouseEvent) {

        FxmlScene fxml = null;

        if (mouseEvent.getSource() == btnDashboard) {

            fxml = FxmlScene.builder().url("/fxml/Dashboard.fxml").build();
            fxml.windowPop();
            loadStage(fxml, mouseEvent);

        } else if (mouseEvent.getSource() == btnStudents) {

            fxml = FxmlScene.builder().url("/fxml/Students.fxml").build();
            fxml.applicationPop();
            loadStage(fxml, mouseEvent);

        } else if (mouseEvent.getSource() == btn_Timetable) {

            fxml = FxmlScene.builder().url("/fxml/Timetable.fxml").build();
            fxml.applicationPop();
            loadStage(fxml, mouseEvent);

        } else if (mouseEvent.getSource() == btnSettings) {

            fxml = FxmlScene.builder().url("/fxml/replace/Layout.fxml").build();
            fxml.applicationPop();
            loadStage(fxml, mouseEvent);

        }


        else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("알림");
            alert.setHeaderText("존재하지 않는 화면입니다.");
            alert.setContentText("얌마");
            alert.showAndWait();

            TextInputDialog dialog = new TextInputDialog("a");
            dialog.setTitle("알림");
            dialog.setHeaderText("존재하지 않는 화면입니다.");
            dialog.setContentText("얌마");
            Optional<String> s = dialog.showAndWait();

            s.ifPresent(System.out::println);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadStage(FxmlScene fxml , ActionEvent event){

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml.getUrl())));

            if(fxml.isPop()){

                stage = new Stage();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.getIcons().add(new Image("icons/icon.png"));
                stage.initModality(fxml.getModality());

            }else{

                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

            }
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void loadStageReplace(String fxml ,ActionEvent event){

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void loadStageModal(String fxml) {

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            stage = new Stage();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("icons/icon.png"));

            /**
             * Modality.APPLICATION_MODAL
             *  - modal / parent 교차 focus 가능.
             * Modality.APPLICATION_MODAL
             *  - modal 이 켜져 있을때 parent focus 불가.
             */

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
