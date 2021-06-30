package model;

import javafx.stage.Modality;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FxmlScene {

    String url;
    boolean isPop;
    Modality modality;

    @Builder
    public FxmlScene(String url) {
        this.url = url;
        this.isPop = false;
    }


    public void windowPop(){
        this.isPop = true;
        this.modality = Modality.WINDOW_MODAL;
    }

    public void applicationPop(){
        this.isPop = true;
        this.modality = Modality.APPLICATION_MODAL;
    }
}
