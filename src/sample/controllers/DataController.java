package sample.controllers;

import javafx.fxml.Initializable;

import java.util.List;

public abstract class DataController implements Initializable {

    public abstract void initData(Object o);
    public abstract void initListData(List<?> o);

}
