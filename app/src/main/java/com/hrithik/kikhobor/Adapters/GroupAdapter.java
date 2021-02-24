package com.hrithik.kikhobor.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrithik.kikhobor.Activities.ChatActivity;
import com.hrithik.kikhobor.Models.Message;
import com.hrithik.kikhobor.Models.User;
import com.hrithik.kikhobor.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {


    private ArrayList<Message> mMessage = new ArrayList<>();
    private Context mcontext;

    public GroupAdapter(Context context, ArrayList<Message> message){
        mMessage = message;
        mcontext =  context;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {

        Message message = mMessage.get(position);

        holder.profile.setVisibility(View.VISIBLE);
        Glide.with(holder.profile.getContext()).load(message.getPhotoUrl()).into(holder.profile);

        holder.Msg.setText(message.getMessage());


        holder.username.setText(message.getName());

       // holder.msgTime.setText((int) message.getTimestamp());



    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profile;
        TextView username;
        TextView Msg;
        TextView msgTime;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {

            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            username = itemView.findViewById(R.id.username);
            Msg = itemView.findViewById(R.id.Msg);
            msgTime = itemView.findViewById(R.id.msgTime);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }
    }


}