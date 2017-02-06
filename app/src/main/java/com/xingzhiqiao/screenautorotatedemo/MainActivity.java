package com.xingzhiqiao.screenautorotatedemo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private Button oriBtn;

    private MyOrientoinListener myOrientoinListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oriBtn = (Button) findViewById(R.id.orientation);

        myOrientoinListener = new MyOrientoinListener(this);
        boolean autoRotateOn = (android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        //检查系统是否开启自动旋转
        if (autoRotateOn) {
            myOrientoinListener.enable();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁时取消监听
        myOrientoinListener.disable();
    }

    class MyOrientoinListener extends OrientationEventListener {

        public MyOrientoinListener(Context context) {
            super(context);
        }

        public MyOrientoinListener(Context context, int rate) {
            super(context, rate);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            Log.d(TAG, "orention" + orientation);
            int screenOrientation = getResources().getConfiguration().orientation;
            if (((orientation >= 0) && (orientation < 45)) || (orientation > 315)) {//设置竖屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && orientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    Log.d(TAG, "设置竖屏");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    oriBtn.setText("竖屏");
                }
            } else if (orientation > 225 && orientation < 315) { //设置横屏
                Log.d(TAG, "设置横屏");
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    oriBtn.setText("横屏");
                }
            } else if (orientation > 45 && orientation < 135) {// 设置反向横屏
                Log.d(TAG, "反向横屏");
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    oriBtn.setText("反向横屏");
                }
            } else if (orientation > 135 && orientation < 225) {
                Log.d(TAG, "反向竖屏");
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    oriBtn.setText("反向竖屏");
                }
            }
        }
    }
}
