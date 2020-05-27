package com.example.owa;

public class userRegisterWorkshop extends workshopsView {

    public String user_Name;
    public String user_dob;
    public String workshop_date;
    public String user_email;
    public String workshop_name;

    public userRegisterWorkshop() {

    }

    public userRegisterWorkshop(String userName, String dob, String workshopdate, String useremail, String workshop_name) {
        this.user_Name = userName;
        this.user_dob = dob;
        this.workshop_date = workshopdate;
        this.user_email = useremail;
        this.workshop_name = workshop_name;

    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getWorkshop_date() {
        return workshop_date;
    }

    public void setWorkshop_date(String workshop_date) {
        this.workshop_date = workshop_date;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getWorkshop_name() {
        return workshop_name;
    }

    public void setWorkshop_name(String workshop_name) {
        this.workshop_name = workshop_name;
    }
}