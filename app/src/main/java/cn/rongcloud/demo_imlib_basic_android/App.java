package cn.rongcloud.demo_imlib_basic_android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import cn.rongcloud.demo_imlib_basic_android.message.CustomizeMessage;
import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.message.FileMessage;

/**
 * Created by Beyond on 16/10/2016.
 */

public class App extends Application {
    /**
     * 由"开发者平台"提供的 App Key，
     */
    private static final String APP_KEY = "82hegw5uh8ijx";

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongIMClient.init(this, APP_KEY);
        }

        /**
         * 用于自定义消息的注册, 注册后方能正确识别自定义消息, 建议在init后及时注册，保证自定义消息到达时能正确解析。
         */
        try {
            RongIMClient.registerMessageType(FileMessage.class);
            RongIMClient.registerMessageType(CustomizeMessage.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }

        AppContext.getInstance().init(getApplicationContext());
        AppContext.getInstance().registerReceiveMessageListener();
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid)
                return appProcess.processName;
        }
        return null;
    }
}
