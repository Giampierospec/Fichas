package com.giamp.fichasvers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<Ficha> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyAdapter(Context context,ArrayList<Ficha> ficha){
        this.listData = ficha;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    public int getId(int position){
        return listData.get(position).getId();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.id_view);
            holder.preg = (TextView) convertView.findViewById(R.id.preg_view);
            holder.ans = (TextView) convertView.findViewById(R.id.answer_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.id.setText(String.valueOf(listData.get(position).getId()));
        holder.preg.setText(listData.get(position).getPreg());
        holder.ans.setText(listData.get(position).getRes());




        return convertView;
    }

    static class ViewHolder {
        TextView id;
        TextView preg;
        TextView ans;
    }
}


