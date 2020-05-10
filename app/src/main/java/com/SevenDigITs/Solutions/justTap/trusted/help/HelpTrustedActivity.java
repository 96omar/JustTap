package com.SevenDigITs.Solutions.justTap.trusted.help;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.SevenDigITs.Solutions.justTap.R;
import com.SevenDigITs.Solutions.justTap.trusted.User_Id;
import com.SevenDigITs.Solutions.justTap.user.MainUserActivity;
import com.SevenDigITs.Solutions.justTap.user.help.FullScreenMediaControllerUser;

public class HelpTrustedActivity extends AppCompatActivity {

    // initializing action variables
    private VideoView videoView;
    private MediaController mediaController;
    boolean connected;

    Intent intentLessone2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_trusted);

        /** defining XML object and tag them */
        videoView = findViewById(R.id.videoView2);
        /** this if for check about button fullscreen */

        /** check if mobile network is available or not**/
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            System.out.println("Connected to Network");
            String fullScreen = getIntent().getStringExtra("fullScreenInd");
            if ("y".equals(fullScreen)) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
            }

            /** Getting video uri */
            String vidAddress = "https://firebasestorage.googleapis.com/v0/b/just-tap-3d747.appspot.com/o/videos%2FJustTap%20Trusted.mp4?alt=media&token=63e8469e-026e-4d06-ae74-77154cf58343";
            Uri vidUri = Uri.parse(vidAddress);
            // Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.one);

            /** add uri to video view */
            videoView.setVideoURI(vidUri);

            /** get to full screen mode */
            mediaController = new FullScreenMediaControllerUser(this);
            mediaController.setAnchorView(videoView);

            /** on complete video go to next vedio */
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    intentLessone2 = new Intent(getApplicationContext(), User_Id.class);
                    startActivity(intentLessone2);
                    finish();
                }
            });
            /** on error occurs in loading video */
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Toast.makeText(getApplicationContext(), " An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error is occured while playing an video
                    return false;
                }
            });

            /** show controller of video */
            videoView.setMediaController(mediaController);
            videoView.start();
        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.checkConnection, Toast.LENGTH_LONG).show();
            connected = false;
        }


    }
}
