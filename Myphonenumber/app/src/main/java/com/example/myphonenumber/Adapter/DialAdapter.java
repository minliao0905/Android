package com.example.myphonenumber.Adapter;

import java.util.List;

import android.content.Context;

import android.content.Intent;

import android.net.Uri;

import android.view.LayoutInflater;

import android.view.View;

import android.view.View.OnClickListener;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.myphonenumber.R;
import com.example.myphonenumber.entity.CallLogBean;


public class DialAdapter extends BaseAdapter {

    private Context ctx;
    private List callLogs;
    private LayoutInflater inflater;

    public DialAdapter(Context context, List callLogs) {
        this.ctx = context;

        this.callLogs = callLogs;

        this.inflater = LayoutInflater.from(context);
    }

@Override

    public int getCount() {
        return callLogs.size();

    }

@Override

    public Object getItem(int position) {
        return callLogs.get(position);
    }

@Override

    public long getItemId(int position) {
        return position;
    }

@Override

public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
       if (convertView == null) {
    convertView = inflater.inflate(R.layout.usercontacts_list_item,null);
    holder = new ViewHolder();
    //设置当前记录的通话类型
    holder.call_type = (ImageView) convertView.findViewById(R.id.call_type);
    holder.name = (TextView) convertView.findViewById(R.id.name);
    holder.number = (TextView) convertView.findViewById(R.id.number);
    holder.time = (TextView) convertView.findViewById(R.id.time);
    holder.call_btn = (ImageButton) convertView.findViewById(R.id.contactlist_call_btn);
    convertView.setTag(holder); // 缓存

    } else {
        holder = (ViewHolder) convertView.getTag();
    }
    CallLogBean callLog = (CallLogBean) callLogs.get(position);
    switch (callLog.getType()) {
        case 1:
            holder.call_type.setBackgroundResource(R.drawable.amazing);
            break;
        case 2:
            holder.call_type.setBackgroundResource(R.drawable.amazing);
            break;
        case 3:
            holder.call_type.setBackgroundResource(R.drawable.amazing);
            break;
    }
    holder.name.setText(callLog.getName());

    holder.number.setText(callLog.getNumber());

    holder.time.setText(callLog.getDate());

    addViewListener(holder.call_btn, callLog, position);

    return convertView;

    }

    private static class ViewHolder {
        ImageView call_type;

        TextView name;

        TextView number;

        TextView time;

        ImageButton call_btn;
    }
//设置监听当点击通讯记录时也可进行通话
    private void addViewListener(View view, final CallLogBean callLog, final int position) {
        view.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri uri = Uri.parse("tel:" + callLog.getNumber());
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            ctx.startActivity(intent);
        }
    });
    }

}