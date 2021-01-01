package com.example.mhealthapp;

//Class For Pill ReminDEr
// User Will View This INFO
public class User {
    private String med_text;
    private String dose_text;
    private String food_text;
    private String time_text1;
    private String time_text2;
    private String time_text3;

    public User(String med_txt,String dose_txt,String food_txt,
                String time_txt1,String time_txt2,String time_txt3){
        this.med_text = med_txt;
        this.dose_text = dose_txt;
        this.food_text = food_txt;
        this.time_text1 = time_txt1;
        this.time_text2 = time_txt2;
        this.time_text3 = time_txt3;
    }

    public String getMed_txt(){
        return med_text;
    }
    public String getDose_txt(){
        return dose_text;
    }
    public String getFood_txt(){
        return food_text;
    }
    public String getTime_txt1(){
        return time_text1;
    }
    public String getTime_txt2(){
        return time_text2;
    }
    public String getTime_txt3(){
        return time_text3;
    }

}