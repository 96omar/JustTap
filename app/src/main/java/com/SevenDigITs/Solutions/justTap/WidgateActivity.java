package com.SevenDigITs.Solutions.justTap;

import android.Manifest;
import android.Manifest.permission;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


/**
 * Implementation of App Widget functionality.
 */

public class WidgateActivity extends AppWidgetProvider {
    //declaration to get mobile from firebase
    private static final String TAG = "ViewDatabase";
    String mobile;
    Intent callIntent;

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (final int appWidgetId : appWidgetIds) {

            //  Construct the RemoteViews object
            updateAppWidget(context, appWidgetManager, appWidgetId);

            //get trusted phone number from firebase
            String deviceID = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference rootref = ref.child("User-" + deviceID);

            rootref.child("TrustedPhoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    //Get map of users in datasnapshot
                    mobile = dataSnapshot.getValue(String.class).toString();


                    System.out.println("this is " + mobile);
                    callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.setData(Uri.parse("tel:" + mobile));
                    System.out.println("this is new" + mobile);

                    // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_widgate);

                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, callIntent, 0);

                    // Here the basic operations the remote view can do.
                    views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);

                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read user", databaseError.toException());
                }

            });

        }

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,

                                 int appWidgetId) {


        // Construct an Intent object includes web adresss.


    }


}