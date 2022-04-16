package com.example.changeimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.changeimage.ImgURi.headimgActivity;

import java.util.ArrayList;
import java.util.List;


public class StartActivity extends Activity implements View.OnClickListener{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState  ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
    }

        public void init(){
            Button btn1 = findViewById(R.id.easymodel);
            Button btn2 = findViewById(R.id.midmodel);
            LinearLayout picker = findViewById(R.id.pickerll);
            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
            picker.setOnClickListener(this);
            tv_col = findViewById(R.id.pickercol);
            tv_row = findViewById(R.id.pickerrow);
            getRow_Col();
        }
    private  static TextView tv_col  ;
    private static TextView  tv_row  ;
    public static  int[] num = new int[2];
        @Override
        public void onClick(View v) {
            int id = v.getId();
//        Toast.makeText(StartActivity.this,"当前点击事件",Toast.LENGTH_SHORT).show();

//            TextView tv_col = findViewById(R.id.pickercol);
//            TextView tv_row = findViewById(R.id.pickerrow);
//            int row = Integer.parseInt((String)tv_row.getText());
//            int col = Integer.parseInt((String)tv_col.getText());
            switch (id){
                case R.id.easymodel:
                    Intent intent1 = new Intent(StartActivity.this,MainActivity.class);
//                    intent1.putExtra("ROW",row);
//                    intent1.putExtra("COL",col);
                    startActivity(intent1);
                    break;
                case R.id.midmodel:
                    Intent intent2 = new Intent(StartActivity.this,ImgActivity.class);
//                    intent2.putExtra("ROW",row);
//                    intent2.putExtra("COL",col);
                    startActivity(intent2);
                    break;
                case R.id.pickerll:
                    showPickerView();

                default:
                    break;
            }
        }


    private void showPickerView() {
//      要展示的数据
       List<String> listData = getData();
//      监听选中
        OptionsPickerView pvOptions = new OptionsPickerBuilder(StartActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                tv_row.setText(listData.get(options1));
                tv_col.setText(listData.get(options1));//               返回的分别是三个级别的选中位置
//              展示选中数据
                getRow_Col();
            }


        })
                .setSelectOptions(0,0)
                .setOutSideCancelable(false)//点击背的地方不消失
                .build();//创建
//      把数据绑定到控件上面
        pvOptions.setPicker(listData );
//      展示
        pvOptions.show();
    }

    /**
     * 造一组数据
     */
    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i+3+"");
        }
        return list;

    }
    public static  int[] getRow_Col(){
      num[0] = Integer.parseInt((String)tv_row.getText());
      num[1]=  Integer.parseInt((String)tv_col.getText());
        Log.v("start","1:" + num[0] +" "+num[1] );
       return num;
    }

}
