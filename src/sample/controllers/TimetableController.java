package sample.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class TimetableController implements Initializable {
    private CalendarView calendar;
    public GridPane pnlHost;
    private Thread updateTimeThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCalendar();
    }

    private void loadCalendar() {


        calendar = new CalendarView();

        Calendar classes = new Calendar("Classes");


        Calendar meetings = new Calendar("Meetings");

        classes.setStyle(Calendar.Style.STYLE1);
        meetings.setStyle(Calendar.Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("Timetable");
        myCalendarSource.getCalendars().addAll(classes, meetings);

        calendar.getCalendarSources().addAll(myCalendarSource);

        calendar.setRequestedTime(LocalTime.now());


        updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendar.setToday(LocalDate.now());
                        calendar.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        calendar.showMonthPage();
        pnlHost.getChildren().add(calendar);
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
        ((Stage)pnlHost.getScene().getWindow()).close();
    }



}
