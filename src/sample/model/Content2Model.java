package sample.model;

import javafx.beans.property.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class Content2Model {

    private ObjectProperty<Long> rowNum;
    private StringProperty isbn;
    private StringProperty bookName;
    private StringProperty publisher;
    private StringProperty data1;
    private ObjectProperty<LocalDate> regDate;
    private ObjectProperty<LocalTime> regTime;


    public Content2Model(long rowNum, String isbn, String bookName, String publisher, String data1) {
        this.rowNum = new SimpleObjectProperty<Long>(rowNum);
        this.isbn = new SimpleStringProperty(isbn);
        this.bookName = new SimpleStringProperty(bookName);
        this.publisher = new SimpleStringProperty(publisher);
        this.data1 = new SimpleStringProperty(data1);
        this.regDate = new SimpleObjectProperty<>(LocalDate.now());
        this.regTime = new SimpleObjectProperty<>(LocalTime.now());
    }
}
