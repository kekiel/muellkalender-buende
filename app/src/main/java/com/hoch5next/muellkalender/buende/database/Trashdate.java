package com.hoch5next.muellkalender.buende.database;

/**
 * Created by kekiel on 24.11.15.
 */
public class Trashdate {

    private String date;
    private String type;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Trashdate() {
    }

    Trashdate(String tdate, String author) {
        this.date = tdate;
        this.type = author;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }


}
