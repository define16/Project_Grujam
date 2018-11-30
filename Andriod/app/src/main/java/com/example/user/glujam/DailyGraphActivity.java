package com.example.user.glujam;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by SUJIN on 2018-02-23.
 */

public class DailyGraphActivity extends AppCompatActivity {
    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    private double[] Ddata;
    private boolean mIsRotationEnabled = true;


    String myJSON;
    GetDataJSON mTask;

    private static final String TAG_RESULTS = "Data";
    private static final String TAG_Date = "Date_";
    private static final String TAG_TIme = "Time_";
    private static final String TAG_Type = "DataType_";
    private static final String TAG_Values = "Value_";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ArrayList<DataDto1> dataList;

    ListView list;


    String url = "http://10.50.248.24:8080/mssql/UserInfo_Section.php";

    ArrayList<DataDto> Time1 = new  ArrayList<DataDto>();
    Data d;

    protected void onCreate(Bundle savedInstatceState) {
        super.onCreate(savedInstatceState);
        setContentView(R.layout.activity_dailygraph);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        pieChart = (PieChart) findViewById(R.id.chart1);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        dataList = new ArrayList<DataDto1>();

        Log.e("DDDDDDDDDDD", "쓰레드 시작");
        mTask = new GetDataJSON();

        mTask.execute(url); //스레드 시작




        Button WeeklyButton = (Button) findViewById(R.id.button3);

        WeeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //버튼이 눌렸을 때
                Intent intent = new Intent(DailyGraphActivity.this, WeeklyGraphActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });
        Log.e("DDDDDDDDDDD", "쓰레드 끝");



    }


    public void AddValuesToPIEENTRY() {
     /*수치계산 하루 수면시간 double형 데이터 <- 데이터 계산해서 삽입하는 부분 여기다 하면 됨*/
        Log.e("그리기","앞@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Time1 = d.getTime1();
        /*수치계산 하루 수면시간 double형 데이터 <- 데이터 계산해서 삽입하는 부분 여기다 하면 됨*/

        for(int i=0; i<Time1.size();i++){
            entries.add(new BarEntry(Time1.get(i).getValue(), i));
            //Log.e("값",String.format("%f",Time1.get(i).getValue()));
        }
    Log.e("그리기","뒤@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    public void AddValuesToPieEntryLabels(){

        Time1 = d.getTime1();
        Log.e("라벨","앞@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        for (int j = 0; j < Time1.size(); j++) {

            switch(Time1.get(j).getType()) {
               case 1:
                   PieEntryLabels.add("Sleep");
                   break;
                case 2:
                    PieEntryLabels.add("Wake Up");
                    break;
                case 3:
                    PieEntryLabels.add("Yet");
                    break;
            }

        }
        Log.e("라벨","뒤@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    //데이터베이스부분
    protected void showList() {
        //  여기도 함수가 호출 되지 않는다.
        try {
            Log.e("Json : ",myJSON);
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            Log.e("DDDDDDDDDDD", "쓰레드 2");
            DataDto1 d1;

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                String date = c.getString(TAG_Date);
                Log.e("Date : ",date);
                String time = c.getString(TAG_TIme);
                String type = c.getString(TAG_Type);
                String values = c.getString(TAG_Values);
                double values_d = Double.parseDouble(values);

                d1 = new DataDto1();
                Log.e("DDDDDDDDDDD", "쓰레드 3_반복문쪽");
                d1.setDate(date);
                d1.setTime(time);
                d1.setDatatype(Integer.parseInt(type));
                d1.setValue(values_d);
                dataList.add(d1);

            }

            Log.e("DDDDDDDDDDD", "쓰레드 3_삽입 완료");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    class GetDataJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String uri = params[0];

            BufferedReader bufferedReader = null;
            try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }

            return sb.toString().trim();

        } catch (Exception e) {
            return null;
        }


    }

        @Override
        protected void onPostExecute(String result) {

            myJSON = result;
            Log.e("DDDDDDDDDDD", "쓰레드 1");
            showList();
            //list 넘기기
           d = new Data(dataList);

            //처리 후 list 받기
            Time1 = d.getTime1();



            AddValuesToPIEENTRY();
            AddValuesToPieEntryLabels();  //여기가 들어가지 않음


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


        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
        }

    }



}