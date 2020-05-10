package com.SevenDigITs.Solutions.justTap.user.account;

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
import com.SevenDigITs.Solutions.justTap.user.MainUserActivity;
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

public class User_Register extends AppCompatActivity {


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
            firebaseUserPhoneNum, firebaseTrustedPhoneNum,
            firebaseAge, firebaseGender;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);


        // declare variables
        sign_in = (Button) findViewById(R.id.user_login_button_User);
        create_account = (Button) findViewById(R.id.user_create_new_account_button_User);
        name = (EditText) findViewById(R.id.user_name_User);
        mail = (EditText) findViewById(R.id.user_email_User);
        pass = (EditText) findViewById(R.id.user_password_User);
        u_Phone = (EditText) findViewById(R.id.user_phone_number_User);
        t_Phone = (EditText) findViewById(R.id.trusted_phone_number_User);
        age = (EditText) findViewById(R.id.user_age_User);


        /**spiner code */
        spin = (Spinner) findViewById(R.id.user_genderSpinner_User);
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
        awesomeValidation.addValidation(this, R.id.user_login_button_User, "[a-zA-Z\\s]+", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.user_email_User, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.user_password_User, "((?=.*\\d)(?=.*[a-z]).{6,20})", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.user_phone_number_User, "^[+]?[0-9]{11}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.trusted_phone_number_User, "^[+]?[0-9]{11}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.user_age_User, Range.closed(15, 70), R.string.invalid_age_trusted);


        // Get firebase variables
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        String deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebase = new Firebase(databaseReference + "/User-" + deviceID);


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
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(User_Register.this,
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
                       ///
                        Toast.makeText(getApplicationContext(), R.string.dataError, Toast.LENGTH_SHORT).show();
                    }
                    connected = true;
                    System.out.println("Connected to Network");
                } else {
                    Toast.makeText(User_Register.this, R.string.checkConnection, Toast.LENGTH_LONG).show();
                    connected = false;
                }

            }
        });

        //Button for Login
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_Login.class);
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
        final String user_Phone = u_Phone.getText().toString();
        final String trusted_Phone = t_Phone.getText().toString();
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
                                                firebaseName = firebase.child("UserName");
                                                firebaseName.setValue(username);
                                                firebaseMail = firebase.child("UserEmail");
                                                firebaseMail.setValue(user_email);
                                                firebaseAge = firebase.child("UserAge");
                                                firebaseAge.setValue(user_Age);
                                                firebaseUserPhoneNum = firebase.child("UserPhoneNumber");
                                                firebaseUserPhoneNum.setValue(user_Phone);
                                                firebaseTrustedPhoneNum = firebase.child("TrustedPhoneNumber");
                                                firebaseTrustedPhoneNum.setValue(user_Phone);
                                                firebaseGender = firebase.child("UserGender");
                                                firebaseGender.setValue(String.valueOf(spin.getSelectedItem()));
                                            }
                                        }
                                    });

                            updateUI();


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            updateUI2();
                            Toast.makeText(User_Register.this, R.string.check_mail_exisiting, Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }


    // Save account and go to main activity
    public void updateUI() {
        Intent intent = new Intent(getApplicationContext(), MainUserActivity.class);
        startActivity(intent);
        //progress.dismiss();
        finish();
    }

    public void updateUI2() {
        Intent intent = new Intent(getApplicationContext(), User_Login.class);
        startActivity(intent);
        //progress.dismiss();
        finish();
    }


}


