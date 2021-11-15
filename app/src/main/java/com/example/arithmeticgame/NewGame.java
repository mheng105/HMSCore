package com.example.arithmeticgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class NewGame extends AppCompatActivity {
    Button btnBack,btnNext;
    RadioButton rbtnEasy,rbtnHard,rbtnNormal;
    EditText edtTextPersonName;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        getView();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NewGame.this,Home.class);
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
                if(edtTextPersonName.getText().toString().isEmpty()){
                    Toast.makeText(NewGame.this,"Bạn chưa nhập Nickname",Toast.LENGTH_SHORT).show();
                }
                else{
                if (level==0){
                    Toast.makeText(NewGame.this,"Bạn chưa chọn level",Toast.LENGTH_SHORT).show();
                    }
                else
                {
                    Intent intent=new Intent(NewGame.this,Game.class);
                    intent.putExtra("Nickname",edtTextPersonName.getText().toString());
                    intent.putExtra("Level",level);
                    startActivity(intent);
                }
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
        edtTextPersonName=(EditText) findViewById(R.id.edtTextPersonName);
    }
}