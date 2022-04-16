package com.example.newwheather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvpm;
    private TextView tvTemp;
    private TextView tvWind;
    private ImageView ivIcon;
    private Map<String,String> map ;
    private List<Map<String,String>> list;
    private String temp,weather,name,pm,wind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        try{
            InputStream is = this.getResources().openRawResource(R.raw.weather);
            List<WeatherInfo> weatherinfolist = WeatherService.getInfosFromXML(is);
            is.close();
            if(weatherinfolist==null){
                System.out.println("文件读取失败！");
                return;
            }
            list = new ArrayList<Map<String,String>>();
            for(WeatherInfo info:weatherinfolist){
                map = new HashMap<String,String>();
                map.put("temp",info.getTemp());
                map.put("name",info.getName());
                map.put("pm",info.getPm());
                map.put("weather",info.getWeather());
                map.put("wind",info.getWind());
                list.add(map);
            }
            //System.out.print(weatherinfolist);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"解析失败",Toast.LENGTH_SHORT).show();
        }
       // getMap(0,R.drawable.cloud_sun);

    }

    private void initView() {
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvpm = (TextView) findViewById(R.id.tv_pm);
        tvTemp = (TextView) findViewById(R.id.tv_temp);
        tvWind = (TextView) findViewById(R.id.tv_wind);
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        ivIcon =  (ImageView) findViewById(R.id.iv_icon);
        findViewById(R.id.btn_bj).setOnClickListener(this);
        findViewById(R.id.btn_sh).setOnClickListener(this);
        findViewById(R.id.btn_sz).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_bj:getMap(0,R.drawable.cloud_sun);
            break;
            case R.id.btn_sh:getMap(1,R.drawable.sun);
            break;
            case R.id.btn_sz:getMap(2,R.drawable.clouds);
            break;
        }
    }
    private void getMap(int number,int iconNumber){
        Map<String,String> citymap = list.get(number);
        name = citymap.get("name");
        temp = citymap.get("temp");
        pm = citymap.get("pm");
        weather = citymap.get("weather");
        wind = citymap.get("wind");

        tvCity.setText(name);
        tvpm.setText(pm);
        tvWeather.setText(weather);
        tvWind.setText(wind);
        tvTemp.setText(temp);
        ivIcon.setImageResource(iconNumber);

    }
}