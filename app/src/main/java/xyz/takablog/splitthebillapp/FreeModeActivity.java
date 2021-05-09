package xyz.takablog.splitthebillapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FreeModeActivity extends AppCompatActivity {

    int totalAmount, members, tapNum = 0, r, expectedValue, remMember;
    TextView textOrder, textAmount;
    ImageView imageTile;
    Button btnNext;
    ArrayList<Integer> amountList;
    MediaPlayer player, player2, player5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        player = MediaPlayer.create(this, R.raw.sound);
        player2 = MediaPlayer.create(this, R.raw.sound3);
        player5 = MediaPlayer.create(this, R.raw.sound5);

//        音を鳴らす
        player2.start();
        amountList = new ArrayList<Integer>();

//        値を取得
        totalAmount = getIntent().getIntExtra("amount", 0);
        members = getIntent().getIntExtra("members", 0);
        remMember = members;

//        テキストを取得
        textOrder = findViewById(R.id.textOrder);
        textAmount = findViewById(R.id.text_amount);
        imageTile = findViewById(R.id.imageTile);
        btnNext = findViewById(R.id.btn_next);

        btnState(true, false);
        displayText(tapNum + 1);

//        あらかじめ計算しておき、ランダムに0円の人を配置する
        amountList.add(0);
        remMember--;
        for (int i = 2; i < members; i++) {
            expectedValue = totalAmount / remMember * 2;
            r = (int) Math.floor(Math.random() * expectedValue);
            amountList.add(r);
            totalAmount -= r;
            remMember--;
        }
        amountList.add(totalAmount);
        int n = (int) Math.floor(Math.random() * members);
        amountList.set(0, amountList.get(n));
        amountList.set(n, 0);
    }

//    あらかじめ計算しておいた値を出力
    public void tapTile(View view) {
        btnState(false, true);
        tapNum++;
        imageTile.setImageResource(R.drawable.broken_tile);
        textAmount.setText(String.valueOf(amountList.get(tapNum - 1)) + "円");
        if (amountList.get(tapNum - 1) >= 10000) {
            player5.start();
        } else {
            player.start();
        }
        if (tapNum == members) {
            btnNext.setText(R.string.btn_result);
        }
    }

//    ゲーム中なら次の人へ、終了したら結果画面へ遷移
    public void tapNext(View view) {
        if (tapNum == members) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putIntegerArrayListExtra("list", amountList);
            startActivity(intent);
        } else {
            displayText(tapNum + 1);
            imageTile.setImageResource(R.drawable.unbroken_tile);
            btnState(true, false);
            textAmount.setText(R.string.text_tap);
        }
    }

//    セッティング画面へ遷移
    public void tapSetting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

//    ボタンの状態を変えるメソッド
    public void btnState(boolean tile, boolean next) {
        imageTile.setEnabled(tile);
        btnNext.setEnabled(next);
    }

//    決まったテキストを出力するメソッド
    public void displayText(int n) {
        textOrder.setText(String.valueOf(n) + "人目");
    }
}