package com.wuguangxin.simple.ui;

import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.wuguangxin.simple.R;

import androidx.appcompat.app.AppCompatActivity;

public class GestureViewActivity extends AppCompatActivity {

    private static final String TAG = "GestureViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_view);
        setTitle("手势密码");

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);    // 监听来电状态
    }


    /**
     * 电话状态监听器
     */
    private static class MyPhoneStateListener extends PhoneStateListener {

        /**
         * 当来电时，暂停播放，来电结束，继续播放
         * @param state 状态
         * @param phoneNumber 来电号码
         */
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:    // 来电/震铃(incomingNumber只能在震铃状态获取)
                Log.e(TAG, "onCallStateChanged：CALL_STATE_RINGING 震铃(incomingNumber只能在震铃状态获取");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:    // 接听
                Log.e(TAG, "onCallStateChanged：CALL_STATE_OFFHOOK 摘机");
                break;
            case TelephonyManager.CALL_STATE_IDLE:       // 空闲/挂断
                Log.e(TAG, "onCallStateChanged：CALL_STATE_IDLE 空闲/挂断电话");
                break;
            }
        }
    }
}
