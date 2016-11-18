package com.dalong.taobaoproductdetail.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalong.francyconverflow.FancyCoverFlow;
import com.dalong.francyconverflow.FancyCoverFlowAdapter;
import com.dalong.taobaoproductdetail.R;

import java.util.List;


public class MyFancyCoverFlowAdapter extends FancyCoverFlowAdapter {
    private Context mContext;

    public List<Item> list;

    public MyFancyCoverFlowAdapter(Context context, List<Item> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public View getCoverFlowItem(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fancycoverflow, null);
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            convertView.setLayoutParams(new FancyCoverFlow.LayoutParams(width / 2, FancyCoverFlow.LayoutParams.WRAP_CONTENT));
            holder = new ViewHolder();
            holder.product_name = (TextView) convertView.findViewById(R.id.name);
            holder.product_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.product_price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Item item = getItem(position);
        holder.product_name.setText(item.getName());
        holder.product_price.setText(item.getPrice());
        Glide
                .with(mContext)
                .load(item.getUrl())
                .centerCrop()
                .crossFade()
                .into(holder.product_icon);
        return convertView;
    }

    public void setSelected(int position) {
        position = position % list.size();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    list.get(i).setSelected(true);
                } else {
                    list.get(i).setSelected(false);
                }
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Item getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    static class ViewHolder {
        TextView product_name;
        ImageView product_icon;
        TextView product_price;
    }
}
