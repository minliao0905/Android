package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private String[] names = {"京东商城","QQ","新浪微博","天猫","浏览器","微信","淘宝"};
    private int[] icons = {R.drawable.jd,R.drawable.qq,R.drawable.wb,R.drawable.tm,R.drawable.ll,R.drawable.wx,R.drawable.tb};
    //图片合集
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.ilv);
        //创建一个内部类实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Toast点击显示第"+position+"个item", Toast.LENGTH_SHORT).show();
            }
        });
    }



    class MyBaseAdapter extends BaseAdapter  {
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
            View view = View.inflate(MainActivity.this,R.layout.list_item,null);
            TextView mTextView  = (TextView) view.findViewById(R.id.item_tv);
            mTextView.setText(names[position]);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
            imageView.setBackgroundResource(icons[position]);
            return view;

            //可使用viewHolder类改善代码，减少ListView的耗时操作
        }
    }

}