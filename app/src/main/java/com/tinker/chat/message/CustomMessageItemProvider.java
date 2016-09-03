package com.tinker.chat.message;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tinker.chat.R;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.ConversationKey;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.ArraysDialogFragment;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.RichContentMessage;

/**
 * Created by tiankui on 16/8/31.
 */
@ProviderTag( messageContent = CustomMessage.class , showProgress = false,showPortrait = false,centerInHorizontal = true)
public class CustomMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomMessage> {
    private Context context;
    @Override
    public View newView(Context context, ViewGroup group) {
        this.context=context;
        CustomMessageItemProvider.Hd hd = new CustomMessageItemProvider.Hd();
        View view = LayoutInflater.from(context).inflate(R.layout.insert_msg_layout, null);
        hd.tvSend= (TextView) view.findViewById(R.id.tv_send);
        hd.title= (TextView) view.findViewById(R.id.tv_name);
        hd.content= (TextView) view.findViewById(R.id.tv_price);
        hd.imgHead= (ImageView) view.findViewById(R.id.img_head);
        hd.mLayout= (LinearLayout) view.findViewById(R.id.rlt_goods);
        view.setTag(hd);
        return view;
    }
    @Override
    public void bindView(View v, int position, final CustomMessage content, UIMessage message) {
        CustomMessageItemProvider.Hd hd = (CustomMessageItemProvider.Hd) v.getTag();
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = dm.widthPixels;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        v.setLayoutParams(params);
        hd.title.setText(content.getTitle());
        hd.content.setText(content.getContent());
//        hd.imgHead.setImageURI(Uri.parse(content.getImgUrl() == null ? null : content.getImgUrl()));
        hd.tvSend.setVisibility(View.VISIBLE);
        hd.tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichContentMessage message = RichContentMessage.obtain(content.getTitle(), content.getContent(),
                        content.getImgUrl());
                message.setExtra(content.getMessageExtra());
//                IM.getInstance().sendRichContentMessage(content.getTargetId(), message, new RongIMClient.SendMessageCallback() {
//                    @Override
//                    public void onSuccess(Integer integer) {
//                        Toast.makeText(context,"发送成功",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
//                        Toast.makeText(context,"发送失败",Toast.LENGTH_LONG).show();
//                    }
//                });
            }

        });
    }

    @Override
    public Spannable getContentSummary(CustomMessage customMessage) {
        return new SpannableString(RongContext.getInstance().getResources().getString(io.rong.imkit.R.string.rc_message_content_rich_text));
    }
    @Override
    public void onItemClick(View view, int i, CustomMessage customMessage, UIMessage uiMessage) {
    }

    @Override
    public void onItemLongClick(View view, int i, CustomMessage customMessage, final UIMessage message) {
        String name = null;
        if(!message.getConversationType().getName().equals(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName()) && !message.getConversationType().getName().equals(Conversation.ConversationType.PUBLIC_SERVICE.getName())) {
            UserInfo items1 = message.getUserInfo();
            if(items1 == null) {
                items1 = RongContext.getInstance().getUserInfoFromCache(message.getSenderUserId());
            }
            if(items1 != null) {
                name = items1.getName();
            }
        } else {
            ConversationKey items = ConversationKey.obtain(message.getTargetId(), message.getConversationType());
//            PublicServiceProfile info = (PublicServiceProfile)RongContext.getInstance().getPublicServiceInfoCache().get(items.getKey());
//            if(info != null) {
//                name = info.getName();
//            }
        }
        String[] items2 = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete)};
        ArraysDialogFragment.newInstance(name, items2).setArraysDialogItemListener(new ArraysDialogFragment.OnArraysDialogItemListener() {
            public void OnArraysDialogItemClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    RongIM.getInstance().getRongIMClient().deleteMessages(new int[]{message.getMessageId()}, (RongIMClient.ResultCallback)null);
                }
            }
        }).show(((FragmentActivity)view.getContext()).getSupportFragmentManager());

    }
    class Hd{
        ImageView imgHead;
        TextView title,content,tvSend;
        LinearLayout mLayout;
        Hd(){}
    }
}
/**调用insertMessage方法
        CustomMessage message = CustomMessage.obtain(Goods.getName(), Goods.getPrice(),
        Goods.getUrl(),targetId,tag+extra);
        message.setExtra(extra);
        RongIMClient.getInstance().insertMessage(Conversation.ConversationType.PRIVATE, targetId,String.valueOf(myuserId), message, new RongIMClient.ResultCallback<Message>() {
@Override
public void onSuccess(Message message) {
// RongIMClient.getInstance().setMessageSentStatus(message.getMessageId(), Message.SentStatus.RECEIVED );
        RongIMClient.getInstance().setMessageSentStatus(message.getMessageId(), Message.SentStatus.RECEIVED, new RongIMClient.ResultCallback<Boolean>() {
@Override
public void onSuccess(Boolean aBoolean) {
        }
@Override
public void onError(RongIMClient.ErrorCode errorCode) {
        }
        });
        }
@Override
public void onError(RongIMClient.ErrorCode errorCode) {
// RongIMClient.getInstance().setMessageSentStatus(message.getMessageId(), Message.SentStatus.RECEIVED);
        }
        });
 */
