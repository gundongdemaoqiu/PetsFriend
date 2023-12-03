package com.example.application1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.core.Tag;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangProf extends AppCompatActivity implements View.OnClickListener {
    private EditText Phone;
    private EditText name;
    Button  go_back;
    Button  confirm;
    String change_name;
    String change_phone;
    FirebaseAuth mAuth;
    DatabaseReference UsersDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_prof);
        Phone=findViewById(R.id.Acti_change_phone);
        name=findViewById(R.id.Acti_change_name);
        go_back=findViewById(R.id.go_back);
        go_back.setOnClickListener(this);
        confirm=findViewById(R.id.Confirm_change);
        confirm.setOnClickListener(this);
        mAuth= FirebaseAuth.getInstance();

        UsersDb= FirebaseDatabase.getInstance().getReference().child("Users");


    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.Confirm_change){
            String currentUid=mAuth.getCurrentUser().getUid().toString();
            DatabaseReference currentUserRef=UsersDb.child(currentUid);

            change_name=name.getText().toString();
            change_phone=Phone.getText().toString();
            currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        Log.d(TAG,"currentuid22: "+currentUid);
                        if (!change_name.equals("")){
                            currentUserRef.child("name").setValue(change_name);
                        }
                        if (!change_phone.equals("")){
                            currentUserRef.child("Phone").setValue(change_phone);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (view.getId()==R.id.go_back){
            Intent intent=new Intent(this, AccountInfo.class);
            startActivity(intent);
        }

    }
}