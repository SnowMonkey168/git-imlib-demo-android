package cn.rongcloud.demo_imlib_basic_android;

import android.content.Context;
import android.widget.Toast;

import cn.rongcloud.demo_imlib_basic_android.logger.Log;
import cn.rongcloud.demo_imlib_basic_android.message.CustomizeMessage;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.FileMessage;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by Beyond on 27/10/2016.
 */

class AppContext{
    private static final String TAG = "AppContext";
    private int mMessageId;

    private static AppContext appContext = new AppContext();
    private Context mContext;

    private AppContext() {
    }

    static AppContext getInstance() {
        return appContext;
    }

    public int getMessageId() {
        return mMessageId;
    }

    private void setMessageId(int mMessageId) {
        this.mMessageId = mMessageId;
    }

    void init(Context context) {
        mContext = context.getApplicationContext();
    }

    void registerReceiveMessageListener() {
        RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);
    }

    /**
     * 设置接收消息的监听器
     */
    private RongIMClient.OnReceiveMessageListener onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
        @Override
        public boolean onReceived(Message message, int i) {
            if (message.getContent() instanceof TextMessage) {
                Log.d(TAG, "收到文本消息: " + ((TextMessage) message.getContent()).getContent());
                Log.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
                setMessageRead(message); //设置收到的消息为已读消息
            } else if (message.getContent() instanceof ImageMessage) {
                Log.d(TAG, "收到图片消息, Uri --> " + ((ImageMessage) message.getContent()).getThumUri() + '\n');
            } else if (message.getContent() instanceof VoiceMessage) {
                Log.d(TAG, "收到语音消息,Uri --> " + ((VoiceMessage)message.getContent()).getUri());
                Log.d(TAG, "语音消息时长: " + ((VoiceMessage)message.getContent()).getDuration() + '\n');
            } else if (message.getContent() instanceof FileMessage) {
                Log.d(TAG, "服务端 Uri --> " + ((FileMessage)message.getContent()).getFileUrl() + '\n');
            } else if (message.getContent() instanceof CustomizeMessage) {
                Log.d(TAG, "成功发送自定义消息，它的时间戳: " + ((CustomizeMessage) message.getContent()).getSendTime());
                Log.d(TAG, "自定义消息的内容: " + ((CustomizeMessage) message.getContent()).getContent() + '\n');
            }
            setMessageId(message.getMessageId());
            return false;
        }
    };

    /**
     * 设置消息为已读消息
     */
    private void setMessageRead(Message message) {
        if (message.getMessageId() > 0) {
            io.rong.imlib.model.Message.ReceivedStatus status = message.getReceivedStatus();
            status.setRead();
            message.setReceivedStatus(status);
            RongIMClient.getInstance().setMessageReceivedStatus(message.getMessageId(), status, null);
            Toast.makeText(mContext, "该条消息已设置为已读", Toast.LENGTH_LONG).show();
        }
    }
}
