package com.tzj.eventbustest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

/**
 * <p> FileName： SecondActivity</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/2/28
 */
public class SecondActivity extends AppCompatActivity {

    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new TestObserver("我发送了"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
