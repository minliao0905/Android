package com.example.myemo.ui.Fragment.Saolei;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.myemo.R;

public class Saol_Fragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.saol_welcome, container, false);
        Button tz_enter = (Button) rootView.findViewById(R.id.saol_enter);
        tz_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),StartActivity.class));
                // 跳转界面
            }
        });
        return rootView;
    }
}
