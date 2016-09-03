package com.tinker.chat.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.tinker.chat.R;

/**
 * Created by tiankui on 16/8/31.
 * 对应的Activity要使用FragmentActivity;
 * Fragment中最好加上id;
 */
public class ConversationListActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
    }
}
