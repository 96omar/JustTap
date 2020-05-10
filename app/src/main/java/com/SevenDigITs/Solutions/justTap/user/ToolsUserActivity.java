package com.SevenDigITs.Solutions.justTap.user;

import android.content.ComponentName;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.listview.MainAdapter;
import com.SevenDigITs.Solutions.justTap.listview.MainLancherClass;

import java.util.ArrayList;
import java.util.List;

public class ToolsUserActivity extends AppCompatActivity {

    // main declaration
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainLancherClass> list;
    private RelativeLayout l1;
    private Animation uptodown, downtoup;

    public static final String CALCULATOR_PACKAGE ="com.android.calculator2";
    public static final String CALCULATOR_CLASS ="com.android.calculator2.Calculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_user);

        getSupportActionBar().setTitle(R.string.toolsTitle);

        recyclerView = (RecyclerView) findViewById(R.id.user_tools_recycler_view);
        /** Make animation In List View **/
        //l1 = (RelativeLayout) findViewById(R.id.relative_anmi_tools);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        recyclerView.setAnimation(downtoup);

        list = new ArrayList<>();
        adapter = new MainAdapter(this, list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();


    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        final int[] covers = new int[]{
                R.mipmap.ic_user_tools_wifi,
                R.mipmap.ic_user_tools_blutooth,
                R.mipmap.ic_user_tools_airplan,
                R.mipmap.ic_user_tools_data,
                R.mipmap.ic_user_tools_sound,
                R.mipmap.ic_user_tools_screen,
                R.mipmap.ic_user_tools_call,
                R.mipmap.ic_user_tools_contact,
                R.mipmap.ic_user_tools_massage,
                R.mipmap.ic_user_tools_camera,
                R.mipmap.ic_user_tools_gallary,
                R.mipmap.ic_user_tools_calculator
        };


        MainLancherClass a = new MainLancherClass(R.string.cardTitleTool1, R.string.cardContentNull, covers[0]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool2, R.string.cardContentNull, covers[1]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool11, R.string.cardContentNull, covers[2]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool3, R.string.cardContentNull, covers[3]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool12, R.string.cardContentNull, covers[4]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool4, R.string.cardContentNull, covers[5]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool5, R.string.cardContentNull, covers[6]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool6, R.string.cardContentNull, covers[7]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool7, R.string.cardContentNull, covers[8]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool8, R.string.cardContentNull, covers[9]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool9, R.string.cardContentNull, covers[10]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleTool10, R.string.cardContentNull, covers[11]);
        list.add(a);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new MainAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // albumList.get(position);
                //Toast.makeText(MainActivity.this, "You Clicked ", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    intent.setComponent(cn);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (position == 1) {
                    final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName("com.android.settings",
                            "com.android.settings.bluetooth.BluetoothSettings");
                    intent.setComponent(cn);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (position == 2) {
                    Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (position == 3) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
                    startActivity(intent);

                } else if (position == 4) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SOUND_SETTINGS), 0);

                } else if (position == 5) {
                    startActivityForResult(new Intent(Settings.ACTION_DISPLAY_SETTINGS), 0);

                } else if (position == 6) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    startActivity(intent);

                } else if (position == 7) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                    startActivity(intent);
                }
                else if (position == 8) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                    //intent.setType("vnd.android-dir/mms-sms");
                    startActivity(intent);
                }
                else if (position == 9) {
                    Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
                else if (position == 10) {
                    Intent galleryIntent = new Intent(Intent.ACTION_VIEW,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(galleryIntent);
                }
                else if (position == 11) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setComponent(new ComponentName(
                            CALCULATOR_PACKAGE,
                            CALCULATOR_CLASS));
                    startActivity(intent);
                }

            }
        });

    }


}
