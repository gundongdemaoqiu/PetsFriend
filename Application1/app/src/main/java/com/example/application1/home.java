package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class home extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
//        ImageButton profile=findViewById(R.id.profile);
//        ImageButton home=findViewById(R.id.home);
//        ImageButton friend=findViewById(R.id.Friend);
//        ImageButton notification=findViewById(R.id.notification);
//        profile.setOnClickListener(this);
//        friend.setOnClickListener(this);
//        notification.setOnClickListener(this);
    }

//    @Override
    public void onClick(View view) {
//        if (view.getId()==R.id.profile){
//            Intent intent=new Intent(this, profile.class);
//            startActivity(intent);
//        }
//        if (view.getId()== R.id.Friend){
//            Intent intent=new Intent(this, friend.class);
//            startActivity(intent);
//        }
//        if (view.getId()== R.id.notification){
//            Intent intent=new Intent(this, Notification.class);
//            startActivity(intent);
//        }


    }
}