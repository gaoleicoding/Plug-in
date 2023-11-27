/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tencent.shadow.sample.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tencent.shadow.core.manager.installplugin.InstalledPlugin;
import com.tencent.shadow.dynamic.host.EnterCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SamplePluginManager extends FastPluginManager {
    private final String TAG = "SamplePluginManager";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Context mCurrentContext;
    private String pluginZipPath, pluginKyxlZipPath;

    public SamplePluginManager(Context context) {
        super(context);
        mCurrentContext = context;
        pluginZipPath = mCurrentContext.getFilesDir() + "/plugin-debug.zip";
        pluginKyxlZipPath = mCurrentContext.getFilesDir() + "/plugin-kyxl.zip";

    }

    /**
     * @return PluginManager实现的别名，用于区分不同PluginManager实现的数据存储路径
     */
    @Override
    protected String getName() {
        return "test-dynamic-manager";
    }

    /**
     * @return 宿主中注册的PluginProcessService实现的类名
     */
    @Override
    protected String getPluginProcessServiceName(String partKey) {
        String serviceName = "";
        if (Constant.PART_KEY_PLUGIN_MAIN_APP.equals(partKey)) {
            serviceName = "com.tencent.shadow.sample.host.PluginProcessPPS";
        } else if (Constant.PART_KEY_PLUGIN_ANOTHER_APP.equals(partKey)) {
            serviceName = "com.tencent.shadow.sample.host.Plugin2ProcessPPS";
        }
        Log.d(TAG, "getPluginProcessServiceName partKey: " + partKey);
        Log.d(TAG, "getPluginProcessServiceName serviceName: " + serviceName);
        return serviceName;
    }

    @Override
    public void enter(final Context context, long fromId, Bundle bundle, final EnterCallback callback) {
        if (fromId == Constant.FROM_ID_START_ACTIVITY) {
            bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, pluginZipPath);
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, Constant.PART_KEY_PLUGIN_MAIN_APP);
            bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME, "com.tencent.shadow.sample.plugin.MainActivity");
            onStartActivity(context, bundle, callback);
        } else if (fromId == Constant.FROM_ID_START_ACTIVITY_2) {
            bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, pluginKyxlZipPath);
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, Constant.PART_KEY_PLUGIN_ANOTHER_APP);
            bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME, "com.fifedu.tsdx.ui.activity.login.SplashActivity");
            onStartActivity(context, bundle, callback);
        } else {
            Toast.makeText(context, "暂时找不到要打开的页面，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadViewToHost(final Context context, Bundle bundle) {
        Intent pluginIntent = new Intent();
        pluginIntent.setClassName(
                context.getPackageName(),
                "com.tencent.shadow.sample.plugin.app.lib.usecases.service.HostAddPluginViewService"
        );
        pluginIntent.putExtras(bundle);
        try {
            mPluginLoader.startPluginService(pluginIntent);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void onStartActivity(final Context context, Bundle bundle, final EnterCallback callback) {
        final String pluginZipPath = bundle.getString(Constant.KEY_PLUGIN_ZIP_PATH);
        final String partKey = bundle.getString(Constant.KEY_PLUGIN_PART_KEY);
        final String className = bundle.getString(Constant.KEY_ACTIVITY_CLASSNAME);
        if (className == null) {
            throw new NullPointerException("className == null");
        }
        final Bundle extras = bundle.getBundle(Constant.KEY_EXTRAS);

        if (callback != null) {
            final View view = LayoutInflater.from(mCurrentContext).inflate(R.layout.activity_load_plugin, null);
            callback.onShowLoadingView(view);
        }

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InstalledPlugin installedPlugin = installPlugin(pluginZipPath, null, true);
                    Log.d(TAG, "loadPlugin partKey: " + partKey);
                    loadPlugin(installedPlugin.UUID, partKey);
                    callApplicationOnCreate(partKey);
                    Intent pluginIntent = new Intent();
                    pluginIntent.setClassName(
                            context.getPackageName(),
                            className
                    );
                    if (extras != null) {
                        pluginIntent.replaceExtras(extras);
                    }
                    Intent intent = mPluginLoader.convertActivityIntent(pluginIntent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mPluginLoader.startActivityInPluginProcess(intent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (callback != null) {
                    callback.onCloseLoadingView();
                }
            }
        });
    }
}
