package com.example.arithmeticgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewGame extends AppCompatActivity {
    Button btnBack,btnNext;
    RadioButton rbtnEasy,rbtnHard,rbtnNormal;
    TextView tvUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        getView();
        getUsername();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NewGame.this,Home.class);
                intent.putExtra("username",tvUsername.getText().toString());
                startActivity(intent);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int level=0;
                if(rbtnEasy.isChecked())
                    level=1;
                if(rbtnNormal.isChecked())
                    level=2;
                if(rbtnHard.isChecked())
                    level=3;
                if (level==0){
                    Toast.makeText(NewGame.this,"Bạn chưa chọn level",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(NewGame.this,Game.class);
                    intent.putExtra("Nickname",tvUsername.getText().toString());
                    intent.putExtra("Level",level);
                    startActivity(intent);
                }
            }
        });
    }

    private void getView(){
        btnBack=(Button) findViewById(R.id.btnHome);
        btnNext=(Button) findViewById(R.id.btnPlay);
        rbtnEasy=(RadioButton) findViewById(R.id.rbtnEasy);
        rbtnNormal=(RadioButton) findViewById(R.id.rbtnNormal);
        rbtnHard=(RadioButton) findViewById(R.id.rbtnHard);
        tvUsername=(TextView)findViewById(R.id.tvUsername);
    }
    private void getUsername(){
        Bundle b=getIntent().getExtras();
        if(b!=null){
            String user=b.getString("name");
            tvUsername.setText(user);
        }
    }
}