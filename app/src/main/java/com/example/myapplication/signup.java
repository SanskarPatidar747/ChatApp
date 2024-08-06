package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class signup extends AppCompatActivity {
    EditText username,password,cpassword,email;
    FirebaseAuth auth;
    TextView login;
    Button signin;
    CircleImageView image;
    Uri imageURI;
    String imageuri;
    FirebaseDatabase database;
    FirebaseStorage storage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        EditText username= (EditText) findViewById(R.id.Username);
        EditText password= (EditText) findViewById(R.id.signinPassword);
        EditText cpassword= (EditText) findViewById(R.id.signinconformPassword);
        EditText email= (EditText) findViewById(R.id.signinEmailAddress);
        TextView login= (TextView) findViewById(R.id.textView8);
        Button signin= (Button) findViewById(R.id.signinbutton);
         image=findViewById(R.id.profilerg);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),10);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(signup.this,login.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee =username.getText().toString();
                String pass =password.getText().toString();
                String Cpass =cpassword.getText().toString();
                String emaill =email.getText().toString();
                String status="hey i am using this Application";
                if (TextUtils.isEmpty(namee)||TextUtils.isEmpty(emaill)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(Cpass)||TextUtils.isEmpty(status)){
                    Toast.makeText(signup.this, "fell the above information", Toast.LENGTH_SHORT).show();
                }
                 else if (pass.length()<6) {
                    password.setError("password must be 6 char or more");
                } else if (!pass.equals(Cpass)) {
                    password.setError("password doesn't match");
                }
                else {
                    auth.createUserWithEmailAndPassword(emaill,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String id = task.getResult().getUser().getUid();
                                DatabaseReference reference= database.getReference().child("user").child(id);
                                StorageReference storageReference = storage.getReference().child("upload").child(id);
                                if (imageURI!=null){
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri = uri.toString();
                                                        Users users = new Users(id,namee,emaill,pass,Cpass,imageuri,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    Intent intent = new Intent(signup.this,splash.class);
                                                                    startActivities(new Intent[]{intent});
                                                                    finish();
                                                                }else {
                                                                    Toast.makeText(signup.this, "error in creating users", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                                    }
                                                });
                                            }
                                        }
                                    });
                                }else {
                                    String status="hey i am using this Application";
                                    imageuri ="https://firebasestorage.googleapis.com/v0/b/my-application-fe0ab.appspot.com/o/man.png?alt=media&token=b6efa100-1610-4e1a-9206-2a4212a51128";
                                    Users users = new Users(id,namee,emaill,pass,imageuri,status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent intent = new Intent(signup.this,splash.class);
                                                startActivities(new Intent[]{intent});
                                                finish();
                                            }else {
                                                Toast.makeText(signup.this, "error in creating users", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            }else{
                                Toast.makeText(signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }


            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if (data!=null){
                imageURI=data.getData();
                image.setImageURI(imageURI);
            }
        }
    }
}