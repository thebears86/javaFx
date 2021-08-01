package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {

    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;


    public void settingLocale(){
        Locale.setDefault(getLocale(Locale.getDefault()));
    }

    public Locale getLocale(Locale locale) {
        if (locale == null) {
            locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));

        }
        return locale;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Set Locale for datePicker.
        settingLocale();
        //Locale.setDefault(Locale.KOREAN);




        Thread.setDefaultUncaughtExceptionHandler(Main::showError);


        System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행
        Font.loadFont(getClass().getResourceAsStream("/font/NanumSquareR.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/font/NanumGothic.ttf"), 10);


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/index/Index.fxml")));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //grab your root here
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        //move around here
        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setScene(scene);
        new animatefx.animation.FadeIn(root).play();
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            errorAlert(e);
        } else {
            System.err.println("An unexpected error occurred in "+t);
        }
    }

    private static void errorAlert(Throwable e) {

        e.printStackTrace();

        System.out.println(e.getMessage());
        System.out.println("===================================================");
        System.out.println(e.getCause().getMessage());
        System.out.println("===================================================");
        System.out.println(e.getCause().getCause().getMessage());
        System.out.println("===================================================");
        System.out.println(e.getLocalizedMessage());
        System.out.println("===================================================");
        System.out.println(e.getCause().getLocalizedMessage());



        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getCause().getCause().getMessage());
        alert.showAndWait();

    }

}
