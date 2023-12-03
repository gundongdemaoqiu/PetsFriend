
/////////////////////////////////////// this is new version using database.
package com.example.application1;

import static android.content.ContentValues.TAG;

import static java.lang.Long.parseLong;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class chat extends AppCompatActivity {
    LinearLayout layout;
    private FirebaseAuth mAuth;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    String currentuid;
    String currentname;
    String chat_friend_uid;
    String Chatroom_id;
    private final List<ChatObject> chatLists = new ArrayList<>();
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    Firebase reference1;
    Firebase reference2;
    Button Chat_go_back;
    DatabaseReference current_match_ref;
    DatabaseReference messages_Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        //从intent取出bundle
        Bundle bundle = intent.getBundleExtra("Message");
        //获取数据
        chat_friend_uid = bundle.getString("chatwith_uid");
//        Log.d(TAG, "chat_friend_uid"+chat_friend_uid);
        //显示数据


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        ChatMessage mychat = new ChatMessage();
//        mychat.messageUser= user.getDisplayName();
        mychat.chatWith = "someone";
        mychat.messageUser = user.getUid();
//        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        Chat_go_back=findViewById(R.id.chat_go_back);
        messageArea = (EditText) findViewById(R.id.messageArea);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
//        scrollView = (ScrollView)findViewById(R.id.scrollView);
        currentuid = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Firebase.setAndroidContext(this);
        DatabaseReference rootRef = database.getReference();

        current_match_ref = rootRef.child("Users").child(currentuid).child("connections").child("matches");
        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(chat.this));

        chatAdapter = new ChatAdapter(chatLists, chat.this);
        chatRecyclerView.setAdapter(chatAdapter);
//        ////// 这个用来新建里聊天室拿到chatroom_id，这个chatroomid已经是乔恩据friend里面传的数据单一确定的chatroom

        current_match_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(chat_friend_uid)) {
                    // Loop through all child nodes under chat_friend_uid
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        // Get the value of 'chat' from each child
                        if (childSnapshot.hasChild("chatId")) {
                            Chatroom_id = childSnapshot.child("chatId").getValue(String.class);

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // this is for get current use's name:
        rootRef.child("Users").child(currentuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {                          //////// 这里之后需要具体根据user friend进行配对
                    Log.d(TAG, "kfg");
                    currentname = snapshot.child("name").getValue().toString();     /// 这里是拿到现在user的name作为聊天对象的名字
                    Toast.makeText(chat.this, "has name", Toast.LENGTH_SHORT);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d(TAG, "chat_1");
//        createChatRoom();
        Log.d(TAG, "chat_2");
// Then, monitor the database for changes under the created chatroom_id
//        monitorDatabase();
        Log.d(TAG, "chat_3");



//        Log.d(TAG, "klklasd6+ " + Chatroom_id);
        rootRef.child("Chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.d(TAG, "klklasd Snapshot does not exist");
                } else {
                    // The snapshot exists
                    if (dataSnapshot.hasChild(Chatroom_id)){
                        Log.d(TAG, "klklasd exists");
                        monitorDatabase();

                    }
                    Log.d(TAG, "klklasd Snapshot exists");
                    // Now attach the ChildEventListener to monitor child additions

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });


/////这是发送信息button后对于数据库的处理
        Chat_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chat.this,AccountInfo.class);
                startActivity(intent);
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
//                final String getTxtMessage = messageText.getText().toString();
                final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("sender", currentname);
                    map.put("send_time", currentTimestamp);
                    rootRef.child("Users").child(currentuid).child("connections").child("matches").child(chat_friend_uid).child("last_msg").setValue(currentTimestamp);
                    rootRef.child("Chat").child(Chatroom_id).push().setValue(map);
                }
                messageArea.setText("");

            }
        });


    }

    private void monitorDatabase() {

        DatabaseReference chatroom_ref = FirebaseDatabase.getInstance().getReference().child("Chat").child(Chatroom_id);
        chatroom_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Retrieve and display the values 'a', 'b', 'c' for each key
                final String message = dataSnapshot.child("message").getValue().toString();
                final String userName = dataSnapshot.child("sender").getValue().toString();
                final String send_time = dataSnapshot.child("send_time").getValue().toString();
                long timestampSeconds = Long.parseLong(send_time);
                long timestampMilliseconds = timestampSeconds * 1000;
                Date date = new Date(timestampMilliseconds);

                SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDay = dayFormat.format(date);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedTime = timeFormat.format(date);
                ChatObject my_chat_object = new ChatObject(true, userName, message, formattedDay, formattedTime);

                chatLists.add(my_chat_object);
                chatAdapter.updateChatList(chatLists);///  charadapter：update一下

                chatRecyclerView.scrollToPosition(chatLists.size() - 1);   //将试图滚动到最后一条信息
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

            // Other ChildEventListener methods...

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancellation
            }
        });
    }

