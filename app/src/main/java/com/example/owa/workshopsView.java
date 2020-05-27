package com.example.owa;

public class workshopsView {
    public String workName;
    public String workCategory;
    public String wstartTime;
    public String wendTime;
    public String wdate;
    public String wroomNo;
    public String wfee;
    public String wage;
    public String wNoSeats;

    public workshopsView(String workName, String wdate, String wstartTime, String wendTime) {

    }

    public workshopsView(String workName, String wstartTime, String wendTime, String workCategory, String wNoSeats, String wage, String wfee, String wroomNo, String wdate) {
        this.workName = workName;
        this.wstartTime = wstartTime;
        this.wendTime = wendTime;
        this.wdate = wdate;
        this.wroomNo = wroomNo;
        this.wfee = wfee;
        this.wage = wage;
        this.wNoSeats = wNoSeats;
        this.workCategory = workCategory;
    }

    public workshopsView() {

    }


    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWstartTime() {
        return wstartTime;
    }

    public void setWstartTime(String wstartTime) {
        this.wstartTime = wstartTime;
    }

    public String getWendTime() {
        return wendTime;
    }

    public void setWendTime(String wendTime) {
        this.wendTime = wendTime;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    public String getWroomNo() {
        return wroomNo;
    }

    public void setWroomNo(String wroomNo) {
        this.wroomNo = wroomNo;
    }

    public String getWfee() {
        return wfee;
    }

    public void setWfee(String wfee) {
        this.wfee = wfee;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getwNoSeats() {
        return wNoSeats;
    }

    public void setwNoSeats(String wNoSeats) {
        this.wNoSeats = wNoSeats;
    }

    public String getWorkCategory() {
        return workCategory;
    }

    public void setWorkCategory(String workCategory) {
        this.workCategory = workCategory;
    }


}

