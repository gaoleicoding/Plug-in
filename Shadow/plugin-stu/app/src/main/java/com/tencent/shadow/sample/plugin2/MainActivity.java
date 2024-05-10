package com.tencent.shadow.sample.plugin2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fifedu.lib_common_utils.dialog.BaseDialogCallBack;
import com.fifedu.lib_common_utils.dialog.BaseDialogUtils;
import com.fifedu.lib_common_utils.glide.GlideUtil;
import com.fifedu.lib_common_utils.permission.PermissionCallBack;
import com.fifedu.lib_common_utils.permission.PermissionUtils;


public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    //笔记中心
    private final String loadUrl = "https://note.fifedu.com/static/fiforal/note-center-static/v100/app/index.html#/noteData/listPage?userId=abf0c489bd17448ea28d81d780dfb897&schoolId=2000000026000000001&jtzy=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyZWFsTmFtZSI6Imo1Iiwic2Nob29sSWQiOiIyMDAwMDAwMDI2MDAwMDAwMDAxIiwiaXNzIjoiZmlmYWMiLCJpZCI6IjI4MTEwMDAwMjYwMDE3NjE4NzEiLCJleHAiOjE3MTQ4NzE0NTIsIm1lbWJlcklkIjoiYWJmMGM0ODliZDE3NDQ4ZWEyOGQ4MWQ3ODBkZmI4OTcifQ.ks3e1Fc2lxE42TPq7kinn9YrNS-AEd2KRUYBqMydC-s&domain=fifedu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_skip = findViewById(R.id.bt_skip);
        Button bt_interact_host = findViewById(R.id.bt_interact_host);
        Button bt_network_util = findViewById(R.id.bt_network_util);
        Button bt_dialog_util = findViewById(R.id.bt_dialog_util);
        Button bt_glide_util = findViewById(R.id.bt_glide_util);
        ImageView iv_photo = findViewById(R.id.iv_photo);
        Button bt_permission_util = findViewById(R.id.bt_permission_util);
        Button bt_interact_tea = findViewById(R.id.bt_interact_tea);

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
        bt_interact_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("HOST_BROADCAST_ACTION");
                intent.putExtra("PLUGIN_KEY", Constant.FROM_KYXLSTU_KEY_SEND_TO_HOST);
                intent.putExtra("PLUGIN_VALUE", Constant.FROM_KYXLSTU_VALUE_SEND_TO_HOST);
                sendBroadcast(intent);
            }
        });
        bt_interact_tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", loadUrl);
                startActivity(intent);
            }
        });
        bt_network_util.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getPersonalCenter();

            }
        });
        bt_glide_util.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posterUrl = "https://t7.baidu.com/it/u=1190388788,2005197677&fm=193";
                GlideUtil.loadImg(iv_photo, posterUrl);

            }
        });
        bt_dialog_util.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialogUtils.createCommonDialog(MainActivity.this, "确定要退出吗", "", "确定", "取消", BaseDialogUtils.DIALOG_SHOWTYPE_NORMAL, true, new BaseDialogCallBack() {

                    @Override
                    public void onConfirm() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        bt_permission_util.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.requestPermissions(MainActivity.this, false, new PermissionCallBack() {
                    @Override
                    public void onGranted() {

                    }

                }, PermissionUtils.PERMISSIONS_VIDEO_SIMPLE);

            }
        });
    }
}