package com.SevenDigITs.Solutions.justTap.user.medication.mfaqs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.listview.MainAdapter;
import com.SevenDigITs.Solutions.justTap.listview.MainLancherClass;
import com.SevenDigITs.Solutions.justTap.user.CommunicationCompanyUserActivity;
import com.SevenDigITs.Solutions.justTap.user.medication.HealthyUserActivity;

import java.util.ArrayList;
import java.util.List;

public class FAQSHealthyActivity extends AppCompatActivity {

    // main declaration
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainLancherClass> list;
    private RelativeLayout l1;
    private Animation uptodown, downtoup;
    private ScrollView scrollView;
    private TextView mainTitle, content1, content2, content3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqshealthy);

        recyclerView = (RecyclerView) findViewById(R.id.user_health_faqs_recycler_view);

        scrollView = (ScrollView) findViewById(R.id.scrollHealth);
        mainTitle = (TextView) findViewById(R.id.faqs_disease);
        content1 = (TextView) findViewById(R.id.faqs_treat_content);
        content2 = (TextView) findViewById(R.id.faqs_Def);
        content3 = (TextView) findViewById(R.id.links);


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
                R.mipmap.ic_user_faqs_q,
                R.mipmap.ic_user_faqs_q,
                R.mipmap.ic_user_faqs_q,
                R.mipmap.ic_user_faqs_q,
                R.mipmap.ic_user_faqs_q,

        };


        MainLancherClass a = new MainLancherClass(R.string.cardTitleFAQs1, R.string.cardContentNull, covers[0]);
        list.add(a);


        a = new MainLancherClass(R.string.cardTitleFAQs2, R.string.cardContentNull, covers[1]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleFAQs3, R.string.cardContentNull, covers[2]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleFAQs4, R.string.cardContentNull, covers[3]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleFAQs5, R.string.cardContentNull, covers[4]);
        list.add(a);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new MainAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // albumList.get(position);
                //Toast.makeText(MainActivity.this, "You Clicked ", Toast.LENGTH_SHORT).show();
                if (position == 0) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleFAQs1);
                    content2.setText(R.string.cardContentFAQs1);
                    content1.setText(R.string.DiabetesTreatment);
                    content3.setMovementMethod(LinkMovementMethod.getInstance());
                    content3.setText(R.string.DiabetesLink1);




                } else if (position == 1) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleFAQs2);
                    content2.setText(R.string.cardContentFAQs2);
                    content1.setText(R.string.BloodPressureTreatment);
                    content3.setMovementMethod(LinkMovementMethod.getInstance());
                    content3.setText(R.string.BloodPressureLink);
                }
                else if (position == 2) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleFAQs3);
                    content2.setText(R.string.cardContentFAQs3);
                    content1.setText(R.string.HepatitisCTreatment);
                    content3.setMovementMethod(LinkMovementMethod.getInstance());
                    content3.setText(R.string.HepatitisClink);
                }
                else if (position == 3) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleFAQs4);
                    content2.setText(R.string.cardContentFAQs4);
                    content1.setText(R.string.AlzheimerTreatment);
                    content3.setMovementMethod(LinkMovementMethod.getInstance());
                    content3.setText(R.string.AlzheimerLink);
                }
                else if (position == 4) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleFAQs5);
                    content2.setText(R.string.cardContentFAQs5);
                    content1.setText(R.string.BreastCancerTreatment);
                    content3.setMovementMethod(LinkMovementMethod.getInstance());
                    content3.setText(R.string.BreastCancerLink);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        if(recyclerView.getVisibility() == View.GONE) {
            scrollView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } else if(recyclerView.getVisibility() == View.VISIBLE) {
            Intent i = new Intent(getApplicationContext(), HealthyUserActivity.class);
            startActivity(i);
            finish();
        }
    }
}

