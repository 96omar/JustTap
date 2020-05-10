package com.SevenDigITs.Solutions.justTap.user.medication;

import android.content.Intent;
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
import com.SevenDigITs.Solutions.justTap.user.medication.mfaqs.FAQSHealthyActivity;
import com.SevenDigITs.Solutions.justTap.user.medication.mpill.ViewController.MainActivityBill;

import java.util.ArrayList;
import java.util.List;

public class HealthyUserActivity extends AppCompatActivity {

    // main declaration
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainLancherClass> list;
    private RelativeLayout l1;
    private Animation uptodown, downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_user);

        recyclerView = (RecyclerView) findViewById(R.id.user_health_recycler_view);
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
                R.mipmap.ic_user_health_pill,
                R.mipmap.ic_user_health_faqs

        };


        MainLancherClass a = new MainLancherClass(R.string.cardTitleHealth1, R.string.cardContentHealth1, covers[0]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleHealth2, R.string.cardContentHealth2, covers[1]);
        list.add(a);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new MainAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // albumList.get(position);
                //Toast.makeText(MainActivity.this, "You Clicked ", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    Intent questionsIntent = new Intent(HealthyUserActivity.this, MainActivityBill.class);
                    startActivity(questionsIntent);

                } else if (position == 1) {
                    Intent questionsIntent = new Intent(HealthyUserActivity.this, FAQSHealthyActivity.class);
                    startActivity(questionsIntent);
                }

            }
        });

    }


}
