package com.example.application1;


public class ChatObject {

    //private String userId, name, message, date,time;
    private String name, message, date,time;
    private Boolean currentUser;

//    public ChatObject(String userId, String name, String message, String date, String time) {
//        this.userId = userId;
//        this.name = name;
//        this.message = message;
//        this.date = date;
//        this.time = time;
//    }

    public ChatObject(Boolean currentUser, String name, String message, String date, String time) {
        this.currentUser = currentUser;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    //  public String getUserId() {
//        return userId;
//    }

    public Boolean getCurrentUser() {
        return currentUser;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }

}