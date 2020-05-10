package com.SevenDigITs.Solutions.justTap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenDigITs.Solutions.justTap.user.MainUserActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileUsersActivity extends AppCompatActivity {

    // Declare main variables
    private ImageView imgEditImage;
    private Button btnSave;
    private LinearLayout layout1, layout2;
    private EditText edUserName, edPhoneNum, edTPhoneNum;
    private CircleImageView img;
    private TextView txtEmail;
    private static final String TAG = ProfileUsersActivity.class.getSimpleName();
    boolean connected;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Bitmap my_image;

    DatabaseReference databaseReference, rootRef, rootRef2;
    //Firebase
    FirebaseStorage storage;
    Firebase firebaseName, firebaseUserPhoneNum, firebaseTrustedPhoneNum, firebase;
    StorageReference storageReference;
    private String deviceID, userName, userEmail, userPhoneNum, userTPhoneNum, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_users);

        imgEditImage = (ImageView) findViewById(R.id.edit_photo);
        img = (CircleImageView) findViewById(R.id.profile);
        txtEmail = (TextView) findViewById(R.id.ed_email);
        edUserName = (EditText) findViewById(R.id.ed_user_name);
        edPhoneNum = (EditText) findViewById(R.id.ed_ph_user);
        edTPhoneNum = (EditText) findViewById(R.id.ed_ph);
        btnSave = (Button) findViewById(R.id.btnSaveData);
        layout1 = (LinearLayout) findViewById(R.id.ed_lin_ph1);
        layout2 = (LinearLayout) findViewById(R.id.ed_lin_ph2);

        final ProgressDialog ringProgressDialog = android.app.ProgressDialog.show(EditProfileUsersActivity.this,
                "Please wait ...", "Just Few Seconds....", true);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        /** Database Connection */
        deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        rootRef = databaseReference.child("User-" + deviceID);
        rootRef2 = databaseReference.child("Trusted-" + deviceID);
        firebase = new Firebase(databaseReference + "/User-" + deviceID);

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


                    /** Check The Profile picture  **/
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
                                        edUserName.setText(userName);
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
                                        edPhoneNum.setText(userPhoneNum);

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
                                        edTPhoneNum.setText(userTPhoneNum);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        /** Failed to read value */
                                        Log.e(TAG, "Failed to read user", databaseError.toException());
                                    }
                                });


                            } else if (dataSnapshot.child("Trusted-" + deviceID).exists()) {
                                layout1.setVisibility(View.GONE);
                                layout2.setVisibility(View.GONE);

                                /** retrieve user email from database */
                                rootRef2.child("TrustedName").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        /** Get map of users in datasnapshot */
                                        String name = dataSnapshot.getValue(String.class);
                                        userName = name.toString().trim();
                                        System.out.println(userName);
                                        edUserName.setText(userName);
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
                    Toast.makeText(getApplicationContext(), R.string.checkConnection, Toast.LENGTH_LONG).show();
                    Intent n = new Intent(getApplicationContext(), MainUserActivity.class);
                    startActivity(n);
                    finish();

                }

            }
        }).start();

        imgEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog ringProgressDialog2 = android.app.ProgressDialog.show(EditProfileUsersActivity.this,
                        "Please wait ...", "Just Few Seconds....", true);
                // Delay for checking data connection
                ringProgressDialog2.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Here you should write your time consuming task...
                            // Let the progress ring for 10 seconds...
                            Thread.sleep(4000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog2.dismiss();

                        /** Check if mobile network is available or not **/
                        connected = false;
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            //we are connected to a network
                            connected = true;
                            System.out.println("Connected to Network");



                            final String username = edUserName.getText().toString();
                            final String user_Phone = edPhoneNum.getText().toString();
                            final String trusted_Phone = edTPhoneNum.getText().toString();

                            /** get name and make a validation */
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child("User-" + deviceID).exists()) {

                                        uploadImage();


                                        firebaseName = firebase.child("UserName");
                                        firebaseName.setValue(username);

                                        firebaseUserPhoneNum = firebase.child("UserPhoneNumber");
                                        firebaseUserPhoneNum.setValue(user_Phone);

                                        firebaseTrustedPhoneNum = firebase.child("TrustedPhoneNumber");
                                        firebaseTrustedPhoneNum.setValue(trusted_Phone);

                                        Intent n = new Intent(getApplicationContext(), ProfileUsersActivity.class);
                                        startActivity(n);
                                        finish();

                                    } else if (dataSnapshot.child("Trusted-" + deviceID).exists()) {
                                        uploadImage();
                                        firebaseName = firebase.child("TrustedName");
                                        firebaseName.setValue(username);

                                        Intent n = new Intent(getApplicationContext(), ProfileUsersActivity.class);
                                        startActivity(n);
                                        finish();

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
                            Toast.makeText(getApplicationContext(), R.string.checkConnection, Toast.LENGTH_LONG).show();
                            Intent n = new Intent(getApplicationContext(), MainUserActivity.class);
                            startActivity(n);
                            finish();

                        }

                    }
                }).start();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
//                final ProgressDialog progressDialog = new ProgressDialog(this);
//                progressDialog.setTitle("Uploading...");
//                progressDialog.show();

            StorageReference ref = storageReference.child("images/image_" + deviceID + ".png");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            //ri.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
}
