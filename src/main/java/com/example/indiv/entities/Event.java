package com.example.indiv.entities;

public class Event {
    private String time;
    private String eventName;
    private String place;
    private String notes;

    public Event(String time, String subjectName, String roomNumber, String notes)
    {
        this.time = time;
        this.eventName = subjectName;
        this.place = roomNumber;
        this.notes = notes;

        System.out.println(this.time + this.eventName + this.place + this.notes);
    }

    public String getTime()
    {
        return this.time;
    }

    public String getEventName()
    {
        return this.eventName;
    }

    public String getPlace()
    {
        return this.place;
    }

    public String getNotes()
    {
        return this.notes;
    }
}
