package com.example.application1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AccountInfo extends AppCompatActivity {
    private BottomNavigationView my_navig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        my_navig=findViewById(R.id.bottom_navigation);
        Fragment prof_frag=new ProfileFrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, prof_frag).commit();

        my_navig.setSelectedItemId(R.id.nav_profile);


        my_navig.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {    // 这里是设置监听来改变不同的fragment
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId()==R.id.nav_profile){
                    fragment = new ProfileFrag();
                }
                if (item.getItemId()==R.id.nav_discover){
                    fragment = new DiscoverFrag();
                }
                if (item.getItemId()==R.id.nav_friend){
                    fragment = new FriendFrag();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                return true;
            }
        });
    }


}