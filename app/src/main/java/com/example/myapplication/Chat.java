package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    String reciverimg,reciverUid,reciverName,senderid;
    CircleImageView profi;
    TextView username;
    CardView sendbtn;
    EditText writemsg;
    FirebaseAuth auth;
    public static String senderimg;
    public static String reciverIImg;
    String SenderRoom,ReciverRoom;
    FirebaseDatabase database;
    RecyclerView messanger;
    ArrayList<msgModelclass> messagesarryalist;
    messagesAdpter messagesAdpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        reciverName = getIntent().getStringExtra("nameee");
        reciverUid = getIntent().getStringExtra("Uid");
        reciverimg = getIntent().getStringExtra("profilepic");

        messagesarryalist= new ArrayList<>();

        sendbtn=findViewById(R.id.sendbtn);
        username= findViewById(R.id.Username5);
        writemsg=findViewById(R.id.writebtn);
        profi =findViewById(R.id.profilerg3);

        messanger=findViewById(R.id.mmesanger);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messanger.setLayoutManager(linearLayoutManager);
        messagesAdpter = new messagesAdpter(Chat.this,messagesarryalist);
        messanger.setAdapter(messagesAdpter);

        Picasso.get().load(reciverimg).into(profi);
        username.setText(""+reciverName);

        senderid = auth.getUid();
        SenderRoom=senderid+reciverUid;
        ReciverRoom=reciverUid+senderid;

        DatabaseReference refrence = database.getReference().child("user").child(auth.getUid());
        DatabaseReference chatrefrence = database.getReference().child("chats").child(SenderRoom).child("messages");
//        DatabaseReference chatrefrence = database.getReference().child("chats").child(SenderRoom).child("messages");
        chatrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesarryalist.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    msgModelclass messages= dataSnapshot.getValue(msgModelclass.class);
                    messagesarryalist.add(messages);
                }
                messagesAdpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderimg = snapshot.child("profilpic").getValue().toString();
                reciverIImg=reciverimg;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messge =writemsg.getText().toString();
                if(messge.isEmpty()){
                    Toast.makeText(Chat.this, "Enter the message.", Toast.LENGTH_SHORT).show();
                }
                writemsg.setText("");
                Date date = new Date();
                msgModelclass messagess = new msgModelclass(messge,senderid,date.getTime());
                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats").child(SenderRoom).child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats").child(ReciverRoom).child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
                });


            }
        });
    }
}