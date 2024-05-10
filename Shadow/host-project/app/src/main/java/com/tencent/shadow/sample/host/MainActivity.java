package com.tencent.shadow.sample.host;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.PluginManager;
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlStuApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlTeaApplication;

public class MainActivity extends Activity {
    Button bt_plugin, bt_service, bt_plugin_stu, bt_plugin_tea, bt_common_util, bt_add_plugin_view;
    private final String HOST_BROADCAST_ACTION = "HOST_BROADCAST_ACTION";
    private HostBroadcastReceiver hostBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_plugin = findViewById(R.id.bt_plugin);
        bt_service = findViewById(R.id.bt_service);
        bt_plugin_stu = findViewById(R.id.bt_plugin_stu);
        bt_plugin_tea = findViewById(R.id.bt_plugin_tea);
        bt_common_util = findViewById(R.id.bt_common_util);
        bt_add_plugin_view = findViewById(R.id.bt_add_plugin_view);

        bt_plugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);//防止点击重入

                PluginManager pluginManager = InitApplication.getPluginManager();
                pluginManager.enter(MainActivity.this, Constant.FROM_ID_START_ACTIVITY, new Bundle(), new EnterCallback() {
                    @Override
                    public void onShowLoadingView(View view) {
//                        MainActivity.this.setContentView(view);//显示Manager传来的Loading页面
                    }

                    @Override
                    public void onCloseLoadingView() {
//                        MainActivity.this.setContentView(linearLayout);
                    }

                    @Override
                    public void onEnterComplete() {
                        v.setEnabled(true);
                    }
                });
            }
        });

        bt_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);//防止点击重入

                PluginManager pluginManager = InitApplication.getPluginManager();
                pluginManager.enter(MainActivity.this, Constant.FROM_ID_CALL_SERVICE, new Bundle(), new EnterCallback() {
                    @Override
                    public void onShowLoadingView(View view) {
                    }

                    @Override
                    public void onCloseLoadingView() {
                    }

                    @Override
                    public void onEnterComplete() {
                        v.setEnabled(true);
                    }
                });
            }
        });
        bt_plugin_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);//防止点击重入

                PluginManager pluginManager = InitKyxlStuApplication.getPluginManager();
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.baidu.com");
                pluginManager.enter(MainActivity.this, Constant.FROM_ID_START_KYXLSTU_ACTIVITY, bundle, new EnterCallback() {
                    @Override
                    public void onShowLoadingView(View view) {
//                        MainActivity.this.setContentView(view);//显示Manager传来的Loading页面
                    }

                    @Override
                    public void onCloseLoadingView() {
//                        MainActivity.this.setContentView(linearLayout);
                    }

                    @Override
                    public void onEnterComplete() {
                        v.setEnabled(true);
                    }
                });
            }
        });
        bt_plugin_tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);//防止点击重入

                PluginManager pluginManager = InitKyxlTeaApplication.getPluginManager();
                pluginManager.enter(MainActivity.this, Constant.FROM_ID_START_KYXLTEA_ACTIVITY, new Bundle(), new EnterCallback() {
                    @Override
                    public void onShowLoadingView(View view) {
//                        MainActivity.this.setContentView(view);//显示Manager传来的Loading页面
                    }

                    @Override
                    public void onCloseLoadingView() {
//                        MainActivity.this.setContentView(linearLayout);
                    }

                    @Override
                    public void onEnterComplete() {
                        v.setEnabled(true);
                    }
                });
            }
        });
        bt_common_util.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Utils.getPersonalCenter();
            }
        });
        bt_add_plugin_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(MainActivity.this, HostAddPluginViewActivity.class);
                startActivity(intent);
            }
        });
        // 注册广播接收器
        hostBroadcastReceiver = new HostBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(HOST_BROADCAST_ACTION);
        registerReceiver(hostBroadcastReceiver, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(hostBroadcastReceiver);
    }

}