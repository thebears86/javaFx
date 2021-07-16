package sample.controllers.replace;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.controllers.DataController;
import sample.model.Content2Model;
import sample.model.ContentExtendType;

import java.lang.invoke.SwitchPoint;
import java.net.URL;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class Content2Controller extends DataController {
    @FXML public Button changedBtn;
    @FXML public TableColumn<? extends Content2Model , Long> rowNum;
    @FXML public TableColumn<? extends Content2Model , String> isbn;
    @FXML public TableColumn<? extends Content2Model , String> bookName;
    @FXML public TableColumn<? extends Content2Model , String> publisher;
    @FXML public TableColumn<? extends Content2Model , String> data1;
    @FXML public TableColumn<? extends Content2Model , LocalDate> regDate;
    @FXML public TableColumn<? extends Content2Model, LocalTime> regTime;
    @FXML public TableColumn<ContentExtendType, String> content1;
    @FXML public TableColumn<ContentExtendType , String> content2;
    @FXML public TableColumn<ContentExtendType , String> content3;
    @FXML public TableColumn<ContentExtendType , String> content4;
    @FXML public TableColumn<ContentExtendType , String> content5;
    @FXML public TableColumn<ContentExtendType , String> content6;


    @FXML public TableView<ContentExtendType> tab3Table;

    private ObservableList<ContentExtendType> tableData = FXCollections.observableArrayList();

    private Object oldValue;


    public void table3EventInit(){

        tab3Table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tab3Table.getSelectionModel().getSelectedItem() != null){
                    if(mouseEvent.getPickResult().getIntersectedNode().equals(oldValue)){
                        tab3Table.getSelectionModel().clearSelection();
                        oldValue = null;
                    } else {
                        oldValue = mouseEvent.getPickResult().getIntersectedNode();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText(String.valueOf(tab3Table.getSelectionModel().getSelectedItem().getRowNum().getValue()));
                        alert.showAndWait();
                    }
                }
            }
        });

        tab3Table.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                tab3Table.setEditable(true);
                // allows the individual cells to be selected
                tab3Table.getSelectionModel().cellSelectionEnabledProperty().set(true);

                if(keyEvent.getCode().isLetterKey() || keyEvent.getCode().isDigitKey()){
                    editFocusedCell();

                }else if( keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.TAB ){

                    tab3Table.getSelectionModel().selectNext();
                    keyEvent.consume();

                }else if(keyEvent.getCode() == KeyCode.LEFT){
                    tab3Table.getSelectionModel().selectPrevious();
                    keyEvent.consume();
                }else if(keyEvent.getCode() == KeyCode.ESCAPE){
                    tab3Table.setEditable(false);
                    // cancel allows the individual cells to be selected
                    tab3Table.getSelectionModel().cellSelectionEnabledProperty().set(false);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    private void editFocusedCell() {
        final TablePosition<ContentExtendType, ?> focusedCell = tab3Table.focusModelProperty().get().focusedCellProperty().get();

        tab3Table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }


    @Override
    public void initData(Object o) {



        if (o instanceof ArrayList) {

            ArrayList<?> list = (ArrayList<?>) o;

            if(list.stream().anyMatch(x-> !(x instanceof ContentExtendType))){
                throw new IllegalArgumentException("실패 이새끼야.");
            }
            Platform.runLater(()->{
                tab3Table.getItems().addAll((Collection<? extends ContentExtendType>) list);
            });

        } else if(o instanceof ContentExtendType) {
            tab3Table.getItems().add((ContentExtendType) o);

        } else{
            throw new IllegalArgumentException("실패 이새끼야.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rowNum.setCellValueFactory       (cellData -> cellData.getValue().getRowNum());
        isbn.setCellValueFactory         (cellData -> cellData.getValue().getIsbn());
        bookName.setCellValueFactory     (cellData -> cellData.getValue().getBookName());
        publisher.setCellValueFactory    (cellData -> cellData.getValue().getPublisher());
        data1.setCellValueFactory        (cellData -> cellData.getValue().getData1());
        regDate.setCellValueFactory      (cellData -> cellData.getValue().getRegDate());
        regTime.setCellValueFactory      (cellData -> cellData.getValue().getRegTime());
        content1.setCellValueFactory     (cellData -> cellData.getValue().getContent1());
        content2.setCellValueFactory     (cellData -> cellData.getValue().getContent2());
        content3.setCellValueFactory     (cellData -> cellData.getValue().getContent3());
        content4.setCellValueFactory     (cellData -> cellData.getValue().getContent4());
        content5.setCellValueFactory     (cellData -> cellData.getValue().getContent5());
        content6.setCellValueFactory     (cellData -> cellData.getValue().getContent6());




        table3EventInit();
    }

    public void changedBtnAction(MouseEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("좆까");
        alert.setContentText("테스트");
        alert.showAndWait();

    }


}
