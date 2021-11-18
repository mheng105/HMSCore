package com.example.arithmeticgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    DbHelper dbHelper;
    Button btnNewGame,btnSignOut,btnSettings,btnAwards,btnTwoPerson;
    TextView tvAcc;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getView();
        getData();
        receiveNewGame();
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,NewGame.class);
                intent.putExtra("name",tvAcc.getText().toString());
                startActivity(intent);
        }
    });
        btnTwoPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iWaiting=new Intent(Home.this,WaitingRoom.class);
                iWaiting.putExtra("name",tvAcc.getText().toString());
                startActivity(iWaiting);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(Home.this,Settings.class);
                startActivity(i2);
            }
        });
        btnAwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(Home.this,Awards.class);
                startActivity(i3);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*dbHelper=new DbHelper(Home.this);
                dbHelper.delete();*/
                FirebaseAuth.getInstance().signOut();
                //tvAcc.setText("");
                Intent i4=new Intent(Home.this,LogIn.class);
                startActivity(i4);
            }
        });
}
    private void getView(){
        btnNewGame=(Button) findViewById(R.id.btnNewGame);
        btnSignOut=(Button) findViewById(R.id.btnSignOut);
        btnSettings=(Button) findViewById(R.id.btnSettings);
        btnAwards=(Button) findViewById(R.id.btnPlay);
        btnTwoPerson=(Button)findViewById(R.id.btnTwoPerson);
        tvAcc=(TextView)findViewById(R.id.tvAccount);
    }
    private void getData(){
        mData=FirebaseDatabase.getInstance().getReference("USER");
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Account acc=snapshot.getValue(Account.class);
                Bundle bunrcv = getIntent().getExtras();
                if(bunrcv!=null){
                    String mail=bunrcv.getString("email");
                    if(acc.mail.equals(mail)){
                        tvAcc.setText(acc.user);
                    }
                }
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void receiveNewGame(){
        Bundle b=getIntent().getExtras();
        if(b!=null){
            tvAcc.setText(b.getString("username"));
        }
    }
}