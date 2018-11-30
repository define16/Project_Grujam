package com.example.user.glujam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    int cnt = 0;
    @Override
            protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);


//                Intent serviceintent = new Intent(
//                        getApplicationContext(),//현재제어권자
//                        MyService.class); // 이동할 컴포넌트
//                startService(serviceintent); // 서비스 시작


                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        try{
//                            Toast toastView = Toast.makeText(MainActivity.this, "업로드완료! 음악이 재생됩니다!" , Toast.LENGTH_SHORT);
//                            toastView.show();
//
//                        }catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }



                    }
                });

                ImageButton videoButton = (ImageButton) findViewById(R.id.VideoButton1);//해당 버튼을 지정합니다.
                videoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //버튼이 눌렸을 때
                        try{
                            Toast toastView = Toast.makeText(MainActivity.this, "모빌동작버튼을 클릭했습니다." , Toast.LENGTH_SHORT);
                            toastView.show();

                        }catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
            }
        });

        ImageButton musicButton = (ImageButton) findViewById(R.id.MusicButton1);//해당 버튼을 지정합니다.
        musicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //버튼이 눌렸을 때
                    Intent intent = new Intent(MainActivity.this, MusicMainActivity.class);
                    startActivity(intent); //액티비티 이동
            }
        });

        ImageButton graphButton = (ImageButton) findViewById(R.id.GraphButton1);//해당 버튼을 지정합니다.
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //버튼이 눌렸을
                Intent intent = new Intent(MainActivity.this, DailyGraphActivity2.class);
                startActivity(intent); //액티비티 이동
            }
        });



        //추가한 라인
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();


    }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
