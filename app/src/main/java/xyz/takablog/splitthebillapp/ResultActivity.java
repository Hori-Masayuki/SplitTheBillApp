package xyz.takablog.splitthebillapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ArrayList<Integer> amountList;
    ArrayList<String> strList;
    ListView listView;
    int total = 0;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//        音を鳴らす
        player = MediaPlayer.create(this, R.raw.sound2);
        player.start();

//        結果を出力
        listView = findViewById(R.id.list);
        amountList = new ArrayList<Integer>();
        amountList = getIntent().getIntegerArrayListExtra("list");
        strList = new ArrayList<String>();

        for (int i = 0; i < amountList.size(); i++) {
            strList.add(i + 1 + "人目：" + amountList.get(i) + "円");
            total += amountList.get(i);
        }
        strList.add("合計：" + total + "円");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items, strList);
        listView.setAdapter(adapter);
    }

//    セッティング画面へ遷移
    public void tapSetting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}