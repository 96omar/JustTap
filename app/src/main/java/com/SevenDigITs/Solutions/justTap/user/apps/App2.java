package com.SevenDigITs.Solutions.justTap.user.apps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SevenDigITs.Solutions.justTap.R;

public class App2 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app2, container, false);

        CardView facebook = (CardView) rootView.findViewById(R.id.facebook_id);
        CardView viber = (CardView) rootView.findViewById(R.id.viber_id);
        CardView Whatsapp = (CardView) rootView.findViewById(R.id.what_app_id);
        CardView skype = (CardView) rootView.findViewById(R.id.skype_id);
        CardView instagram = (CardView) rootView.findViewById(R.id.instagram_id);
        CardView Youtube = (CardView) rootView.findViewById(R.id.youtube_id);
        CardView soundcolud = (CardView) rootView.findViewById(R.id.sound_Cloud_id);
        CardView Messenger = (CardView) rootView.findViewById(R.id.messenger_id);
        CardView Magnifier = (CardView) rootView.findViewById(R.id.magnifier_id);
        CardView Quran = (CardView) rootView.findViewById(R.id.quran_id);



        facebook.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.facebook.katana");
                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.facebook.katana");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.katana&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });


        viber.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.viber.voip");
                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.viber.voip");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.viber.voip&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        Whatsapp.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.whatsapp");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent =  getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.whatsapp");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm =  getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        skype.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.skype.raider");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.skype.raider");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.skype.raider&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.instagram.android");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.instagram.android");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.instagram.android&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        Youtube.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.google.android.youtube");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.google.android.youtube");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        soundcolud.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.soundcloud.android");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.soundcloud.android");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.soundcloud.android&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        Messenger.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.facebook.orca");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.facebook.orca");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.orca&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        Magnifier.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.app2u.magnifier");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.app2u.magnifier");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.app2u.magnifier&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        Quran.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                // Use package name which we want to check
                boolean isAppInstalled = appInstalledOrNot("com.quran.labs.androidquran");

                if (isAppInstalled) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.quran.labs.androidquran");
                    startActivity(LaunchIntent);
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    //Copy App URL from Google Play Store.
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.quran.labs.androidquran&hl=en"));
                    startActivity(intent);
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });

        return rootView;
    }
}
