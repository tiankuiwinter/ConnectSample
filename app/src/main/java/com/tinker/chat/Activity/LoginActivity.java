package com.tinker.chat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tinker.chat.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**给定userId,userName,portraitUri(这里可以只给定userId,即可生成token);
     * userId=2016,userName=Tinker,portraitUri="https://www.google.com.sg/search?q=%E7%BE%8E%E5%A5%B3&biw=1280&bih=680&tbm=isch&imgil=Uk2uAbQhFeBcjM%253A%253Bms9hHeM3-Arh7M%253Bhttps%25253A%25252F%25252Fi.4meee.com%25252Farticles%25252Fview%25252F168220&source=iu&pf=m&fir=Uk2uAbQhFeBcjM%253A%252Cms9hHeM3-Arh7M%252C_&usg=__D3tCWcAnFbI7Z6Xv9f_kR52wolM%3D&ved=0ahUKEwjI4-jvgs_OAhUZT48KHV_xAb0QyjcIJw&ei=hcq3V4iNGZmevQTf4ofoCw#imgrc=7wmYdUQh1Lrm5M%3A";
     * 在API调试中生成token,如下;
     * 然后在IMKit的manifest中进行配置appkey(注意区分开发环境和生产环境,刚开始一般为开发环境)
     */

    private static final String TOKEN="nM2WjtH6b58ITam3Te6O2DpS38uxF4AKNtGem1XsNxjpKs3JN2u6pN/y3o+BXsrFjRzXkv5Dv2gPEjiVFsFw4w==";
    private Button mLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton= (Button) findViewById(R.id.ac_bt_log_in);

        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        RongIM.connect(TOKEN, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String s) {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }

            @Override
            public void onTokenIncorrect() {

            }
        });
    }
}
