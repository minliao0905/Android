package com.example.ui.menu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game2048.MainActivity;
import com.example.game2048.R;
import com.example.game2048.SPSave;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Game_Record extends Activity {
    private Map<String,String> userMap = new HashMap<String,String>();
    private ListView mListView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userMap = SPSave.getUserRecord(getBaseContext());   //获取context??
        super.onCreate(savedInstanceState);
//        Toast.makeText(Game_Record.this, "Gamerecord", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_gamerecord);

        mListView = (ListView) findViewById(R.id.recordlist);
        //创建一个内部类实例
        MylistAdapter mAdapter = new MylistAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int position, long id) {
                Toast.makeText(Game_Record.this, "Toast点击显示第"+position+"个item", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //创建自定义适配器
    class MylistAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return userMap.size()/2;
        } //因为同时保存时间和分数，所以需要/2

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
           return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //获取view对象
            View view = View.inflate(Game_Record.this,R.layout.resordlist_item,null);
            TextView mTextView  = (TextView) view.findViewById(R.id.score_ed);
            TextView mTimeView = (TextView) view.findViewById(R.id.score_time);
            if(userMap.get("score"+position)!=null){
                mTextView.setText(userMap.get("score"+position)+"分");
                mTimeView.setText(userMap.get("time"+position));
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
//            imageView.setBackgroundResource();
            return view;
        }
    }
}
