package com.example.arithmeticgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    SwitchCompat swtSound,swtEffect;
    Button btnPlay,btnHome;
    static boolean isCheckeds=false;
    static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getViews();

        //swtSound.setShowText(false);
        if(isCheckeds==true){
            swtSound.setChecked(true);
        }else
            swtSound.setChecked(false);
        if(isCheckeds==false)
        mediaPlayer =MediaPlayer.create(Settings.this,R.raw.moanh);
        swtSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //swtSound.setShowText(true);    
                    isCheckeds=true;
                    mediaPlayer.start();

                    Toast.makeText(Settings.this, "Turn on", Toast.LENGTH_SHORT).show();
                }else {
                    //swtSound.setShowText(false);
                    isCheckeds=false;
                    mediaPlayer.pause();
                    //mediaPlayer.release();
                    Toast.makeText(Settings.this, "Turn off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        swtEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    swtEffect.setShowText(true);
                    Toast.makeText(Settings.this, "Turn on", Toast.LENGTH_SHORT).show();
                }else {

                    swtEffect.setShowText(false);
                    Toast.makeText(Settings.this, "Turn off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusSound,statusEffect;
                if(swtSound.isChecked()){
                    statusSound=swtSound.getTextOn().toString();
                }else statusSound=swtSound.getTextOff().toString();
                if(swtEffect.isChecked()){
                    statusEffect=swtEffect.getTextOn().toString();
                }else statusEffect=swtEffect.getTextOff().toString();
                Toast.makeText(getApplicationContext(),"Sound: "+statusSound+"\n"+"Effect: "+statusEffect,Toast.LENGTH_SHORT).show();

                Intent iPlay=new Intent(Settings.this,NewGame.class);
                startActivity(iPlay);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iHome=new Intent(Settings.this,Home.class);
                startActivity(iHome);
            }
        });
    }

    private void getViews(){
        swtSound=(SwitchCompat) findViewById(R.id.swtSound);
        swtEffect=(SwitchCompat) findViewById(R.id.swtEffect);
        btnPlay=(Button) findViewById(R.id.btnPlay);
        btnHome=(Button) findViewById(R.id.btnHome);
    }
}