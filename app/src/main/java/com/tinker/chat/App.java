package com.tinker.chat;

import android.app.Application;
import android.net.Uri;

import com.tinker.chat.Utils.RongGenerate;
import com.tinker.chat.message.CustomMessage;
import com.tinker.chat.message.CustomMessageItemProvider;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by tiankui on 16/8/20.
 * 在App中进行初始化;
 * 调用RongIM.init(context);
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        RongIM.init(this);

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                UserInfo userInfo=new UserInfo(s,"HH", Uri.parse(RongGenerate.generateDefaultAvatar("Tinker", s)));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
                return null;
            }
        },true);

        RongIM.registerMessageType(CustomMessage.class);
        RongIM.registerMessageTemplate(new CustomMessageItemProvider());
    }
}
