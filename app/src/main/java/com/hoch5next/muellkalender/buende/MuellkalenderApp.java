package com.hoch5next.muellkalender.buende;

import android.app.Application;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.hoch5next.muellkalender.buende.database.Trashtype;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by kekiel on 01.11.15.
 */
public class MuellkalenderApp extends Application {

    public static Firebase myFirebaseDb;
    private Firebase myTrashTypes;
    public static Map<String,Trashtype> trashTypeMap;

    @Override
    public void onCreate() {
        super.onCreate();

        // init firebase database access
        Firebase.setAndroidContext(this);

        // init firebase DB
        myFirebaseDb = new Firebase("https://muellapp.firebaseio.com/calendars/0");

        // load list of trashtypes for selected calendar
        myTrashTypes = myFirebaseDb.child("trashtypes");

        // retrieve values and store to map
        myTrashTypes.addValueEventListener(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                   // @todo error handling / what if no connection available on first launch?
                                                   GenericTypeIndicator<Map<String,Trashtype>> t = new GenericTypeIndicator<Map<String,Trashtype>>() {};
                                                   trashTypeMap = dataSnapshot.getValue(t);
                                               }

                                               @Override
                                               public void onCancelled(FirebaseError firebaseError) {

                                               }
                                           }
        );

    }
}
