package com.example.myemo.Log.view;

import android.util.Log;
import android.util.Xml;


import com.example.myemo.Sql.Style;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class styleService  {
    public static List<Style> getInforFromXML (InputStream is)throws Exception{
        XmlPullParser parser = Xml.newPullParser() ;
        parser.setInput(is,"utf-8");
        List<Style> rs = new ArrayList<>();
        Style styleinfo = null;

        int type = parser.getEventType();

        while(type !=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("style".equals(parser.getName())){
                        styleinfo = new Style();
                        styleinfo.setStyleid(rs.size());
//                        int id= Integer.parseInt(parser.getAttributeName(0));
//                        modelinfo.setId(id);   //无法转换 导致报错，但无关紧要
                    }else if("background".equals(parser.getName())){
                       styleinfo.setBackground(parser.nextText())  ;
                    }else if("textcolor".equals(parser.getName())){
                       styleinfo.setTextcolor(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("style".equals(parser.getName())){
                        rs.add(styleinfo);
//                        Log.v("style",styleinfo.toString());
                    }
                    break;
            }
            type = parser.next();
        }
        return rs;
    }
}
