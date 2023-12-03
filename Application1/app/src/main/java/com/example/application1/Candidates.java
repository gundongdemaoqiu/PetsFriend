package com.example.application1;

public class Candidates {
    private String uid;
    private String photoUrl;
    private String pets;
    private String gender;
    private String name;
    public Candidates() {}

    public Candidates(String uid, String photoUrl, String pets,String gender,String name) {
        this.uid = uid;
        this.photoUrl = photoUrl;
        this.pets = pets;
        this.gender = gender;
        this.name=name;

    }
    public String getUid(){
        return this.uid;
    }
    public String getPhotoUrl(){
        return this.photoUrl;
    }

    public String getPets(){
        return this.pets;
    }
    public String getGender(){
        return this.gender;
    }
    public String getName(){
        return this.name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
