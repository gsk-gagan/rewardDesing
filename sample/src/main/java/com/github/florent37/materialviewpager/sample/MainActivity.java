package com.github.florent37.materialviewpager.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.florent37.materialviewpager.sample.fragment.HomeScrollFragment;
import com.github.florent37.materialviewpager.sample.fragment.RecyclerViewFragment;
import com.github.florent37.materialviewpager.sample.fragment.ScrollFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends DrawerActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String MY_PREFERENCE_KEY = "RewardYourselfPreferenceKey";
    public static final String REWARD_TOTAL_KEY = "RewardTotalKey";
    public static final String WEEKLY_TOTAL_KEY = "WeeklyTotalKey";
    public static final String TODAYS_TOTAL_KEY = "TodaysTotalKey";
    public static final String LAST_VALUE_KEY = "LastValueKey";
    public static final String LAST_CATEGORY_KEY = "LastCategoryKey";
    public static final String LAST_MESSAGE_KEY = "LastMessageKey";
    public static final String LAST_TIME_KEY = "LastTimeKey";



    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        ButterKnife.bind(this);

        Log.i("GSK", "Hi There");


        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return HomeScrollFragment.newInstance();
                    case 1:
                        return ScrollFragment.newInstance();
                    case 2:
                        return ScrollFragment.newInstance();
                    default:
                        return ScrollFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "Home";
                    case 1:
                        return "History";
                    case 2:
                        return "Portfolio";
                    case 3:
                        return "Goal";
                }
                return "";
            }
        });

        final Resources res = getResources();

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                            R.color.green,
                            res.getDrawable(R.drawable.nav_menu_back_3));
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.blue,
                            "http://www.hdiphonewallpapers.us/phone-wallpapers/540x960-1/540x960-mobile-wallpapers-hd-2218x5ox3.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.cyan,
                            "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.red,
                            "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }


        //Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(this);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        
        if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout, Stopping Service", Toast.LENGTH_SHORT).show();
            stopService(new Intent(this, MyService.class));
        } else if (id == R.id.nav_feedback) {
            Toast.makeText(this, "Feedback, Start Service", Toast.LENGTH_SHORT).show();
            startService(new Intent(this, MyService.class));
        } else if (id == R.id.nav_social) {
            Toast.makeText(this, "Creating Notification", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            Notification notif = new Notification.Builder(MainActivity.this)
                    .setTicker("Ticker Title")
                    .setContentTitle("Content Title")
                    .setContentText("Content Text - This is the message")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent).getNotification();

            notif.flags = Notification.FLAG_AUTO_CANCEL;

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, notif);

        } else if (id == R.id.nav_setting) {
            Toast.makeText(this, "Starting Notification with Activity", Toast.LENGTH_SHORT).show();


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(this, Reward.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Content Title")
                    .setContentText("Content Text - This is the message")
                    .setContentIntent(pendingIntent).getNotification();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, notification);

        }

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
