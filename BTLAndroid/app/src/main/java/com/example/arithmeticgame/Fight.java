package com.example.arithmeticgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Fight extends AppCompatActivity {
    Button btnAnswer1,btnAnswer2,btnAnswer3,btnAnswer4;
    TextView Name1,Name2,Score1,Score2,operation1,operation2,operation3,socau;
    private Socket mSocket;
    String usernamefull;
    String competitorName;
    String idphong="";
    int numberQuestion=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        getViews();
        Intent intent=getIntent();
        usernamefull=intent.getStringExtra("username").toString();
        competitorName=intent.getStringExtra("competitor");
        idphong=intent.getStringExtra("maphong").toString();
        Name1.setText(competitorName);
        Name2.setText(usernamefull);
        try {
            mSocket = IO.socket("http://192.168.1.234:3000/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
        mSocket.emit("choi",usernamefull,idphong);
        mSocket.on("cau-hoi",onPlay);
        mSocket.on("cong-diem",onSuccess);
        mSocket.on("ket-thuc",onEnd);
    }
    private void getViews(){
        Name1=(TextView)findViewById(R.id.Name1);
        Name2=(TextView)findViewById(R.id.Name2);
        btnAnswer1= findViewById(R.id.btnAnswer1);
        btnAnswer2=(Button) findViewById(R.id.btnAnswer2);
        btnAnswer3=(Button) findViewById(R.id.btnAnswer3);
        btnAnswer4=(Button) findViewById(R.id.btnAnswer4);
        Score1=(TextView) findViewById(R.id.Score1);
        Score2=(TextView) findViewById(R.id.Score2);
        operation1=(TextView) findViewById(R.id.operation1);
        operation2=(TextView) findViewById(R.id.operation2);
        operation3=(TextView) findViewById(R.id.operation3);
        socau=findViewById(R.id.socau);
        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer so1=Integer.parseInt(operation1.getText().toString());
                Integer so2=Integer.parseInt(operation3.getText().toString());
                String dapan="";
                numberQuestion=Integer.parseInt(socau.getText().toString().split("/")[0]);
                switch (operation2.getText().toString()){
                    case "+":
                        if(Integer.parseInt(btnAnswer1.getText().toString())==so1+so2) dapan="true";
                        else dapan="false";
                        break;
                    case "-":
                        if(Integer.parseInt(btnAnswer1.getText().toString())==so1-so2) dapan="true";
                        else dapan="false";
                        break;
                    case "*":
                        if(Integer.parseInt(btnAnswer1.getText().toString())==so1*so2) dapan="true";
                        else dapan="false";
                        break;
                    default: break;
                }
                mSocket.emit("dap-an",dapan,numberQuestion,idphong);
            }
        });
        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer so1=Integer.parseInt(operation1.getText().toString());
                Integer so2=Integer.parseInt(operation3.getText().toString());
                numberQuestion=Integer.parseInt(socau.getText().toString().split("/")[0]);
                String dapan="";
                switch (operation2.getText().toString()){
                    case "+":
                        if(Integer.parseInt(btnAnswer2.getText().toString())==so1+so2) dapan="true";
                        else dapan="false";
                        break;
                    case "-":
                        if(Integer.parseInt(btnAnswer2.getText().toString())==so1-so2) dapan="true";
                        else dapan="false";
                        break;
                    case "*":
                        if(Integer.parseInt(btnAnswer2.getText().toString())==so1*so2) dapan="true";
                        else dapan="false";
                        break;
                    default: break;
                }
                mSocket.emit("dap-an",dapan,numberQuestion,idphong);
            }
        });
        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer so1=Integer.parseInt(operation1.getText().toString());
                Integer so2=Integer.parseInt(operation3.getText().toString());
                String dapan="";
                numberQuestion=Integer.parseInt(socau.getText().toString().split("/")[0]);
                switch (operation2.getText().toString()){
                    case "+":
                        if(Integer.parseInt(btnAnswer3.getText().toString())==so1+so2) dapan="true";
                        else dapan="false";
                        break;
                    case "-":
                        if(Integer.parseInt(btnAnswer3.getText().toString())==so1-so2) dapan="true";
                        else dapan="false";
                        break;
                    case "*":
                        if(Integer.parseInt(btnAnswer3.getText().toString())==so1*so2) dapan="true";
                        else dapan="false";
                        break;
                    default: break;
                }
                mSocket.emit("dap-an",dapan,numberQuestion,idphong);
            }
        });
        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer so1=Integer.parseInt(operation1.getText().toString());
                Integer so2=Integer.parseInt(operation3.getText().toString());
                String dapan="";
                numberQuestion=Integer.parseInt(socau.getText().toString().split("/")[0]);
                switch (operation2.getText().toString()){
                    case "+":
                        if(Integer.parseInt(btnAnswer4.getText().toString())==so1+so2) dapan="true";
                        else dapan="false";
                        break;
                    case "-":
                        if(Integer.parseInt(btnAnswer4.getText().toString())==so1-so2) dapan="true";
                        else dapan="false";
                        break;
                    case "*":
                        if(Integer.parseInt(btnAnswer4.getText().toString())==so1*so2) dapan="true";
                        else dapan="false";
                        break;
                    default: break;
                }
                mSocket.emit("dap-an",dapan,numberQuestion,idphong);
            }
        });
    }
    final private Emitter.Listener onSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray question;
                    try {
                        question = data.getJSONArray("congdiem");

                        if(question.getString(2).equals("true")){

                            if(question.getString(0).equals(usernamefull))
                            {
                                btnAnswer1.setEnabled(false);
                                btnAnswer2.setEnabled(false);
                                btnAnswer3.setEnabled(false);
                                btnAnswer4.setEnabled(false);
                                int new_score=Integer.parseInt(Score2.getText().toString())+Integer.parseInt(question.getString(1));
                                Score2.setText(String.valueOf(new_score));
                                Toast.makeText(getApplicationContext(),"Ban tra  loi dung roi",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                int new_score=Integer.parseInt(Score1.getText().toString())+Integer.parseInt(question.getString(1));
                                Score1.setText(String.valueOf(new_score));
                                Toast.makeText(getApplicationContext(),"Doi thu vua ghi diem",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            if(question.getString(2).equals("false")) {
                                if(question.getString(0).equals(usernamefull)) {
                                    btnAnswer1.setEnabled(false);
                                    btnAnswer2.setEnabled(false);
                                    btnAnswer3.setEnabled(false);
                                    btnAnswer4.setEnabled(false);
                                    Toast.makeText(getApplicationContext(), "Ban tra  loi sai roi", Toast.LENGTH_SHORT).show();
                                }
                            }}

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    final private Emitter.Listener onPlay = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray question;
                    try {
                        question = data.getJSONArray("question");
                        operation1.setText(question.getString(0));
                        operation2.setText(question.getString(1));
                        operation3.setText(question.getString(2));
                        numberQuestion=Integer.parseInt(question.getString(3));
                        socau.setText(String.format("%s/%s", numberQuestion,"5"));
                        btnAnswer1.setText(question.getString(4));
                        btnAnswer2.setText(question.getString(5));
                        btnAnswer3.setText(question.getString(6));
                        btnAnswer4.setText(question.getString(7));
                        btnAnswer4.setEnabled(true);
                        btnAnswer3.setEnabled(true);
                        btnAnswer2.setEnabled(true);
                        btnAnswer1.setEnabled(true);

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    final private Emitter.Listener onEnd = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String ketthuc;
                        AlertDialog.Builder builder = new AlertDialog.Builder(Fight.this);
                        // builder.setTitle("Thong bao ket qua");
                        if (Integer.parseInt(Score2.getText().toString()) > Integer.parseInt(Score1.getText().toString())) {
                            builder.setMessage("Chuc mung ban da chien thang " + Name1.getText().toString());
                            builder.setIcon(R.drawable.buchc);
                        }
                        if (Integer.parseInt(Score2.getText().toString()) == Integer.parseInt(Score1.getText().toString())){
                            builder.setMessage("Ban hoa " + Name1.getText().toString());
                            builder.setIcon(R.mipmap.ic_player1);
                        }

                        if (Integer.parseInt(Score2.getText().toString()) < Integer.parseInt(Score1.getText().toString())) {
                            builder.setMessage("Rat tiec ban da thua " + Name1.getText().toString());
                            builder.setIcon(R.mipmap.ic_player2);
                        }
                        builder.setPositiveButton("HOME", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Fight.this, Home.class);
                                intent.putExtra("username",usernamefull);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.setTitle("Result");
                        alertDialog.show();
                    }catch (Exception e){

                    }
                }
            });
        }
    };
}