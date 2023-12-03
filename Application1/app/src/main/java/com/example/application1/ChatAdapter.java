package com.example.application1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private List<ChatObject> chatList;
    private final Context context;
    private String userId;

    public ChatAdapter(List<ChatObject> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatObject obj = chatList.get(position);
        if (obj.getCurrentUser()) {
            holder.myLayout.setVisibility(View.VISIBLE);
//            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(obj.getMessage());
            holder.myName.setText(obj.getName());
            holder.myTime.setText(obj.getDate()+" "+obj.getTime());
//            holder.oppoName.setText(obj.getName());
        }
//        else {
//            holder.oppoLayout.setVisibility(View.VISIBLE);
//            holder.myLayout.setVisibility(View.GONE);
//
//            holder.oppoMessage.setText(obj.getMessage());
//            holder.myName.setText(obj.getName());
//            holder.oppoTime.setText(obj.getDate()+" "+obj.getTime());
//        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void updateChatList(List<ChatObject> chatList) {
        this.chatList = chatList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMessage, myMessage;
        private TextView oppoTime, myTime;
        private TextView oppoName, myName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
//            oppoMessage = itemView.findViewById(R.id.oppoMessage);
            myMessage = itemView.findViewById(R.id.myMessage);
//            oppoTime = itemView.findViewById(R.id.oppoMsgTime);
            myTime = itemView.findViewById(R.id.myMsgTime);
//            oppoName = itemView.findViewById(R.id.oppoName);
            myName = itemView.findViewById(R.id.myName);

        }
    }
}