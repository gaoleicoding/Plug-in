package com.tencent.shadow.sample.manager;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tencent.shadow.core.manager.installplugin.InstalledPlugin;
import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.loader.PluginServiceConnection;
import com.tencent.shadow.sample.plugin.IMyAidlInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SamplePluginManager extends FastPluginManager {
    private final String TAG = "SamplePluginManager";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Context mCurrentContext;
    private String pluginZipPath, pluginKyxlStuZipPath, pluginKyxlTeaZipPath;

    public SamplePluginManager(Context context) {
        super(context);
        mCurrentContext = context;
        pluginZipPath = mCurrentContext.getExternalFilesDir("shadow") + "/plugin-debug.zip";
        pluginKyxlStuZipPath = mCurrentContext.getExternalFilesDir("shadow") + "/plugin-kyxlstu.zip";
        pluginKyxlTeaZipPath = mCurrentContext.getExternalFilesDir("shadow") + "/plugin-kyxltea.zip";
    }

    /**
     * @return PluginManager实现的别名，用于区分不同PluginManager实现的数据存储路径
     */
    @Override
    protected String getName() {
        return "sample-manager";
    }

    /**
     * @return 宿主中注册的PluginProcessService实现的类名
     */
    @Override
    protected String getPluginProcessServiceName(String partKey) {
        String serviceName = "";
        if (Constant.PART_KEY_PLUGIN_SAMPLE.equals(partKey)) {
            serviceName = "com.tencent.shadow.sample.introduce_shadow_lib.MainPluginProcessService";
        } else if (Constant.PART_KEY_PLUGIN_KYXLSTU.equals(partKey)) {
            serviceName = "com.tencent.shadow.sample.introduce_shadow_lib.KyxlStuPluginProcessService";
        } else if (Constant.PART_KEY_PLUGIN_KYXLTEA.equals(partKey)) {
            serviceName = "com.tencent.shadow.sample.introduce_shadow_lib.KyxlTeaPluginProcessService";
        }
        Log.d(TAG, "serviceName: " + serviceName);
        return serviceName;
    }

    @Override
    public void enter(final Context context, long fromId, Bundle bundle, final EnterCallback callback) {
        if (fromId == Constant.FROM_ID_START_ACTIVITY) {
            bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, pluginZipPath);
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, Constant.PART_KEY_PLUGIN_SAMPLE);
            bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME, "com.tencent.shadow.sample.plugin.MainActivity");
            onStartActivity(context, bundle, callback);
        } else if (fromId == Constant.FROM_ID_CALL_SERVICE) {
            bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, pluginZipPath);
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, Constant.PART_KEY_PLUGIN_SAMPLE);
            bundle.putString(Constant.KEY_SERVICE_CLASSNAME, "com.tencent.shadow.sample.plugin.MyService");
            callPluginService(context, bundle);
        } else if (fromId == Constant.FROM_ID_START_KYXLSTU_ACTIVITY) {
            bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, pluginKyxlStuZipPath);
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, Constant.PART_KEY_PLUGIN_KYXLSTU);
            bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME, "com.tencent.shadow.sample.plugin2.MainActivity");
            onStartActivity(context, bundle, callback);
        } else if (fromId == Constant.FROM_ID_START_KYXLTEA_ACTIVITY) {
            bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, pluginKyxlTeaZipPath);
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, Constant.PART_KEY_PLUGIN_KYXLTEA);
            bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME, "com.tencent.shadow.sample.plugin3.MainActivity");
            onStartActivity(context, bundle, callback);
        } else {
            Toast.makeText(context, "暂时找不到要打开的页面，请稍后再试", Toast.LENGTH_SHORT).show();
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
                    InstalledPlugin installedPlugin
                            = installPlugin(pluginZipPath, null, true);//这个调用是阻塞的
                    Intent pluginIntent = new Intent();
                    pluginIntent.setClassName(
                            context.getPackageName(),
                            className
                    );
                    if (extras != null) {
                        pluginIntent.replaceExtras(extras);
                    }

                    startPluginActivity(context, installedPlugin, partKey, pluginIntent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (callback != null) {
                    Handler uiHandler = new Handler(Looper.getMainLooper());
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onCloseLoadingView();
                            callback.onEnterComplete();
                        }
                    });
                }
            }
        });
    }

    private void callPluginService(final Context context, Bundle bundle) {
        final String pluginZipPath = bundle.getString(Constant.KEY_PLUGIN_ZIP_PATH);
        final String partKey = bundle.getString(Constant.KEY_PLUGIN_PART_KEY);
        final String className = bundle.getString(Constant.KEY_SERVICE_CLASSNAME);

        Intent pluginIntent = new Intent();
        pluginIntent.setClassName(context.getPackageName(), className);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InstalledPlugin installedPlugin
                            = installPlugin(pluginZipPath, null, true);//这个调用是阻塞的

                    loadPlugin(installedPlugin.UUID, partKey);

                    Intent pluginIntent = new Intent();
                    pluginIntent.setClassName(context.getPackageName(), className);

                    boolean callSuccess = mPluginLoader.bindPluginService(pluginIntent, new PluginServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
                            try {
                                String s = iMyAidlInterface.basicTypes(1, 2, true, 4.0f, 5.0, "6");
                                Log.i("SamplePluginManager", "iMyAidlInterface.basicTypes : " + s);
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName componentName) {
                            throw new RuntimeException("onServiceDisconnected");
                        }
                    }, Service.BIND_AUTO_CREATE);

                    if (!callSuccess) {
                        throw new RuntimeException("bind service失败 className==" + className);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
