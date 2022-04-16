package com.example.myemo.ui.Fragment.Saolei.ui.service;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ModelService {
    public static List<Modelinfo> getInforFromXML (InputStream is)throws Exception{
        XmlPullParser parser = Xml.newPullParser() ;
        parser.setInput(is,"utf-8");
        List<Modelinfo> rs = new ArrayList<Modelinfo>();;
        Modelinfo modelinfo = null;
        int type = parser.getEventType();

        while(type !=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("model".equals(parser.getName())){
                        modelinfo = new Modelinfo();
//                        int id= Integer.parseInt(parser.getAttributeName(0));
//                        modelinfo.setId(id);   //无法转换 导致报错，但无关紧要
                    }else if("ROW".equals(parser.getName())){
                        int row = Integer.parseInt(parser.nextText());
                        modelinfo.setROW(row);
                    }else if("COL".equals(parser.getName())){
                        int row = Integer.parseInt(parser.nextText());
                        modelinfo.setCOL(row);
                    }else if("LEICOUNT".equals(parser.getName())){
                        int row = Integer.parseInt(parser.nextText());
                        modelinfo.setLeicount(row);
                    }else if("textsize".equals(parser.getName())){
                        int row = Integer.parseInt(parser.nextText());
                        modelinfo.setTextsize(row);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("model".equals(parser.getName())){
//                        Log.v("startactivity",modelinfo.toString());
                        rs.add(modelinfo);
                    }
                    break;
            }
            type = parser.next();
        }
        return rs;
    }
}
