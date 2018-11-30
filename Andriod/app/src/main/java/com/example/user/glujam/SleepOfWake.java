package com.example.user.glujam;

import java.util.ArrayList;

/**
 * Created by User on 2018-02-26
 *
 * 카메라(1) - 0(수면), 1(기상), 2(뒤집기)
 * 진동(2) - 실수값
 * 압력(3) - 실수값
 * 소리(4) - 실수값
 * 영상(5)
 * 음악(6)
 *
 */

public class SleepOfWake {

    private String date_s;

    private String time_s;
    private int dataType_s;
    private double value_s;

    private int hour;
    private int min;
    private int sec;

    private ArrayList<SleepOfWake> dataList;

    public SleepOfWake(){    }

    public SleepOfWake(String date_s, String time_s, int dataType_s, double value_s){
        this.date_s = date_s;
        this.time_s = time_s;
        this.dataType_s = dataType_s;
        this.value_s = value_s;
    }

    public String getDate_s() {
        return date_s;
    }

    public void setDate_s(String date_s) {
        this.date_s = date_s;
    }

    public String getTime_s() {
        return time_s;
    }

    public void setTime_s(String time_s) {
        this.time_s = time_s;
    }

    public int getDataType_s() {
        return dataType_s;
    }

    public void setDataType_s(int dataType_s) {
        this.dataType_s = dataType_s;
    }

    public double getValue_s() {
        return value_s;
    }

    public void setValue_s(double value_s) {
        this.value_s = value_s;
    }

    void setDataList(ArrayList<SleepOfWake> dataList){
        this.dataList = dataList;
    }

}
