package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행
        Font.loadFont(getClass().getResourceAsStream("/font/NanumSquareR.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/font/NanumGothic.ttf"), 10);




        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Home.fxml")));
        primaryStage.setTitle("KeepToo SMSys");
        primaryStage.getIcons().add(new Image("/resources/icons/icon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        /*
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources.fxml")));

        //Scene deptOne = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample2.fxml")));

        primaryStage.setTitle("Hello World");

        Scene scene = new Scene(root, 1080, 1024);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setScene(scene);

        //primaryStage.setFullScreen(true);
        //primaryStage.setFullScreenExitHint("EXIT");
        //primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("q"));

        primaryStage.show();

        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("sample2.fxml"));

        AnchorPane pane = load.load();*/


    }


    public static void main(String[] args) {
        launch(args);
    }
}
