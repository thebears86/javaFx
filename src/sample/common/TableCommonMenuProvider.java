package sample.common;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class TableCommonMenuProvider {

    public static ContextMenu addContextMenu(TableView tableView){

        ContextMenu menu = new ContextMenu();


        MenuItem selectMode = new MenuItem("선택모드 변경");
        selectMode.setOnAction(event -> {

            if(tableView.getSelectionModel().isCellSelectionEnabled()){

                tableView.getSelectionModel().setCellSelectionEnabled(false);
                selectMode.setText("셀선택");
            }else{
                tableView.getSelectionModel().setCellSelectionEnabled(true);
                selectMode.setText("줄선택");
            }

        });

        MenuItem item = new MenuItem("Copy cell");
        item.setOnAction(event -> {
            TableCopyAndPasteUtils.copySelectionToClipboard(tableView);
        });
        item.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));//don't work on ContextMenu but keep is for the display text

        MenuItem export = new MenuItem("Copy table (csv)");
        export.setOnAction(event -> {
            //exportTableToClipboard(createCsvFromTable.get());
        });

        MenuItem fileExport = new MenuItem("Save table (csv)");
        fileExport.setOnAction(event -> {
            //exportTableToFile(createCsvFromTable.get(),tableView.getScene().getWindow());
        });

        menu.getItems().add(selectMode);
        menu.getItems().add(item);
        menu.getItems().add(export);
        menu.getItems().add(fileExport);

        tableView.setContextMenu(menu);

        return menu;

    }


}
