/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.application1;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PlayGamesAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private TextView hi;
    private EditText email;
    private EditText pass;
    private EditText name;
    private EditText reset;
    Button btn_translation;
    Button autofill;
    Button profile;
    Button signin;
    Button create;
    Button accessability;
    private DatabaseReference UsersDb;
//    public static class User {
//
//
//        public String date_of_birth;
//        public String full_name;
//        public String nickname;
//
//        public User( String fullName) {
//            this.full_name=fullName;
//        }
//
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth=FirebaseAuth.getInstance();
        UsersDb= FirebaseDatabase.getInstance().getReference().child("Users");


        ///// 用来替代setvalueAscyn的问题  使用onCompleteListener来实时更新
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        Map<String, User> users = new HashMap<>();
//        users.put("alanisawesome", new User("Alan Turing"));
//        users.put("gracehop", new User("Grace Hopper" ));
//
//        DatabaseReference rootRef = database.getReference();
//        DatabaseReference usersRef = rootRef.child("users");
//        usersRef.child("firstUser").setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d("TAG", "User added successfully.");
//                } else {
//                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
//                }
//            }
//        });



//
////        myRef.setValue("Hello");
//        usersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });



        email = findViewById(R.id.sign_in_email);
        pass=findViewById(R.id.pass);   //// rember the length should larger than zero, or function crush!!!
//        name=findViewById(R.id.name);
        hi = findViewById(R.id.hi);
//        reset=findViewById(R.id.reset_pass);
        FirebaseUser user = mAuth.getCurrentUser();

        accessability=findViewById(R.id.accessability);
        accessability.setOnClickListener(this);


        create=findViewById(R.id.btn_create);
        create.setOnClickListener(this);
        signin=findViewById(R.id.signin);
        signin.setOnClickListener(this);
        profile=findViewById(R.id.forget_password);
        profile.setOnClickListener(this);
        autofill=findViewById(R.id.autofill_password);
        autofill.setOnClickListener(this);
        btn_translation=findViewById(R.id.btn_translation);
        btn_translation.setOnClickListener(this);


        String currentLocale = getResources().getConfiguration().getLocales().toLanguageTags();
        String translationText = getString(R.string.translation);
        if (currentLocale.equals("zh")) {
            btn_translation.setText(translationText);
        } else {
            btn_translation.setText(translationText);
        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_create){
//            Toast.makeText(this, user.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, sign_up.class);
            startActivity(intent);
//            createAccount(user.getText().toString(),pass.getText().toString());

        }
        if (view.getId()==R.id.signin) {
            Toast.makeText(this, "sign ining", Toast.LENGTH_SHORT).show();
            signinWithEmailAndPassword();
        }
        if (view.getId()==R.id.btn_translation) {
            Toast.makeText(this, "translatie", Toast.LENGTH_SHORT).show();
            Locale currentLocale = getResources().getConfiguration().locale;
            String currentLocaleTag = currentLocale.toLanguageTag();

            Locale newLocale;
            String newLocaleTag;

// Check the current locale and set the new locale accordingly
            if (currentLocaleTag.equals("zh")) {
                newLocale = Locale.ENGLISH;
                newLocaleTag = "en";
            } else {
                newLocale = Locale.CHINESE;
                newLocaleTag = "zh";
            }

// Set the locale for the app
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(newLocaleTag);
            AppCompatDelegate.setApplicationLocales(appLocale);

// Apply the new locale configuration
            Configuration configuration = getResources().getConfiguration();
            configuration.setLocale(newLocale);
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

// Restart the activity to reflect the new locale
            Intent intent = getIntent();
            finish();
            startActivity(intent);


        }

        if (view.getId()==R.id.forget_password) {
            Toast.makeText(this, "get profile ", Toast.LENGTH_SHORT).show();
//            getUserProfile();
            String forget_email=email.getText().toString();
            if (forget_email.isEmpty()){
                email.setError("Please input your email for reset pssword");
                email.requestFocus();
                return;
            }
            else {
                sendPasswordReset( forget_email);
            }
        }
        if (view.getId()==R.id.autofill_password) {
//            Toast.makeText(this, "checking verfication", Toast.LENGTH_SHORT).show();
//            checkCurrentUser();
            FirebaseUser user = mAuth.getCurrentUser();
            String currentuid = mAuth.getCurrentUser().getUid();
            UsersDb.child(currentuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String getPassword = snapshot.child("password").getValue().toString();
                        String getEmail = snapshot.child("email").getValue().toString();
                        email.setText(getEmail);
                        pass.setText(getPassword);               // 这是唯一一个，利用数据库修改的profile个人信息。
                        Toast.makeText(MainActivity.this, "Succeffuslly auto_filled", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        email.setError("Not saved account");
                        email.requestFocus();
                        return;
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


        }
        if (view.getId()==R.id.accessability) {
            Toast.makeText(this, "sent email ", Toast.LENGTH_SHORT).show();
//            sendEmailVerification();
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);

        }
        }

    private void signinWithEmailAndPassword() {

        String myemail=email.getText().toString();
        String password=pass.getText().toString();
        if (myemail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Email is required!");
            pass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(myemail).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }
        // [START create_user_with_email]
        mAuth.signInWithEmailAndPassword(myemail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Log in successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(MainActivity.this, AccountInfo.class);
                            startActivity(intent);
//                            hi.setText(R.string.auth_successful);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            email.setError("Error email or password, Please check");
                            email.requestFocus();
                            return;
//                            updateUI(null);
//                            hi.setText(R.string.auth_failed);
//                            checkForMultiFactorFailure(task.getException());
                        }

//                        if (!task.isSuccessful()) {
//                            hi.setText(R.string.auth_failed);
//                        }
//                        hideProgressBar();
                    }
                });
    }


