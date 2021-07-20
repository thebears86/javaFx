package sample.controllers.replace;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.common.ExcelValidator;
import sample.controllers.DataController;
import sample.model.Content2Model;
import sample.model.ContentExtendType;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Content2Controller extends DataController {
    @FXML public Button changedBtn;
    @FXML public TableColumn<Content2Model , Long> rowNum;
    @FXML public TableColumn<Content2Model , String> isbn;
    @FXML public TableColumn<Content2Model , String> bookName;
    @FXML public TableColumn<Content2Model , String> publisher;
    @FXML public TableColumn<Content2Model , String> data1;
    @FXML public TableColumn<Content2Model , LocalDate> regDate;
    @FXML public TableColumn<Content2Model , LocalTime> regTime;

    @FXML public TableColumn<ContentExtendType , String> content1;
    @FXML public TableColumn<ContentExtendType , String> content2;
    @FXML public TableColumn<ContentExtendType , String> content3;
    @FXML public TableColumn<ContentExtendType , String> content4;
    @FXML public TableColumn<ContentExtendType , String> content5;
    @FXML public TableColumn<ContentExtendType , String> content6;

    @FXML public TableView<ContentExtendType> tab3Table;

    @Override
    public void initListData(List<?> list) {

        if(list.isEmpty()){
            return;
        }

        if(!(list.get(0) instanceof ContentExtendType)){
            throw new IllegalArgumentException("실패 이새끼야.");
        }

        List<ContentExtendType> targetList = (List<ContentExtendType>) list;

        tab3Table.getItems().addAll(targetList);
    }

    @Override
    public void initData(Object o) {

    }

    public void dblClick(){
        tab3Table.setEditable(true);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rowNum.setCellValueFactory      (cellData -> cellData.getValue().getRowNum());

        isbn.setCellValueFactory        (cellData -> cellData.getValue().getIsbn());
        isbn.setCellFactory(TextFieldTableCell.forTableColumn());


        bookName.setCellValueFactory    (cellData -> cellData.getValue().getBookName());
        bookName.setCellFactory(TextFieldTableCell.forTableColumn());

        publisher.setCellValueFactory   (cellData -> cellData.getValue().getPublisher());
        publisher.setCellFactory(TextFieldTableCell.forTableColumn());

        data1.setCellValueFactory       (cellData -> cellData.getValue().getData1());

        regDate.setCellValueFactory     (cellData -> cellData.getValue().getRegDate());

        regTime.setCellValueFactory     (cellData -> cellData.getValue().getRegTime());




        tab3Table.setOnMouseClicked(cellEditHandler());
        tab3Table.setOnKeyReleased(keyEventEventHandler());

        tab3Table.getColumns().forEach(x->{
            x.addEventHandler(TableColumn.CellEditEvent.ANY , event -> editResume());
        });



    }

    public EventHandler<MouseEvent> cellEditHandler(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                tab3Table.getSelectionModel().setCellSelectionEnabled(false);

                /*
                 * 더블클릭시에 편집모드 진입
                 */
                if(event.getClickCount() >= 2){
                    ContentExtendType selectedItem = tab3Table.getSelectionModel().getSelectedItem();
                    if(selectedItem.isEditable()){
                        dblClick();
                    }
                }
            }
        };
    }





    public EventHandler<KeyEvent> keyEventEventHandler(){
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                tab3Table.getSelectionModel().setCellSelectionEnabled(true);



                if(keyEvent.getCode().isWhitespaceKey()){

                }




                System.out.println(keyEvent.getEventType());
            }
        };
    }


    public void changedBtnAction(MouseEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("좆까");
        alert.setContentText("테스트");
        alert.showAndWait();

    }


    public void loadFile(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Excel Upload");
        File file = fileChooser.showOpenDialog(this.tab3Table.getScene().getWindow());

        ExcelValidator.checkerWithError(file);

    }




    public void editResume() {

        tab3Table.requestFocus();

    }
}
