package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextView textView;
    Button button;
    EditText email,password;
    android.app.ProgressDialog progressdialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressdialogue = new ProgressDialog(this);
        progressdialogue.setMessage("Process is running Please wait...");
        progressdialogue.setCancelable(false);
        TextView textView = (TextView) findViewById(R.id.textView8);
        getSupportActionBar().hide();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Button button= findViewById(R.id.logbutton);
        EditText email= findViewById(R.id.editTextlogEmail);
        EditText password=findViewById(R.id.editTextlogPassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email= email.getText().toString();
                String pass= password.getText().toString();
                auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressdialogue.show();
                            try {
                                Intent intent = new Intent(login.this,splash.class);
                                startActivities(new Intent[]{intent});
                                finish();

                            }catch (Exception e){
                                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,signup.class);
                startActivities(new Intent[]{intent});
                finish();

            }
        });
    }
}