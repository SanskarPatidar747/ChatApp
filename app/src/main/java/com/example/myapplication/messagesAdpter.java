package com.example.myapplication;

import static com.example.myapplication.Chat.reciverIImg;
import static com.example.myapplication.Chat.senderimg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdpter extends RecyclerView.Adapter {
    Context context;
    ArrayList<msgModelclass> messageAdpterArraylist;
    int ITEM_SEND=1;
    int ITEM_RECIVE=2;

    public messagesAdpter(Context context, ArrayList<msgModelclass> messageAdpterArraylist) {
        this.context = context;
        this.messageAdpterArraylist = messageAdpterArraylist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderViewholder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.reciver_layout,parent,false);
            return new reciverViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelclass messages = messageAdpterArraylist.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });
        if (holder.getClass()==senderViewholder.class) {
            senderViewholder viewholder=(senderViewholder) holder;
            viewholder.textView1.setText(messages.getMessage());
            Picasso.get().load(senderimg).into(((senderViewholder) holder).circleImageView1);
        }
        else {
            reciverViewholder reciverViewholder=(messagesAdpter.reciverViewholder) holder;
            reciverViewholder.textView.setText(messages.getMessage());
            Picasso.get().load(reciverIImg).into(((reciverViewholder) holder).circleImageView);
        }

    }

    @Override
    public int getItemCount() {
//        return 0;
        return messageAdpterArraylist.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelclass messages = messageAdpterArraylist.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderid())){
            return ITEM_SEND;
        }
        else {
            return ITEM_RECIVE;
        }
    }

    class senderViewholder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView1;
        TextView textView1;
        public senderViewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView1 = itemView.findViewById(R.id.profilerggg);
            textView1 =  itemView.findViewById(R.id.msgsendertyp);
        }
    }
    class reciverViewholder extends RecyclerView.ViewHolder {
         CircleImageView circleImageView;
        TextView textView;
        public reciverViewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.pro);
            textView =  itemView.findViewById(R.id.recivertextset);
        }
    }
}
