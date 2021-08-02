package sample.controllers.market;

import javafx.scene.input.MouseEvent;
import sample.model.market.Fruit;

public interface MyListener {

    public void onClickListener(MouseEvent event , Fruit fruit);

}
