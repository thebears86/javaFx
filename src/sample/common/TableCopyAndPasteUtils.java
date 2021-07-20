package sample.common;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.*;
import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

@Slf4j
public class TableCopyAndPasteUtils {
    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance();


    /**
     * Install the keyboard handler:
     *   + CTRL + C = copy to clipboard
     *   + CTRL + V = paste to clipboard
     * @param table
     */
    public static void installCopyPasteHandler(TableView<?> table) {

        // install copy/paste keyboard handler
        table.setOnKeyPressed(new TableKeyEventHandler());

    }

    /**
     * Copy/Paste keyboard event handler.
     * The handler uses the keyEvent's source for the clipboard data. The source must be of type TableView.
     */
    public static class TableKeyEventHandler implements EventHandler<KeyEvent> {

        KeyCodeCombination copy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination paste = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

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

            }

        }

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
