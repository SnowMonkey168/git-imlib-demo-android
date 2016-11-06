package cn.rongcloud.demo_imlib_basic_android.fakeserver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.rongcloud.demo_imlib_basic_android.utils.HttpUtil;

/**
 * 模拟服务器
 */

public class FakeServer {
    /**
     * 由"开发者平台"提供的 App Key 和 App Secret，
     */
    private static final String APP_KEY = "82hegw5uh8ijx";
    private static final String APP_SECRET = "3i2GToscq4";

    /**
     * 获取融云Token, 通过调用融云ServerApi获得
     */
    public static void getToken(String userId, String userName, String userPortrait, HttpUtil.OnResponse callback) {
        try {
            String register_data = "userId=" + URLEncoder.encode(userId,"UTF-8")
                    + "&name=" + URLEncoder.encode(userName,"UTF-8")
                    + "&portraitUri=" + URLEncoder.encode(userPortrait,"UTF-8");
            HttpUtil httpUtil = new HttpUtil();
            httpUtil.setOnResponse(callback);
            httpUtil.post(APP_KEY, APP_SECRET, register_data, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
