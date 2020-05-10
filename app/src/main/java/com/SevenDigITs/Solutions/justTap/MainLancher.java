package com.SevenDigITs.Solutions.justTap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.SevenDigITs.Solutions.justTap.listview.MainAdapter;
import com.SevenDigITs.Solutions.justTap.listview.MainLancherClass;
import com.SevenDigITs.Solutions.justTap.trusted.account.Trusted_Login;
import com.SevenDigITs.Solutions.justTap.user.account.User_Login;
import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;


public class MainLancher extends AppCompatActivity {

    //Declration user and trusted session
    public static final String PREFS_NAME_MAIN_USER = "LoginPrefsMainUser";
    public static final String PREFS_NAME_MAIN_TRUSTED = "LoginPrefsMainTrusted";
    SharedPreferences settings,settings2;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainLancherClass> list;
    private LinearLayout l1;
    private Animation uptodown,downtoup;
    private static final String SHOWCASE_ID = "sequence example";


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lancher);
//      Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//      setSupportActionBar(toolbar);


        //session open for trusted
        settings = getSharedPreferences(PREFS_NAME_MAIN_TRUSTED, 0);
        if (settings.getString("logged", "").toString().equals("logged1")) {
            Intent intent = new Intent(MainLancher.this, Trusted_Login.class);
            startActivity(intent);
            finish();
        }

        //session open for user
        settings2 = getSharedPreferences(PREFS_NAME_MAIN_USER, 0);
        if (settings2.getString("logged", "").toString().equals("logged2")) {
            Intent intent = new Intent(MainLancher.this, User_Login.class);
            startActivity(intent);
            finish();
        }


        // Get firbase instance
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Make animation In List View
        l1 = (LinearLayout) findViewById(R.id.liner_anmi);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        l1.setAnimation(downtoup);
        recyclerView.setAnimation(uptodown);


        // method for ToolBar
        initCollapsingToolbar();

        list = new ArrayList<>();
        adapter = new MainAdapter(this, list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // Method for praparing content in list view
        prepareAlbums();

        // The Title
        try {
            Glide.with(this).load(R.drawable.logo).into((ImageView) findViewById(R.id.main_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.main_collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
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
                R.mipmap.ic_user,
                R.mipmap.ic_trusted,
        };


        MainLancherClass a = new MainLancherClass(R.string.main_title, R.string.main_content, covers[0]);
        list.add(a);

        a = new MainLancherClass(R.string.main_title2, R.string.main_content2, covers[1]);
        list.add(a);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new MainAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // albumList.get(position);
                //Toast.makeText(MainActivity.this, "You Clicked ", Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    settings2 = getSharedPreferences(PREFS_NAME_MAIN_USER, 0);
                    SharedPreferences.Editor editor = settings2.edit();
                    editor.putString("logged", "logged2");
                    editor.commit();
                    Intent trustedIntent = new Intent(MainLancher.this, User_Login.class);
                    startActivity(trustedIntent);
                    finish();


                } else if (position == 1) {
                    settings = getSharedPreferences(PREFS_NAME_MAIN_TRUSTED, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged1");
                    editor.commit();
                    Intent userIntent = new Intent(MainLancher.this, Trusted_Login.class);
                    startActivity(userIntent);
                    finish();
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

    // on back for go to home screen
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    //check if user is already sign in
    @Override
    protected void onStart() {
        super.onStart();

    }


}
