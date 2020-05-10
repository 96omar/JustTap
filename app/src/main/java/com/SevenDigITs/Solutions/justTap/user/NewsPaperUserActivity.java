package com.SevenDigITs.Solutions.justTap.user;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
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
import com.SevenDigITs.Solutions.justTap.listview.UserMiniCardAdapter;
import com.SevenDigITs.Solutions.justTap.listview.UserMiniCardClass;

import java.util.ArrayList;
import java.util.List;

public class NewsPaperUserActivity extends AppCompatActivity {

    // main declaration
    private RecyclerView recyclerView;
    private UserMiniCardAdapter adapter;
    private List<UserMiniCardClass> list;
    private RelativeLayout l1;
    private Animation uptodown, downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_paper_user);


        recyclerView = (RecyclerView) findViewById(R.id.user_newspaper_recycler_view);
        /** Make animation In List View **/
        //l1 = (RelativeLayout) findViewById(R.id.relative_anmi_tools);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        recyclerView.setAnimation(downtoup);

        list = new ArrayList<>();
        adapter = new UserMiniCardAdapter(this, list);

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

        UserMiniCardClass a = new UserMiniCardClass(R.string.cardTitleNews1);
        list.add(a);

        a = new UserMiniCardClass(R.string.cardTitleNews2);
        list.add(a);

        a = new UserMiniCardClass(R.string.cardTitleNews3);
        list.add(a);

        a = new UserMiniCardClass(R.string.cardTitleNews4);
        list.add(a);

        a = new UserMiniCardClass(R.string.cardTitleNews5);
        list.add(a);

        a = new UserMiniCardClass(R.string.cardTitleNews6);
        list.add(a);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new UserMiniCardAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // albumList.get(position);
                //Toast.makeText(MainActivity.this, "You Clicked ", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    Uri webpage = Uri.parse("https://akhbarelyom.com/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                } else if (position == 1) {
                    Uri webpage = Uri.parse("https://www.youm7.com/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                } else if (position == 2) {
                    Uri webpage = Uri.parse("http://www.akhbarak.net/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                } else if (position == 3) {
                    Uri webpage = Uri.parse("https://www.shorouknews.com/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                } else if (position == 4) {
                    Uri webpage = Uri.parse("http://www.ahram.org.eg/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } else if (position == 5) {
                    Uri webpage = Uri.parse("http://www.algomhuria.net.eg/algomhuria/today/fpage/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                }

            }
        });

    }


}