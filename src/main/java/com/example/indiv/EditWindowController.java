package com.example.indiv;

import com.example.indiv.entities.Event;
import com.example.indiv.entities.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditWindowController implements Initializable {

    public WeekdayController controller;
    public int rowId;
    public Boolean isForSubject;

    @FXML
    private Label labelWindow;

    @FXML
    private Label time;
    @FXML
    private Label name;
    @FXML
    private Label place;
    @FXML
    private Label notes;

    @FXML
    private TextField timeValue;
    @FXML
    private TextField nameValue;
    @FXML
    private TextField placeValue;
    @FXML
    private TextField notesValue;

    @FXML
    private Button applyButton;

    @FXML
    public void onButtonClicked()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setParentController(WeekdayController controller)
    {
        this.controller = controller;
    }

    public void initData(int rowId, Subject sub)
    {
        isForSubject = true;
        this.rowId = rowId;
        System.out.println(sub.getTime());
        labelWindow.setText("Редактирование");

        time.setText("Время");
        name.setText("Предмет");
        place.setText("Номер кабинета");
        notes.setText("Заметки");

        timeValue.setText(sub.getTime());
        nameValue.setText(sub.getSubjectName());
        placeValue.setText(sub.getRoomNumber());
        notesValue.setText(sub.getNotes());
    }

    public void initData(int rowId, Event event)
    {
        isForSubject = false;
        this.rowId = rowId;
        labelWindow.setText("Редактирование");

        time.setText("Время");
        name.setText("Мероприятие");
        place.setText("Место");
        notes.setText("Заметки");

        timeValue.setText(event.getTime());
        nameValue.setText(event.getEventName());
        placeValue.setText(event.getPlace());
        notesValue.setText(event.getNotes());
    }

    public void initData(int rowId)
    {
        isForSubject = false;
        this.rowId = rowId;
        labelWindow.setText("Создание мероприятия");

        time.setText("Время");
        name.setText("Мероприятие");
        place.setText("Место");
        notes.setText("Заметки");
    }

    public void applyButtonClicked(ActionEvent actionEvent) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        if (isForSubject)
        {
            Subject subject = new Subject(timeValue.getText(), nameValue.getText(), placeValue.getText(), notesValue.getText());
            controller.setEdits(this.rowId, subject);
            controller.editXml(this.rowId, subject, null);
        }
        if (!isForSubject)
        {
            Event event = new Event(timeValue.getText(), nameValue.getText(), placeValue.getText(), notesValue.getText());
            controller.setEdits(this.rowId, event);
            controller.editXml(this.rowId, null, event);
        }
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }
}
