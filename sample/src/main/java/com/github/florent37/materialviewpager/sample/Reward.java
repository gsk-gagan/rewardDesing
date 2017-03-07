package com.github.florent37.materialviewpager.sample;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Reward extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView rewardTextView;
    private int minReward, maxReward, reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        minReward = 2;
        maxReward = 100;
        reward = 16;

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
                }
                if(current == denyButton) {
                    Toast.makeText(Reward.this, "Reward Denied", Toast.LENGTH_SHORT).show();
                }
                Reward.this.finish();
            }
        };
        approveButton.setOnClickListener(approveDenyListner);
        denyButton.setOnClickListener(approveDenyListner);

        //Transparency
        final ArrayList<ImageView> imageList = new ArrayList<>();
        imageList.add((ImageView) findViewById(R.id.movie_iv));
        imageList.add((ImageView) findViewById(R.id.gym_iv));
        imageList.add((ImageView) findViewById(R.id.office_iv));
        imageList.add((ImageView) findViewById(R.id.thumbsup_iv));
        imageList.add((ImageView) findViewById(R.id.wallet_iv));

        View.OnClickListener imageSelectListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewAlphaHelper(imageList, (ImageView) view);
            }
        };

        for(ImageView image : imageList) {
            image.setOnClickListener(imageSelectListner);
        }
    }

    private void imageViewAlphaHelper(ArrayList<ImageView> imageList, ImageView imageSelected) {
        for(ImageView image : imageList) {
            if(image == imageSelected) {
                image.setAlpha(1.0f);
            } else {
                image.setAlpha(0.5f);
            }
        }
    }
}
