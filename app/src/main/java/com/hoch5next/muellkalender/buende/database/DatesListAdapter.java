package com.hoch5next.muellkalender.buende.database;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.hoch5next.muellkalender.buende.MuellkalenderApp;
import com.hoch5next.muellkalender.buende.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by kekiel on 24.11.15.
 */
public class DatesListAdapter extends FirebaseListAdapter<Trashdate> {

    private static SimpleDateFormat sdfDateText = new SimpleDateFormat("EEEE, dd.MMMM yyyy", Locale.getDefault());

    public DatesListAdapter(Query ref, Activity activity, int layout) {
        super(ref, Trashdate.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>Trashdate</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view  A view instance corresponding to the layout we passed to the constructor.
     * @param model An instance representing the current date to be shown
     */

    @Override
    protected void populateView(View view, Trashdate model) {
        Date trashDate = Date.valueOf(model.getDate());
        String trashTypeText = model.getType();
        // look up text, color & icon in map of trashtypes
        if (MuellkalenderApp.trashTypeMap != null) {
            Trashtype theType = MuellkalenderApp.trashTypeMap.get(model.getType());
            if (theType != null) {
                trashTypeText = theType.getFullname();
            }
        }
        ((TextView) view.findViewById(R.id.trash_type_text)).setText(trashTypeText);
        ((TextView) view.findViewById(R.id.trash_date)).setText(sdfDateText.format(trashDate));
    }
}

