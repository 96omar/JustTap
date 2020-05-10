package com.SevenDigITs.Solutions.justTap.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.listview.MainAdapter;
import com.SevenDigITs.Solutions.justTap.MainLancher;
import com.SevenDigITs.Solutions.justTap.listview.MainLancherClass;
import com.SevenDigITs.Solutions.justTap.ProfileUsersActivity;
import com.SevenDigITs.Solutions.justTap.trusted.User_Id;
import com.SevenDigITs.Solutions.justTap.user.apps.MainFragment;
import com.SevenDigITs.Solutions.justTap.user.help.HelpUserActivity;
import com.SevenDigITs.Solutions.justTap.user.medication.HealthyUserActivity;
import com.SevenDigITs.Solutions.justTap.user.note.MainActivityReminder;
import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainUserActivity extends AppCompatActivity {

    // main declaration
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainLancherClass> list;
    private RelativeLayout l1;
    private Animation uptodown, downtoup;
    public static final String PREFS_NAME_MAIN_USER = "LoginPrefsMainUser";

    String lang;

    //For Floating Button
    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton flotBtnProfile, flotBtnCalender, flotBtnAlarm,
            flotBtnLogout, flotBtnHelp, flotBtnTranslate, flotBtnBrowser, flotBtnFlash;

    // Boalean Variable to check the connectivity of internet And Flash checked
    boolean connected, isTorchOn;

    // Deceleration for Flash Light
    private CameraManager mCameraManager;
    private String mCameraId;

    //Firebase Deceleration
    private static final String TAG = "ViewDatabase";
    private FirebaseAuth mAuth;
    private Firebase myFirebase;
    DatabaseReference databaseReference, rootRef;

    //Emergency calling
    public String mobile, deviceID;

    //GPS tracker Deceleration for PERMISSIONS REQUEST
    private static final int PERMISSIONS_REQUEST = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        /**Start GPS Tracker
         //Check GPS is enabled**/
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this, R.string.errorGPSMassage, Toast.LENGTH_SHORT).show();
        }

        /**Check location permission is granted - if it is, start the service, otherwise request the permission**/
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }

        /** open gps location system  **/
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainUserActivity.this);
            dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH);
        int permission3 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (permission2 == PackageManager.PERMISSION_GRANTED
                && permission3 == PackageManager.PERMISSION_GRANTED) {
            System.out.println("BLUETOOTH ENABLED");
            System.out.println("CAMERA ENABLED");
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH},
                    PERMISSIONS_REQUEST);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST);
        }


        /** check if mobile network is available or not**/
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            System.out.println("Connected to Network");
        } else {
            Toast.makeText(MainUserActivity.this,
                    R.string.checkConnection, Toast.LENGTH_LONG).show();
            connected = false;
        }


/**start firebasae auth to get phonenumber for emergency**/
        connected = false;
        ConnectivityManager connectivityManager2 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager2.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            mAuth = FirebaseAuth.getInstance();
            deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            rootRef = databaseReference.child("/User-" + deviceID);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("User-" + deviceID).exists()) {
                        /** retrieve user email from database */
                        rootRef.child("TrustedPhoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                /** Get map of users in datasnapshot */
                                String ph = dataSnapshot.getValue(String.class);
                                mobile = ph.toString().trim();
                                System.out.println(mobile);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                /**  Failed to read value */
                                Log.e(TAG, "Failed to read user data", databaseError.toException());
                            }
                        });

                    } else {

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
            Toast.makeText(MainUserActivity.this,
                    R.string.checkConnection, Toast.LENGTH_LONG).show();
            connected = false;
        }


        /** Code for menu for Floating Button**/
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        flotBtnProfile = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        flotBtnCalender = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        flotBtnAlarm = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        flotBtnHelp = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        flotBtnLogout = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);
        flotBtnTranslate = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item6);
        //flotBtnExit = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item6);
        //flotBtnBrowser = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item7);
        flotBtnFlash = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item8);

        flotBtnProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent profile = new Intent(MainUserActivity.this, ProfileUsersActivity.class);
                startActivity(profile);

            }
        });
        isTorchOn = false;
        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(MainUserActivity.this)
                    .create();
            alert.setTitle(R.string.flashTitleError);
            alert.setMessage("Your device does not support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // closing the application
                            finish();
                            System.exit(0);
                        }
                    });
            alert.show();
            return;
        }
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        flotBtnFlash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (isTorchOn) {
                        turnOffFlashLight();
                        isTorchOn = false;
                    } else {
                        Toast.makeText(MainUserActivity.this, R.string.flashTapAgain, Toast.LENGTH_LONG).show();
                        turnOnFlashLight();
                        isTorchOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        flotBtnTranslate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = null;
                if (flotBtnTranslate.getLabelText().toString().equals("English")) {
                    flotBtnTranslate.setLabelText("عربي");
                    String lang1 = "ar";
                    changeLang(getBaseContext(), lang1);
                    Toast.makeText(getApplicationContext(), "Please Wait , Open Application Again After 20 sec ,Thanks For using JustTap", Toast.LENGTH_LONG).show();

                } else if (flotBtnTranslate.getLabelText().toString().equals("عربي")) {
                    flotBtnTranslate.setLabelText("English");
                    String lang1 = "en";
                    changeLang(getBaseContext(), lang1);
                    Toast.makeText(getApplicationContext(), "Please Wait , Open Application Again After 20 sec ,Thanks For using JustTap", Toast.LENGTH_LONG).show();

                }


//                Intent intent = new Intent(getApplicationContext(), ToolsUserActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();

            }
        });
