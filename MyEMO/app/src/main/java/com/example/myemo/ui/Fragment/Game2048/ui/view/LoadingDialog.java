package com.example.myemo.ui.Fragment.Game2048.ui.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myemo.R;
//当合成了2048时弹出的提示框，
//自定义弹出Dialog 类
public class LoadingDialog {

    Dialog mLoadingDialog;

    public static void LDialog(Context context,int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View contenView = LayoutInflater.from(context).inflate(R.layout.tz_dialog_show, null);
        builder.setView(contenView);

        final AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);   //设置背景透明
        WindowManager m = dialogWindow.getWindowManager();
        ImageView img= contenView.findViewById(R.id.tzm1);
        ImageView imgbg = contenView.findViewById(R.id.tzm2);
        switch (type){
            case 1024:
                img.setImageResource(R.drawable.tz1024);
                imgbg.setImageResource(R.drawable.tz2048bg);
                break;
            case 2048:
                img.setImageResource(R.drawable.tz2048);
                imgbg.setImageResource(R.drawable.tz2048bg);
                break;
            case 4096:
                img.setImageResource(R.drawable.tz4096);
                imgbg.setImageResource(R.drawable.tz2048bg);
                break;
            case 8192:
                img.setImageResource(R.drawable.tz8192);
                imgbg.setImageResource(R.drawable.tz2048bg);
                break;
            case 0:
                img.setImageResource(R.drawable.gameover);
                break;
            default:
                img.setImageResource(R.drawable.tz_dianzan);
                imgbg.setImageResource(R.drawable.tz_dianzanbg);
                break;
        }

        dialog.show();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.35); // 高度设置为屏幕的0.35
        p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.60

        dialog.getWindow().setAttributes(p);//设置大小

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                boolean click = false;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    click = true;
                }
                return click;
            }
        });

    }

    public void show() {
        mLoadingDialog.show();
    }

    public void close() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


}
