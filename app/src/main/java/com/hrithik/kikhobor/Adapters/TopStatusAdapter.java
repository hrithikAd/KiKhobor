package com.hrithik.kikhobor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrithik.kikhobor.Activities.ChatActivity;
import com.hrithik.kikhobor.Activities.MainActivity;
import com.hrithik.kikhobor.Models.Message;
import com.hrithik.kikhobor.Models.Status;
import com.hrithik.kikhobor.Models.User;
import com.hrithik.kikhobor.Models.UserStatus;
import com.hrithik.kikhobor.R;
import com.hrithik.kikhobor.databinding.ItemSentBinding;
import com.hrithik.kikhobor.databinding.ItemStatusBinding;

import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.OnStoryChangedCallback;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class TopStatusAdapter extends RecyclerView.Adapter<TopStatusAdapter.TopStatusViewHolder> {

    Context context;
    ArrayList<UserStatus> userStatuses;

    public TopStatusAdapter(Context context, ArrayList<UserStatus> userStatuses) {
        this.context = context;
        this.userStatuses = userStatuses;
    }

    @NonNull
    @Override
    public TopStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);



        return new TopStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStatusViewHolder holder, int position) {

        UserStatus userStatus = userStatuses.get(position);

        Status lastStatus = userStatus.getStatuses().get(userStatus.getStatuses().size() - 1);

        Glide.with(context).load(lastStatus.getImageUrl()).into(holder.binding.image);

        holder.binding.circularStatusView.setPortionsCount(userStatus.getStatuses().size());

        holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View v) {

                    ALLStory(userStatuses.get(position));


            }


        });

        //swipe up

    }

    @Override
    public int getItemCount() {
        return userStatuses.size();
    }

    public class TopStatusViewHolder extends RecyclerView.ViewHolder {

        ItemStatusBinding binding;

        public TopStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStatusBinding.bind(itemView);
        }
    }


    public void ALLStory(UserStatus userStatus){



        ArrayList<MyStory> myStories = new ArrayList<>();
        for(Status status : userStatus.getStatuses()) {
            myStories.add(new MyStory(status.getImageUrl()));
        }

        new StoryView.Builder(((MainActivity)context).getSupportFragmentManager())
                .setStoriesList(myStories) // Required
                .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                .setTitleText(userStatus.getName()) // Default is Hidden
                .setSubtitleText("") // Default is Hidden
                .setTitleLogoUrl(userStatus.getProfileImage()) // Default is Hidden
                .setStoryClickListeners(new StoryClickListeners() {
                    @Override
                    public void onDescriptionClickListener(int position) {
                        //your action
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {
                        //your action
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    User user = snapshot1.getValue(User.class);
                                    if(user.getName().equals(userStatus.getName())) {


                                        Intent intent = new Intent(context, ChatActivity.class);
                                        intent.putExtra("name", user.getName());
                                        intent.putExtra("uid", user.getUid());
                                        context.startActivity(intent);



                                    }





                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }

                    
                }) // Optional Listeners
                .build() // Must be called before calling show method
                .show();






    }





}
