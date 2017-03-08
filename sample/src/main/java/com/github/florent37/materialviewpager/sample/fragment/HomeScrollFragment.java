package com.github.florent37.materialviewpager.sample.fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.Reward;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeScrollFragment extends Fragment {

    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;

    private LineChartView weeklyChart;

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

        //Chart
        weeklyChart = (LineChartView) view.findViewById(R.id.weeklyProgressChart_c);
        setWeeklyChart(weeklyChart);

    }

    private final String[] weeklyChartLabels = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private float[] weeklyChartValues = {1f, 2f, 4f, 3f, 0f};

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
