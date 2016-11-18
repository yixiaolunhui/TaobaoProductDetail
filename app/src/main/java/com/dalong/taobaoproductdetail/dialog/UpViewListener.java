package com.dalong.taobaoproductdetail.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dalong.francyconverflow.FancyCoverFlow;
import com.dalong.taobaoproductdetail.R;

import java.util.List;

/**
 * Created by zhouweilong on 2016/11/18.
 */

public class UpViewListener implements UpDialog.ViewListener {
    private Context mContext;

    private List<Item> mFancyCoverFlows;

    private FancyCoverFlow mfancyCoverFlow;

    private  MyFancyCoverFlowAdapter mMyFancyCoverFlowAdapter;
    private TextView mNum;

    public UpViewListener(Context mContext,List<Item> mFancyCoverFlows) {
        this.mFancyCoverFlows=mFancyCoverFlows;
        this.mContext=mContext;
    }

    @Override
    public void bindView(View v) {
        mfancyCoverFlow = (FancyCoverFlow) v.findViewById(R.id.fancyCoverFlow);
        mNum = (TextView) v.findViewById(R.id.num);
        mMyFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(mContext, mFancyCoverFlows);
        mfancyCoverFlow.setAdapter(mMyFancyCoverFlowAdapter);
        mMyFancyCoverFlowAdapter.notifyDataSetChanged();
        mfancyCoverFlow.setUnselectedAlpha(0.1f);//通明度
        mfancyCoverFlow.setUnselectedSaturation(0.5f);//设置选中的饱和度
        mfancyCoverFlow.setUnselectedScale(0.8f);//设置选中的规模
        mfancyCoverFlow.setSpacing(20);//设置间距
        mfancyCoverFlow.setMaxRotation(0);//设置最大旋转
        mfancyCoverFlow.setScaleDownGravity(0.5f);
        mfancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
//        mfancyCoverFlow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Item homeFancyCoverFlow = (Item) mfancyCoverFlow.getSelectedItem();
//                Toast.makeText(mContext,homeFancyCoverFlow.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
        mfancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNum.setText("（"+(position+1)+"/"+mFancyCoverFlows.size()+"）");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
