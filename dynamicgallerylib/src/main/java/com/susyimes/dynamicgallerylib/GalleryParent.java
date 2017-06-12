package com.susyimes.dynamicgallerylib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.susyimes.dynamicgallerylib.bus.DAction;
import com.susyimes.dynamicgallerylib.bus.DBus;
import com.susyimes.dynamicgallerylib.utils.Dp2px;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class GalleryParent extends FragmentActivity {
    @BindView(R2.id.viewpager)
    ViewPager viewPager;
    @BindView(R2.id.topbar)
    RelativeLayout topbar;
    @BindView(R2.id.bottombar)
    RelativeLayout bottombar;

    private List<String> imgdata;
    private Subscription subscription;
    private boolean showflag=true;

    private int topbarheight;
    private int bottombarheight;
    private int scrollup=0;

    private int totaloffset=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_parent);
        ButterKnife.bind(this);
        imgdata=new ArrayList<>();
        imgdata=getIntent().getStringArrayListExtra("imgdata");

        topbarheight=Dp2px.UseDp2Px(this,45);
        bottombarheight=Dp2px.UseDp2Px(this,150);
        initViewPager();
        initBus();

    }

    private void initBus() {
        subscription = DBus.getDefault().toObserverable(DAction.class)
                .subscribe(new Action1<DAction>() {
                    @Override
                    public void call(DAction dAction) {
                        if (dAction.getAction().equals("offset")){


                                totaloffset+=dAction.getPosition();
                           /* ViewCompat.offsetTopAndBottom(topbar,-dAction.getPosition());
                            ViewCompat.offsetTopAndBottom(bottombar,dAction.getPosition());*/
                           /* ViewCompat.offsetTopAndBottom(topbar,-dAction.getPosition());
                            ViewCompat.offsetTopAndBottom(bottombar,dAction.getPosition());*/
                            ViewCompat.offsetTopAndBottom(topbar,-dAction.getPosition());
                            ViewCompat.offsetTopAndBottom(bottombar,dAction.getPosition());
                          /*  if(dAction.getPosition()>0){
                            ViewCompat.offsetTopAndBottom(topbar,-dAction.getPosition());
                            ViewCompat.offsetTopAndBottom(bottombar,dAction.getPosition());}else {
                              *//*  ViewCompat.offsetTopAndBottom(topbar,dAction.getPosition());
                                ViewCompat.offsetTopAndBottom(bottombar,-dAction.getPosition());*//*
                              ViewCompat.animate(topbar).translationY(0).start();
                                ViewCompat.animate(bottombar).translationY(0).start();
                            }*/
                            Log.i("actiontotal",totaloffset+"");



                        }else if (dAction.getAction().equals("show")){


                                    if (showflag){

                                        ViewCompat.animate(topbar).translationY(-topbarheight).setDuration(200);
                                        ViewCompat.animate(bottombar).translationY(bottombarheight).setDuration(200);
                                        showflag=false;}else {
                                        ViewCompat.animate(topbar).translationY(0).setDuration(200);
                                        ViewCompat.animate(bottombar).translationY(0).setDuration(200);
                                        showflag=true;
                                    }


                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void initViewPager() {
        viewPager.setAdapter(new GalleryAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(imgdata.size());


    }

    private class GalleryAdapter extends FragmentStatePagerAdapter {


        public GalleryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            GalleryChild galleryChild=GalleryChild.newInstance(imgdata.get(position),position);

            return galleryChild;
        }

        @Override
        public int getCount() {
            return imgdata.size();
        }


    }
}
