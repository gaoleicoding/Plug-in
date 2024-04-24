package com.tencent.shadow.sample.host;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.PluginManager;
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlStuApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlTeaApplication;

public class MainActivity extends Activity {

    public static final int FROM_ID_START_ACTIVITY = 1001;
    public static final int FROM_ID_CALL_SERVICE = 1002;
    public static final int FROM_ID_START_KYXLSTU_ACTIVITY = 1003;
    public static final int FROM_ID_START_KYXLTEA_ACTIVITY = 1004;
    Button bt_plugin, bt_service, bt_plugin_stu, bt_plugin_tea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_plugin = findViewById(R.id.bt_plugin);
        bt_service = findViewById(R.id.bt_service);
        bt_plugin_stu = findViewById(R.id.bt_plugin_stu);
        bt_plugin_tea = findViewById(R.id.bt_plugin_tea);

        bt_plugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);//防止点击重入

                PluginManager pluginManager = InitApplication.getPluginManager();
                pluginManager.enter(MainActivity.this, FROM_ID_START_ACTIVITY, new Bundle(), new EnterCallback() {
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
                pluginManager.enter(MainActivity.this, FROM_ID_CALL_SERVICE, new Bundle(), new EnterCallback() {
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
                pluginManager.enter(MainActivity.this, FROM_ID_START_KYXLSTU_ACTIVITY, new Bundle(), new EnterCallback() {
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
                pluginManager.enter(MainActivity.this, FROM_ID_START_KYXLTEA_ACTIVITY, new Bundle(), new EnterCallback() {
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

    }
}