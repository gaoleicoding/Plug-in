package com.tencent.shadow.sample.host;

import android.app.ActivityManager;
import android.content.Context;

import com.fifedu.lib_common_utils.log.LogUtils;
import com.fifedu.lib_common_utils.net.NetWorkCallback;
import com.fifedu.lib_common_utils.net.NetWorkUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {
    private final static String TAG = "Utils";

    public static String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }

    public static void getPersonalCenter() {
        String studentPrivacyUrl = "https://www.fifedu.com/iplat-api/kyxl/userinfo/getPersonalCenter";
        LinkedHashMap<String, String> mParams = new LinkedHashMap<>();
        mParams.put("appCode", "kyxl_student_app");
        StringBuilder tempUrl = new StringBuilder(studentPrivacyUrl + "?");
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            tempUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        NetWorkUtils.instance().send(NetWorkUtils.METHOD_GET, mParams, tempUrl.toString(), new NetWorkCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.d(TAG, "host getPersonalCenter result: " + result);

            }

            @Override
            public void onFailed(String error) {
                LogUtils.e(TAG, "host getPersonalCenter error: " + error);
            }
        });
    }
}
