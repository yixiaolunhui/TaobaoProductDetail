package com.dalong.taobaoproductdetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.dalong.refreshlayout.OnRefreshListener;
import com.dalong.taobaoproductdetail.dialog.Item;
import com.dalong.taobaoproductdetail.dialog.UpDialog;
import com.dalong.taobaoproductdetail.dialog.UpViewListener;
import com.dalong.taobaoproductdetail.refresh.TaobaoRefreshLayout;
import com.dalong.taobaoproductdetail.util.status.SystemStatusManager;
import com.dalong.taobaoproductdetail.view.LocalImageHolderView;

import java.util.ArrayList;
import java.util.List;

/**
 * 淘宝详情
 */
public class MainActivity extends AppCompatActivity implements View.OnScrollChangeListener {
    private TaobaoRefreshLayout refreshview;
    private List<Item> mFancyCoverFlows=new ArrayList<>();
    private List<String> localImages = new ArrayList<String>();
    private ConvenientBanner convenientBanner;
    private ScrollView scrollview;
    private RelativeLayout mTitle;
    private TextView mTitleName;
    private int mHeight;
    public SystemStatusManager tintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTranslucentStatus();
        initData();
        initView();
        refreshview=(TaobaoRefreshLayout)findViewById(R.id.refreshLayout);
        refreshview.setCanRefresh(true);
        refreshview.setCanLoad(true);
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                UpDialog.create(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_up_view)//设置显示布局
                        .setViewListener(new UpViewListener(MainActivity.this,mFancyCoverFlows))
                        .setDimAmount(0.6f) //设置背景透明度
                        .setCancelOutside(true) // 设置是否可以点击其他区域关闭dialog
                        .setTag("comment")
                        .show();
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,200);
            }

            @Override
            public void onLoadMore() {
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
            }
        });


    }
    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.trantracent_wode);
    }
    private void initView() {
        // 头部图片集
        convenientBanner = (ConvenientBanner)findViewById(R.id.imagepager);
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        mTitleName = (TextView) findViewById(R.id.title_name);
        mTitle = (RelativeLayout) findViewById(R.id.title);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) convenientBanner.getLayoutParams();
        params.height = getScreenWidth(this);
        convenientBanner.setLayoutParams(params);


        convenientBanner.setPages( new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
       .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

        ViewTreeObserver vto = convenientBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                mHeight = convenientBanner.getHeight();

                scrollview.setOnScrollChangeListener(MainActivity.this);
            }
        });
    }


    Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    refreshview.stopRefresh(true);
                    break;
                case 1:
                    refreshview.stopLoadMore(true);
                    break;
            }
        }
    };


    public String[] urls=new String[]{
            "https://g-search3.alicdn.com/img/bao/uploaded/i4/i4/TB1M7MpNFXXXXbAXpXXXXXXXXXX_!!0-item_pic.jpg_360x360Q90.jpg",
            "https://g-search3.alicdn.com/img/bao/uploaded/i4/i3/TB1aPyvNFXXXXcuXFXXXXXXXXXX_!!0-item_pic.jpg_360x360Q90.jpg",
            "https://img.alicdn.com/imgextra/i1/141040316104003590/TB2ADyDbYeI.eBjSspkXXaXqVXa_!!0-saturn_solar.jpg_200x200.jpg",
            "https://img.alicdn.com/imgextra/i4/180510320154953942/TB24Sc7cr1J.eBjy1zeXXX9kVXa_!!0-saturn_solar.jpg_200x200.jpg",
            "https://g-search1.alicdn.com/img/bao/uploaded/i4/i3/TB1ch60OXXXXXcaaXXXXXXXXXXX_!!0-item_pic.jpg_360x360xzQ90.jpg",
            "https://g-search1.alicdn.com/img/bao/uploaded/i4/imgextra/i1/112740319270471162/TB2jc1Tcr5K.eBjy0FnXXaZzVXa_!!0-saturn_solar.jpg_360x360Q90.jpg",
            "https://g-search2.alicdn.com/img/bao/uploaded/i4/i1/2506473611/TB2VfVebxvzQeBjSZFAXXaF9VXa_!!2506473611.jpg_360x360Q90.jpg",
            "https://g-search2.alicdn.com/img/bao/uploaded/i4/i2/TB1CckuOXXXXXbPaFXXXXXXXXXX_!!0-item_pic.jpg_360x360Q90.jpg",
            "https://g-search1.alicdn.com/img/bao/uploaded/i4/i3/TB158FaNFXXXXbzXXXXXXXXXXXX_!!0-item_pic.jpg_360x360Q90.jpg",
            "https://g-search1.alicdn.com/img/bao/uploaded/i4/i4/TB1K5ksOXXXXXc.XXXXXXXXXXXX_!!0-item_pic.jpg_360x360Q90.jpg"
    };
    public  String[] names=new String[]{
            "东慕新款男士羽绒服男中长款加厚修身款青年韩版冬",
            "羽绒服男士中长款青少年学生冬季外套加厚大码连帽",
            "JackJones杰克琼斯中长款棒球领灰鸭绒修身男士羽绒",
            "波司登2016新品男士秋轻薄时尚简约短款纯色立领羽",
            "卡西玫韩版大毛领修身加长款羽绒服 修身加厚过膝羽",
            "JackJones杰克琼斯16秋季白鸭绒男士短款羽绒服外套",
            "卡西玫韩版大毛领修身加长款羽绒服 修身加厚过膝羽",
            "清仓特价中老年羽绒服男士加厚大码中长款老年人爸",
            "波司登2016新款春款 男士简约轻薄短款保暖正品 羽绒",
            "【经典款】Columbia/哥伦比亚户外男Omni-Heat800"
    };
    public void initData(){

        for(int i=0;i<urls.length;i++){
            Item item=new Item();
            item.setName(names[i]);
            item.setUrl(urls[i]);
            item.setSelected(false);
            item.setPrice("¥"+68*(1+i));
            mFancyCoverFlows.add(item);
            localImages.add(urls[i]);
        }
    }

    @Override
    public void onScrollChange(View view, int x, int y, int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            mTitle.setBackgroundColor(Color.argb((int) 0, 255,255,255));
        } else if (y > 0 && y <= mHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / mHeight;
            float alpha = (255 * scale);
            mTitleName.setTextColor(Color.argb((int) alpha, 1,24,28));
            mTitle.setBackgroundColor(Color.argb((int) alpha, 255,255,255));
        } else {    //滑动到banner下面设置普通颜色
            mTitle.setBackgroundColor(Color.argb((int) 255, 255,255,255));
        }
    }
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
