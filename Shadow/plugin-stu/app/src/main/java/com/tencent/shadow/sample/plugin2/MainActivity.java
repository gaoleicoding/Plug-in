package com.tencent.shadow.sample.plugin2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_skip = findViewById(R.id.bt_skip);
        String url = getIntent().getStringExtra("url");
        Log.e(TAG, "stu MainActivity url:" + url);

        bt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://www.pgyer.com/qYLG");
                startActivity(intent);
            }
        });
    }
}