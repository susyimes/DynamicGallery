package com.susyimes.dynamicgallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.susyimes.dynamicgallerylib.GalleryParent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        list.add("file:///android_asset/a10.jpg");
        list.add("file:///android_asset/asd.jpg");
        list.add("file:///android_asset/dsa.jpg");
        list.add("file:///android_asset/img.jpg");
        list.add("file:///android_asset/qqq.jpg");
        Intent intent=new Intent(MainActivity.this,GalleryParent.class);
        intent.putStringArrayListExtra("imgdata",list);
        startActivity(intent);

    }
}
