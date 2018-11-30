package com.example.user.glujam;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class WeeklyGraphActivity2 extends AppCompatActivity {


    private BarChart barChart;
    private ArrayList<BarEntry> barEntries;
    private BarDataSet barDataSet;
    private ArrayList<String> theDates;
    private double[] Wdata;

    protected void onCreate(Bundle savedInstatceState)
    {
        super.onCreate(savedInstatceState);
        setContentView(R.layout.activity_weeklygraph2);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        barChart = (BarChart) findViewById(R.id.bargraph);


        /*수치계산 월~ 일요일까지 double형 데이터 <- 데이터 계산해서 삽입하는 부분 여기다 하면 됨*/
        Wdata = new double []{15.2f, 12.87f, 11.5f, 17f, 8.4f, 10.1f, 8.5f};

        barEntries = new ArrayList<>();
        for(int i=0; i<Wdata.length;i++)
        {
            barEntries.add(new BarEntry((float) Wdata[i],i));
        }

        barDataSet = new BarDataSet(barEntries,"Dates");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.getXAxis().setTextColor(Color.WHITE);

        barDataSet.setValueTextSize(9);
        barChart.getXAxis().setTextSize(5);
        theDates = new ArrayList<>();
        theDates.add("Mon");
        theDates.add("Thu");
        theDates.add("Wed");
        theDates.add("Thur");
        theDates.add("Fri");
        theDates.add("Sat");
        theDates.add("Sun");



        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(true);
        barChart.setDescription(""); /*Description 제거*/
        barChart.animateY(1500);
        Button DailyButton = (Button) findViewById(R.id.button2);

        DailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //버튼이 눌렸을 때
                Intent intent = new Intent(WeeklyGraphActivity2.this, DailyGraphActivity3.class);
                startActivity(intent); //액티비티 이동
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(WeeklyGraphActivity2.this, MainActivity.class);
        startActivity(intent); //액티비티 이동
    }

}
