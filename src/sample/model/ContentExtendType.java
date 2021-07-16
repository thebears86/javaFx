package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(includeFieldNames = true)
public class ContentExtendType extends Content2Model{

    private StringProperty content1;
    private StringProperty content2;
    private StringProperty content3;
    private StringProperty content4;
    private StringProperty content5;
    private StringProperty content6;


    @Builder
    public ContentExtendType(long rowNum, String isbn, String bookName, String publisher, String data1, String content1, String content2, String content3, String content4, String content5, String content6) {
        super(rowNum, isbn, bookName, publisher, data1);
        this.content1 = new SimpleStringProperty(content1);
        this.content2 = new SimpleStringProperty(content2);
        this.content3 = new SimpleStringProperty(content3);
        this.content4 = new SimpleStringProperty(content4);
        this.content5 = new SimpleStringProperty(content5);
        this.content6 = new SimpleStringProperty(content6);
    }



}
