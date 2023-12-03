package com.example.application1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFrag extends Fragment implements View.OnClickListener {


    FirebaseAuth mAuth;
    TextView name;

    TextView email;
    TextView gender;
    TextView phone;
    TextView birth_date;
    Button sign_out;
    Button Change_profile;
    public ProfileFrag() {
        // Required empty public constructor
    }

    public static ProfileFrag newInstance(String param1, String param2) {
        ProfileFrag fragment = new ProfileFrag();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        String currentId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(currentId);
//        mAuth = FirebaseAuth.getInstance();
//        String uid= mAuth.getCurrentUser().getUid();
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        name= view.findViewById(R.id.display_name);
        email= view.findViewById(R.id.display_email);
        birth_date=view.findViewById(R.id.display_birth_date);
        gender=view.findViewById(R.id.display_gender);
        phone=view.findViewById(R.id.display_phone);


        sign_out=view.findViewById(R.id.prof_sign_out);
        sign_out.setOnClickListener(this);
        Change_profile=view.findViewById(R.id.profile_Change_profile);
        Change_profile.setOnClickListener(this);
////        TextView name= view.findViewById(R.id.display_name);
//
//
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String getName = snapshot.child("name").getValue().toString();
                name.setText(getName);               // 这是唯一一个，利用数据库修改的profile个人信息。
                String getGender = snapshot.child("Pets gender").getValue().toString();
                gender.setText(getGender);               // 这是唯一一个，利用数据库修改的profile个人信息。
                String getBirthdate = snapshot.child("Birthdate").getValue().toString();
                birth_date.setText(getBirthdate);               // 这是唯一一个，利用数据库修改的profile个人信息。
                String getEmail = snapshot.child("email").getValue().toString();
                email.setText(getEmail);               // 这是唯一一个，利用数据库修改的profile个人信息。
                String getPhone = snapshot.child("Phone").getValue().toString();
                phone.setText(getPhone);               // 这是唯一一个，利用数据库修改的profile个人信息。
//                String getEmail=snapshot.child("email").getValue().toString();
//                email.setText(getEmail);
//                String Phone=snapshot.child("email").getValue().toString();
//                email.setText(getEmail);
                // 还可以补充pets gender等等
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.prof_sign_out){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.profile_Change_profile){
            Intent intent = new Intent(getActivity(), ChangProf.class);
            startActivity(intent);

        }
    }

   
}