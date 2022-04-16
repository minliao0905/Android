package com.example.myemo.ui.Fragment.Game2048.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myemo.R;

public class TZ_Fragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tz_welcome, container, false);
         Button tz_enter = (Button) rootView.findViewById(R.id.tz_enter);
         tz_enter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 startActivity(new Intent(getContext(),MainActivity.class));
                 getActivity().finish();   //为了避免在 多次当前页面跳转时 ，用户按返回按钮时 出现多次跳回HomeActivity 界面
                 // 跳转界面
             }
         });
        return rootView;
    }

}
