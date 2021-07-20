package sample.controllers.replace;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.FxmlScene;
import sample.FxmlLoader;
import sample.model.ContentExtendType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {

    @FXML public Button menu;
    @FXML public Button btn1;
    @FXML public Button btn2;
    @FXML public Button btn3;

    @FXML public AnchorPane leftMenu;
    @FXML public Button show;
    @FXML public Button hide;
    @FXML public AnchorPane context;
    @FXML public BorderPane rootPane;

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

    public void btn1Action(MouseEvent actionEvent) throws IOException {

        FxmlScene fxmlScene = FxmlScene.builder().url("/fxml/replace/content1.fxml").build();

        FXMLLoader loader = new FXMLLoader();
        URL url = Objects.requireNonNull(getClass().getResource(fxmlScene.getUrl()));
        loader.setLocation(url);

        rootPane.setCenter(loader.load());

        /*
         * FXMLLoader.load() 이후에 컨트롤러에 접근가능하다.
         * load 이전에 호출하면 NullPointerException 발생.
         */
        Content1Controller controller = loader.getController();
        controller.initData("TESTDATA");

    }

    public void btn2Action(MouseEvent actionEvent) throws IOException {

        FxmlScene fxmlScene = FxmlScene.builder().url("/fxml/replace/content2.fxml").build();
        FXMLLoader loader = new FXMLLoader();
        URL url = Objects.requireNonNull(getClass().getResource(fxmlScene.getUrl()));
        loader.setLocation(url);

        rootPane.setCenter(loader.load());

        /*
         * FXMLLoader.load() 이후에 컨트롤러에 접근가능하다.
         * load 이전에 호출하면 NullPointerException 발생.
         */
        Content2Controller controller = loader.getController();

        List<ContentExtendType> list = new ArrayList<>();

        for(long i = 0 ; i < 10000 ; i++){

            list.add(
                    ContentExtendType.builder()
                            .rowNum(i)
                            .isbn("isbn"+i)
                            .publisher("publisher"+i)
                            .data1("data1"+i)
                            .bookName("bookName"+i)
                            .content1("content1_"+i)
                            .content2("content2_"+i)
                            .content3("content3_"+i)
                            .content4("content4_"+i)
                            .content5("content5_"+i)
                            .content6("content6_"+i)
                            .build()
            );
        }

        controller.initListData(list);
    }

    public void btn3Action(MouseEvent actionEvent){


        FxmlScene url = FxmlScene.builder().url("/fxml/replace/content3.fxml").build();

        rootPane.setCenter(new FxmlLoader().getPage(url));
    }


    private void showLeftMenu() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.millis(300L));
        slide.setNode(leftMenu);

        slide.setToX(0);
        slide.play();

        leftMenu.setTranslateX(-200);

        TranslateTransition slideContext = new TranslateTransition();
        slideContext.setDuration(Duration.millis(300L));
        slideContext.setNode(context);
        slideContext.setToX(0);
        slideContext.play();

        context.setTranslateX(200);

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
