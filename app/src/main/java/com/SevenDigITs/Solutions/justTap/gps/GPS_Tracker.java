package com.SevenDigITs.Solutions.justTap.gps;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.trusted.User_Id;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class GPS_Tracker extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = GPS_Tracker.class.getSimpleName();
    private HashMap<String, Marker> mMarkers = new HashMap<>();
    private GoogleMap mMap;
    boolean connected;

    // This variables is each name of session of all trusted

    public static final String PREFS_NAME_MAIN_TRUSTED = "LoginPrefsMainTrusted";

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference,ref;
    String id ;
    Double latt,longt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps__tracker);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Authenticate with Firebase when the Google map is loaded
        mMap = googleMap;
        mMap.setMaxZoomPreference(16);
       loginToFirebase();
    }

    private void loginToFirebase() {

        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            subscribeToUpdates();
            Log.d(TAG, "firebase auth success");
        }else {
            Log.d(TAG, "firebase auth failed");
        }
//        String email = getString(R.string.firebase_email);
//        String password = getString(R.string.firebase_password);
//        // Authenticate with Firebase and subscribe to updates
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(
//                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    subscribeToUpdates();
//                    Log.d(TAG, "firebase auth success");
//                } else {
//                    Log.d(TAG, "firebase auth failed");
//                }
//            }
//        });
    }

    private void subscribeToUpdates() {

        // Get the location from firebase
     //   String deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //Get the bundle from User_id activity
        Bundle bundle = getIntent().getExtras();
        //Extract the data from User_id activity

        id = bundle.getString("id");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        System.out.println(id);

        /** Check if mobile network is available or not **/
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            System.out.println("Connected to Network");
        /** get name and make a validation */
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {

                    ref = FirebaseDatabase.getInstance().getReference().child(id).child("locations");
                    ref.child("latitude").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            /** Get map of users in datasnapshot */
                            Double lath = dataSnapshot.getValue(Double.class);
                            latt = lath;
                            System.out.println(latt);
                        }@Override
                        public void onCancelled(DatabaseError databaseError) {
                            /**  Failed to read value */
                            Log.e(TAG, "Failed to read user", databaseError.toException());
                        }
                    });

                    ref.child("longitude").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            /** Get map of users in datasnapshot */
                            Double lon = dataSnapshot.getValue(Double.class);
                            longt = lon;
                            System.out.println(longt);
                        }@Override
                        public void onCancelled(DatabaseError databaseError) {
                            /**  Failed to read value */
                            Log.e(TAG, "Failed to read user", databaseError.toException());
                        }
                    });

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



        final String path = (id) + "/" + getString(R.string.firebase_path);
        /** get name and make a validation */
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {
                    ref = FirebaseDatabase.getInstance().getReference().child(id).child("locations");

                    ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                            String key = dataSnapshot.getKey();
//                            double lat = Double.parseDouble(latt.toString().trim());
//                            double lng = Double.parseDouble(longt.toString().trim());
//                            System.out.println(lat+lng);
                            LatLng location = new LatLng(latt, longt);
                            if (!mMarkers.containsKey(key)) {
                                mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
                            } else {
                                mMarkers.get(key).setPosition(location);
                            }
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            for (Marker marker : mMarkers.values()) {
                                builder.include(marker.getPosition());
                            }
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

                            String key = dataSnapshot.getKey();

//                            double lat = Double.parseDouble(latt.toString().trim());
//                            double lng = Double.parseDouble(longt.toString().trim());
//                            System.out.println(lat+lng);
                            LatLng location = new LatLng(latt, longt);
                            if (!mMarkers.containsKey(key)) {
                                mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
                            } else {
                                mMarkers.get(key).setPosition(location);
                            }
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            for (Marker marker : mMarkers.values()) {
                                builder.include(marker.getPosition());
                            }
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.d(TAG, "Failed to read value.", error.toException());
                            System.out.println("Failed to read value");
                        }
                    });

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



    }
    // Add marker to map with user location
    private void setMarker(DataSnapshot dataSnapshot) {
        // When a location update is received, put or update
        // its value in mMarkers, which contains all the markers
        // for locations received, so that we can build the
        // boundaries required to show them all on the map at once
        String key = dataSnapshot.getKey();
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
        System.out.println(value);
        double lat = Double.parseDouble(value.get("latitude").toString());
        double lng = Double.parseDouble(value.get("longitude").toString());
        System.out.println(lat+lng);
        LatLng location = new LatLng(lat, lng);
        if (!mMarkers.containsKey(key)) {
            mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
        } else {
            mMarkers.get(key).setPosition(location);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkers.values()) {
            builder.include(marker.getPosition());
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));

    }


    // add about you menu
//    @Override
//    /** Inflate the menu; this adds items to the action bar if it is present */
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.my_menu, menu);
//        return true;
//    }

   // @Override
    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     */
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.About_you) {
//            SharedPreferences settings = getSharedPreferences(PREFS_NAME_MAIN_TRUSTED, 0);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.remove("logged1");
//            editor.clear();
//            editor.commit();
//            mAuth.signOut();
//            Intent n = new Intent(getApplicationContext(), MainLancher.class);
//            startActivity(n);
//            finish();
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent n = new Intent(getApplicationContext(), User_Id.class);
        startActivity(n);
        finish();
    }
}
