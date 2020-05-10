package com.SevenDigITs.Solutions.justTap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenDigITs.Solutions.justTap.user.MainUserActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUsersActivity extends AppCompatActivity {

    // Declare main variables
    private ImageView imgEditProfile, rate;
    private TextView txtUserName, txtEmail, txtPhoneNum, txtTPhoneNum, txtDeviceId;
    private Button btnShared;
    private LinearLayout layout1, layout2, layout3;
    private View view;
    private static final String TAG = ProfileUsersActivity.class.getSimpleName();
    private Animation animAlpha;
    boolean connected;
    private CircleImageView img;
    private Bitmap my_image;

    DatabaseReference databaseReference, rootRef, rootRef2;
    FirebaseStorage storage;
    private String deviceID, userName, userEmail, userPhoneNum, userTPhoneNum, userID;
    Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_users);

        imgEditProfile = (ImageView) findViewById(R.id.edit);
        rate = (ImageView) findViewById(R.id.rate);
        txtEmail = (TextView) findViewById(R.id.emailTextView);
        img = (CircleImageView) findViewById(R.id.main_profile);
        txtUserName = (TextView) findViewById(R.id.user_name);
        txtPhoneNum = (TextView) findViewById(R.id.phTextView_user);
        txtTPhoneNum = (TextView) findViewById(R.id.phTextView);
        txtDeviceId = (TextView) findViewById(R.id.device_id);
        btnShared = (Button) findViewById(R.id.btnShareID);
        layout1 = (LinearLayout) findViewById(R.id.lin_ph1);
        layout2 = (LinearLayout) findViewById(R.id.lin_ph2);
        layout3 = (LinearLayout) findViewById(R.id.lin_ph3);
        view = (View) findViewById(R.id.view_visible);
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }
        });
        final ProgressDialog ringProgressDialog = ProgressDialog.show(ProfileUsersActivity.this,
                "Please wait ...", "Just Few Seconds....", true);



        deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(getApplicationContext(), EditProfileUsersActivity.class);
                startActivity(n);

            }
        });

        //This on Click is responable for share the id to the trusted person to get start to track it
        btnShared.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnShared.startAnimation(animAlpha);
                //Get the selected text
                String selectedText = txtDeviceId.getText().toString();

                //if no text is selected share the entire text area.
                if (selectedText.length() >= 0) {
                    String dataToShare = txtDeviceId.getText().toString();
                    selectedText = dataToShare;
                }
                //Share the text
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, selectedText);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                Toast.makeText(ProfileUsersActivity.this, "Text Shared", Toast.LENGTH_SHORT).show();

            }
        });


        // Delay for checking data connection
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

                /** Check if mobile network is available or not **/
                connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                    System.out.println("Connected to Network");

                    /** Retrieve user Photo **/
                    StorageReference ref = storage.getInstance().getReferenceFromUrl("gs://just-tap-3d747.appspot.com/").child("images/image_" + deviceID + ".png");
                    try {
                        final File localFile = File.createTempFile("Images", "jpeg");
                        ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener< FileDownloadTask.TaskSnapshot >() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                my_image = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                              //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), my_image);
                                img.setImageBitmap(my_image);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /** Check if data is existed in firebase **/
                    /** Database Connection */
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    rootRef = databaseReference.child("User-" + deviceID);
                    rootRef2 = databaseReference.child("Trusted-" + deviceID);

                    /** get name and make a validation */
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("User-" + deviceID).exists()) {
                                /** retrieve user name from database */
                                rootRef.child("UserName").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        /** Get map of users in datasnapshot */
                                        String name = dataSnapshot.getValue(String.class);
                                        userName = name.toString().trim();
                                        System.out.println(userName);
                                        txtUserName.setText(userName);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        /** Failed to read value */
                                        Log.e(TAG, "Failed to read user", databaseError.toException());
                                    }
                                });

                                /** Retrieve user email from database */
                                rootRef.child("UserEmail").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        /** Get map of users in datasnapshot */
                                        String mail = dataSnapshot.getValue(String.class);
                                        userEmail = mail.toString().trim();
                                        System.out.println(userEmail);
                                        txtEmail.setText(userEmail);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        /** Failed to read value */
                                        Log.e(TAG, "Failed to read user", databaseError.toException());
                                    }
                                });

                                /** Retrieve user phone num from database */
                                rootRef.child("UserPhoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        /** Get map of users in datasnapshot */
                                        String phone = dataSnapshot.getValue(String.class);
                                        userPhoneNum = phone.toString().trim();
                                        System.out.println(userPhoneNum);
                                        txtPhoneNum.setText(userPhoneNum);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        /** Failed to read value */
                                        Log.e(TAG, "Failed to read user", databaseError.toException());
                                    }
                                });

                                /** Retrieve user trusted phone num from database */
                                rootRef.child("TrustedPhoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        /** Get map of users in datasnapshot */
                                        String phone = dataSnapshot.getValue(String.class);
                                        userTPhoneNum = phone.toString().trim();
                                        System.out.println(userTPhoneNum);
                                        txtTPhoneNum.setText(userTPhoneNum);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        /** Failed to read value */
                                        Log.e(TAG, "Failed to read user", databaseError.toException());
                                    }
                                });

                                //This to get the device id for track the user
                                txtDeviceId.setText("User-" + deviceID);

                            } else if (dataSnapshot.child("Trusted-" + deviceID).exists()) {
                                layout1.setVisibility(View.GONE);
                                layout2.setVisibility(View.GONE);
                                layout3.setVisibility(View.GONE);
                                view.setVisibility(View.GONE);

                                /** retrieve user email from database */
                                rootRef2.child("TrustedName").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        /** Get map of users in datasnapshot */
                                        String name = dataSnapshot.getValue(String.class);
                                        userName = name.toString().trim();
                                        System.out.println(userName);
                                        txtUserName.setText(userName);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        /** Failed to read value */
                                        Log.e(TAG, "Failed to read user", databaseError.toException());
                                    }
                                });

                                /** Retrieve user email from database */
                                rootRef2.child("TrustedEmail").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        /** Get map of users in datasnapshot */
                                        String mail = dataSnapshot.getValue(String.class);
                                        userEmail = mail.toString().trim();
                                        System.out.println(userEmail);
                                        txtEmail.setText(userEmail);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        /** Failed to read value */
                                        Log.e(TAG, "Failed to read user", databaseError.toException());
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.dataError, Toast.LENGTH_LONG).show();
                                Intent n = new Intent(getApplicationContext(), MainUserActivity.class);
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
                    Toast.makeText(ProfileUsersActivity.this, R.string.checkConnection, Toast.LENGTH_LONG).show();
                    Intent n = new Intent(getApplicationContext(), MainUserActivity.class);
                    startActivity(n);
                    finish();

                }

            }
        }).start();

    }


}
