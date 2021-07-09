package sample.controllers.replace;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.controllers.DataController;
import sample.model.Content2Model;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Content2Controller extends DataController {
    @FXML public Button changedBtn;
    @FXML public TableColumn<Content2Model , Long> rowNum;
    @FXML public TableColumn<Content2Model , String> isbn;
    @FXML public TableColumn<Content2Model , String> bookName;
    @FXML public TableColumn<Content2Model , String> publisher;
    @FXML public TableColumn<Content2Model , String> data1;
    @FXML public TableColumn<Content2Model , LocalDate> regDate;
    @FXML public TableColumn<Content2Model , LocalTime> regTime;
    @FXML public TableColumn c2;
    @FXML public TableColumn c3;
    @FXML public TableColumn c4;
    @FXML public TableColumn c5;
    @FXML public TableColumn c6;
    @FXML public TableColumn c7;
    @FXML public TableColumn c8;
    @FXML public TableColumn c9;
    @FXML public TableView<Content2Model> tab3Table;

    @Override
    public void initData(Object o) {

        if( !(o instanceof Content2Model) ){
            throw new IllegalArgumentException("실패 이새끼야.");
        }

        tab3Table.getItems().add((Content2Model) o);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rowNum.setCellValueFactory      (cellData -> cellData.getValue().getRowNum());
        isbn.setCellValueFactory        (cellData -> cellData.getValue().getIsbn());
        bookName.setCellValueFactory    (cellData -> cellData.getValue().getBookName());
        publisher.setCellValueFactory   (cellData -> cellData.getValue().getPublisher());
        data1.setCellValueFactory       (cellData -> cellData.getValue().getData1());
        regDate.setCellValueFactory     (cellData -> cellData.getValue().getRegDate());
        regTime.setCellValueFactory     (cellData -> cellData.getValue().getRegTime());




    }

    public void changedBtnAction(MouseEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("좆까");
        alert.setContentText("테스트");
        alert.showAndWait();

    }


}
