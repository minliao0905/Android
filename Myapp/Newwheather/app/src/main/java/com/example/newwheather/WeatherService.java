package com.example.newwheather;

import android.util.Xml;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    public static List<WeatherInfo> getInfosFromXML(InputStream is) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is,"utf-8");
        List<WeatherInfo> weatherInfos = null;
        WeatherInfo weatherInfo = null;
        int type = parser.getEventType();
        while(type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("infos".equals(parser.getName())){
                        weatherInfos = new ArrayList<WeatherInfo>();
                    }else if ("city".equals(parser.getName())){
                        weatherInfo = new WeatherInfo();
                        String idstr = parser.getAttributeName(0);
                        weatherInfo.setId(idstr);
                    }else if("temp".equals(parser.getName())){
                        String temp = parser.nextText();
                        weatherInfo.setTemp(temp);
                    }else if("weather".equals(parser.getName())){
                        String weather = parser.nextText();
                        weatherInfo.setTemp(weather);
                    }else if("pm".equals(parser.getName())){
                        String pm = parser.nextText();
                        weatherInfo.setTemp(pm);
                    }else if("wind".equals(parser.getName())){
                        String wind = parser.nextText();
                        weatherInfo.setTemp(wind);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("city".equals(parser.getName())){
                        weatherInfos.add(weatherInfo);
                        weatherInfo = null;
                    }
                    break;
            }
            type = parser.next();
        }
        return weatherInfos;
    }
}
