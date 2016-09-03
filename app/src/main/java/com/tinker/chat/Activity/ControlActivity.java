package com.tinker.chat.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tinker.chat.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Created by tiankui on 16/8/31.
 */
public class ControlActivity extends Activity implements View.OnClickListener {

    private Button mStartPrivateChatButton;
    private Button mStartConversationListButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mStartPrivateChatButton=(Button) findViewById(R.id.ac_bt_start_private_chat);
        mStartConversationListButton=(Button)findViewById(R.id.ac_bt_start_conversation_list);

        mStartPrivateChatButton.setOnClickListener(this);
        mStartConversationListButton.setOnClickListener(this);
    }

    /**
     * 开启私聊和会话列表中的<intent-filter><data host="包名"></data></intent-filter>
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view==mStartPrivateChatButton){
            RongIM.getInstance().startPrivateChat(ControlActivity.this,"10000","私聊");
            TextMessage textMessage=TextMessage.obtain("hello");
            RongIM.getInstance().insertMessage(Conversation.ConversationType.PRIVATE, "10000", "10001", textMessage, new RongIMClient.ResultCallback<Message>() {
                @Override
                public void onSuccess(Message message) {
                    RongIM.getInstance().setMessageReceivedStatus(message.getMessageId(), new Message.ReceivedStatus(2), new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            Log.e("LogInActivity","Success+++");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                    Log.e("LogInActivity","Success");
                }
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });


//
//            CustomMessage message = CustomMessage.obtain("呵呵", "1.5",
//                    "","10000","");
//            message.setExtra("");
//            RongIMClient.getInstance().insertMessage(Conversation.ConversationType.PRIVATE, "10000","10001", message, new RongIMClient.ResultCallback<Message>() {
//                @Override
//                public void onSuccess(Message message) {
//                    RongIMClient.getInstance().setMessageSentStatus(message.getMessageId(), Message.SentStatus.RECEIVED, new RongIMClient.ResultCallback<Boolean>() {
//                        @Override
//                        public void onSuccess(Boolean aBoolean) {
//                            Log.e("ControlActivity","成功");
//                        }
//                        @Override
//                        public void onError(RongIMClient.ErrorCode errorCode) {
//                        }
//                    });
//                }
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//                }
//            });
        }
        /**
         * 开启会话列表,调用startConversationList(context);
         * 较推荐的方法是调用startConversationList(context,map);其中map是会话类型的集合;
         * 如果使用startActivity,那么会话列表中没有会话;
         */
        if(view==mStartConversationListButton){
            if(RongIM.getInstance()!=null){
                RongIM.getInstance().startConversationList(ControlActivity.this);
            }
        }
    }
}
