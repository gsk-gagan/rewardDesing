package com.github.florent37.materialviewpager.sample.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import com.github.florent37.materialviewpager.sample.HistoryHelper;
import com.github.florent37.materialviewpager.sample.MainActivity;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.Reward;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeScrollFragment extends Fragment {

    private SharedPreferences sharedpreferences;

    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;

    private LineChartView weeklyChart;
    private TextView totalRewardTV, weeklyRemainingTV;


    public static HomeScrollFragment newInstance() {
        return new HomeScrollFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);

        //Floating Action Bar
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Reward.class);
                startActivity(intent);
            }
        });

        sharedpreferences = this.getActivity().getSharedPreferences(MainActivity.MY_PREFERENCE_KEY, Context.MODE_PRIVATE);


        totalRewardTV = (TextView) view.findViewById(R.id.totalReward_tv);
        weeklyRemainingTV = (TextView) view.findViewById(R.id.rewardWeeklyRemaining_tv);

        weeklyChart = (LineChartView) view.findViewById(R.id.weeklyProgressChart_c);
        setWeeklyChart(weeklyChart);

        updateViews();

    }

    @Override
    public void onResume(){
        super.onResume();

        updateViews();
    }

    private final String[] weeklyChartLabels = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private float[] weeklyChartValues = {1f, 2f, 4f, 1f};

    private void updateViews() {
        int totalRewardValue = sharedpreferences.getInt(MainActivity.REWARD_TOTAL_KEY, 663);
        int weekTotal = sharedpreferences.getInt(MainActivity.WEEKLY_TOTAL_KEY, 8);
        int todaysTotal = sharedpreferences.getInt(MainActivity.TODAYS_TOTAL_KEY,
                (int) weeklyChartValues[weeklyChartValues.length-1]);

        int lastValue = sharedpreferences.getInt(MainActivity.LAST_VALUE_KEY,
                (int) weeklyChartValues[weeklyChartValues.length-1]);
        HistoryHelper.RewardCategory lastCategory = HistoryHelper.RewardCategory.fromString(
                sharedpreferences.getString(MainActivity.LAST_CATEGORY_KEY, HistoryHelper.RewardCategory.OTHER.toString()));
        String lastMessage = sharedpreferences.getString(MainActivity.LAST_MESSAGE_KEY, "Feels good to Reward");
        String lastTime = sharedpreferences.getString(MainActivity.LAST_TIME_KEY, "09-03-2017 09:36");

        weeklyChartValues[weeklyChartValues.length-1] = todaysTotal;

        totalRewardTV.setText("$" + totalRewardValue);
        weeklyRemainingTV.setText("($" + (21-weekTotal) + " more for Weekly Goal)");

        //Chart
        float allValues[] = new float[7];

        float weeklyAvg = 0;
        float maxWeekly = 0;
        for(float value : weeklyChartValues) {
            weeklyAvg += value;
            if(value > maxWeekly)
                maxWeekly = value;
        }
        weeklyAvg /= weeklyChartValues.length;

        for(int i=0; i<7; i++) {
            if(i < weeklyChartValues.length) {
                allValues[i] = weeklyChartValues[i];
            } else {
                allValues[i] = weeklyAvg;
            }
        }

        weeklyChart.updateValues(0,allValues);
        weeklyChart.updateValues(1,allValues);
        weeklyChart.setAxisBorderValues(0,(int) maxWeekly, 1);
        weeklyChart.notifyDataUpdate();

    }

    private void generateNumbers () {

    }

    private void setWeeklyChart (LineChartView chart) {
        float allValues[] = new float[7];

        float weeklyAvg = 0;
        float maxWeekly = 0;
        for(float value : weeklyChartValues) {
            weeklyAvg += value;
            if(value > maxWeekly)
                maxWeekly = value;
        }
        weeklyAvg /= weeklyChartValues.length;

        for(int i=0; i<7; i++) {
            if(i < weeklyChartValues.length) {
                allValues[i] = weeklyChartValues[i];
            } else {
                allValues[i] = weeklyAvg;
            }
        }


        LineSet dataSet = new LineSet(weeklyChartLabels, allValues);
        if(weeklyChartValues.length < 7) {
            dataSet.setColor(Color.parseColor("#758cbb"))
                    .setFill(Color.parseColor("#2d374c"))
                    .setDotsColor(Color.parseColor("#758cbb"))
                    .setThickness(4)
                    .setDashed(new float[] {10f, 10f})
                    .beginAt(weeklyChartValues.length - 1);
            chart.addData(dataSet);
        }

        dataSet = new LineSet(weeklyChartLabels, allValues);
        dataSet.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4)
                .endAt(weeklyChartValues.length);
        chart.addData(dataSet);

        // Chart
        chart
                .setBorderSpacing(Tools.fromDpToPx(15))
                .setAxisBorderValues(0, (int) (maxWeekly + 1))
//                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#6a84c3"))
                .setXAxis(false)
                .setYAxis(false)
                .setBackgroundColor(Color.parseColor("#333f55"));


        Animation anim = new Animation().setEasing(new BounceInterpolator());

        chart.show(anim);
    }
}
