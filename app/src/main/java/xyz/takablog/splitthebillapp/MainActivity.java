package xyz.takablog.splitthebillapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        音楽を鳴らす
        player = MediaPlayer.create(this, R.raw.sound4);
        player.start();
    }

//    セッティング画面へ遷移
    public void tapStart(View view) {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

//    遊び方画面へ遷移
    public void tapHowTo(View view) {
        Intent intent = new Intent(this, HowToActivity.class);
        startActivity(intent);
    }
}