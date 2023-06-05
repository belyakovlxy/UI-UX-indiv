package com.example.indiv;

import com.example.indiv.entities.Event;
import com.example.indiv.entities.Subject;
import com.example.indiv.entities.WeekdayInfo;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javafx.scene.layout.Background;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WeekdayController implements Initializable {
    public Label labelSchedule;
    public Label labelEvents;
    public Label weekday;
    private String currentWeekday;
    public int selectedEventId;

    @FXML
    public TableView<Subject> scheduleTable;

    @FXML
    private TableColumn<Subject, String> scheduleTime;

    @FXML
    private TableColumn<Subject, String> subjectName;

    @FXML
    private TableColumn<Subject, String> roomNumber;

    @FXML
    private TableColumn<Subject, String> scheduleNotes;


    @FXML
    public TableView<Event> eventsTable;

    @FXML
    private TableColumn<Event, String> eventTime;

    @FXML
    private TableColumn<Event, String> eventName;

    @FXML
    private TableColumn<Event, String> place;

    @FXML
    private TableColumn<Event, String> eventNotes;


    Map<String, WeekdayInfo> weekSchedule = new HashMap<>();

    private Subject getSubjectFromXml(Element eElement)
    {
        return new Subject(
                eElement.getElementsByTagName("time").item(0).getTextContent(),
                eElement.getElementsByTagName("subjectName").item(0).getTextContent(),
                eElement.getElementsByTagName("roomNumber").item(0).getTextContent(),
                eElement.getElementsByTagName("notes").item(0).getTextContent()
        );
    }

    private Event getEventFromXml(Element eElement)
    {
        return new Event(
                eElement.getElementsByTagName("time").item(0).getTextContent(),
                eElement.getElementsByTagName("eventName").item(0).getTextContent(),
                eElement.getElementsByTagName("place").item(0).getTextContent(),
                eElement.getElementsByTagName("notes").item(0).getTextContent()
        );
    }

    public void setTableHeight()
    {
        scheduleTable.setFixedCellSize(27);
        scheduleTable.prefHeightProperty().bind(scheduleTable.fixedCellSizeProperty().multiply(Bindings.size(scheduleTable.getItems()).add(2)));
        scheduleTable.minHeightProperty().bind(scheduleTable.prefHeightProperty());
        scheduleTable.maxHeightProperty().bind(scheduleTable.prefHeightProperty());

        eventsTable.setFixedCellSize(27);
        eventsTable.prefHeightProperty().bind(eventsTable.fixedCellSizeProperty().multiply(Bindings.size(eventsTable.getItems()).add(2)));
        eventsTable.minHeightProperty().bind(eventsTable.prefHeightProperty());
        eventsTable.maxHeightProperty().bind(eventsTable.prefHeightProperty());
    }

    @FXML
    void rowSubjectClicked(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2 && !event.isConsumed())
        {
            Subject subject = scheduleTable.getSelectionModel().getSelectedItem();
            int rowId = scheduleTable.getSelectionModel().getSelectedCells().get(0).getRow();

            System.out.println(subject.getTime() + subject.getSubjectName());

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit_window.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            EditWindowController controller = fxmlLoader.getController();
            controller.initData(rowId, subject);
            controller.setParentController(this);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void rowEventClicked(MouseEvent mEvent) throws IOException {
        int rowId = eventsTable.getSelectionModel().getSelectedCells().get(0).getRow();
        if (mEvent.getClickCount() == 2 && !mEvent.isConsumed())
        {
            Event event = eventsTable.getSelectionModel().getSelectedItem();
            System.out.println(event.getTime() + event.getEventName());

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit_window.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            EditWindowController controller = fxmlLoader.getController();
            controller.initData(rowId, event);
            controller.setParentController(this);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }

        selectedEventId = rowId;
    }
    @FXML
    private void changeWeekday(ActionEvent event)
    {
        Button btn = (Button) event.getSource();
        String id = btn.getId();
        System.out.println(id);

        switch (id){
            case ("mondayButton"):
                currentWeekday = "monday";
                weekday.setText("Понедельник");
                scheduleTable.setItems(weekSchedule.get("monday").subjectList);
                eventsTable.setItems(weekSchedule.get("monday").eventList);
                setTableHeight();
                break;
            case ("tuesdayButton"):
                currentWeekday = "tuesday";
                weekday.setText("Вторник");
                scheduleTable.setItems(weekSchedule.get("tuesday").subjectList);
                eventsTable.setItems(weekSchedule.get("tuesday").eventList);
                setTableHeight();
                break;
            case ("wednesdayButton"):
                currentWeekday = "wednesday";
                weekday.setText("Среда");
                scheduleTable.setItems(weekSchedule.get("wednesday").subjectList);
                eventsTable.setItems(weekSchedule.get("wednesday").eventList);
                setTableHeight();
                break;
            case ("thursdayButton"):
                currentWeekday = "thursday";
                weekday.setText("Четверг");
                scheduleTable.setItems(weekSchedule.get("thursday").subjectList);
                eventsTable.setItems(weekSchedule.get("thursday").eventList);
                setTableHeight();
                break;
            case ("fridayButton"):
                currentWeekday = "friday";
                weekday.setText("Пятница");
                scheduleTable.setItems(weekSchedule.get("friday").subjectList);
                eventsTable.setItems(weekSchedule.get("friday").eventList);
                setTableHeight();
                break;
            case ("saturdayButton"):
                currentWeekday = "saturday";
                weekday.setText("Суббота");
                scheduleTable.setItems(weekSchedule.get("saturday").subjectList);
                eventsTable.setItems(weekSchedule.get("saturday").eventList);
                setTableHeight();
                break;
        }
    }

    public void setEdits(int rowId, Subject subject)
    {
        weekSchedule.get(currentWeekday).subjectList.set(rowId, subject);
        scheduleTable.setItems(weekSchedule.get(currentWeekday).subjectList);
        setTableHeight();
    }

    public void setEdits(int rowId, Event event)
    {
        if (rowId == -1)
        {
            weekSchedule.get(currentWeekday).eventList.add(event);
        }
        else
        {
            weekSchedule.get(currentWeekday).eventList.set(rowId, event);
        }
        eventsTable.setItems(weekSchedule.get(currentWeekday).eventList);
        setTableHeight();
    }

    public void editXml(int rowId, Subject sub, Event event) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        boolean isForSubject = (sub != null);

        File inputFile = new File("src\\main\\resources\\com\\example\\indiv\\info.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("weekday");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            Element eElement = (Element) nNode;
            System.out.println("Current Element Attribute:" + eElement.getAttribute("day") + currentWeekday);

            if (eElement.getAttribute("day").equals(currentWeekday))
            {
                if (isForSubject)
                {
                    NodeList nSubjects = ((Element) nNode).getElementsByTagName("subject");

                    Node nSubject = nSubjects.item(rowId);

                    ((Element) nSubject).getElementsByTagName("time").item(0).setTextContent(sub.getTime());
                    ((Element) nSubject).getElementsByTagName("subjectName").item(0).setTextContent(sub.getSubjectName());
                    ((Element) nSubject).getElementsByTagName("roomNumber").item(0).setTextContent(sub.getRoomNumber());
                    ((Element) nSubject).getElementsByTagName("notes").item(0).setTextContent(sub.getNotes());
                }
                else
                {
                    if (rowId == -1)
                    {
                        Node nEvents = ((Element) nNode).getElementsByTagName("events").item(0);
                        Element eEvent = doc.createElement("event");

                        Element eTime = doc.createElement("time");
                        eTime.setTextContent(event.getTime());
                        eEvent.appendChild(eTime);

                        Element eName = doc.createElement("eventName");
                        eName.setTextContent(event.getEventName());
                        eEvent.appendChild(eName);

                        Element ePlace = doc.createElement("place");
                        ePlace.setTextContent(event.getPlace());
                        eEvent.appendChild(ePlace);

                        Element eNotes = doc.createElement("notes");
                        eNotes.setTextContent(event.getNotes());
                        eEvent.appendChild(eNotes);

                        nEvents.appendChild(eEvent);
                    }
                    else
                    {
                        NodeList nEvents = ((Element) nNode).getElementsByTagName("event");


                        Node nEvent = nEvents.item(rowId);

                        ((Element) nEvent).getElementsByTagName("time").item(0).setTextContent(event.getTime());
                        ((Element) nEvent).getElementsByTagName("eventName").item(0).setTextContent(event.getEventName());
                        ((Element) nEvent).getElementsByTagName("place").item(0).setTextContent(event.getPlace());
                        ((Element) nEvent).getElementsByTagName("notes").item(0).setTextContent(event.getNotes());
                    }
                }
                break;
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(doc),
                new StreamResult(inputFile));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currentWeekday = "monday";
        weekday.setText("Понедельник");

        try {
            File inputFile = new File("src\\main\\resources\\com\\example\\indiv\\info.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("weekday");
            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                Element eElement = (Element) nNode;
                System.out.println("Current Element Attribute:" + eElement.getAttribute("day"));

                NodeList nSubjects = ((Element) nNode).getElementsByTagName("subject");
                ObservableList<Subject> subjectList = FXCollections.observableArrayList();
                ObservableList<Event> eventList = FXCollections.observableArrayList();
                for (int i = 0; i < nSubjects.getLength(); i++)
                {
                    Node nSubject = nSubjects.item(i);
                    System.out.println("\nCurrent Element :" + nSubject.getNodeName());

                    Subject sub = getSubjectFromXml((Element) nSubject);
                    subjectList.add(sub);
                    System.out.println(sub.toString());
                }

                NodeList nEvents = ((Element) nNode).getElementsByTagName("event");
                for (int i = 0; i < nEvents.getLength(); i++)
                {
                    Node nEvent = nEvents.item(i);
                    System.out.println("\nCurrent Element :" + nEvent.getNodeName());

                    Event event = getEventFromXml((Element) nEvent);
                    eventList.add(event);
                    System.out.println(event.toString());
                }

                weekSchedule.put(eElement.getAttribute("day"), new WeekdayInfo(subjectList, eventList));
                System.out.println("----------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        scheduleTime.setCellValueFactory(new PropertyValueFactory<Subject, String>("time"));
        subjectName.setCellValueFactory(new PropertyValueFactory<Subject, String>("subjectName"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<Subject, String>("roomNumber"));
        scheduleNotes.setCellValueFactory(new PropertyValueFactory<Subject, String>("notes"));

        scheduleTable.setItems(weekSchedule.get(currentWeekday).subjectList);

        eventTime.setCellValueFactory(new PropertyValueFactory<Event, String>("time"));
        eventName.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        place.setCellValueFactory(new PropertyValueFactory<Event, String>("place"));
        eventNotes.setCellValueFactory(new PropertyValueFactory<Event, String>("notes"));

        eventsTable.setItems(weekSchedule.get(currentWeekday).eventList);

        setTableHeight();
    }

    public void addEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit_window.fxml"));

        Parent root = (Parent) fxmlLoader.load();
        EditWindowController controller = fxmlLoader.getController();
        controller.initData(-1);
        controller.setParentController(this);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteEvent(ActionEvent actionEvent) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        if (selectedEventId >= weekSchedule.get(currentWeekday).eventList.size())
        {
            return;
        }

        File inputFile = new File("src\\main\\resources\\com\\example\\indiv\\info.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("weekday");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            Element eElement = (Element) nNode;
            System.out.println("Current Element Attribute:" + eElement.getAttribute("day") + currentWeekday);

            if (eElement.getAttribute("day").equals(currentWeekday))
            {
                Node nEvents = ((Element) nNode).getElementsByTagName("events").item(0);
                Node nEvent = ((Element) nNode).getElementsByTagName("event").item(selectedEventId);

                nEvents.removeChild(nEvent);

                break;
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(doc),
                new StreamResult(inputFile));


        weekSchedule.get(currentWeekday).eventList.remove(selectedEventId);
        eventsTable.setItems(weekSchedule.get(currentWeekday).eventList);
    }
}
