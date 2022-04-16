package com.example.myemo.ui.Fragment.Saolei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myemo.ui.Fragment.Saolei.ui.service.ModelService;
import com.example.myemo.ui.Fragment.Saolei.ui.service.Modelinfo;

import java.io.InputStream;
import java.util.List;
import com.example.myemo.R;
import com.example.myemo.ui.main.HomeActivity;

public class StartActivity extends Activity implements View.OnClickListener{
    public static Modelinfo modelinfo = null;
    private List<Modelinfo> modelinfoList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saol_start);
        init();
    }
    public void init(){
        Button btn1 = findViewById(R.id.easymodel);
        Button btn2 = findViewById(R.id.midmodel);
        Button btn3 = findViewById(R.id.hardmodel);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        try{
            InputStream is = this.getResources().openRawResource(R.raw.modelinfo);
            modelinfoList = ModelService.getInforFromXML(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Modelinfo getModelinfo(){
        return modelinfo;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
//        Toast.makeText(StartActivity.this,"当前点击事件",Toast.LENGTH_SHORT).show();
        switch (id){
            case R.id.easymodel:
                modelinfo = modelinfoList.get(0);
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                break;
            case R.id.midmodel:
                modelinfo = modelinfoList.get(1);
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                break;
            case R.id.hardmodel:
                modelinfo = modelinfoList.get(2);
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                break;
            default:
                break;
        }
    }

}
