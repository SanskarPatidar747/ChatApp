package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.viewholder> {
    splash splash;
    ArrayList<Users> usersArrayList;
    public  UserAdpter(){

    }
    public UserAdpter(splash splash, ArrayList<Users> usersArrayList) {
        this.splash=splash;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(splash).inflate(R.layout.user_tem1,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpter.viewholder holder, int position) {
        Users users=usersArrayList.get(position);
        holder.textView13.setText(users.username);
        holder.userstatus.setText(users.status);
        Picasso.get().load(users.profilpic).into(holder.imageView4);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(splash,Chat.class);
                intent.putExtra("nameee",users.getUsername());
                intent.putExtra("profilepic",users.getProfilpic());
                intent.putExtra("Uid",users.getUserid());
                splash.startActivities(new Intent[]{intent});
            }
        });
    }

    @Override
    public int getItemCount() {

        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
       CircleImageView imageView4;
        TextView textView13;
        TextView userstatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView4=itemView.findViewById(R.id.imageView4);
            textView13=itemView.findViewById(R.id.textView13);
            userstatus=itemView.findViewById(R.id.userstatus);
        }
    }
}
