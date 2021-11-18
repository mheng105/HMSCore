package com.example.arithmeticgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    Button btnSignIn,btnCancel;
    EditText edtMail,edtUser,edtPaswd,edtConfirm;
    FirebaseAuth mAuth;
    DatabaseReference mDb;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        /*DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
        myRef.setValue("Hello, Hang!");*/
        mAuth=FirebaseAuth.getInstance();
        getViews();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPaswd.getText().toString().equals(edtConfirm.getText().toString())) {
                    SignUp();
                }else Toast.makeText(SignUp.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
    }

    private void reload() {
        edtMail.setText("");
        edtUser.setText("");
        edtPaswd.setText("");
        edtConfirm.setText("");
    }

    void getViews(){
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        edtMail=(EditText)findViewById(R.id.tvMail);
        edtUser=(EditText)findViewById(R.id.tvUser);
        edtConfirm=(EditText)findViewById(R.id.edtConfirm);
        edtPaswd=(EditText)findViewById(R.id.edtPaswd);
    }

    public void SignUp(){
        String email=edtMail.getText().toString();
        String passwd=edtPaswd.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this,"Success",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUp.this,"Fail",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mDb=FirebaseDatabase.getInstance().getReference("USER").push();
        mDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()==null){
                    Account acc=new Account();
                    acc.mail=edtMail.getText().toString().trim();
                    acc.user=edtUser.getText().toString().trim();
                    acc.pass=edtPaswd.getText().toString().trim();
                    mDb.setValue(acc,new DatabaseReference.CompletionListener(){
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error==null){
                                Toast.makeText(SignUp.this,"Sign Up successful",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(SignUp.this,LogIn.class);
                                Bundle b=new Bundle();
                                b.putString("mail",edtMail.getText().toString().trim());
                                b.putString("pass",edtPaswd.getText().toString().trim());
                                i.putExtras(b);
                                startActivity(i);
                            }else Toast.makeText(SignUp.this,"Sign Up fail: "+error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else Toast.makeText(SignUp.this,"This account already exists",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}