package com.example.arithmeticgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class WaitingRoom extends AppCompatActivity {
    private Socket mSocket;
    String username="";
    TextView txtUsername;
    ListView lstUser;
    String id="";
    //ArrayList<UserSocket> list=new ArrayList<UserSocket>();
    ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        getView();
        getUser();
        Intent intent=getIntent();
        username= intent.getStringExtra("name");
        try {
            mSocket = IO.socket("http://192.168.1.234:3000/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
        txtUsername.setText(username);
        mSocket.emit("xemphong",username);
        mSocket.on("hiendanhsach",onHienListUser);
        mSocket.on("loi-moi",onMoichoi);
        mSocket.on("Phan-hoi",onPhanhoi);
    }
    private void getView(){
        txtUsername=(TextView)findViewById(R.id.txtUsername);
        lstUser=(ListView)findViewById(R.id.lstUser);
    }
    private void getUser(){
        Bundle b=getIntent().getExtras();
        if(b!=null){
            String user=b.getString("name");
            txtUsername.setText(user);
        }
    }
    final private Emitter.Listener onHienListUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray ListUser;
                    try {

                        ListUser = data.getJSONArray("danhsach");
                        list.clear();
                        for(int i=0;i<ListUser.length();i++){
                            String[] object=ListUser.getString(i).split(",");
                            if(!object[1].equals(username))
                                //list.add(new UserSocket(object[0],object[1]));
                                list.add(String.valueOf(object[1])+",ID:"+String.valueOf(object[0]));
                        }
                        //ArrayAdapter<UserSocket> adapter=new ArrayAdapter<UserSocket>(WaitingRoom.this,android.R.layout.simple_list_item_1);
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(WaitingRoom.this,android.R.layout.simple_list_item_1);
                        adapter.addAll(list);
                        lstUser.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        lstUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //UserSocket clickUser=(UserSocket) (lstUser.getItemAtPosition(position));
                                String choose[]=list.get(position).split(",ID:");
                                /*String idclick=clickUser.getId();
                                String nameclick=clickUser.getName();*/
                                String idclick=choose[1];
                                String nameclick=choose[0];
                                //lstUser.getItemAtPosition(position).toString();
                                mSocket.emit("gui-yeu-cau-choi",idclick,nameclick,username);
                            }
                        });

                    } catch (JSONException e) {
                        Toast.makeText(WaitingRoom.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                        // return;
                    }
                }
            });
        }
    };
    final private Emitter.Listener onMoichoi= new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String question;
                    try {
                        question = data.getString("loimoi");
                        String[] x=question.split(",");
                        AlertDialog.Builder builder=new AlertDialog.Builder(WaitingRoom.this);
                        builder.setMessage(x[1]+" moi ban choi game!");
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                dialog.cancel();
                                mSocket.emit("traloi",x[0],username,x[1],false);
                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                dialog.cancel();
                                Intent intentx=new Intent(WaitingRoom.this,Fight.class);
                                intentx.putExtra("username",username);
                                intentx.putExtra("competitor",x[1]);
                                intentx.putExtra("maphong",username);
                                mSocket.emit("traloi",x[0],username,x[1],true);
                                mSocket.disconnect();
                                mSocket.close();
                                startActivity(intentx);
                            }
                        });

                        AlertDialog alertDialog=builder.create();
                        alertDialog.setTitle("Ban nhan duoc loi moi!");
                        alertDialog.show();
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    final private Emitter.Listener onPhanhoi= new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String name,phanhoi;
                    try {
                        name=data.getString("nguoichoi");
                        phanhoi = data.getString("phanhoi");

                        AlertDialog.Builder builder=new AlertDialog.Builder(WaitingRoom.this);
                        if(phanhoi.equals("false"))
                            builder.setMessage(name+" da tu choi choi voi ban!");
                        else
                            builder.setMessage(name+" da chap nhan choi voi ban!");
                        AlertDialog alertDialog=builder.create();
                        alertDialog.setTitle("Thong bao!");
                        alertDialog.show();
                        if(phanhoi.equals("true"))
                        {
                            Intent intentx=new Intent(WaitingRoom.this,Fight.class);
                            intentx.putExtra("username",username);
                            intentx.putExtra("competitor",name);
                            intentx.putExtra("maphong",name);
                            mSocket.disconnect();
                            mSocket.close();
                            startActivity(intentx);
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
}
