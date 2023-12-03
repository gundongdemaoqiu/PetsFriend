package com.example.application1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendFrag extends Fragment {
     private FirebaseAuth mAuth;
     private DatabaseReference UsersDb;
     private ListView friend_list;
//     private String [] friends_info;
    private List<String> friends_info_test;
     private FriendAdapter friend_adapter;
    private List<String> friends_uid_list;

    public FriendFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendFrag newInstance(String param1, String param2) {
        FriendFrag fragment = new FriendFrag();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_friend, container, false);
        View v=inflater.inflate(R.layout.fragment_friend, container, false);

//        friends_info=new String[3];
        friends_info_test=new ArrayList<String>();
        friends_uid_list=new ArrayList<String>();
//        View v = inflater.inflate(R.layout.fragment_profile, container, false);   // false 意思是不粘贴到原文件去，只是显示出来，然后放到listview里面

//        String itemNames[] = {"Edit Profile", "Settings", "Your Pet's Document"};
        SetFriendsInfo();

//        String itemNames[] = {"Edit Profile", "Settings", "Your Pet's Document"};

        Integer imageid[] = {R.drawable.dog, R.drawable.ic_chat, R.drawable.ic_pets};
        ////////////////这里imgae实际上也应该从数据库中photoUrl中拿取，这里暂时先用一张图片代替

        friend_list = v.findViewById(R.id.Friend_listview);   // 拿出里面要进行设置的list

        // 注意这里作者没有用数据库进行实时的picture和like等其他view控件的更新，所以可以作为优化空间！！！！！！！！！！！！

        // For populating list data
//        friend_adapter  = new FriendAdapter(getActivity(), friends_info, imageid);
        friend_adapter  = new FriendAdapter(getActivity(), friends_info_test, imageid);
        friend_list.setAdapter(friend_adapter);
        friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //AdapterView.OnItemClickListener()是用来检测arrayadpet成员变化
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), chat.class);
                Bundle bundle = new Bundle();
                bundle.putString("chatwith_uid", friends_uid_list.get(position));
                intent.putExtra("Message", bundle);
                startActivity(intent);//   现在功能只能点击时间来edit profuile，

            }
        });

//        friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //AdapterView.OnItemClickListener()是用来检测arrayadpet成员变化
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (friends_info.get(position).equals("Edit Profile")) {
//
//                }
////                    switchActivity();    //   现在功能只能点击时间来edit profuile，
//            }
//        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void SetFriendsInfo(){
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        UsersDb= FirebaseDatabase.getInstance().getReference();
//        DatabaseReference currentUserDb=UsersDb.child("Users").child(currentUser.getUid());
        // 这里提供给所有的新用户进行交流，还没有限制条件match

        UsersDb.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists() && !snapshot.getKey().equals(currentUser.getUid())
                && snapshot.child("connections").child("matches").hasChild(currentUser.getUid())) {// 这是检查是
//                    否已经match来进行好友判断


                    String name = snapshot.child("name").getValue(String.class);
                    Log.d(TAG, "asdasdempty!!!!"+name);
//                    friends_info[0]=name;               //  这里可以遍历所有friend的名字来点击
//                    friends_info[1]=name;
//                    friends_info[2]=name;
                    friends_info_test.add(name);
                    friends_uid_list.add(snapshot.getKey());

                    friend_adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

//    public List<>  getListFromDb(){
//
//    }
}