package com.SevenDigITs.Solutions.justTap.trusted;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.SevenDigITs.Solutions.justTap.ProfileUsersActivity;
import com.SevenDigITs.Solutions.justTap.gps.GPS_Tracker;
import com.SevenDigITs.Solutions.justTap.R;

import com.SevenDigITs.Solutions.justTap.MainLancher;
import com.SevenDigITs.Solutions.justTap.trusted.help.HelpTrustedActivity;
import com.SevenDigITs.Solutions.justTap.user.help.HelpUserActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class User_Id extends AppCompatActivity {
    private EditText userID;
    private Button goBtn ;
    private FirebaseAuth mAuth;
    boolean connected;
    private static final String TAG = ProfileUsersActivity.class.getSimpleName();
    ProgressDialog ringProgressDialog;

    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton flotBtnProfile, flotBtnTranslate, flotBtnAlarm,
            flotBtnLogout, flotBtnHelp, flotBtnExit, flotBtnBrowser, flotBtnFlash;

    DatabaseReference databaseReference,ref;

    // This variables is each name of session of all trusted
    public static final String PREFS_NAME_MAIN_TRUSTED = "LoginPrefsMainTrusted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__id);

        Toast.makeText(User_Id.this, R.string.validation_user_id, Toast.LENGTH_LONG).show();

        goBtn = (Button) findViewById(R.id.go_button);
        userID = (EditText) findViewById(R.id.user_id_edit_text);
        //imageButton = (ImageButton) findViewById(R.id.trusted_logout_F_Id);


        /** Code for menu for Floating Button**/
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_Tmenu);
        flotBtnProfile = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Titem1);
        flotBtnHelp = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Titem4);
        flotBtnLogout = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Titem5);
        flotBtnExit = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Titem6);
        flotBtnTranslate = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Titem7);

        flotBtnProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), ProfileUsersActivity.class);
                startActivity(profile);

            }
        });

        flotBtnHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent help = new Intent(getApplicationContext(), HelpTrustedActivity.class);
                startActivity(help);

            }
        });
        flotBtnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    mAuth.signOut();
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME_MAIN_TRUSTED, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove("logged1");
                    editor.clear();
                    editor.commit();
                   // Toast.makeText(getApplicationContext(),"Please Wait , Open Application Again After 20 sec ,Thanks For using JustTap", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainLancher.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(getApplicationContext(), User_Id.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        flotBtnTranslate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (flotBtnTranslate.getLabelText().toString().equals("English")){
                    flotBtnTranslate.setLabelText("عربي");
                    String lang1  = "ar";
                    changeLang(getBaseContext(),lang1);
                    Toast.makeText(getApplicationContext(),"Please Wait , Open Application Again After 20 sec ,Thanks For using JustTap", Toast.LENGTH_LONG).show();

                }
                else if (flotBtnTranslate.getLabelText().toString().equals("عربي")){
                    flotBtnTranslate.setLabelText("English");
                    String lang1  = "en";
                    changeLang(getBaseContext(),lang1);
                    Toast.makeText(getApplicationContext(),"Please Wait , Open Application Again After 20 sec ,Thanks For using JustTap", Toast.LENGTH_LONG).show();

                }



            }
        });

        flotBtnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                // stopService(new Intent(MainUserActivity.this, TrackerService.class));
                finish();
                System.exit(0);

            }
        });
        /** Ending Code for menu for Floating Button**/




        //This on click is reasonable for get the Id and sent it to map activity to start track user
        goBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final String getrec = userID.getText().toString();

                        /** Check if mobile network is available or not **/
                        connected = false;
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            //we are connected to a network
                            connected = true;
                            System.out.println("Connected to Network");
                            /** Check if data is existed in firebase **/
                            /** Database Connection */
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            ref = databaseReference.child(getrec);


                            /** get name and make a validation */
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(getrec).exists()) {
                                        Toast.makeText(getApplicationContext(), R.string.loadingLocation, Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(User_Id.this, GPS_Tracker.class);

                                        //Create the bundle
                                        Bundle bundle = new Bundle();

                                        //Add your data to bundle
                                        bundle.putString("id", getrec);

                                        //Add the bundle to the intent
                                        i.putExtras(bundle);

                                        //Fire that second activity
                                        startActivity(i);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.dataError, Toast.LENGTH_LONG).show();
                                        Intent n = new Intent(getApplicationContext(), User_Id.class);
                                        startActivity(n);
                                        finish();
                                        System.out.println("Failed to read user ID");
                                        Log.e(TAG, "Failed to read user ID");
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    /** Failed to read value */
                                    Log.e(TAG, "Failed to read user", databaseError.toException());
                                }
                            });


                        } else {
                            connected = false;
                            Toast.makeText(getApplicationContext(), R.string.checkConnection, Toast.LENGTH_LONG).show();
                            Intent n = new Intent(getApplicationContext(), User_Id.class);
                            startActivity(n);
                            finish();

                        }


            }

        });


    }

    public static void changeLang(Context context, String lang) {

        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}
