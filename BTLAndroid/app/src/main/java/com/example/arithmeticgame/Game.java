package com.example.arithmeticgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity implements View.OnClickListener {
    Button btnAns1,btnAns2,btnAns3,btnAns4;
    TextView tv1,tv2,tv3,tv4,tv5;
    public static final int CHECK_TRUE=1001;
    public static final int SET_RED=1002;
    public static final int SET_GREEN=1003;
    public static final int SET_OLD=1004;
    private Handler mhandler;
    private String nickname="";
    private int socau=1;
    private int score=0;
    private int level=0;
    private int time=0;
    private int chon=0;
    private int dapan=0;
    private int viewid=0;
    private int so1=0;
    private int so2=0;
    private String dau="";
    protected String choice="a";
    //final long starttime=System.currentTimeMillis();
    ArrayList<String> dscauhoi=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getView();
        Intent intent = getIntent();
        nickname = intent.getStringExtra("Nickname");
        level = intent.getIntExtra("Level", 0);
        tv4.setText("Score: " + score + "   " + socau + "/10");

        inithander();
        createQues();

        btnAns1.setOnClickListener(this);
        btnAns2.setOnClickListener(this);
        btnAns3.setOnClickListener(this);
        btnAns4.setOnClickListener(this);
        demthoigian();


    }

    private void demthoigian() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    Message message=new Message();
                    message.what=CHECK_TRUE;
                    message.arg1=time;
                    mhandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time++;
                } while (true);
            }
        }
        );
        thread.start();
    }

    private void inithander() {
        mhandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case CHECK_TRUE:
                        tv5.setText(String.valueOf(msg.arg1)+" s");
                        break;
                    /*case SET_GREEN:
                        findViewById(msg.arg1).setBackgroundResource(R.color.colorAccent);
                        break;
                    case SET_RED:
                        findViewById(msg.arg1).setBackgroundResource(R.color.colorPrimary);
                        break;
                    case SET_OLD:
                        findViewById(msg.arg1).setBackgroundResource(R.color.yellow);
                        break;*/
                }
            }

        };
    }

    private void getView(){
        btnAns1=(Button) findViewById(R.id.btnAns1);
        btnAns2=(Button) findViewById(R.id.btnAns2);
        btnAns3=(Button) findViewById(R.id.btnAns3);
        btnAns4=(Button) findViewById(R.id.btnAns4);
        tv1=(TextView) findViewById(R.id.tv1);
        tv2=(TextView) findViewById(R.id.tv2);
        tv3=(TextView) findViewById(R.id.tv3);
        tv4=(TextView) findViewById(R.id.tvScore);
        tv5=(TextView) findViewById(R.id.tvTime);

    }

    private void createQues(){
        //mhandler.sendEmptyMessage(SET_OLD);
       /* btnAns1.setBackgroundResource(R.color.yellow);
        btnAns2.setBackgroundResource(R.color.yellow);
        btnAns3.setBackgroundResource(R.color.yellow);
        btnAns4.setBackgroundResource(R.color.yellow);
*/
         int first,second,pheptinh,ans=0,wrong=0,wrong2=0,wrong3=0,pos;
         int max=0,min=0,lech=0;
         Random random=new Random();
         switch(level){
             case 1:
                 max=10;
                 min=0;
                 lech=7;
                 break;
             case 2:
                 max=100;
                 min=10;
                 lech=11;
                 break;
             case 3:
                 max=1000;
                 min=100;
                 lech=101;
                 break;
         }
         first=random.nextInt(max-min)+min;
         second=random.nextInt(max-min)+min;
         pheptinh=random.nextInt(2);
         if(pheptinh==0){
             ans=first+second;
             tv2.setText("+");}
             else{
                 ans=first-second;
                 tv2.setText("-");
             }
             while(wrong==wrong2||wrong==wrong3||wrong2==wrong3||ans==wrong||ans==wrong2||ans==wrong3) {
                 wrong=ans-random.nextInt(lech)-1;
                 wrong2=ans+random.nextInt(lech)+1;
                 wrong3=ans+random.nextInt(lech)-1;
             }
            pos=random.nextInt(4);
            switch (pos) {
                case 0:
                    btnAns1.setText(ans + "");
                    btnAns2.setText(wrong + "");
                    btnAns3.setText(wrong2 + "");
                    btnAns4.setText(wrong3 + "");
                    break;
                case 1:
                    btnAns2.setText(ans + "");
                    btnAns1.setText(wrong + "");
                    btnAns3.setText(wrong2 + "");
                    btnAns4.setText(wrong3 + "");
                    break;
                case 2:
                    btnAns3.setText(ans + "");
                    btnAns2.setText(wrong + "");
                    btnAns1.setText(wrong2 + "");
                    btnAns4.setText(wrong3 + "");
                    break;
                case 3:
                    btnAns4.setText(ans + "");
                    btnAns2.setText(wrong + "");
                    btnAns3.setText(wrong2 + "");
                    btnAns1.setText(wrong3 + "");
                    break;
            }
             tv1.setText(first+"");
             tv3.setText(second+"");
    }

    @Override
    public void onClick(View v) {
        so1=Integer.parseInt(tv1.getText().toString());
        so2=Integer.parseInt(tv3.getText().toString());

        if(tv2.getText().toString().compareTo("+")==0){
            dapan=so1+so2;
            dau="+";
        }
        else{
            dapan=so1-so2;
            dau="-";
        }

        switch(v.getId()){
            case R.id.btnAns1:
                chon=Integer.parseInt(btnAns1.getText().toString());
                break;
            case R.id.btnAns2:
                chon=Integer.parseInt(btnAns2.getText().toString());
                break;
            case R.id.btnAns3:
                chon=Integer.parseInt(btnAns3.getText().toString());
                break;
            case R.id.btnAns4:
                chon=Integer.parseInt(btnAns4.getText().toString());
                break;
        }
        viewid=v.getId();
        CV cv=new CV();
        cv.execute();

        // khandler.postDelayed(new Runnable() {
            //@Override
          //  public void run() {
        /*CountDownTimer timer=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l) {
                if(dapan==chon){
                    score+=10;
                    choice="True";
                    //ms.what=SET_GREEN;
                    findViewById(v.getId()).setBackgroundResource(R.color.colorAccent);
                    Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                else{
                    choice="False";
                    //ms.what=SET_RED;
                    Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {

            }
        }*/

            //}
       // },1000);


        //ms.arg1=v.getId();
        //mhandler.sendMessage(ms);
        /*Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim_active);
        btnAns1.startAnimation(animation);
        btnAns2.startAnimation(animation);
        btnAns3.startAnimation(animation);
        btnAns4.startAnimation(animation);
        tv1.startAnimation(animation);
        tv2.startAnimation(animation);
        tv3.startAnimation(animation);*/




    }
    protected class CV extends AsyncTask<Void,Integer,Integer> {
        /*@Override
        protected void onPreExecute() {

        }
*/
        @Override
        protected Integer doInBackground(Void... voids) {
            int lc = 0;
            if (dapan == chon) {
                score += 10;
                choice="đúng";
                lc = 1;

            } else {
                choice="sai";
                lc = 0;
            }

            publishProgress(lc);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            btnAns1.setBackgroundResource(R.color.yellow);
            btnAns2.setBackgroundResource(R.color.yellow);
            btnAns3.setBackgroundResource(R.color.yellow);
            btnAns4.setBackgroundResource(R.color.yellow);
            String addResult=socau+ ": "+ so1+dau+so2 + " Result: "+dapan+" Ans: "+choice;
            dscauhoi.add(addResult);
            socau++;
            tv4.setText("Score: "+score+"   "+socau+"/10");
            //long distance =System.currentTimeMillis()-starttime;
            //tv5.setText("Time: "+distance/1000+"s");
            if(socau==11){
                //long timeplay=System.currentTimeMillis()-starttime;
                //int so=(int)(timeplay/1000);
                Toast.makeText(Game.this, "Game Over", Toast.LENGTH_SHORT).show();
                //AlertDialog.Builder alertx=new AlertDialog.Builder();
                Intent move=new Intent(Game.this,ScoreNote.class);
                move.putStringArrayListExtra("res",dscauhoi);
                move.putExtra("Nickname",nickname);
                move.putExtra("Score",score);
                //move.putExtra("Time",so);
                move.putExtra("Level",level);
                move.putExtra("Time",time);
                startActivity(move);
            }else
                createQues();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int lck = values[0];
            if (lck == 0)
               // findViewById(viewid).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                 findViewById(viewid).setBackgroundResource(R.color.red);
            else
                findViewById(viewid).setBackgroundResource(R.color.green);
        }
    }
}


