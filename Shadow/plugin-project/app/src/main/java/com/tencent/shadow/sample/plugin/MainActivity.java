package com.tencent.shadow.sample.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_host = findViewById(R.id.bt_host);
        Button bt_plugin_stu = findViewById(R.id.bt_plugin_stu);
        Button bt_plugin_tea = findViewById(R.id.bt_plugin_tea);
        bt_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_PLUGIN_KEY_SEND_TO_HOST);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_PLUGIN_VALUE_SEND_TO_HOST);
                sendBroadcast(intent);
            }
        });
        bt_plugin_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_PLUGIN_KEY_SKIP_TO_STU);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_PLUGIN_VALUE_SKIP_TO_STU);
                sendBroadcast(intent);
            }
        });
        bt_plugin_tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_PLUGIN_KEY_SKIP_TO_TEA);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_PLUGIN_VALUE_SKIP_TO_TEA);
                sendBroadcast(intent);
            }
        });
    }
}