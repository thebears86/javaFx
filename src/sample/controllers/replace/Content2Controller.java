package sample.controllers.replace;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import sample.common.ExcelValidator;
import sample.common.TableCommonMenuProvider;
import sample.common.TableCopyAndPasteUtils;
import sample.common.stringEnums.ThreadNames;
import sample.controllers.DataController;
import sample.model.Content2Model;
import sample.model.ContentExtendType;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Content2Controller extends DataController {
    @FXML public Button changedBtn;
    /*
     * Tab3 Table Part
     */
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

    private final ObservableList<ContentExtendType> tab3TableList = FXCollections.observableArrayList();





    @FXML public MenuItem copyMenu;
    @FXML public ProgressBar progressBar;
    @FXML public AnchorPane topAnchorPane;

    private Thread th;

    @Override
    public void initListData(List<?> list) {

        tab3TableList.addListener(new MyListChangeListener());


        if(list.isEmpty()){
            return;
        }

        if(!(list.get(0) instanceof ContentExtendType)){
            throw new IllegalArgumentException("실패 이새끼야.");
        }

        List<ContentExtendType> targetList = (List<ContentExtendType>) list;

        tab3TableList.addAll(targetList);

        tab3Table.getItems().setAll(tab3TableList);

    }

    @Override
    public void initData(Object o) {

    }

    public void changeTableEditMode(boolean t){
        tab3Table.setEditable(t);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
         * Set Context Menu Config
         */
        TableCommonMenuProvider.addContextMenu(tab3Table);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                /*
                 * Set Table Cell Config
                 */
                rowNum.setCellValueFactory      (cellData -> cellData.getValue().getRowNum());

                isbn.setCellValueFactory        (cellData -> cellData.getValue().getIsbn());
                //수정가능한 셀을 위한 TextField Set
                isbn.setCellFactory(TextFieldTableCell.forTableColumn());

                bookName.setCellValueFactory    (cellData -> cellData.getValue().getBookName());
                //수정가능한 셀을 위한 TextField Set
                bookName.setCellFactory(TextFieldTableCell.forTableColumn());

                publisher.setCellValueFactory   (cellData -> cellData.getValue().getPublisher());
                //수정가능한 셀을 위한 TextField Set
                publisher.setCellFactory(TextFieldTableCell.forTableColumn());

                data1.setCellValueFactory       (cellData -> cellData.getValue().getData1());
                regDate.setCellValueFactory     (cellData -> cellData.getValue().getRegDate());
                regTime.setCellValueFactory     (cellData -> cellData.getValue().getRegTime());

                /*
                 * Set TableView Config
                 */
                tab3Table.setOnMouseClicked(cellEditHandler());
                //테이블 Copy & Paste 이벤트 설정.
                TableCopyAndPasteUtils.installCopyPasteHandler(tab3Table);

                //Cell 수정 후 TableView 다시 포커스 이벤트 설정.
                tab3Table.getColumns().forEach(x->{
                    x.addEventHandler(TableColumn.CellEditEvent.ANY , event -> tableFocusResume());
                });
            }
        });


        //테이블 선택모드 셋팅
        //tab3Table.getSelectionModel().setCellSelectionEnabled(true);

        progressAuto();
/*
        Scene scene = this.topAnchorPane.set



        System.out.println("window.toString() = " + scene.toString());

 */
    }



    public EventHandler<MouseEvent> cellEditHandler(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(event.isShiftDown()){
                    extractedSelectedMulti();
                    event.consume();
                }else{
                    tab3Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }

                /*
                 * 테이블 선택모드가 Row 단위일때 : false
                 * 선택모드가 Cell 단위일때 : true
                 */
                //tab3Table.getSelectionModel().setCellSelectionEnabled(false);

                /*
                 * 더블클릭시에 편집모드 진입
                 */
                if(event.getClickCount() >= 2){
                    ContentExtendType selectedItem = tab3Table.getSelectionModel().getSelectedItem();
                    if(selectedItem.isEditable()){
                        changeTableEditMode(true);
                    }
                }
            }
        };
    }

    private void extractedSelectedMulti() {

        changeTableEditMode(false);
        //tab3Table.getSelectionModel().setCellSelectionEnabled(true);


        final int idx = tab3Table.getSelectionModel().getFocusedIndex();

        if(idx >= 0 && idx < tab3Table.getItems().size()){

            if(tab3Table.getSelectionModel().isSelected(idx)){
                tab3Table.getSelectionModel().clearSelection(idx);
            }
        }
    }

    public EventHandler<KeyEvent> keyEventEventHandler(){
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if(keyEvent.getCode().isArrowKey()){
                    //tab3Table.getSelectionModel().setCellSelectionEnabled(true);
                    keyEvent.consume();
                }
                /*if(copy.match(keyEvent)){
                    copySelectionToClipboard(tab3Table);
                }*/

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

    public void tableFocusResume() {
        tab3Table.requestFocus();
    }


    private static class MyListChangeListener implements ListChangeListener<ContentExtendType>{
        @Override
        public void onChanged(Change<? extends ContentExtendType> change) {

            System.out.println("list size = " + change.getList().size());

            if(!change.next()){
                return;
            }

            if(change.wasAdded()){
                System.out.println("list getAddedSize = " + change.getAddedSize());
            }

            System.out.println("list getFrom = " + change.getFrom());



        }
    }




    public void progressAuto(){

        /*Thread th = Thread.*/
        Collection<Thread> next = getNext();
        if (next.iterator().hasNext()){
            next.iterator().forEachRemaining(Thread::interrupt);
        }
        th = new Thread(ThreadNames.fx_th_progress.name()){

            boolean isRun = true;

            @Override
            public void run() {
                while (isRun){
                    Platform.runLater(()->{
                        LocalTime localTime = LocalTime.now();
                        int second = localTime.getSecond() + 1;
                        double percent = new BigDecimal(second).doubleValue() / 60;
                        progressBar.setProgress(percent);

                    });

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e){
                        //e.printStackTrace();
                        this.isRun = false;
                    }
                }
            }

            @Override
            public void interrupt() {
                super.interrupt();
                this.isRun = false;
                log.info(ThreadNames.fx_th_progress.name() + " is interrupted.");
            }
        };
        th.start();

    }
    private Collection<Thread> getNext() {
        return ThreadUtils.findThreadsByName(ThreadNames.fx_th_progress.name());
    }
}
