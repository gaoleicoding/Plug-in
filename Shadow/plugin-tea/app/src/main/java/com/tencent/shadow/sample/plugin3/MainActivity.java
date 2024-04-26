package com.tencent.shadow.sample.plugin3;

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
        String url = getIntent().getStringExtra("url");
        Log.e(TAG, "tea MainActivity url:" + url);
        Button bt_skip = findViewById(R.id.bt_skip);
        bt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://www.pgyer.com/roOl");
                startActivity(intent);
            }
        });
    }
}