package com.example.application1;

public class Userinfo {
    private String username;
    private String email;
    private String phone;
    private String password;
    private String birth_date;
    private String gender;


    public Userinfo() {}

    public Userinfo(String username, String email, String password,String phone,String birth_date,String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone=phone;
        this.gender=gender;
        this.birth_date=birth_date;
    }


    public String getUsername() {
        return username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone= phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
