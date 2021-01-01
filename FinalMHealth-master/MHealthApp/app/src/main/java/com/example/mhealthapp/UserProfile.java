package com.example.mhealthapp;

public class UserProfile {
   public String name, userageV, userweightV, userheightV,
            usergenderV , userDOB,userDiabatic,userBloodprsr,
            userHeartdisease,userPregnant,userOther, userBloodgrp;

    public UserProfile(String name, String userageV, String userweightV, String userheightV, String usergenderV, String userDOB, String userDiabatic, String userBloodprsr, String userHeartdisease, String userPregnant, String userOther, String userBloodgrp) {
        this.name = name;
        this.userageV = userageV;
        this.userweightV = userweightV;
        this.userheightV = userheightV;
        this.usergenderV = usergenderV;
        this.userDOB = userDOB;
        this.userDiabatic = userDiabatic;
        this.userBloodprsr = userBloodprsr;
        this.userHeartdisease = userHeartdisease;
        this.userPregnant = userPregnant;
        this.userOther = userOther;
        this.userBloodgrp = userBloodgrp;
    }



}