//    private void createAccount(String email, String password) {
//        // [START create_user_with_email]
//        String test=email.getText().toString();
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            hi.setText(test+"true");
//
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                            hi.setText(test+"false");
//                        }
//                    }
//                });
//        // [END create_user_with_email]
//    }

    public void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // User is signed in

            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            if (emailVerified) {
                hi.setText("verified ");
            }
            else {
                hi.setText(name+" not");
            }


        } else {
          hi.setText("not login");
            // No user is signed in
        }
        // [END check_current_user]
    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = mAuth.getCurrentUser();


        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            if (emailVerified) {
                hi.setText(name+"   "+email);
            }
            else {
                hi.setText("not verified");
            }



        }
        // [END get_user_profile]
    }

    public void getProviderData() {
        // [START get_provider_data]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }
        }
        // [END get_provider_data]
    }

    public void updateProfile(String name) {
        // [START update_profile]
        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();
        hi.setText(name);
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
        // [END update_profile]
    }

    public void updateEmail() {
        // [START update_email]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail("user@example.com")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
        // [END update_email]
    }

    public void updatePassword(String new_password) {  // only if the user has loged in
        // [START update_password]
        FirebaseUser user = mAuth.getCurrentUser();
        String newPassword = new_password;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            hi.setText(newPassword);
                        }
                        else {
                            hi.setText("not succeffuly reset"+newPassword);
                        }
                    }
                });
        // [END update_password]
    }

    public void sendEmailVerification() {
        // [START send_email_verification]

        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            hi.setText("succeffuly sent");
                        }
                        else {
                            hi.setText("not sent");
                        }
                    }
                });
        // [END send_email_verification]
    }

    public void sendEmailVerificationWithContinueUrl() {
        // [START send_email_verification_with_continue_url]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        String url = "http://www.example.com/verify?uid=" + user.getUid();
        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(url)
                .setIOSBundleId("com.example.ios")
                // The default for this is populated with the current android package name.
                .setAndroidPackageName("com.example.android", false, null)
                .build();

        user.sendEmailVerification(actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });

        // [END send_email_verification_with_continue_url]
        // [START localize_verification_email]
        auth.setLanguageCode("fr");
        // To apply the default app language instead of explicitly setting it.
        // auth.useAppLanguage();
        // [END localize_verification_email]
    }

    public void sendPasswordReset(String email) {
        // [START send_password_reset]

//        String emailAddress = mAuth.getCurrentUser().getEmail();

        String emailAddress=email;
        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            hi.setText("succeffuly");
                        }
                        else {
                            hi.setText("not");
                        }
                    }
                });
        // [END send_password_reset]
    }

    public void deleteUser() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
        // [END delete_user]
    }

    public void reauthenticate() {
        // [START reauthenticate]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });
        // [END reauthenticate]
    }

    public void authWithGithub() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // [START auth_with_github]
        String token = "<GITHUB-ACCESS-TOKEN>";
        AuthCredential credential = GithubAuthProvider.getCredential(token);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
        // [END auth_with_github]
    }


    public void linkAndMerge(AuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // [START auth_link_and_merge]
        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = task.getResult().getUser();
                        // Merge prevUser and currentUser accounts and data
                        // ...
                    }
                });
        // [END auth_link_and_merge]
    }


    public void unlink(String providerId) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // [START auth_unlink]
        mAuth.getCurrentUser().unlink(providerId)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Auth provider unlinked from account
                            // ...
                        }
                    }
                });
        // [END auth_unlink]
    }

    public void buildActionCodeSettings() {
        // [START auth_build_action_code_settings]
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "com.example.android",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();
        // [END auth_build_action_code_settings]
    }

    public void sendSignInLink(String email, ActionCodeSettings actionCodeSettings) {
        // [START auth_send_sign_in_link]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
        // [END auth_send_sign_in_link]
    }

    public void verifySignInLink() {
        // [START auth_verify_sign_in_link]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String emailLink = intent.getData().toString();

        // Confirm the link is a sign-in with email link.
        if (auth.isSignInWithEmailLink(emailLink)) {
            // Retrieve this from wherever you stored it
            String email = "someemail@domain.com";

            // The client SDK will parse the code from the link for you.
            auth.signInWithEmailLink(email, emailLink)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Successfully signed in with email link!");
                                AuthResult result = task.getResult();
                                // You can access the new user via result.getUser()
                                // Additional user info profile *not* available via:
                                // result.getAdditionalUserInfo().getProfile() == null
                                // You can check if the user is new or existing:
                                // result.getAdditionalUserInfo().isNewUser()
                            } else {
                                Log.e(TAG, "Error signing in with email link", task.getException());
                            }
                        }
                    });
        }
        // [END auth_verify_sign_in_link]
    }

    public void linkWithSignInLink(String email, String emailLink) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // [START auth_link_with_link]
        // Construct the email link credential from the current URL.
        AuthCredential credential =
                EmailAuthProvider.getCredentialWithLink(email, emailLink);

        // Link the credential to the current user.
        auth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Successfully linked emailLink credential!");
                            AuthResult result = task.getResult();
                            // You can access the new user via result.getUser()
                            // Additional user info profile *not* available via:
                            // result.getAdditionalUserInfo().getProfile() == null
                            // You can check if the user is new or existing:
                            // result.getAdditionalUserInfo().isNewUser()
                        } else {
                            Log.e(TAG, "Error linking emailLink credential", task.getException());
                        }
                    }
                });
        // [END auth_link_with_link]
    }

    public void reauthWithLink(String email, String emailLink) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // [START auth_reauth_with_link]
        // Construct the email link credential from the current URL.
        AuthCredential credential =
                EmailAuthProvider.getCredentialWithLink(email, emailLink);

        // Re-authenticate the user with this credential.
        auth.getCurrentUser().reauthenticateAndRetrieveData(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User is now successfully reauthenticated
                        } else {
                            Log.e(TAG, "Error reauthenticating", task.getException());
                        }
                    }
                });
        // [END auth_reauth_with_link]
    }

    public void differentiateLink(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // [START auth_differentiate_link]
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInMethods = result.getSignInMethods();
                            if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                                // User can sign in with email/password
                            } else if (signInMethods.contains(EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD)) {
                                // User can sign in with email/link
                            }
                        } else {
                            Log.e(TAG, "Error getting sign in methods for user", task.getException());
                        }
                    }
                });
        // [END auth_differentiate_link]
    }

    public void getGoogleCredentials() {
        String googleIdToken = "";
        // [START auth_google_cred]
        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
        // [END auth_google_cred]
    }


