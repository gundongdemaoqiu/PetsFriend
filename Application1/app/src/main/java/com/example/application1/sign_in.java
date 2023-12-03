package com.example.application1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_in extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user==null) {
//                startActivity(new Intent(this, home.class), REQUEST_LOGIN);
                startActivity(new Intent(sign_in.this, home.class));
            }else{
                // TODO after login
            }
        }
    };
//    private TextView mStatusTextView;
//    private TextView mDetailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.sign_in);

        Button create= findViewById(R.id.create);
        Button login=findViewById(R.id.login);
        login.setOnClickListener(this);
        create.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

//        create.setOnClickListener(v->startActivity(new Intent(this,LoginSuccess.class)),);
//        login.setOnClickListener(v->startActivity(new Intent(this,sign_up.class)));

    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
        if(currentUser != null){
            currentUser.reload();
        }
    }
//    private void updateUI(FirebaseUser user) {
////        hideProgressDialog();
//        if (user != null) {
//            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.login).setVisibility(View.GONE);
//            findViewById(R.id.forget).setVisibility(View.VISIBLE);
//        }
////        else {
////            mStatusTextView.setText(R.string.signed_out);
////            mDetailTextView.setText(null);
////
////            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
////            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
////        }
//    }


    @Override
    public void onClick(View view) {
      if (view.getId()==R.id.login){
          Intent intent=new Intent(this, home.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
      }
      if (view.getId()==R.id.create){
          Intent intent=new Intent(this, sign_up.class);
          startActivity(intent);
      }
    }

}