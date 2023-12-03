package com.example.application1;

import android.widget.LinearLayout;

import java.util.Date;

public class ChatMessage {


    public static String messageUser;
    public static String chatWith;
    private static String messageText;
    private static long messageTime;


    public ChatMessage() {
        this.messageText = "";
        this.messageUser = "";
        this.chatWith="";
        // Initialize to current time
        messageTime = new Date().getTime();
//        public ChatMessage(){
//
//    }
    }

//    public ChatMessage(){
//
//    }
//
//    public String getMessageText() {
//        return messageText;
//    }
//
//    public void setMessageText(String messageText) {
//        this.messageText = messageText;
//    }
//
//    public String getMessageUser() {
//        return messageUser;
//    }
//
//    public void setMessageUser(String messageUser) {
//        this.messageUser = messageUser;
//    }
//
//    public long getMessageTime() {
//        return messageTime;
//    }
//
//    public void setMessageTime(long messageTime) {
//        this.messageTime = messageTime;
//    }
}
