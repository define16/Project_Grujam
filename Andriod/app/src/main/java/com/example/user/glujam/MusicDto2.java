package com.example.user.glujam;

/**
 * Created by User on 2018-02-24.
 */

public class MusicDto2 {

    private String path;
    private int index;

    public MusicDto2(){
    }

    public MusicDto2(int index, String path) {
        this.index = index;
        this.path = path;
    }

    public String getPath() {return path;}

    public void setPath(String path) {this.path = path;}

    public int getIndex() {return index;}

    public void setIndex(int index) {this.index = index;}

    @Override
    public String toString() {
        return "MusicDto{" +
                "id='" + index + ",  " +
                ", albumId='" + path + '\'' +
                '}';
    }

}