//        flotBtnBrowser.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY, "");
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
//
//            }
//        });
        flotBtnCalender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long startMillis = System.currentTimeMillis();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                startActivity(intent);

            }
        });
        flotBtnAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                startActivity(intent);

            }
        });
        flotBtnHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent help = new Intent(getApplicationContext(), HelpUserActivity.class);
                startActivity(help);

            }
        });
        flotBtnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    mAuth.signOut();
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME_MAIN_USER, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove("logged2");
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), MainLancher.class);
                    startActivity(i);
                    finish();

                } else {
                    Intent i = new Intent(getApplicationContext(), User_Id.class);
                    startActivity(i);
                    finish();
                }
            }
        });
//        flotBtnExit.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//                homeIntent.addCategory(Intent.CATEGORY_HOME);
//                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(homeIntent);
//                // stopService(new Intent(MainUserActivity.this, TrackerService.class));
//                finish();
//                System.exit(0);
//
//            }
//        });
        /** Ending Code for menu for Floating Button**/


        // This for toolbar of this activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);
        /** Make animation In List View **/
        l1 = (RelativeLayout) findViewById(R.id.relative_anmi);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        l1.setAnimation(uptodown);
        recyclerView.setAnimation(downtoup);

        list = new ArrayList<>();
        adapter = new MainAdapter(this, list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.logo).into((ImageView) findViewById(R.id.user_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * GPS tracker Methods
     * //This methods responsable for Get location of user throw notification
     **/
    private void startTrackerService() {

        startService(new Intent(this, TrackerService.class));
        // finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            startTrackerService();
        } else {
            //finish();
        }
    }

    public static void changeLang(Context context, String lang) {

        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    /**
     * Method for turning on flash
     **/
    public void turnOnFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
                flotBtnFlash.setImageResource(R.drawable.ic_menu_flash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for turning off flash
     **/
    public void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
                flotBtnFlash.setImageResource(R.drawable.ic_menu_flash_off);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Emergency Call method
     **/
    public void call() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        startActivity(callIntent);
    }


    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.user_collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.user_appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        final int[] covers = new int[]{
                R.mipmap.ic_emrg_user,
                R.mipmap.ic_user_tools,
                R.mipmap.ic_user_health,
                R.mipmap.ic_user_reminder,
                R.mipmap.ic_user_apps,
                R.mipmap.ic_user_newspaper,
                R.mipmap.ic_user_charge,
                R.mipmap.ic_user_emergency_call

        };


        MainLancherClass a = new MainLancherClass(R.string.cardTitle1, R.string.cardContent1, covers[0]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitle2, R.string.cardContent2, covers[1]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitle3, R.string.cardContent3, covers[2]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitle4, R.string.cardContent4, covers[3]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitle5, R.string.cardContent5, covers[4]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitle6, R.string.cardContent6, covers[5]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitle7, R.string.cardContent7, covers[6]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitle8, R.string.cardContent8, covers[7]);
        list.add(a);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new MainAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // albumList.get(position);
                //Toast.makeText(MainActivity.this, "You Clicked ", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    /** check if mobile network is available or not**/
                    connected = false;
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        call();
                        connected = true;
                        //System.out.println("Connected to Network");
                    } else {
                        Toast.makeText(MainUserActivity.this,
                                R.string.checkConnection, Toast.LENGTH_LONG).show();
                        connected = false;
                    }

                } else if (position == 1) {
                    Intent Tools = new Intent(MainUserActivity.this, ToolsUserActivity.class);
                    startActivity(Tools);
                } else if (position == 2) {
                    Intent healthy = new Intent(MainUserActivity.this, HealthyUserActivity.class);
                    startActivity(healthy);
                } else if (position == 3) {
                    Intent note = new Intent(MainUserActivity.this, MainActivityReminder.class);
                    startActivity(note);
                } else if (position == 4) {
                    Toast.makeText(MainUserActivity.this,
                            R.string.loadingApps, Toast.LENGTH_LONG).show();
                    Intent apps = new Intent(MainUserActivity.this, MainFragment.class);
                    startActivity(apps);

                    // Delay For Loading Apps
                    final ProgressDialog ringProgressDialog = ProgressDialog.show(MainUserActivity.this,
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
                        }
                    }).start();


                } else if (position == 5) {
                    // TODO Change The newspaper design List
                    Intent newsPaper = new Intent(MainUserActivity.this, NewsPaperUserActivity.class);
                    startActivity(newsPaper);
                } else if (position == 6) {
                    // TODO Change The Chargee design List
                    Intent charge = new Intent(MainUserActivity.this, CommunicationCompanyUserActivity.class);
                    startActivity(charge);
                } else if (position == 7) {
                    // TODO Change The Emergency design List
                    Intent emeargency = new Intent(MainUserActivity.this, EmergencyCallingActivityUser.class);
                    startActivity(emeargency);
                }


            }
        });

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, TrackerService.class));
        finish();
        System.exit(0);
    }
}
