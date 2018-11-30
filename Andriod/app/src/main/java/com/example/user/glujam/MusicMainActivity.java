package com.example.user.glujam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 2018-02-12.
 */

public class MusicMainActivity extends AppCompatActivity {
    private ListView listView;
    public static ArrayList<MusicDto> list;
    public static ArrayList<MusicDto2> musicPaths;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ftpMain ftp_ivr ;
    String fileName;
    String targetName;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_musicmain);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            getMusicList(); // 디바이스 안에 있는 mp3 파일 리스트를 조회하여 LIst를 만듭니다.
            listView = (ListView)findViewById(R.id.listview);
            MyAdapter adapter = new MyAdapter(this,list);
            listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override       // position : 리스트뷰의 인덱스
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        fileName = list.get(position).getTitle() + ".mp3";
                        targetName = musicPaths.get(position).getPath();
                        ftp_ivr = new ftpMain("dxsaq0.iptime.org", "admin", "zx0000" ,"/audio/"+fileName);
                        ftpTask ftp = new ftpTask();
                        ftp.execute();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                try{
                                    Toast toastView = Toast.makeText(MusicMainActivity.this, "업로드완료! 음악이 재생됩니다!" , Toast.LENGTH_SHORT);
                                    toastView.show();

                                }catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(MusicMainActivity.this,MusicActivity.class);
                                intent.putExtra("position",position);
                                intent.putExtra("playlist",list);
                                startActivity(intent);
                            }
                        }, 2000);  // 2000은 2초를 의미합니다.

            }
        });
    } //end onCreate

    Cursor cursor;
    public  void getMusicList(){
            int index = 0;
            list = new ArrayList<>();
            musicPaths = new ArrayList<>();
            //가져오고 싶은 컬럼 명을 나열합니다. 음악의 아이디, 앰블럼 아이디, 제목, 아스티스트 정보를 가져옵니다.
            String[] projection = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DATA
        };

         cursor =  getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);


        while(cursor.moveToNext()) {
            MusicDto musicDto = new MusicDto();
            MusicDto2 musicDto2 = new MusicDto2();

            musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDto.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            musicDto2.setPath(path);
            musicDto2.setIndex(index);

            Log.d("path", path);
            list.add(musicDto);
            musicPaths.add(musicDto2);
            index++;
        }
        cursor.close();
    }

    public void requestRead() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            readFile();
        }
    }

    public void readFile() {
        // do something
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile();
            } else {
                // Permission Denied
                Toast.makeText(MusicMainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    class ftpTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... strings) {
            File file = new File(targetName);
            boolean result = false;
            try {
                result = ftp_ivr.upload(file, "/audio/" + fileName);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Boolean result){
            if(result){
                Log.d("upload", "success");
            }else{
                Log.d("upload", "fail");
            }
        }
    }


}


