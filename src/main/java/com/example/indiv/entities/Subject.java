package com.example.indiv.entities;

import java.util.List;

public class Subject {
    private String time;
    private String subjectName;
    private String roomNumber;
    private String notes;

    public Subject(String time, String subjectName, String roomNumber, String notes)
    {
        this.time = time;
        this.subjectName = subjectName;
        this.roomNumber = roomNumber;
        this.notes = notes;

        System.out.println(this.time + this.subjectName + this.roomNumber + this.notes);
    }

    public String getTime()
    {
        return this.time;
    }

    public String getSubjectName()
    {
        return this.subjectName;
    }

    public String getRoomNumber()
    {
        return this.roomNumber;
    }

    public String getNotes()
    {
        return this.notes;
    }

    public void setTime(String time) {this.time = time;}
    public void setSubjectName(String subjectName) {this.subjectName = subjectName;}
    public void setRoomNumber(String roomNumber) {this.roomNumber = roomNumber;}
    public void setNotes(String notes) {this.notes = notes;}

    @Override
    public String toString()
    {
        return String.format("%s\n%s\n%s\n%s", this.time, this.subjectName, this.roomNumber, this.notes);
    }
}
