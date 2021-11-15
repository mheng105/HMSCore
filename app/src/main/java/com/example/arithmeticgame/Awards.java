package com.example.arithmeticgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Awards extends AppCompatActivity {
    DbHelper dbHelper;
    Button btnHome;
    TextView tvtop1,tvtop1score,tvtop1time,tvtop2,tvtop2score,tvtop2time,tvtop3,tvtop3score,tvtop3time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards);
        getViews();
        dbHelper=new DbHelper(Awards.this);
        /*int dem = dbHelper.getStudentsCount();
        if(dem==0){
            Toast.makeText(Xephang.this, "Lỗi", Toast.LENGTH_SHORT).show();
        }*/

        ArrayList<BangXH> list=dbHelper.getAllList();
        if(list.size()>0) {
            tvtop1.setText(list.get(0).getNickname());
            tvtop1score.setText(list.get(0).getScore() + "");
            tvtop1time.setText(list.get(0).getTime() + " s");
            if(list.size()>1) {
                tvtop2.setText(list.get(1).getNickname());
                tvtop2score.setText(list.get(1).getScore() + "");
                tvtop2time.setText(list.get(1).getTime() + " s");
            }
            if(list.size()>2) {
                tvtop3.setText(list.get(2).getNickname());
                tvtop3score.setText(list.get(2).getScore() + "");
                tvtop3time.setText(list.get(2).getTime() + " s");
            }
        }

        // ArrayAdapter<Diem> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arraydiem);
        //listView.setAdapter(adapter);

        //else txt2.setText(diem.getName());
        //txt3.setText(arraydiem.get(0).getScore()+"");
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iHome=new Intent(Awards.this,Home.class);
                startActivity(iHome);
            }
        });
    }

    private void getViews(){
        tvtop1=(TextView) findViewById(R.id.tvtop1);
        tvtop1score=(TextView) findViewById(R.id.tvtop1score);
        tvtop1time=(TextView) findViewById(R.id.tvtop1time);
        tvtop2=(TextView) findViewById(R.id.tvtop2);
        tvtop2score=(TextView) findViewById(R.id.tvtop2score);
        tvtop2time=(TextView) findViewById(R.id.tvtop2time);
        tvtop3=(TextView) findViewById(R.id.tvtop3);
        tvtop3score=(TextView) findViewById(R.id.tvtop3score);
        tvtop3time=(TextView) findViewById(R.id.tvtop3time);
        btnHome=(Button)findViewById(R.id.btnHome);
    }
}