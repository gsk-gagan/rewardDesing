package com.github.florent37.materialviewpager.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reward extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView rewardTextView;
    private TextView notificationMessage;
    private int minReward, maxReward, reward;
    private String rewardMessage;

    public static final String REWARD_VALUE_KEY = "REWARD_VALUE_KEY";
    public static final String NOTIFICATION_MSG_KEY = "NOTIFICATION_MSG_KEY";
    public static final String CATEGORY_KEY = "CATEGORY_KEY";

    private ImageView movieIV, gymIV, officeIV, thumbsupIV, walletIV;
    private ArrayList<ImageView> imageList;

    private EditText rewardCategoryET;
    private HistoryHelper.RewardCategory rewardCategoryEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        //Transparency
        imageList = new ArrayList<>();
        movieIV = (ImageView) findViewById(R.id.movie_iv);
        gymIV = (ImageView) findViewById(R.id.gym_iv);
        officeIV = (ImageView) findViewById(R.id.office_iv);
        thumbsupIV = (ImageView) findViewById(R.id.thumbsup_iv);
        walletIV = (ImageView) findViewById(R.id.wallet_iv);
        imageList.add(movieIV);
        imageList.add(gymIV);
        imageList.add(officeIV);
        imageList.add(thumbsupIV);
        imageList.add(walletIV);


        View.OnClickListener imageSelectListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewAlphaHelper(imageList, (ImageView) view);
            }
        };

        for(ImageView image : imageList) {
            image.setOnClickListener(imageSelectListner);
        }

        rewardCategoryET = (EditText) findViewById(R.id.rewardCategoryMessage_et);


        minReward = 2;
        maxReward = 100;
        reward = 16;
        rewardMessage = "Good to See you Here!!";
        rewardCategoryEnum = HistoryHelper.RewardCategory.OTHER;

        Bundle intentExtras = getIntent().getExtras();
        if(intentExtras != null) {
            float intentReward = (float) intentExtras.get(REWARD_VALUE_KEY);
            String intentRewardMessage = (String) intentExtras.get(NOTIFICATION_MSG_KEY);
//            String intentCategory = (String) intentExtras.get(CATEGORY_KEY);  //TODO-To be used Later

            if(intentReward != 0) {
                reward = (int) intentReward;
            }
            if(intentRewardMessage != null) {
                rewardMessage = intentRewardMessage;
            }
            imageViewAlphaHelper(imageList, movieIV);
            rewardCategoryET.setText("Leisurely Spending");
        }

        notificationMessage = (TextView) findViewById(R.id.notificationText_tv);
        notificationMessage.setText(rewardMessage);

        rewardTextView = (TextView) findViewById(R.id.rewardText_tv);
        rewardTextView.setText("$"+reward);

        seekBar = (SeekBar) findViewById(R.id.rewardSeekBar_sb);
        seekBar.setProgress(reward);
        seekBar.setMax(maxReward);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < minReward)
                    reward = minReward;
                else
                    reward = progress;
                rewardTextView.setText("$"+reward);
            }
        });

        //Approve/Deny Buttons
        final Button approveButton = (Button) findViewById(R.id.rewardApprove_bt);
        final Button denyButton = (Button) findViewById(R.id.rewardDeny_bt);

        View.OnClickListener approveDenyListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button current = (Button) view;
                if(current == approveButton) {
                    Toast.makeText(Reward.this, "Reward Approved of $" + reward, Toast.LENGTH_SHORT).show();

                    SharedPreferences sp = getSharedPreferences(MainActivity.MY_PREFERENCE_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    int totalRewardValue = sp.getInt(MainActivity.REWARD_TOTAL_KEY, 663);
                    int weekTotal = sp.getInt(MainActivity.WEEKLY_TOTAL_KEY, 8);
                    int todaysTotal = sp.getInt(MainActivity.TODAYS_TOTAL_KEY, 1);

                    editor.putInt(MainActivity.REWARD_TOTAL_KEY, totalRewardValue + reward);
                    editor.putInt(MainActivity.WEEKLY_TOTAL_KEY, weekTotal + reward);
                    editor.putInt(MainActivity.TODAYS_TOTAL_KEY, todaysTotal + reward);
                    editor.putInt(MainActivity.LAST_VALUE_KEY, reward);
                    editor.putString(MainActivity.LAST_CATEGORY_KEY, rewardCategoryEnum.toString());
                    editor.putString(MainActivity.LAST_MESSAGE_KEY, rewardMessage);

                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date today = Calendar.getInstance().getTime();
                    String reportDate = df.format(today);
                    editor.putString(MainActivity.LAST_TIME_KEY, reportDate);
                    editor.commit();

                }
                if(current == denyButton) {
                    Toast.makeText(Reward.this, "Reward Denied", Toast.LENGTH_SHORT).show();
                }
                Reward.this.finish();
            }
        };
        approveButton.setOnClickListener(approveDenyListner);
        denyButton.setOnClickListener(approveDenyListner);


    }

    @Override
    public void onResume(){

        Bundle intentExtras = getIntent().getExtras();
        if(intentExtras != null) {
            float intentReward = (float) intentExtras.get(REWARD_VALUE_KEY);
            String intentRewardMessage = (String) intentExtras.get(NOTIFICATION_MSG_KEY);
//            String intentCategory = (String) intentExtras.get(CATEGORY_KEY);  //TODO-To be used Later

            if(intentReward != 0) {
                reward = (int) intentReward;
            }
            if(intentRewardMessage != null) {
                rewardMessage = intentRewardMessage;
            }
            Toast.makeText(this, "GSK: " + intentReward + " " + intentRewardMessage, Toast.LENGTH_SHORT).show();

            imageViewAlphaHelper(imageList, movieIV);
            rewardCategoryET.setText("Leisurely Spending");
        }

        notificationMessage.setText(rewardMessage);

        rewardTextView.setText("$"+reward);

        seekBar.setProgress(reward);


        super.onResume();
    }

    private void imageViewAlphaHelper(ArrayList<ImageView> imageList, ImageView imageSelected) {
        for(ImageView image : imageList) {
//            movieIV, gymIV, officeIV, thumbsupIV, walletIV
            if(imageSelected == movieIV) {
                rewardCategoryEnum = HistoryHelper.RewardCategory.LEISURELY;
            } else if (imageSelected == gymIV) {
                rewardCategoryEnum = HistoryHelper.RewardCategory.WORKOUT;
            } else if (imageSelected == officeIV) {
                rewardCategoryEnum = HistoryHelper.RewardCategory.HARDWORK;
            } else if (imageSelected == thumbsupIV) {
                rewardCategoryEnum = HistoryHelper.RewardCategory.APPRECIATION;
            } else {
                rewardCategoryEnum = HistoryHelper.RewardCategory.OTHER;
            }

            if(image == imageSelected) {
                image.setAlpha(1.0f);
            } else {
                image.setAlpha(0.5f);
            }
        }
    }
}
