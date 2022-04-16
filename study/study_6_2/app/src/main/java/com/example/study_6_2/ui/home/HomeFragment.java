package com.example.study_6_2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.study_6_2.MainActivity;
import com.example.study_6_2.R;
import com.example.study_6_2.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //保存数据UI,获取数据的作用，原型为ViewModel
    private FragmentHomeBinding binding;
    private ListView mListView;
    private String[] names = {"小雪","小明"};
    private int[] icons = {R.drawable.qqhd,R.drawable.headimg_eg_cute};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        final ListView mListView =  binding.listItem;
//        //创建一个内部类实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> AdapterView, View view, int position, long id) {
//                Toast.makeText(root.getContext(), "Toast点击显示第"+position+"个item", Toast.LENGTH_SHORT).show();
//            }
//        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
//创建消息列表显示内容

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
            //获取当前的view对象
            View view = View.inflate(parent.getContext(), R.layout.list_item, null);
            TextView mTextView  = (TextView) view.findViewById(R.id.item_tv);
            mTextView.setText(names[position]);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
            imageView.setBackgroundResource(icons[position]);
            return view;

            //可使用viewHolder类改善代码，减少ListView的耗时操作
        }
    }
}