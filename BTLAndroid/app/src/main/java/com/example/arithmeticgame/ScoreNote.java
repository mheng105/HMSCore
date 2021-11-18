package com.example.arithmeticgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoreNote extends AppCompatActivity {
    DbHelper dbHelper;
    TextView tvName,tvScore,tvTime,tvLevel;
    Button btnHome,btnAwards;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_note);
        getView();
        Intent in=getIntent();
        String name=in.getStringExtra("Nickname");
        int score=in.getIntExtra("Score",0);
        int time=in.getIntExtra("Time",0);
        int level=in.getIntExtra("Level",0);
        ArrayList<String> k1=in.getStringArrayListExtra("res");

        BangXH xhx=new BangXH(name,score,time,level);
        //ghi database
        dbHelper=new DbHelper(ScoreNote.this);
        int a=dbHelper.add(xhx);
        if(a==-1)
            Toast.makeText(ScoreNote.this, "Ghi lỗi", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ScoreNote.this, "Ghi thành công", Toast.LENGTH_LONG).show();

        tvName.setText("Nickname: "+name);
        tvScore.setText("Score: "+score);
        tvTime.setText("Time: "+time);
        tvLevel.setText("Level: "+level);

        //hiện các câu hỏi đáp án
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,k1);
        listView.setAdapter(arrayAdapter);
        btnAwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentx=new Intent(ScoreNote.this,Awards.class);
                startActivity(intentx);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenty=new Intent(ScoreNote.this,Home.class);
                tvName.setText(name);
                intenty.putExtra("username",tvName.getText().toString());
                startActivity(intenty);
            }
        });
    }
    private void getView(){
        tvName=(TextView) findViewById(R.id.tvName);
        tvScore=(TextView) findViewById(R.id.tvScore);
        tvTime=(TextView) findViewById(R.id.tvTime);
        btnHome=(Button) findViewById(R.id.btnHome);
        btnAwards=(Button) findViewById(R.id.btnPlay);
        listView=(ListView) findViewById(R.id.listResult);
        tvLevel=(TextView)findViewById(R.id.tvLevel);
    }
}