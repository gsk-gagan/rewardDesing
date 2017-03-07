package com.github.florent37.materialviewpager.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

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
    }
}
