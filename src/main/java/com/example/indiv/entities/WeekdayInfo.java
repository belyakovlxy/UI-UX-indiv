package com.example.indiv.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WeekdayInfo {
    public ObservableList<Subject> subjectList;
    public ObservableList<Event> eventList;

    public WeekdayInfo(ObservableList<Subject> subList, ObservableList<Event> eventList)
    {
        if (subList == null) {
            this.subjectList = FXCollections.observableArrayList();
        } else {
            this.subjectList = subList;
        }

        if (eventList == null) {
            this.eventList = FXCollections.observableArrayList();
        } else {
            this.eventList = eventList;
        }
    }
}
