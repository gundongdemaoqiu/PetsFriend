package com.example.application1;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.firebase.client.ChildEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFrag extends Fragment {


    private Candidates cards_data[];
    private arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;

    private String currentUId;

    private DatabaseReference userDb;
    ListView listView;
    List<Candidates> rowItems;

    public DiscoverFrag() {
        // Required empty public constructor
    }


    public static DiscoverFrag newInstance(String param1, String param2) {
        DiscoverFrag fragment = new DiscoverFrag();

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
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();
        checkPetBreed();    // 检查宠物种类
        rowItems = new ArrayList<Candidates>();   //有一堆用户信息
        arrayAdapter = new arrayAdapter(getActivity(), R.layout.item, rowItems);   //  R.layout。item。xml，不是id

        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.frame);  ///左滑右滑 //已经实现好了
        flingContainer.setAdapter(arrayAdapter);  // 配置好其他人的信息
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {        //就是滑动用法
            @Override
            public void removeFirstObjectInAdapter() {   // 不配对的话可以删掉
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();  // 通知界面变化
            }

            @Override
            public void onLeftCardExit(Object dataObject) { //
                Candidates obj = (Candidates) dataObject;
                String userId = obj.getUid();   // 这里是对所有user进行匹配
                userDb.child(userId).child("connections").child("nope").child(currentUId).setValue(true);  // connections把nope里面加 ，完美配对是互相有yes
                Toast.makeText(getActivity(), "NOPE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Candidates obj = (Candidates) dataObject;
                String userId = obj.getUid();
                userDb.child(userId).child("connections").child("like").child(currentUId).setValue(true);
                isConnectionMatch(userId);  //检查其他user是否也like   这里是创建聊天id 也就是加好友的意思
                Toast.makeText(getActivity(), "LIKE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String petBreed;
    private String oppBreed;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void checkPetBreed() {   //也是通过数据库找的，之后要慢慢放用户的信息，
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersDb = userDb.child(user.getUid());// 跳转到当前用户的数据库中
        usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
            //使用监听器读取一次
// 在某些情况下，您可能希望立即返回本地缓存中的值，
// 而不必检查服务器上更新后的值。在这些情况下，您可以使用 addListenerForSingleValueEvent 立即从本地磁盘缓存获取数据。
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if (snapshot.getKey().equals(user.getUid())) {
                if (snapshot.exists()) {
                    if (snapshot.child("breed").getValue() != null) {
                        petBreed = snapshot.child("breed").getValue().toString();    // 拿到当前用户的宠物种类

                        getSameBreedPet();    //在该用户加载个人资料的时候进行匹配
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void isConnectionMatch(String userId) {  // 双方的一些判断，先拿到一些数据，关键是对方是不是mantch我，如果match，则完美match，//然后让chat互相enable
        DatabaseReference currentUserConnectionDb = userDb.child(currentUId).child("connections").child("like").child(userId);
        ///先找到对方对我的like库看看是不是true
        currentUserConnectionDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {     // 因为只有like才会改变data，所以change就意味着like
                if (snapshot.exists()) {
                    Toast.makeText(getActivity(), "new Connection", Toast.LENGTH_LONG).show();

                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();
//                    String newKey = chatRef.push().getKey();
                    FirebaseDatabase.getInstance().getReference().child("Chat").child(key).setValue("");
// Create an empty node with the generated key
//                    chatRef.child(newKey).setValue(null);
//                    Log.d(TAG,"????push");
                    //单独创建一个聊天空间 !!!!!z注意这里没有实际对于数据库造成影响！！！！！
                    // 给两人的数据库都更新上聊天室id
                    userDb.child(snapshot.getKey()).child("connections").child("matches").child(currentUId).child("chatId").setValue(key);
                    userDb.child(currentUId).child("connections").child("matches").child(snapshot.getKey()).child("chatId").setValue(key);
                    //直接更新上对应的聊天室id
//                    FirebaseDatabase.getInstance().getReference().child("Chat").child(key).setValue(null);



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void getSameBreedPet() {
          userDb.addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                  if (snapshot.exists() && !snapshot.getKey().equals(user.getUid()) &&  //新用户不能是和当前用户一样
                        !snapshot.child("connections").child("nope").hasChild(currentUId) && //不能是查看过的人
                        !snapshot.child("connections").child("like").hasChild(currentUId) && //不能是查看过的人
                        snapshot.child("breed").getValue().toString().equals(petBreed)) {   // 必须是同一种宠物才可以配对
                    String profileImageUrl = "default";
                    if (!snapshot.child("profileImageUrl").getValue().equals("default")) {
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    // snapshot的key是userid
                    Candidates item = new Candidates(snapshot.getKey(), profileImageUrl,"fod","male",snapshot.child("name").getValue().toString());
                    rowItems.add(item);
                    arrayAdapter.notifyDataSetChanged();  // 更新array
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


}