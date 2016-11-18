package com.dalong.taobaoproductdetail.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalong.refreshlayout.OnHeaderListener;
import com.dalong.taobaoproductdetail.R;

/**
 * Created by dalong on 2016/11/18.
 */

public class TaobaoHeader  extends RelativeLayout implements OnHeaderListener{

    // 初始状态
    public static final int INIT = 0;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;
    // 操作完毕
    public static final int DONE = 5;
    // 当前状态
    private int state = INIT;
    private  TextView headerTv;
    private  ImageView headerIcon;
    private  View headerView;
    private RotateAnimation rotateAnimation;
    private boolean isRefreshAfter;

    public TaobaoHeader(Context context) {
        super(context);
        headerView= LayoutInflater.from(context).inflate(R.layout.taobao_refresh_header, this, true);
        // 初始化下拉布局
        headerIcon = (ImageView) headerView.findViewById(R.id.refresh_icon);
        headerTv = (TextView) headerView.findViewById(R.id.refresh_tv);
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.reverse_anim);
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        measureView(headerView);
        changeState(INIT);
    }
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        changeState(INIT);
    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {
        changeState(RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        changeState(REFRESHING);
    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        changeState(DONE);
    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        changeState(DONE);
    }


    private void changeState(int to) {
        state = to;
        switch (state) {
            case INIT:
                // 下拉布局初始状态
                headerIcon.setVisibility(VISIBLE);
                isRefreshAfter=false;
                headerTv.setText(R.string.refresh_down);
                headerIcon.clearAnimation();
                break;
            case RELEASE_TO_REFRESH:
                headerIcon.setVisibility(VISIBLE);
                // 释放刷新状态
                if(!isRefreshAfter){
                    isRefreshAfter=true;
                    headerIcon.startAnimation(rotateAnimation);
                }

                headerTv.setText(R.string.refresh_up);

                break;
            case REFRESHING:
                // 正在刷新状态
                headerIcon.clearAnimation();
                headerIcon.setVisibility(GONE);
                headerTv.setText(R.string.refresh_loading);
                break;
            case DONE:
                headerIcon.clearAnimation();
                headerTv.setText(R.string.refresh_down);
                break;
        }
    }
}
