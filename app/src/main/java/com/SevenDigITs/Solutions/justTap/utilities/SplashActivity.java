package com.SevenDigITs.Solutions.justTap.utilities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.MainLancher;

public class SplashActivity extends AppCompatActivity {
    private TextView tv1;
    private ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv1 = (TextView)findViewById(R.id.tv1);
        iv1 = (ImageView) findViewById(R.id.iv1);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv1.startAnimation(myanim);
        iv1.startAnimation(myanim);

        final Intent i =new Intent (this,MainLancher.class);
        Thread timer = new Thread(){
            public void run()
            {
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();

                }

            }
        };

        timer.start();


    }
}