//    public void getFbCredentials() {
//        AccessToken token = AccessToken.getCurrentAccessToken();
//        // [START auth_fb_cred]
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        // [END auth_fb_cred]
//    }

    public void getEmailCredentials() {
        String email = "";
        String password = "";
        // [START auth_email_cred]
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        // [END auth_email_cred]
    }



    public void testPhoneVerify() {
        // [START auth_test_phone_verify]
        String phoneNum = "+16505554567";
        String testVerificationCode = "123456";

        // Whenever verification is triggered with the whitelisted number,
        // provided it is not set for auto-retrieval, onCodeSent will be triggered.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNum)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Save the verification id somewhere
                        // ...

                        // The corresponding whitelisted code above should be used to complete sign-in.
                        MainActivity.this.enableUserManuallyInputCode();
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Sign in with the credential
                        // ...
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // ...
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END auth_test_phone_verify]
    }

    private void enableUserManuallyInputCode() {
        // No-op
    }

    public void testPhoneAutoRetrieve() {
        // [START auth_test_phone_auto]
        // The test phone number and code should be whitelisted in the console.
        String phoneNumber = "+16505554567";
        String smsCode = "123456";

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();

        // Configure faking the auto-retrieval with the whitelisted numbers.
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        // Instant verification is applied and a credential is directly returned.
                        // ...
                    }

                    // [START_EXCLUDE]
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }
                    // [END_EXCLUDE]
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END auth_test_phone_auto]
    }

    public void gamesMakeGoogleSignInOptions() {
        // [START games_google_signin_options]
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestServerAuthCode(getString(R.string.default_web_client_id))
                .build();
        // [END games_google_signin_options]
    }

    // [START games_auth_with_firebase]
    // Call this both in the silent sign-in task's OnCompleteListener and in the
    // Activity's onActivityResult handler.
    private void firebaseAuthWithPlayGames(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithPlayGames:" + acct.getId());

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        AuthCredential credential = PlayGamesAuthProvider.getCredential(acct.getServerAuthCode());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    // [END games_auth_with_firebase]

    private void gamesGetUserInfo() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // [START games_get_user_info]
        FirebaseUser user = mAuth.getCurrentUser();
        String playerName = user.getDisplayName();

        // The user's Id, unique to the Firebase project.
        // Do NOT use this value to authenticate with your backend server, if you
        // have one; use FirebaseUser.getIdToken() instead.
        String uid = user.getUid();
        // [END games_get_user_info]
    }

    private void updateUI(@Nullable FirebaseUser user) {
        // No-op
    }
}