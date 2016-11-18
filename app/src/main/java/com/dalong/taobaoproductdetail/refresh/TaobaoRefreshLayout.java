package com.dalong.taobaoproductdetail.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * Created by dalong on 2016/11/18.
 */

public class TaobaoRefreshLayout extends RefreshLayout {
    public TaobaoRefreshLayout(Context context) {
        super(context);
    }

    public TaobaoRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        TaobaoHeader header = new TaobaoHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}
