package com.example.myemo.ui.Fragment.changeimage.Dialog;

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
//自定义弹出Dialog 类
public class LoadingDialog {

    Dialog mLoadingDialog;

    public static void LDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View contenView = LayoutInflater.from(context).inflate(R.layout.chi_dialog_show, null);
        builder.setView(contenView);

        final AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        ImageView gifView2 = contenView.findViewById(R.id.im2);
        ImageView gifView1 = contenView.findViewById(R.id.im1);
        Glide.with(context).load(R.drawable.tz_dianzan).into(gifView2);
        Glide.with(context).load(R.drawable.con1).into(gifView1);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);   //设置背景透明
        WindowManager m = dialogWindow.getWindowManager();

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
