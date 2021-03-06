package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
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

    private final Rectangle2D bounds = Screen.getScreens().get(0).getVisualBounds();
    private final double monitorWidth = bounds.getWidth();
    private final double monitorHeight = bounds.getHeight();


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

        } else if (mouseEvent.getSource() == btnUpdate) {

            fxml = FxmlScene.builder().url("/fxml/pop/dataForm.fxml").build();
            fxml.windowPop();
            loadStage(fxml, mouseEvent);

        } else if(mouseEvent.getSource() == btnClasses){
            fxml = FxmlScene.builder().url("/fxml/market/market.fxml").build();
            fxml.applicationPop();
            loadStage(fxml, mouseEvent);
        }


        else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("??????");
            alert.setHeaderText("???????????? ?????? ???????????????.");
            alert.setContentText("??????");
            alert.showAndWait();

            TextInputDialog dialog = new TextInputDialog("a");
            dialog.setTitle("??????");
            dialog.setHeaderText("???????????? ?????? ???????????????.");
            dialog.setContentText("??????");
            Optional<String> s = dialog.showAndWait();

            s.ifPresent(System.out::println);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String userInfo = location.getUserInfo();

    }

    private void loadStage(FxmlScene fxml , ActionEvent event){

        try {

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml.getUrl())));

            if(fxml.isPop()){

                stage = new Stage();
                scene = new Scene(root);
                stage.setScene(scene);



                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                //stage.setWidth(monitorWidth);
                //stage.setHeight(monitorHeight);
                //stage.setMaximized(true);
                stage.setResizable(false);

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
             *  - modal / parent ?????? focus ??????.
             * Modality.APPLICATION_MODAL
             *  - modal ??? ?????? ????????? parent focus ??????.
             */

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
