package com.SevenDigITs.Solutions.justTap.user.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.MainLancher;
import com.SevenDigITs.Solutions.justTap.user.MainUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class User_Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mail, pass, e1;
    private Button sign_in, create_account, forgetPass, restBttn;
    boolean connected;
    private LinearLayout l1, l2;

    public static final String PREFS_NAME_MAIN_USER = "LoginPrefsMainUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        mail = (EditText) findViewById(R.id.user_email_login);
        pass = (EditText) findViewById(R.id.user_password_Login);
        e1 = (EditText) findViewById(R.id.user_ForgetPasswordText);
        sign_in = (Button) findViewById(R.id.user_LoginButton);
        create_account = (Button) findViewById(R.id.user_RegbuttonLog);
        forgetPass = (Button) findViewById(R.id.user_ForgetPasswordButton);
        restBttn = (Button) findViewById(R.id.user_bttnrest);
        l1 = (LinearLayout) findViewById(R.id.login_gone);
        l2 = (LinearLayout) findViewById(R.id.liner_forget_gone);
        sign_in.setVisibility(View.GONE);

        mail.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                if(!mail.getText().toString().equals("") ){
                    sign_in.setVisibility(View.VISIBLE);
                }else{
                   // Toast.makeText(getApplicationContext(), R.string.validation_massage, Toast.LENGTH_SHORT).show();
                    sign_in.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable name) {
                if(!mail.getText().toString().equals("")){
                    sign_in.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.validation_massage, Toast.LENGTH_SHORT).show();
                    sign_in.setVisibility(View.GONE);
                }

            }
        });

        // Get firbase instance
        mAuth = FirebaseAuth.getInstance();

        // On Click preform
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_Register.class);
                startActivity(intent);
            }
        });

        //on click for forget pass
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);

            }
        });


        restBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delay for Login
                final ProgressDialog ringProgressDialog = ProgressDialog.show(User_Login.this,
                        "Please wait ...", "Just Few Seconds....", true);
                ringProgressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Here you should write your time consuming task...
                            // Let the progress ring for 10 seconds...
                            Thread.sleep(4000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog.dismiss();
                        FirebaseAuth.getInstance().sendPasswordResetEmail(e1.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(User_Login.this, R.string.success_rest, Toast.LENGTH_SHORT).show();
                                            l1.setVisibility(View.VISIBLE);
                                            l2.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(User_Login.this, R.string.failed_rest, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }).start();

            }

        });



        // signin click
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** check if mobile network is available or not**/
                connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network

                    // Delay for register
                    final ProgressDialog ringProgressDialog = ProgressDialog.show(User_Login.this,
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
                            signin();
                        }
                    }).start();
                    connected = true;
                    System.out.println("Connected to Network");
                } else {
                    Toast.makeText(User_Login.this, R.string.checkConnection, Toast.LENGTH_LONG).show();
                    connected = false;
                }
            }
        });

    }

    //sign in method
    public void signin() {
        String email = mail.getText().toString();
        String password = pass.getText().toString();

        if (email.length() > 0 && password.length() > 0 && !email.equals(null) && !password.equals(null)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI();
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(User_Login.this, R.string.wrong_massage,
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), R.string.validation_massage, Toast.LENGTH_SHORT).show();
        }

    }

    // Save account and go to main activity
    public void updateUI() {
        Intent n = new Intent(getApplicationContext(), MainUserActivity.class);
        startActivity(n);
        finish();
    }

    //check if user is already sign in
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME_MAIN_USER, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("logged2");
        editor.clear();
        editor.commit();
        Intent n = new Intent(getApplicationContext(), MainLancher.class);
        startActivity(n);
        finish();

    }
}
