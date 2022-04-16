package com.example.myphonenumber.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myphonenumber.MainActivity;

//自定义类访问权限
public class permissionUtil {
    /**
     * 返回时、否拥改权限
     * @param activity
     * @param permission
     * @return
     */
    public static boolean isownPermisson(Activity activity, String permission){

        return ContextCompat.checkSelfPermission(activity,  permission)  == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission,int requestCode) {
        //设置弹窗对话框，询问用户是否同意访问权限
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setCancelable(false)
                        .setMessage("应用需要获取您的相关权限")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(activity,"如果您不同意该应用的访问权限，您将无法正常使用！",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, new String[]{permission},requestCode);
                            }
                        }).show();
            }
        }
    }

}
