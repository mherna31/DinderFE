package com.example.dinderfe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Profile implements Serializable {
    String first, last, avatar;
    int gender, diet, interest;

    public Profile(JSONObject body) throws JSONException{

        this.first = body.getString("firstname");
        this.last = body.getString("lastname");
        this.avatar = null;
        this.gender = body.getInt("gender");
        this.diet = body.getInt("dietaryRestrictions");
        this.interest = body.getInt("interests");
    }


    @Override
    public String toString() {
        return "Profile{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + gender +
                ", diet=" + diet +
                ", interest=" + interest +
                '}';
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getGender() {
        return gender;
    }

    public int getDiet() {
        return diet;
    }

    public int getInterest() {
        return interest;
    }
}
