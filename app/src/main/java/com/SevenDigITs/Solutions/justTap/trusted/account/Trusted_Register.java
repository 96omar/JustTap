package com.SevenDigITs.Solutions.justTap.trusted.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.trusted.User_Id;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Range;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class Trusted_Register extends AppCompatActivity {


    private EditText name, mail, pass, u_Phone, t_Phone, age;
    private Button sign_in, create_account;
    Spinner spin;
    Intent intent;
    boolean connected;


    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    //defining firebase DB & Ath
    private FirebaseAuth mAuth;
    Firebase firebase, firebaseName, firebaseMail,
            firebaseAge, firebaseGender;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trusted_register);


        // declare variables
        sign_in = (Button) findViewById(R.id.trusted_login_button_Trusted);
        create_account = (Button) findViewById(R.id.trusted_create_new_account_button_Trusted);
        name = (EditText) findViewById(R.id.trusted_name_Trusted);
        mail = (EditText) findViewById(R.id.trusted_email_Trusted);
        pass = (EditText) findViewById(R.id.trusted_password_Trusted);
        age = (EditText) findViewById(R.id.trusted_age_Trusted);


        /**spiner code */
        spin = (Spinner) findViewById(R.id.trusted_genderSpinner_Trusted);
        List<String> list = new ArrayList<String>();
        String gender1 = getString(R.string.spiner_item1);
        String gender2 = getString(R.string.spiner_item2);
        list.add(gender1);
        list.add(gender2);
        //spin.setBackgroundColor(c);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item_drop_down);
        spin.setAdapter(dataAdapter);

        /**starting validation code*/
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        /**adding validation to edittexts*/
        awesomeValidation.addValidation(this, R.id.trusted_name_Trusted, "[a-zA-Z\\s]+", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.trusted_email_Trusted, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.trusted_password_Trusted, "((?=.*\\d)(?=.*[a-z]).{6,20})", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.trusted_age_Trusted, Range.closed(15, 70), R.string.invalid_age_trusted);


        // Get firebase variables
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        String deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebase = new Firebase(databaseReference + "/Trusted-" + deviceID);


        // Button for create new account
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** check if mobile network is available or not**/
                connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    if (awesomeValidation.validate()) {
                        // Delay for register
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(Trusted_Register.this,
                                "Please wait ...", "Just Few Seconds....", true);
                        ringProgressDialog.setCancelable(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Here you should write your time consuming task...
                                    // Let the progress ring for 10 seconds...
                                    Thread.sleep(5000);
                                } catch (Exception e) {

                                }
                                ringProgressDialog.dismiss();
                                createaccount();
                            }
                        }).start();

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.dataError, Toast.LENGTH_SHORT).show();
                    }
                    connected = true;
                    System.out.println("Connected to Network");
                } else {
                    Toast.makeText(Trusted_Register.this, R.string.checkConnection, Toast.LENGTH_LONG).show();
                    connected = false;
                }

            }
        });

        //Button for Login
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Trusted_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method for Create new account
    public void createaccount() {
        // Transfer variables to strings to use in firebase
        final String user_email = mail.getText().toString();
        final String user_password = pass.getText().toString();
        final String username = name.getText().toString();
        final String user_Age = age.getText().toString();

        mAuth.createUserWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Log.d(TAG, "User profile updated.");
                                                // Adding Data In Firebase
                                                firebaseName = firebase.child("TrustedName");
                                                firebaseName.setValue(username);
                                                firebaseMail = firebase.child("TrustedEmail");
                                                firebaseMail.setValue(user_email);
                                                firebaseAge = firebase.child("TrustedAge");
                                                firebaseAge.setValue(user_Age);
                                                firebaseGender = firebase.child("TrustedGender");
                                                firebaseGender.setValue(String.valueOf(spin.getSelectedItem()));
                                            }
                                        }
                                    });

                            updateUI();


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            updateUI2();
                            Toast.makeText(Trusted_Register.this, R.string.check_mail_exisiting, Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }


    // Save account and go to main activity
    public void updateUI() {
        Intent intent = new Intent(getApplicationContext(), User_Id.class);
        startActivity(intent);
        finish();
    }

    public void updateUI2() {
        Intent intent = new Intent(getApplicationContext(), Trusted_Login.class);
        startActivity(intent);
        finish();
    }

}


