package com.hoch5next.muellkalender.buende.database;

/**
 * Created by kekiel on 24.11.15.
 */
public class Trashtype {

    private String shortname;
    private String fullname;
    private String icon;
    private String color;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Trashtype() {
    }

    Trashtype(String tdate, String author, String icon, String color) {
        this.shortname = tdate;
        this.fullname = author;
        this.icon = icon;
        this.color = color;
    }

    public String getShortname() {
        return shortname;
    }

    public String getFullname() {
        return fullname;
    }


    public String getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }
}
