package com.susyimes.dynamicgallerylib;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.susyimes.dynamicgallerylib.bus.DAction;
import com.susyimes.dynamicgallerylib.bus.DBus;
import com.susyimes.dynamicgallerylib.frame.SusNestedScrollView;
import com.susyimes.dynamicgallerylib.utils.Dp2px;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import uk.co.senab.photoview.*;
import uk.co.senab.photoview.BuildConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryChild extends Fragment {
    @BindView(R2.id.img)
    PhotoView imageview;
    @BindView(R2.id.nested_scrollview)
    SusNestedScrollView nested_scrollview;
    @BindView(R2.id.parent)
    RelativeLayout parent;
    @BindView(R2.id.view)
    View view;

    private int position;
    private VelocityTracker mVelocityTracker;
    private int mMaxVelocity;
    private int mPointerId;
    private int screenHeight;
    private boolean canBack=false;
    Subscription subscription;
    private boolean backtoblack=false;
    private int totaloffset=0;
    private int lastoffset=0;

    public GalleryChild() {
        // Required empty public constructor
    }
    public static GalleryChild newInstance(String img, int index_in_viewpager) {
        GalleryChild fragment = new GalleryChild();
        Bundle bundle = new Bundle();
        bundle.putString("img",img );
        bundle.putInt("index_in_viewpager", index_in_viewpager);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_gallery_child, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        String img= getArguments().getString("img");
        Log.i("sda","created");
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        screenHeight = display.getHeight();
        imageview.setMinimumHeight(screenHeight*3);

        Glide.with(this).load(img).into(imageview);


        initAction();



    }
    private void acquireVelocityTracker(final MotionEvent event) {
        if(null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void stopAndFinnish(int way){
        if (way==0){
            nested_scrollview.smoothScrollTo(0,0);

        }else if (way==1){
            nested_scrollview.smoothScrollTo(0,screenHeight*3);

        }
        if (getActivity()!=null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }, 36);
        }
    }

    private void initAction() {
        mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        final Dp2px dp2px=new Dp2px();
        nested_scrollview.post(new Runnable() {

            @Override
            public void run() {
                nested_scrollview.scrollTo(0,(nested_scrollview.getChildAt(0).getBottom()/2)-(screenHeight/2)+dp2px.UseDp2Px(getContext(),12));
            }
        });


        nested_scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                acquireVelocityTracker(motionEvent);
                final VelocityTracker verTracker = mVelocityTracker;
                MotionEvent vtev = MotionEvent.obtain(motionEvent);
                final int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
                Log.i("touch","touch");

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        Log.i("touch","down");


                        mPointerId = motionEvent.getPointerId(0);


                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        Log.i("touch","move");
                        canBack=false;
                        backtoblack=false;

                        break;
                    }
                    case MotionEvent.ACTION_UP:{

                        canBack=true;

                        verTracker.computeCurrentVelocity(1000, mMaxVelocity);
                        final float velocityY = verTracker.getYVelocity(mPointerId);

                        boolean scrollback=true;

                        if (velocityY>2500f){
                            stopAndFinnish(1);
                            scrollback=false;
                        }else if (velocityY<-2500f){
                            stopAndFinnish(0);
                            scrollback=false;
                        }
                        Log.i("acttouchup",motionEvent.getY()+"");
                        Log.i("acttouchh",screenHeight+"");
                        boolean shouldfin=false;
                        if ((0.8f*screenHeight)<motionEvent.getY()){
                            Log.i("acttouchup","2");
                            shouldfin=true;
                        }else if (motionEvent.getY()<0.1f*screenHeight){
                            Log.i("acttouchup","3");

                            shouldfin=true;
                        }
                        if (shouldfin){
                            Log.i("acttouchup","4");
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        }
                        if (scrollback&&!shouldfin) {
                            Log.i("acttouchup","5");
                            nested_scrollview.post(new Runnable() {

                                @Override
                                public void run() {

                                    nested_scrollview.smoothScrollTo(0, (nested_scrollview.getChildAt(0).getBottom() / 2) - (screenHeight / 2)+dp2px.UseDp2Px(getContext(),12));
                                    parent.getBackground().setAlpha(255);
                                    Log.i("acttouchup",(nested_scrollview.getChildAt(0).getBottom() / 2) - (screenHeight / 2)+dp2px.UseDp2Px(getContext(),12)+"");
                                    Log.i("acttouchup","6");
                                    nested_scrollview.scrollTo(0, (nested_scrollview.getChildAt(0).getBottom() / 2) - (screenHeight / 2)+dp2px.UseDp2Px(getContext(),12));
                                   /* textView.getBackground().setAlpha(255);
                                    textSave.getBackground().setAlpha(255);
                                    textView.setTextColor(Color.argb(255,255,255,255));
                                    textSave.setTextColor(Color.argb(255,255,255,255));*/
                                }
                            });
                        }



                        backtoblack=true;

                        break;
                    }





                }

                return false;
            }
        });



        imageview.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                DBus.getDefault().post(new DAction("show"));
            }
        });


        nested_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, final int scrollY, int oldScrollX, int oldScrollY) {

                totaloffset+=scrollY-oldScrollY;
                DBus.getDefault().post(new DAction("offset",(oldScrollY-scrollY)));

                /*if (v.computeVerticalScrollOffset()>1812) {
                    ViewCompat.offsetTopAndBottom(view, (oldScrollY-scrollY));

                }else if (v.computeVerticalScrollOffset()==1812){

                    ViewCompat.animate(view).translationY(1812-totaloffset).start();
                }else {
                    ViewCompat.offsetTopAndBottom(view, -(oldScrollY-scrollY));
                }*/
                Log.i("actionzzz", v.computeVerticalScrollOffset()+"ss"+totaloffset);
                final float halfHeight =screenHeight;
                Log.i("scrolly",scrollY+"");
                Log.i("scrollyhalf",halfHeight+"");
                float ratio = 0;
                if (scrollY < halfHeight) {
                    ratio = 1 - ((halfHeight - scrollY) / halfHeight);
                } else if (scrollY > halfHeight && scrollY < 2 * halfHeight) {
                    ratio = 1 - ((scrollY - halfHeight) / halfHeight);
                } else {
                    ratio = 1;
                }
                final int newAlpha = (int) (ratio * 1 * 255);


                if (!backtoblack){
                    //Observable.


                    parent.getBackground().setAlpha(newAlpha);
                 /*   textView.getBackground().setAlpha(newAlpha);
                    textSave.getBackground().setAlpha(newAlpha);
                    textView.setTextColor(Color.argb(newAlpha,255,255,255));
                    textSave.setTextColor(Color.argb(newAlpha,255,255,255));*/

                }else {

                }


            }
        });




    }


    private void releaseVelocityTracker() {
        if(null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public void onDestroy() {
        releaseVelocityTracker();
        super.onDestroy();
    }
}
