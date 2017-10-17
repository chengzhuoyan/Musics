package com.bwie.vidio.modle.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bwie.vidio.R;

public class FlashActivity extends AppCompatActivity {
    private TextView textView;
    int count = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (count <= 0 ) {// 跳转
                startActivity(new Intent(FlashActivity.this,
                        MainActivity.class));
                finish();
            } else {// 倒计时处理
//                textView.setText(count + "秒");
                count--;
                handler.sendEmptyMessageDelayed(99, 1000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        textView=(TextView)findViewById(R.id.home_text);
        handler.sendEmptyMessageDelayed(99,1000);
    }

}
