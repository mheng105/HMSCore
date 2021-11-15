package com.example.arithmeticgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    DbHelper dbHelper;
    Button btnNewGame,btnHelp,btnSettings,btnAwards,btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getView();
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,NewGame.class);
                startActivity(intent);
        }
    });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Home.this,Help.class);
                startActivity(i1);
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
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper=new DbHelper(Home.this);
                dbHelper.delete();
            }
        });
}
    private void getView(){
        btnNewGame=(Button) findViewById(R.id.btnNewGame);
        btnHelp=(Button) findViewById(R.id.btnHelp);
        btnSettings=(Button) findViewById(R.id.btnSettings);
        btnAwards=(Button) findViewById(R.id.btnPlay);
        btnQuit=(Button) findViewById(R.id.btnQuit);

    }

}