package com.example.arithmeticgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack;
import com.huawei.hms.audiokit.player.manager.HwAudioConfigManager;
import com.huawei.hms.audiokit.player.manager.HwAudioManager;
import com.huawei.hms.audiokit.player.manager.HwAudioManagerFactory;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerConfig;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerManager;
import com.huawei.hms.audiokit.player.manager.HwAudioQueueManager;
import com.huawei.hms.audiokit.player.manager.HwAudioStatusListener;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {
    Button btnLogIn,btnOpen,btnPause;
    EditText edtUser,edtPass;
    TextView tvForget,tvRegister;
    DatabaseReference mData;
    FirebaseAuth mAuth;
    BannerView bannerView;
    private HwAudioPlayerManager mHwAudioPlayerManager;
    private HwAudioConfigManager mHwAudioConfigManager;
    private HwAudioQueueManager mHwAudioQueueManager;
    HwAudioStatusListener mPlayListener;
    List<HwAudioPlayItem> playItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth=FirebaseAuth.getInstance();
        getViews();
        ReceiveSU();
        //addAds();
        loadAds();
        createHwAudioManager();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LogIn.this,SignUp.class);
                startActivity(i);
            }
        });
        bannerView.setAdListener(adListener);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playOnlineList();
                //setVolume(50);
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

    }

    public void getViews(){
        btnLogIn=(Button) findViewById(R.id.btnSignIn);
        edtUser=(EditText) findViewById(R.id.tvUser);
        edtPass=(EditText) findViewById(R.id.edtPaswd);
        tvForget=(TextView) findViewById(R.id.tvForget);
        tvRegister=(TextView)findViewById(R.id.tvSignUp);
        bannerView = (BannerView) findViewById(R.id.hw_banner_view);
        btnOpen=(Button)findViewById(R.id.btnOpenMusic);
        btnPause=(Button)findViewById(R.id.btnPauseMusic);
    }

    /*public void addAds(){
        BannerView bannerView = new BannerView(this);
        bannerView.setAdId("testw6vs28auh3");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        FrameLayout adFrameLayout = findViewById(R.id.ads_frame);
        adFrameLayout.addView(bannerView);
    }*/

    public void loadAds(){
        bannerView.setAdId("testw6vs28auh3");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        bannerView.setBannerRefresh(30);
        AdParam adParam = new AdParam.Builder().build();
        bannerView.loadAd(adParam);
    }
    private AdListener adListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            // Called when an ad is loaded successfully.
        }
        @Override
        public void onAdFailed(int errorCode) {
            // Called when an ad fails to be loaded.
        }
        @Override
        public void onAdOpened() {
            // Called when an ad is opened.
        }
        @Override
        public void onAdClicked() {
            // Called when an ad is clicked.
        }
        @Override
        public void onAdLeave() {
            // Called when an ad leaves an app.
        }
        @Override
        public void onAdClosed() {
            // Called when an ad is closed.
        }
    };

    public void LogIn(){
        String email=edtUser.getText().toString();
        String paswd=edtPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email,paswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogIn.this,"Log in successful",Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(LogIn.this,Home.class);
                            intent.putExtra("email",edtUser.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LogIn.this,"Log in fail",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            reload();
                        }
                    }
                });
        //mData.keepSynced(true);
    }
    private void ReceiveSU(){
        Bundle bSu=getIntent().getExtras();
        if(bSu!=null){
            /*String m= bSu.getString("mail");
            String p=bSu.getString("pass");*/
            edtUser.setText(""+bSu.getString("mail"));
            edtPass.setText(""+bSu.getString("pass"));
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    private void reload() {
        edtUser.setText("");
        edtPass.setText("");
    }
    public void createHwAudioManager() {
        // Create an HwAudioPlayerConfig instance that includes various playback-related configurations. The parameter context cannot be left empty.
        HwAudioPlayerConfig hwAudioPlayerConfig = new HwAudioPlayerConfig(LogIn.this);
        // Add configurations required for creating an HwAudioManager object.
        hwAudioPlayerConfig.setDebugMode(true).setDebugPath("").setPlayCacheSize(20);
        // Create a management instance.
        HwAudioManagerFactory.createHwAudioManager(hwAudioPlayerConfig, new HwAudioConfigCallBack() {
            // Return the management instance through callback.
            @Override
            public void onSuccess(HwAudioManager hwAudioManager) {
                try {
                    Toast.makeText(LogIn.this,"Success",Toast.LENGTH_SHORT).show();
                    // Obtain the playback management instance.
                    mHwAudioPlayerManager = hwAudioManager.getPlayerManager();
                    // Obtain the configuration management instance.
                    mHwAudioConfigManager = hwAudioManager.getConfigManager();
                    // Obtain the queue management instance.
                    mHwAudioQueueManager = hwAudioManager.getQueueManager();
                    // Obtain the sound effect management instance.
                    //mHwAudioEffectManager = hwAudioManager.getEffectManager();
                    hwAudioManager.addPlayerStatusListener(mPlayListener);
                } catch (Exception e) {
                    Toast.makeText(LogIn.this,"Fail: "+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(int errorCode) {
                Toast.makeText(LogIn.this,"Fail: "+errorCode,Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*public List<HwAudioPlayItem> getLocalPlayItemList() {
        // Set the local audio path.
        String path = "";
        // Create an audio object and write audio information into the object.
        HwAudioPlayItem item = new HwAudioPlayItem();
        // Set the audio title.
        item.setAudioTitle("Playing input song");
        // Set the audio ID, which is unique for each audio file. You are advised to set the ID to a hash value.
        item.setAudioId(String.valueOf(path.hashCode()));
        // Set whether an audio file is online (1) or local (0).
        item.setOnline(0);
        // Pass the local audio path.
        item.setFilePath(path);
        playItemList.add(item);
        return playItemList;
    }*/
    public List<HwAudioPlayItem> getOnlinePlayItemList() {
        playItemList = new ArrayList<>();
        // Create an audio object audioPlayItem1 and write information about the song "chengshilvren" into the object.
        HwAudioPlayItem audioPlayItem1 = new HwAudioPlayItem();
        audioPlayItem1.setAudioId("1000");
        audioPlayItem1.setSinger("Taoge");
        audioPlayItem1.setOnlinePath("https://developer.huawei.com/config/file/HMSCore/AudioKit/Taoge-dayu.mp3");
        audioPlayItem1.setOnline(1);
        audioPlayItem1.setAudioTitle("chengshilvren");
        playItemList.add(audioPlayItem1);
        // Create an audio object audioPlayItem2 and write information about the song "dayu" into the object.
        HwAudioPlayItem audioPlayItem2 = new HwAudioPlayItem();
        audioPlayItem2.setAudioId("1001");
        audioPlayItem2.setSinger("Taoge");
        audioPlayItem2.setOnlinePath("https://developer.huawei.com/config/file/HMSCore/AudioKit/Taoge-dayu.mp3");
        audioPlayItem2.setOnline(1);
        audioPlayItem2.setAudioTitle("dayu");
        playItemList.add(audioPlayItem2);
        // Create an audio object audioPlayItem3 and write information about the song "wangge" into the object.
        HwAudioPlayItem audioPlayItem3 = new HwAudioPlayItem();
        audioPlayItem3.setAudioId("1002");
        audioPlayItem3.setSinger("Taoge");
        audioPlayItem3.setOnlinePath("https://developer.huawei.com/config/file/HMSCore/AudioKit/Taoge-wangge.mp3");
        audioPlayItem3.setOnline(1);
        audioPlayItem3.setAudioTitle("wangge");
        playItemList.add(audioPlayItem3);
        return playItemList;
    }
    public void playOnlineList() {
        if (mHwAudioPlayerManager != null) {
            mHwAudioPlayerManager.playList(getOnlinePlayItemList(), 0, 0);
        }
    }
    public void pause() {
        if (mHwAudioPlayerManager == null) {
           Toast.makeText(LogIn.this,"Pause Error",Toast.LENGTH_SHORT).show();
            return;
        }
        mHwAudioPlayerManager.pause();
    }
    /*public void setVolume(int volume){
    if (mHwAudioPlayerManager == null) {
        Toast.makeText(LogIn.this," set volume err",Toast.LENGTH_SHORT).show();
        return;
    }
        mHwAudioPlayerManager.setVolume(50);
    }
    public void clearPlayCache(){
        if(mHwAudioConfigManager!=null){
            mHwAudioConfigManager.clearPlayCache();
        }
    }
    public boolean isQueueEmpty(){
        if(mHwAudioQueueManager!=null){
            return mHwAudioQueueManager.isQueueEmpty();
        }
        return false;
    }*/
}