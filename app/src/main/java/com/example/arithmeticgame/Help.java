package com.example.arithmeticgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Help extends AppCompatActivity {
    Button btnHome,btnPlay;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getView();
        String content="       03 levels: Easy, Normal and Hard.\n" +
                "       A turn of 10 questions, each question is any calculation.\n" +
                "       A correct question corresponds to 10 points. \n" +
                "       Each question has 04 answer so you can choice the right one.\n" +
                "       When you choice an answer, the game will automatic move to the next question.\n" +
                "       When you finish, the screen will show you your score and completion time.\n" +
                "       The rankings are sorted by score, if the scores are equal then they will sort by completion time.\n";
        tvContent.setText(content);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iHome= new Intent(Help.this,Home.class);
                startActivity(iHome);
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPlay=new Intent(Help.this,NewGame.class);
                startActivity(iPlay);
            }
        });
    }

    private void getView(){
        btnHome=(Button) findViewById(R.id.btnHome);
        btnPlay=(Button) findViewById(R.id.btnPlay);
        tvContent=(TextView) findViewById(R.id.tvContent);
    }
}