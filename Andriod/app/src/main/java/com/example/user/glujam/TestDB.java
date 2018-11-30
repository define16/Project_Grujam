package com.example.user.glujam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class TestDB extends AppCompatActivity {

    String myJSON;
    GetDataJSON mTask;


    private static final String TAG_RESULTS = "Data";
    private static final String TAG_Date = "Date_";
    private static final String TAG_TIme = "Time_";
    private static final String TAG_Type = "DataType_";
    private static final String TAG_Values = "Value_";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;


    String url = "http://192.168.25.30:8080/mssql/UserInfo_Section.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        mTask = new GetDataJSON();
        mTask.execute(url);
        //handler.sendEmptyMessage(0);
    }

    protected void showList() {
        try {
            Log.e("Json : ",myJSON);
            JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);

                    String date = c.getString(TAG_Date);
                    Log.e("Date : ",date);
                    String time = c.getString(TAG_TIme);
                    String type = c.getString(TAG_Type);
                    String values = c.getString(TAG_Values);
                    double values_d = Double.parseDouble(values);

                    if (values_d < 80.f){
                        values = "0";
                    }else{
                        values = "1";
                    }

                    HashMap<String, String> dataes = new HashMap<String, String>();

                dataes.put(TAG_Date, date);
                dataes.put(TAG_TIme, time);
                dataes.put(TAG_Type, type);
                dataes.put(TAG_Values, values);

                personList.add(dataes);
            }

            ListAdapter adapter = new SimpleAdapter(
                    TestDB.this, personList, R.layout.item_list,
                    new String[]{TAG_TIme, TAG_Type,TAG_Values},
                    new int[]{R.id.id, R.id.name, R.id.address}
            );

            list.setAdapter(adapter);

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
//                handler.sendEmptyMessageDelayed(0, 1000);
                myJSON = result;
                showList();

            }

        @Override
        protected void onCancelled(){
            super.onCancelled();
        }

    }


//    public Handler handler = new Handler(){
//        public void handleMessage( Message msg){
//            super.handleMessage(msg);
//
//            mTask = new GetDataJSON();
//            mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
//        }
//    };
//    @Override
//    public void onBackPressed(){
//        handler.removeMessages(0);
//        Intent intent = new Intent(TestDB.this, MainActivity.class);
//        startActivity(intent);
//    }

}