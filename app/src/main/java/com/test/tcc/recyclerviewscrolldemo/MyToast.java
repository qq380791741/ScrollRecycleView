package com.test.tcc.recyclerviewscrolldemo;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {
    public static void showToastShort(Context context , String msg) {
        Toast toast  = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view = View.inflate(context, R.layout.toast,null);
        TextView tvPrompt = (TextView)view.findViewById(R.id.tv_msg);
        view.setAlpha(0.8f);
        tvPrompt.setText(msg);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void showToastLong(Context context , String msg) {
        Toast toast  = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view = View.inflate(context, R.layout.toast,null);
        TextView tvPrompt = (TextView)view.findViewById(R.id.tv_msg);
        tvPrompt.setText(msg);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
