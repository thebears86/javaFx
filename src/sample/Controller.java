package sample;

public class Controller {

    /*public void up (ActionEvent e){
        System.out.print("up");
    }

    @FXML
    private TableView<TableRowDataModel> table;
    @FXML
    private TableColumn<TableRowDataModel , String > columnOne;
    @FXML
    private TableColumn<TableRowDataModel , String > columnTwo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnOne.setCellValueFactory(cellData->cellData.getValue().getColumnOne());
        columnTwo.setCellValueFactory(cellData->cellData.getValue().getColumnTwo());
    }

    public void down (ActionEvent e){
        System.out.print("down");

        ObservableList<TableRowDataModel> list = FXCollections.observableArrayList();


        for (int i = 0 ; i < 10000 ; i++){
            TableRowDataModel a = new TableRowDataModel("AA"+i,"AA"+i);
            list.add(a);
        }
        table.setItems(list);



    }

    public void right (ActionEvent e){

        ObservableList<TableRowDataModel> list = FXCollections.observableArrayList();

        for (int i = 0 ; i < 10 ; i++){
            TableRowDataModel a = new TableRowDataModel("AA"+i,"AA"+i);
            list.add(a);
        }
        table.getItems().addAll(list);
        table.scrollTo(list.get(0));

        System.out.print("right");
    }

    public void left (ActionEvent e){





        System.out.print("left");
    }




    @Getter
    @Setter
    @NoArgsConstructor
    public class TableRowDataModel{
        private StringProperty columnOne;
        private StringProperty columnTwo;

        public TableRowDataModel(String  data1, String  data2) {
            this.columnOne = new SimpleStringProperty(data1);
            this.columnTwo = new SimpleStringProperty(data2);
        }



    }*/

}
