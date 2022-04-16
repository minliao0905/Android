package com.example.study3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class listview_3 extends Activity {
    private ListView mListView;
    private String[] names = {"刘一","刘二","刘三","刘四","刘一","刘二","刘三","刘四","刘九"};
    private int[] icons = {R.raw.img01,R.raw.img02,R.raw.img03,R.raw.img04,R.raw.img05,R.raw.img06,R.raw.img07,R.raw.img08,R.raw.img09};
    @Override
    protected void onCreate(Bundle savedInstanceState){
           super.onCreate(savedInstanceState);
           setContentView(R.layout.listview_3);
           Intent intent = getIntent();//获取Intent对象
           Bundle bundle = intent.getExtras();  //获取传输信息，本实验中无需传输数据
           mListView = (ListView) findViewById(R.id.ilv);
        //创建一个内部类实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int position, long id) {
                Toast.makeText(listview_3.this, names[position] + "is clicked", Toast.LENGTH_SHORT).show();
            }
        });

       }
    class MyBaseAdapter extends BaseAdapter {
        //得到item的总数
        @Override
        public int getCount(){
            return names.length;
        }

        @Override
        public Object getItem(int position){
            return names[position];
        }
        //求getItem的意义
        @Override
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //获取view对象
            View view = View.inflate(listview_3.this,R.layout.listitem,null);
            TextView mTextView  = (TextView) view.findViewById(R.id.item_tv);
            mTextView.setText(names[position]);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
            imageView.setBackgroundResource(icons[position]);
            return view;

            //可使用viewHolder类改善代码，减少ListView的耗时操作
        }
    }
}
