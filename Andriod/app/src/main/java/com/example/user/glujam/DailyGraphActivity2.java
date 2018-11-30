package com.example.user.glujam;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * Created by SUJIN on 2018-02-23.
 */

public class DailyGraphActivity2 extends AppCompatActivity {

    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    private double[] Ddata;
    private boolean mIsRotationEnabled = true;

    protected void onCreate(Bundle savedInstatceState) {
        super.onCreate(savedInstatceState);
        setContentView(R.layout.activity_dailygraph2);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        pieChart = (PieChart) findViewById(R.id.chart1);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(pieData);
        pieDataSet.setValueTextColor(Color.WHITE);/*그래프 안의 영역의 텍스트 색*/
        pieDataSet.setValueTextSize(24); /*그래프 안의 영역의 텍스트 사이즈*/
        pieChart.setDescription(""); /*Description 제거*/

        pieChart.getLegend().setTextColor(Color.BLACK); /*그래프 밖의 인덱스 텍스트 색*/
        pieChart.getLegend().setPosition(Legend.LegendPosition.PIECHART_CENTER);

        // 그래프의 회전을 제거 false ,회전 true
        mIsRotationEnabled = false;
        pieChart.setRotationEnabled(mIsRotationEnabled);

        pieChart.animateY(1500);

        Button WeeklyButton = (Button) findViewById(R.id.button3);

        WeeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //버튼이 눌렸을 때
                Intent intent = new Intent(DailyGraphActivity2.this, WeeklyGraphActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });
    }

    public void AddValuesToPIEENTRY(){
     /*수치계산 하루 수면시간 double형 데이터 <- 데이터 계산해서 삽입하는 부분 여기다 하면 됨*/
        Ddata = new double[]{4f,1.5f,1.5f,5f,3f,6f,1f,2f};

        for(int i=0; i<Ddata.length;i++){
            entries.add(new BarEntry((float) Ddata[i], i));
        }



    }

    public void AddValuesToPieEntryLabels(){

        PieEntryLabels.add("Sleep");
        PieEntryLabels.add("Wake Up");
        PieEntryLabels.add("Sleep");
        PieEntryLabels.add("Wake Up");
        PieEntryLabels.add("Sleep");
        PieEntryLabels.add("Wake Up");
        PieEntryLabels.add("Sleep");
        PieEntryLabels.add("Yet");
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DailyGraphActivity2.this, MainActivity2.class);
        startActivity(intent); //액티비티 이동
    }


}