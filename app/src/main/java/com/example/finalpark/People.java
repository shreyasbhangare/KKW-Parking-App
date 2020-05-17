package com.example.finalpark;

public class People {

    private String s_Name,s_prn,s_email,s_gender,s_password,s_parked;

    public People()
    {

    }

    public People(String s_Name,String s_prn, String s_email, String s_gender,String s_password,String s_parked) {
        this.s_Name = s_Name;
        this.s_email = s_email;
        this.s_gender = s_gender;
        this.s_password=s_password;
        this.s_prn=s_prn;
        this.s_parked=s_parked;
    }

    public String getS_parked() {
        return s_parked;
    }

    public void setS_parked(String s_parked) {
        this.s_parked = s_parked;
    }

    public String getS_prn() {
        return s_prn;
    }

    public void setS_prn(String s_prn) {
        this.s_prn = s_prn;
    }

    public String getS_Name() {
        return s_Name;
    }

    public void setS_Name(String s_Prn) {
        this.s_Name = s_Prn;
    }

    public String getS_email() {
        return s_email;
    }

    public void setS_email(String s_email) {
        this.s_email = s_email;
    }

    public String getS_gender() {
        return s_gender;
    }

    public void setS_gender(String s_gender) {
        this.s_gender = s_gender;
    }

    public String getS_password() {
        return s_password;
    }

    public void setS_password(String s_password) {
        this.s_password = s_password;
    }
}
