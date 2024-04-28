package com.tencent.shadow.sample.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.PluginManager;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlStuApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlTeaApplication;

class HostBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = "HostBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String processName = Utils.getProcessName(context);
        Log.e(TAG, "processName:" + processName);

        String key = intent.getStringExtra("PLUGIN_KEY");
        String value = intent.getStringExtra("PLUGIN_VALUE");
        if (Constant.FROM_PLUGIN_KEY_SEND_TO_HOST.equals(key)) {
            Toast.makeText(context, "宿主：" + value, Toast.LENGTH_SHORT).show();
        } else if (Constant.FROM_KYXLSTU_KEY_SEND_TO_HOST.equals(key)) {
            Toast.makeText(context, "宿主：" + value, Toast.LENGTH_SHORT).show();
        } else if (Constant.FROM_KYXLTEA_KEY_SEND_TO_HOST.equals(key)) {
            Toast.makeText(context, "宿主：" + value, Toast.LENGTH_SHORT).show();
        } else if (Constant.FROM_PLUGIN_KEY_SKIP_TO_STU_WEB.equals(key)) {
            PluginManager pluginManager = InitKyxlStuApplication.getPluginManager();
            Bundle bundle = new Bundle();
            bundle.putString("url", value);
            enterActivity(context, Constant.FROM_ID_START_KYXLSTU_WEBACTIVITY, bundle, pluginManager);
        } else if (Constant.FROM_PLUGIN_KEY_SKIP_TO_TEA_WEB.equals(key)) {
            PluginManager pluginManager = InitKyxlTeaApplication.getPluginManager();
            Bundle bundle = new Bundle();
            bundle.putString("url", value);
            enterActivity(context, Constant.FROM_ID_START_KYXLTEA_WEBACTIVITY, bundle, pluginManager);
        }
    }

    private void enterActivity(Context context, int flag, Bundle bundle, PluginManager pluginManager) {

        pluginManager.enter(context, flag, bundle, new EnterCallback() {
            @Override
            public void onShowLoadingView(View view) {
                Log.e(TAG, "onShowLoadingView");
            }

            @Override
            public void onCloseLoadingView() {
                Log.e(TAG, "onCloseLoadingView");
            }

            @Override
            public void onEnterComplete() {
                Log.e(TAG, "onEnterComplete");
            }
        });
    }
}
