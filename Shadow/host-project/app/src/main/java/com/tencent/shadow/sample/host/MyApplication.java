package com.tencent.shadow.sample.host;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlStuApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitKyxlTeaApplication;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        InitApplication.onApplicationCreate(this);
        InitKyxlStuApplication.onApplicationCreate(this);
        InitKyxlTeaApplication.onApplicationCreate(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            webviewSetPath(this);
        }
    }

    private static final String TAG = "MyApplication";

    @RequiresApi(api = 28)
    public void webviewSetPath(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName(context);

            if (!getApplicationContext().getPackageName().equals(processName)) {//判断不等于默认进程名称
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    public String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }

}
