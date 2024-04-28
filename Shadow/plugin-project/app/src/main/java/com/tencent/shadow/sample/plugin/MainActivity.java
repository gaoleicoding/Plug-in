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
        Button bt_interact_host = findViewById(R.id.bt_interact_host);
        Button bt_interact_stu = findViewById(R.id.bt_interact_stu);
        Button bt_interact_tea = findViewById(R.id.bt_interact_tea);
        bt_interact_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_PLUGIN_KEY_SEND_TO_HOST);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_PLUGIN_VALUE_SEND_TO_HOST);
                sendBroadcast(intent);
            }
        });
        bt_interact_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_PLUGIN_KEY_SKIP_TO_STU_WEB);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_PLUGIN_VALUE_SKIP_TO_STU_WEB);
                sendBroadcast(intent);
            }
        });
        bt_interact_tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_PLUGIN_KEY_SKIP_TO_TEA_WEB);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_PLUGIN_VALUE_SKIP_TO_TEA_WEB);
                sendBroadcast(intent);
            }
        });
    }
}