package com.SevenDigITs.Solutions.justTap.user;

import android.content.Intent;
import android.net.Uri;
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
import com.SevenDigITs.Solutions.justTap.listview.UserEmrgAdapter;
import com.SevenDigITs.Solutions.justTap.listview.UserEmrgClass;
import com.SevenDigITs.Solutions.justTap.listview.UserMiniCardAdapter;
import com.SevenDigITs.Solutions.justTap.listview.UserMiniCardClass;

import java.util.ArrayList;
import java.util.List;

public class EmergencyCallingActivityUser extends AppCompatActivity {

    // main declaration
    private RecyclerView recyclerView;
    private UserEmrgAdapter adapter;
    private List<UserEmrgClass> list;
    private RelativeLayout l1;
    private Animation uptodown, downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_calling_user);

        recyclerView = (RecyclerView) findViewById(R.id.user_emrg_recycler_view);
        /** Make animation In List View **/
        //l1 = (RelativeLayout) findViewById(R.id.relative_anmi_tools);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        recyclerView.setAnimation(downtoup);

        list = new ArrayList<>();
        adapter = new UserEmrgAdapter(this, list);

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

        UserEmrgClass a = new UserEmrgClass(R.string.cardTitleEmrg1, R.string.cardContentEmrg1);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg2, R.string.cardContentEmrg2);
        list.add(a);


        a = new UserEmrgClass(R.string.cardTitleEmrg3, R.string.cardContentEmrg3);
        list.add(a);


        a = new UserEmrgClass(R.string.cardTitleEmrg4, R.string.cardContentEmrg4);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg5, R.string.cardContentEmrg5);
        list.add(a);


        a = new UserEmrgClass(R.string.cardTitleEmrg6, R.string.cardContentEmrg6);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg7, R.string.cardContentEmrg7);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg8, R.string.cardContentEmrg8);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg9, R.string.cardContentEmrg9);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg10, R.string.cardContentEmrg10);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg11, R.string.cardContentEmrg11);
        list.add(a);

        a = new UserEmrgClass(R.string.cardTitleEmrg12, R.string.cardContentEmrg12);
        list.add(a);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new UserEmrgAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // albumList.get(position);
                //Toast.makeText(MainActivity.this, "You Clicked ", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "122"));
                    startActivity(callIntent);

                } else if (position == 1) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "123"));
                    startActivity(callIntent);

                } else if (position == 2) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "180"));
                    startActivity(callIntent);

                } else if (position == 3) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "121"));
                    startActivity(callIntent);

                } else if (position == 4) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "125"));
                    startActivity(callIntent);

                } else if (position == 5) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "175"));
                    startActivity(callIntent);


                } else if (position == 6) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "129"));
                    startActivity(callIntent);

                } else if (position == 7) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "16528"));
                    startActivity(callIntent);

                } else if (position == 8) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "126"));
                    startActivity(callIntent);


                } else if (position == 9) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "140"));
                    startActivity(callIntent);

                } else if (position == 10) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "08008886666"));
                    startActivity(callIntent);

                } else if (position == 11) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "150"));
                    startActivity(callIntent);

                }

            }
        });

    }


}