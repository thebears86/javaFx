package sample.listener;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class TextFieldListener implements ChangeListener<String> {

    private final TextField textField ;

    public TextFieldListener(TextField textField) {
        this.textField = textField ;
    }



    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!checkIsbn(newValue) && newValue.length() > 12){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Isbn 정합성 오류");
            alert.setContentText(newValue + " : ISBN이 아닙니다.");
            alert.showAndWait();

            /*
             * 값을 원래대로 되 돌리기 전에 runLater 구문에 넣어서 한템포 늦게 실행해준다.
             */
            Platform.runLater(this.textField::clear);

        }

    }



    /*isbn 규칙성 체크*/
    private static boolean checkIsbn(String isbn){

        if(!Pattern.matches("(^[0-9]*$)", isbn)){
            return false;
        }

        if(isbn.length()!=13 ){
            return false;
        }

        if( !isbn.startsWith("88") && !isbn.startsWith("97")) {
            return false;
        }




        // 0번째 숫자를 일부러 넣어줌 홀수 짝수 판단용
        String tempIsbn = "0"+isbn;
        String[] ary = tempIsbn.split("");


        int oddsSum = 0;
        int evenSum = 0;
        for(int i = 0 ; i < ary.length ; i++ ){

            /*짝수일경우*/
            if (i % 2 == 0){
                oddsSum += Integer.parseInt(ary[i])*3;

            }
            /*홀수일경우*/
            else{
                evenSum += Integer.parseInt(ary[i]);
            }

        }
        /* 합이 10으로 나누어 떨어지지 않을경우*/
        return (oddsSum + evenSum) % 10 == 0;
    }
}