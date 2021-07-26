package sample.common;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;
import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

@Slf4j
public class TableCopyAndPasteUtils {
    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance();
    private static final KeyCodeCombination copy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
    private static final KeyCodeCombination paste = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
    private static final KeyCodeCombination toEndLeft = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN );
    private static final KeyCodeCombination toEndRight = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN );
    private static final KeyCodeCombination toEndTop = new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN );
    private static final KeyCodeCombination toEndBottom = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN );

    /**
     * Install the keyboard handler:
     *   + CTRL + C = copy to clipboard
     *   + CTRL + V = paste to clipboard
     * @param table
     */
    public static void installCopyPasteHandler(TableView<?> table) {

        // install copy/paste keyboard handler
        table.setOnKeyPressed(new TableKeyEventHandler());

        table.setOnKeyReleased(new TableKeyReleasedHandler());
    }

    public static class TableKeyReleasedHandler implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent keyEvent) {
            if(keyEvent.isShiftDown()){
                if( keyEvent.getSource() instanceof TableView) {
                    ((TableView<?>) keyEvent.getSource()).getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }
            }
        }
    }

    /**
     * Copy/Paste keyboard event handler.
     * The handler uses the keyEvent's source for the clipboard data. The source must be of type TableView.
     */
    public static class TableKeyEventHandler implements EventHandler<KeyEvent> {

        @SuppressWarnings("rawtypes")
        private void moveToEnd(TableView tableView, KeyEvent event ){

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            boolean isCellMode = tableView.getSelectionModel().isCellSelectionEnabled();
            int targetIdx;
            TablePosition position = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
            TableColumn column = position.getTableColumn();

            if(toEndBottom.match(event)){

                targetIdx = tableView.getItems().size();

                moveCellSelect(tableView, isCellMode, targetIdx, column);

            }
            else if(toEndTop.match(event)){
                targetIdx = 1;
                moveCellSelect(tableView, isCellMode, targetIdx, column);
            }
            else if(toEndLeft.match(event)){
                targetIdx = selectedIndex;
                TableColumn firstColumn = (TableColumn) tableView.getColumns().get(0);
                moveCellSelect(tableView, isCellMode, targetIdx, firstColumn);
            }
            else if(toEndRight.match(event)){
                targetIdx = selectedIndex;
                TableColumn lastColumn = (TableColumn) tableView.getColumns().get(tableView.getColumns().size()-1);
                moveCellSelect(tableView, isCellMode, targetIdx, lastColumn);
            }

            event.consume();
        }



        public void handle(final KeyEvent keyEvent) {

            if (copy.match(keyEvent)) {
                if( keyEvent.getSource() instanceof TableView) {
                    // copy to clipboard
                    copySelectionToClipboard( (TableView<?>) keyEvent.getSource());
                    // event is handled, consume it
                    keyEvent.consume();
                }
            }
            else if (paste.match(keyEvent)) {
                if( keyEvent.getSource() instanceof TableView) {
                    // copy to clipboard
                    pasteFromClipboard( (TableView<?>) keyEvent.getSource());
                    // event is handled, consume it
                    keyEvent.consume();
                }
            }else{

                if( keyEvent.getSource() instanceof TableView) {
                    if(keyEvent.isControlDown()){
                        moveToEnd((TableView<?>) keyEvent.getSource() , keyEvent);
                    }else if(keyEvent.isShiftDown()){
                        ((TableView<?>) keyEvent.getSource()).getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    }
                }
                keyEvent.consume();
            }

        }

    }

    @SuppressWarnings("rawtypes")
    private static void moveCellSelect(TableView<?> tableView, boolean isCellMode, int targetIdx, TableColumn column) {

        if (isCellMode) {
            tableView.getSelectionModel().select(targetIdx, column);

        } else {
            tableView.getSelectionModel().select(targetIdx);
        }
        tableView.scrollTo(targetIdx);
        tableView.getSelectionModel().focus(targetIdx);
    }

    /**
     * Get table selection and copy it to the clipboard.
     * @param table
     */
    public static void copySelectionToClipboard(TableView<?> table) {

        if(table.getSelectionModel().isCellSelectionEnabled()){

            copyTableSelectedCellsToClipBoard(table);

        }else{
            copySelectionRowsToClipboard(table);
        }


    }


    @SuppressWarnings("rawtypes")
    private static void copySelectionRowsToClipboard(final TableView<?> table) {
        final Set<Integer> rows = new TreeSet<>();
        for (final TablePosition tablePosition : table.getSelectionModel().getSelectedCells()) {
            rows.add(tablePosition.getRow());
        }
        final StringBuilder strb = new StringBuilder();
        boolean firstRow = true;
        for (final Integer row : rows) {
            if (!firstRow) {
                strb.append('\n');
            }
            firstRow = false;
            boolean firstCol = true;
            for (final TableColumn<?, ?> column : table.getColumns()) {
                if (!firstCol) {
                    strb.append('\t');
                }
                firstCol = false;
                final Object cellData = column.getCellData(row);
                strb.append(cellData == null ? "" : cellData.toString());
            }
        }
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(strb.toString());
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    @SuppressWarnings("rawtypes")
    private static void copyTableSelectedCellsToClipBoard(TableView<?> table) {
        StringBuilder clipboardString = new StringBuilder();

        ObservableList<TablePosition> positionList = table.getSelectionModel().getSelectedCells();

        int prevRow = -1;

        for (TablePosition position : positionList) {

            int row = position.getRow();
            int col = position.getColumn();

            // determine whether we advance in a row (tab) or a column
            // (newline).
            if (prevRow == row) {

                clipboardString.append('\t');

            } else if (prevRow != -1) {

                clipboardString.append('\n');

            }

            // create string from cell
            String text = "";

            Object observableValue = (Object) table.getVisibleLeafColumn(col).getCellObservableValue(row);

            // null-check: provide empty string for nulls
            if (observableValue == null) {
                text = "";
            }
            else if( observableValue instanceof DoubleProperty) { // TODO: handle boolean etc

                text = numberFormatter.format( ((DoubleProperty) observableValue).get());

            }
            else if( observableValue instanceof IntegerProperty) {

                text = numberFormatter.format( ((IntegerProperty) observableValue).get());

            }
            else if( observableValue instanceof StringProperty) {

                text = ((StringProperty) observableValue).get();

            }
            else {
                log.info("Unsupported observable value: " + observableValue);
            }

            // add new item to clipboard
            clipboardString.append(text);

            // remember previous
            prevRow = row;
        }

        // create clipboard content
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString.toString());

        // set clipboard content
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    @SuppressWarnings("rawtypes")
    public static void pasteFromClipboard( TableView<?> table) {

        // abort if there's not cell selected to start with
        if( table.getSelectionModel().getSelectedCells().size() == 0) {
            return;
        }

        // get the cell position to start with
        TablePosition pasteCellPosition = table.getSelectionModel().getSelectedCells().get(0);

        log.info("Pasting into cell " + pasteCellPosition);

        String pasteString = Clipboard.getSystemClipboard().getString();

        log.info(pasteString);

        int rowClipboard = -1;

        StringTokenizer rowTokenizer = new StringTokenizer( pasteString, "\n");
        while( rowTokenizer.hasMoreTokens()) {

            rowClipboard++;

            String rowString = rowTokenizer.nextToken();

            StringTokenizer columnTokenizer = new StringTokenizer( rowString, "\t");

            int colClipboard = -1;

            while( columnTokenizer.hasMoreTokens()) {

                colClipboard++;

                // get next cell data from clipboard
                String clipboardCellContent = columnTokenizer.nextToken();

                // calculate the position in the table cell
                int rowTable = pasteCellPosition.getRow() + rowClipboard;
                int colTable = pasteCellPosition.getColumn() + colClipboard;

                // TODO: 붙여넣기 할 때 마지막 Row 이상일 경우 붙여넣기?
                // TODO: 붙여넣기 할 때 마지막 Cell 이상일 경우 에러처리?
                // skip if we reached the end of the table
                if( rowTable >= table.getItems().size()) {
                    continue;
                }
                if( colTable >= table.getColumns().size()) {
                    continue;
                }

                // System.out.println( rowClipboard + "/" + colClipboard + ": " + cell);

                // get cell
                TableColumn tableColumn = table.getVisibleLeafColumn(colTable);
                ObservableValue observableValue = tableColumn.getCellObservableValue(rowTable);

                log.info(rowTable + "/" + colTable + ": " +observableValue);

                // TODO: handle boolean, etc
                if( observableValue instanceof DoubleProperty) {

                    try {

                        double value = numberFormatter.parse(clipboardCellContent).doubleValue();
                        ((DoubleProperty) observableValue).set(value);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                else if( observableValue instanceof IntegerProperty) {

                    try {

                        int value = NumberFormat.getInstance().parse(clipboardCellContent).intValue();
                        ((IntegerProperty) observableValue).set(value);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                else if( observableValue instanceof StringProperty) {

                    ((StringProperty) observableValue).set(clipboardCellContent);

                } else {
                    log.info("Unsupported observable value: " + observableValue);
                }
                log.info(rowTable + "/" + colTable);
            }

        }

    }

}
