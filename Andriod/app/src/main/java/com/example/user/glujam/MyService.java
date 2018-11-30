package com.example.user.glujam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

    public class MyService extends Service {


        String myJSON;
        MyService.GetDataJSON mTask;


    private static final String TAG_RESULTS = "Data";
    private static final String TAG_Date = "Date_";
    private static final String TAG_TIme = "Time_";
    private static final String TAG_Type = "DataType_";
    private static final String TAG_Values = "Value_";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;



    String url = "http://10.50.248.24:8080/mssql/push_find.php";

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");

        personList = new ArrayList<HashMap<String, String>>();
        mTask = new MyService.GetDataJSON();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행

        mTask.execute(url);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행

        Log.d("test", "서비스의 onDestroy");
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
                String values = c.getString(TAG_Values);
                String time = c.getString(TAG_TIme);
                double values_d = Double.parseDouble(values);

                if(values_d == 2.0) {
                    String[] split1 = date.split("-");
                    Log.e("DDDDDDDDDD", split1[2]);
                    GregorianCalendar today = new GregorianCalendar ( );
                    int sec = today.get ( today.SECOND );
                    int sec1 = Integer.parseInt(split1[2]);
                    int gap = sec - sec1;
                    if(gap > 5) {
                        NotificationManager notificationManager= (NotificationManager)MyService.this.getSystemService(MyService.this.NOTIFICATION_SERVICE);
                        Intent intent1 = new Intent(MyService.this.getApplicationContext(),MainActivity.class); //인텐트 생성.



                        Notification.Builder builder = new Notification.Builder(getApplicationContext());
                        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를 없앤다.

                        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( MyService.this,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                /*
                PendingIntent는 일회용 인텐트 같은 개념입니다.
                FLAG_UPDATE_CURRENT - > 만일 이미 생성된 PendingIntent가 존재 한다면, 해당 Intent의 내용을 변경함.
                FLAG_CANCEL_CURRENT - .이전에 생성한 PendingIntent를 취소하고 새롭게 하나 만든다.
                FLAG_NO_CREATE -> 현재 생성된 PendingIntent를 반환합니다.
                FLAG_ONE_SHOT - >이 플래그를 사용해 생성된 PendingIntent는 단 한번밖에 사용할 수 없습니다
                */

                        builder.setSmallIcon(R.drawable.on).setTicker("HETT").setWhen(System.currentTimeMillis())
                                .setNumber(1).setContentTitle("위험!").setContentText("아기가 뒤집어져있습니다! 확인하세요!")
                                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);

                        //해당 부분은 API 4.1버전부터 작동합니다.
                        //setSmallIcon - > 작은 아이콘 이미지
                        //setTicker - > 알람이 출력될 때 상단에 나오는 문구.
                        //setWhen -> 알림 출력 시간.
                        //setContentTitle-> 알림 제목
                        //setConentText->푸쉬내용

                        notificationManager.notify(1, builder.build()); // Notification send

                    }
                }
            }

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

}