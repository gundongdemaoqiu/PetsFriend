package com.example.application1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, CompoundButton.OnCheckedChangeListener {
    Button create;
    Button btn_getdate;
    FirebaseAuth mAuth;
    EditText username;
    EditText password;
    EditText expectation;  //  not used!
    EditText Phone;

    String test_expectation;
    EditText email;
    RadioGroup mRadiogroup;
    RadioGroup mRadiogroup_gender;
    DatabaseReference UserDb;

    TextView bir_date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mAuth=FirebaseAuth.getInstance();
        UserDb = FirebaseDatabase.getInstance().getReference();
        username=findViewById(R.id.username);
        password=findViewById(R.id.my_password);
        email=findViewById(R.id.my_email);
        mRadiogroup=findViewById(R.id.my_radio);
        mRadiogroup_gender=findViewById(R.id.my_radio_gender);
        create = findViewById(R.id.sign_up_create);
        btn_getdate=findViewById(R.id.btn_getdate);
        Phone=findViewById(R.id.my_phone);
        btn_getdate.setOnClickListener(this);
        CheckBox agreement =findViewById(R.id.sign_up_acknowledge);
//        agreement.setOnClickListener(this);
        agreement.setOnCheckedChangeListener(this);
        create.setOnClickListener(this);
        bir_date=findViewById(R.id.birth_date);






    }

    @Override
    public void onClick(View view) {
//        if (view.getId()==R.id.acknow) {
//            create.setEnabled(true);
//        }
        if (view.getId()==R.id.sign_up_create){
            createAccount();
        }
        if (view.getId()==R.id.btn_getdate){
            Calendar calendar=Calendar.getInstance();

            DatePickerDialog dialog=new DatePickerDialog(this,  this,
                    calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }


    }


    private void createAccount() {
        String test_username=username.getText().toString();
        String test_Email = email.getText().toString().trim();
        String test_Password = password.getText().toString();
        String test_date= bir_date.getText().toString();
        String test_phone= Phone.getText().toString();
        int selectId = mRadiogroup.getCheckedRadioButtonId();

        int selectId_gender = mRadiogroup.getCheckedRadioButtonId();
        final RadioButton radioButton = findViewById(selectId);
        final RadioButton radioButton_gender=findViewById(selectId_gender);

        if (radioButton.getText() == null) {
            return;
        }
        if (radioButton_gender.getText() == null) {
            return;
        }
        if (test_username.isEmpty()) {
            username.setError("User name is required!");  // 提示错误信息
            username.requestFocus();   // 红线标识需要
            return;
        }
        if (test_date.isEmpty()) {
            bir_date.setError("Birth date is required!");  // 提示错误信息
            bir_date.requestFocus();   // 红线标识需要
            return;
        }


        if (test_Email.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (test_phone.isEmpty()) {
            Phone.setError("Email is required!");
            Phone.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(test_Email).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }


        if (test_Password.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if (test_Password.length() < 6) {
            password.setError("Min password length should be 6 characters!");
            password.requestFocus();
            return;
        }
        // [START create_user_with_email]
//        String test=user.getText().toString();
        mAuth.createUserWithEmailAndPassword(test_Email, test_Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid= user.getUid().toString();
                            Map userInfo = new HashMap<>();
                            userInfo.put("name", test_username);
                            userInfo.put("email", test_Email);
                            userInfo.put("password", test_Password);
                            userInfo.put("Phone", test_phone);
                            userInfo.put("Birthdate",test_date);
                            userInfo.put("breed", radioButton.getText().toString());   // radio buttion来选择宠物类型
                            userInfo.put("Pets gender", radioButton_gender.getText().toString());   // radio buttion来选择宠物性别
                            userInfo.put("profileImageUrl", "default");   // 这里默认使用的是我们自己的图片，在alteractivity里面用到了
                            // 先默认放自己的图片，后面自己再去该
                            // 不过也可以用if判断这一步直接直接上传图片

                            //这里没有phone

                            UserDb.child("Users").child(uid).updateChildren(userInfo);   // 自动更新child
                            Intent intent = new Intent(sign_up.this, AccountInfo.class);
                            // intent.putExtra("name", editName);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(sign_up.this, "Account already exist.", Toast.LENGTH_SHORT).show();
                            email.setError("Account already exits");
                            email.requestFocus();
                            return;

                        }
                    }
                });
        // [END create_user_with_email]
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
       String desc=String.format("%d/%d/%d",year,month+1,dayofMonth);
       bir_date.setText(desc);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked){
            create.setEnabled(true);
        }
        else {
            create.setEnabled(false);
        }
    }
}