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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.listview.MainAdapter;
import com.SevenDigITs.Solutions.justTap.listview.MainLancherClass;
import com.SevenDigITs.Solutions.justTap.user.medication.HealthyUserActivity;
import com.SevenDigITs.Solutions.justTap.user.medication.mfaqs.FAQSHealthyActivity;
import com.SevenDigITs.Solutions.justTap.user.medication.mpill.ViewController.MainActivityBill;

import java.util.ArrayList;
import java.util.List;

public class CommunicationCompanyUserActivity extends AppCompatActivity {

    // main declaration
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainLancherClass> list;
    private RelativeLayout l1;
    private Animation uptodown, downtoup;
    private ScrollView scrollView;
    private TextView mainTitle, content1, content2, content3, content4, content5, content6, content7;
    private Button bttn1, bttn2, bttn3, bttn4, bttn5, bttn6, bttn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_company_user);


        recyclerView = (RecyclerView) findViewById(R.id.user_comuni_recycler_view);
        scrollView = (ScrollView) findViewById(R.id.scrollComuni);
        mainTitle = (TextView) findViewById(R.id.company_id);
        content1 = (TextView) findViewById(R.id.txt_char1);
        content2 = (TextView) findViewById(R.id.txt_char2);
        content3 = (TextView) findViewById(R.id.txt_char3);
        content4 = (TextView) findViewById(R.id.txt_char4);
        content5 = (TextView) findViewById(R.id.txt_char5);
        content6 = (TextView) findViewById(R.id.txt_char6);
        content7 = (TextView) findViewById(R.id.txt_char7);

        bttn1 = (Button) findViewById(R.id.bttn_char1);
        bttn2 = (Button) findViewById(R.id.bttn_char2);
        bttn3 = (Button) findViewById(R.id.bttn_char3);
        bttn4 = (Button) findViewById(R.id.bttn_char4);
        bttn5 = (Button) findViewById(R.id.bttn_char5);
        bttn6 = (Button) findViewById(R.id.bttn_char6);
        bttn7 = (Button) findViewById(R.id.bttn_char7);


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
                R.mipmap.ic_user_comuni_vod,
                R.mipmap.ic_user_comuni_et,
                R.mipmap.ic_user_comuni_or,
                R.mipmap.ic_user_comuni_we,

        };


        MainLancherClass a = new MainLancherClass(R.string.cardTitleCompany1, R.string.cardContentNull, covers[0]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleCompany2, R.string.cardContentNull, covers[1]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleCompany3, R.string.cardContentNull, covers[2]);
        list.add(a);

        a = new MainLancherClass(R.string.cardTitleCompany4, R.string.cardContentNull, covers[3]);
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
                    mainTitle.setText(R.string.cardTitleCompany1);

                    content1.setText("*858*code#");
                    bttn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content1.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });

                    content2.setText("*868*1#");
                    bttn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content2.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content3.setText("*868*2*num#");
                    bttn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content3.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content4.setText("*888*4#");
                    bttn4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content4.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content5.setText("*505*num#");
                    bttn5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content5.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content6.setText("*868*3#");
                    bttn6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content6.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content7.setText("888");
                    bttn7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content7.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });


                } else if (position == 1) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleCompany2);

                    content1.setText("*555*code#");
                    bttn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content1.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });

                    content2.setText("*555#");
                    bttn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content2.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content3.setText("*557*Num*cost#");
                    bttn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content3.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content4.setText("250");
                    bttn4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content4.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content5.setText("*111*num#");
                    bttn5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content5.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content6.setText("*911#");
                    bttn6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content6.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content7.setText("333");
                    bttn7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content7.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });

                } else if (position == 2) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleCompany3);

                    content1.setText("*102*Code#");
                    bttn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content1.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });

                    content2.setText("400");
                    bttn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content2.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content3.setText("#100*2#");
                    bttn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content3.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content4.setText("#100#4#");
                    bttn4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content4.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content5.setText("*121*num#");
                    bttn5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content5.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content6.setText("#2#100#");
                    bttn6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content6.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content7.setText("110");
                    bttn7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content7.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });

                } else if (position == 3) {

                    recyclerView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    mainTitle.setText(R.string.cardTitleCompany4);

                    content1.setText("*555*Code#");
                    bttn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content1.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });

                    content2.setText("*322#");
                    bttn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content2.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content3.setText("*551*Num*Cost#");
                    bttn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content3.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content4.setText("111");
                    bttn4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content4.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content5.setText("*515*num#");
                    bttn5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content5.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content6.setText("*504#");
                    bttn6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content6.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });
                    content7.setText("111");
                    bttn7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = String.valueOf(Uri.parse(Uri.encode(content7.getText().toString())));
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + s));
                            startActivity(callIntent);
                        }
                    });

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
            Intent i = new Intent(getApplicationContext(), MainUserActivity.class);
            startActivity(i);
            finish();
        }
    }
}
