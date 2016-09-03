package com.tinker.chat.message;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by tiankui on 16/8/31.
 */
@MessageTag(value = "app:custom", flag =MessageTag.ISPERSISTED)
public class CustomMessage extends MessageContent {
    private String content;//消息属性，可随意定义
    private String title;
    private String imgUrl;
    private String url = "";
    private String extra=null ;
    private String targetId;
    private String messageExtra;
    public String getMessageExtra() {
        return messageExtra;
    }
    public void setMessageExtra(String messageExtra) {
        this.messageExtra = messageExtra;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public CustomMessage(String title, String content, String imgUrl, String targetId, String messageExtra){
        this.title=title;
        this.content=content;
        this.imgUrl=imgUrl;
        this.targetId=targetId;
        this.messageExtra=messageExtra;
    }
    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("title", this.getExpression(this.getTitle()));
            jsonObj.put("content", this.getExpression(this.getContent()));
            jsonObj.put("imageUri", this.getImgUrl());
            jsonObj.put("url", this.getUrl());
            jsonObj.put("extra", this.getExtra());
            jsonObj.put("messageExtra", this.getMessageExtra());
            jsonObj.put("targetId", this.getTargetId());
            if(this.getJSONUserInfo() != null) {
                jsonObj.putOpt("user", this.getJSONUserInfo());
            }
        } catch (JSONException var4) {
            Log.e("JSONException", var4.getMessage());
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return new byte[0];
        }
    }

    public CustomMessage(byte[] data) {
        String jsonStr = new String(data);

        try {
            JSONObject e = new JSONObject(jsonStr);
            this.title = e.optString("title");
            this.content = e.optString("content");
            this.imgUrl = e.optString("imageUri");
            this.url = e.optString("url");
            this.extra = e.optString("extra");
            this.targetId = e.optString("targetId");
            this.messageExtra = e.optString("messageExtra");
            if(e.has("user")) {
                this.setUserInfo(this.parseJsonToUserInfo(e.getJSONObject("user")));
            }
        } catch (JSONException var4) {
            Log.e("JSONException", var4.getMessage());
        }

    }

    public CustomMessage(Parcel in) {
        this.title = ParcelUtils.readFromParcel(in);
        this.content = ParcelUtils.readFromParcel(in);
        this.imgUrl = ParcelUtils.readFromParcel(in);
        this.url = ParcelUtils.readFromParcel(in);
        this.extra = ParcelUtils.readFromParcel(in);
        this.messageExtra = ParcelUtils.readFromParcel(in);
        this.targetId = ParcelUtils.readFromParcel(in);
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
//这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<CustomMessage> CREATOR = new Creator<CustomMessage>() {

        @Override
        public CustomMessage createFromParcel(Parcel source) {
            return new CustomMessage(source);
        }

        @Override
        public CustomMessage[] newArray(int size) {
            return new CustomMessage[size];
        }
    };

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest 对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.title);
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.imgUrl);
        ParcelUtils.writeToParcel(dest, this.url);
        ParcelUtils.writeToParcel(dest, this.extra);
        ParcelUtils.writeToParcel(dest, this.messageExtra);
        ParcelUtils.writeToParcel(dest, this.targetId);
        ParcelUtils.writeToParcel(dest, getUserInfo());
//这里可继续增加你消息的属性
    }

    public static CustomMessage obtain(String title, String content, String imageUrl, String targetId, String messageExtra) {
        return new CustomMessage(title, content, imageUrl, targetId,messageExtra);
    }
    private String getExpression(String content) {
        Pattern pattern = Pattern.compile("/[/u([0-9A-Fa-f]+)/]");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(sb, this.toExpressionChar(matcher.group(1)));
        }
        matcher.appendTail(sb);
        Log.d("getExpression--", sb.toString());
        return sb.toString();
    }
    private String toExpressionChar(String expChar) {
        int inthex = Integer.parseInt(expChar, 16);
        return String.valueOf(Character.toChars(inthex));
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