//    public void addMessageBox(String message, int type) {
//        TextView textView = new TextView(chat.this);
//        textView.setText(message);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 0, 10);
//        textView.setLayoutParams(lp);
//
//        if (type == 1) {
//            textView.setBackgroundResource(R.drawable.round_corner1);   // 这是本人和对方聊天背景不同
//        } else {
//            textView.setBackgroundResource(R.drawable.round_corner2);
//        }
//
//        layout.addView(textView);
//        scrollView.fullScroll(View.FOCUS_DOWN);
//    }



}


//////////////////////////////////// below is successful chatting implementation

//
//
//package com.example.application1;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class chat extends AppCompatActivity {
//    LinearLayout layout;
//    private FirebaseAuth mAuth;
//    ImageView sendButton;
//    EditText messageArea;
//    ScrollView scrollView;
//    Firebase reference1;
//    Firebase reference2;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//        mAuth= FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        ChatMessage mychat= new ChatMessage();
//        mychat.messageUser= user.getDisplayName();
//        mychat.chatWith="someone";
//        layout = (LinearLayout)findViewById(R.id.layout1);
//        sendButton = (ImageView)findViewById(R.id.sendButton);
//        messageArea = (EditText)findViewById(R.id.messageArea);
//        scrollView = (ScrollView)findViewById(R.id.scrollView);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        Firebase.setAndroidContext(this);
//        DatabaseReference rootRef = database.getReference();
//        DatabaseReference messageRef = rootRef.child("messages");
//        reference1 = new Firebase(rootRef + mychat.messageUser + "_" + mychat.chatWith);
//        reference2 = new Firebase(rootRef + mychat.chatWith + "_" + mychat.messageUser);
////        DatabaseReference reference1=rootRef.child(ChatMessage.messageUser+"_"+ChatMessage.chatWith);
////        DatabaseReference reference2=rootRef.child(ChatMessage.chatWith+"_"+ChatMessage.messageUser);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String messageText = messageArea.getText().toString();
//
//                if(!messageText.equals("")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("message", messageText);
//                    map.put("user", ChatMessage.messageUser);
//                    reference1.push().setValue(map);
//                    reference2.push().setValue(map);
//                }
//                messageArea.setText("");
//            }
//        });
//
//        reference1.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Map map = dataSnapshot.getValue(Map.class);
//                String message = map.get("message").toString();
//                String userName = map.get("user").toString();
//
//                if(userName.equals(ChatMessage.messageUser)){        //在这里if else快速查找跟谁对话， 但是这个只能实现一对一chat
//                    addMessageBox("You("+mychat.messageUser+"):-\n" + message, 1);
//                }
//                else{
//                    addMessageBox(ChatMessage.chatWith + ":-\n" + message, 2);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//
//
//        });
//    }
//
//    public void addMessageBox(String message, int type){
//        TextView textView = new TextView(chat.this);
//        textView.setText(message);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 0, 10);
//        textView.setLayoutParams(lp);
//
//        if(type == 1) {
//            textView.setBackgroundResource(R.drawable.round_corner1);   // 这是本人和对方聊天背景不同
//        }
//        else{
//            textView.setBackgroundResource(R.drawable.round_corner2);
//        }
//
//        layout.addView(textView);
//        scrollView.fullScroll(View.FOCUS_DOWN);
//    }
//}