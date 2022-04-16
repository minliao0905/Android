package com.example.myemo.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myemo.MainActivity;
import com.example.myemo.R;


import com.example.myemo.ui.Fragment.Game2048.game2048.TZ_Fragment;
import com.example.myemo.ui.Fragment.Saolei.Saol_Fragment;
import com.example.myemo.ui.Fragment.changeimage.Chi_Fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tablayout = null;
    private ViewPager viewpager ;
    private List<Fragment> mfragmentlist;

    private String[] mTabTitles = new String[3];
    private int[] mImages = new int[]{R.mipmap.tab1,R.mipmap.tab3,R.mipmap.tab2};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.tab_viewpager);
        Intent intent = getIntent();
        initView(intent.getIntExtra("currentitem",0));
    }

    private void initView(int position) {
        mTabTitles[0] = "2048";
        mTabTitles[1] = "拼图";
        mTabTitles[2] = "扫雷";
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mfragmentlist = new ArrayList<>();
        mfragmentlist.add(new TZ_Fragment());
        mfragmentlist.add(new Chi_Fragment());
        mfragmentlist.add(new Saol_Fragment());
        MyAdapter  Adapter = new MyAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter( Adapter);

        //将ViewPager和TabLayout绑定
        tablayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(position);   //设置当前游戏位置
        //设置自定义视图
        for (int i = 0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            tab.setCustomView( Adapter.getTabView(i));
        }

    }
    //监听返回键 实现返回游戏界面 homeActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
    class MyAdapter extends FragmentPagerAdapter {

        private Context context;

        public MyAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return mfragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return mfragmentlist.size();
        }

        /**
         * 自定义方法，提供自定义Tab
         *
         * @param position 位置
         * @return 返回Tab的View
         */
        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.zfragment_tab, null);
            TextView textView = (TextView) v.findViewById(R.id.tv_title);
            ImageView imageView = (ImageView) v.findViewById(R.id.iv_icon);
            textView.setText(mTabTitles[position]);
            imageView.setImageResource(mImages[position]);
            //添加一行，设置颜色
            textView.setTextColor(tablayout.getTabTextColors());//
            return v;
        }
    }

}
