package com.tencent.shadow.sample.plugin3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    //AI考试
    private final String loadUrl = "https://assess.fifedu.com/static/fiftest/h5/index.html#/?loginName=csBFSU199105&sessionId=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyZWFsTmFtZSI6Imo1Iiwic2Nob29sSWQiOiIyMDAwMDAwMDI2MDAwMDAwMDAxIiwiaXNzIjoiZmlmYWMiLCJpZCI6IjI4MTEwMDAwMjYwMDE3NjE4NzEiLCJleHAiOjE3MTQ4Nzc0MzIsIm1lbWJlcklkIjoiYWJmMGM0ODliZDE3NDQ4ZWEyOGQ4MWQ3ODBkZmI4OTcifQ.zKqczZ_4oAOcJESuXqoScEoIB6IswWkvK7nW8t10el8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = getIntent().getStringExtra("url");
        Log.e(TAG, "tea MainActivity url:" + url);
        Button bt_skip = findViewById(R.id.bt_skip);
        Button bt_interact_host = findViewById(R.id.bt_interact_host);
        Button bt_interact_stu = findViewById(R.id.bt_interact_stu);
        bt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://www.pgyer.com/roOl");
                startActivity(intent);
            }
        });
        bt_interact_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_KYXLTEA_KEY_SEND_TO_HOST);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_KYXLTEA_VALUE_SEND_TO_HOST);
                sendBroadcast(intent);
            }
        });
        bt_interact_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", loadUrl);
                startActivity(intent);
            }
        });
    }
}