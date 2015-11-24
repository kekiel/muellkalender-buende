package com.hoch5next.muellkalender.buende;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by kekiel on 01.11.15.
 */
public class MuellkalenderApp extends Application {

    public static Firebase myFirebaseDb;

    @Override
    public void onCreate() {
        super.onCreate();

        // init firebase database access
        Firebase.setAndroidContext(this);

        // init firebase DB
        myFirebaseDb = new Firebase("https://muellapp.firebaseio.com/calendars/0");
    }
}
