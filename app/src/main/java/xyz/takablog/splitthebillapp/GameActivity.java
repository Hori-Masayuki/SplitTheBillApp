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

public class GameActivity extends AppCompatActivity {

    int totalAmount, amount, members, tapNum = 0, r, expectedValue, remMember, mode;
    TextView textOrder, textAmount;
    ImageView imageTile;
    Button btnNext;
    ArrayList<Integer> amountList;
    MediaPlayer player, player2, player5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        amountList = new ArrayList<Integer>();

//        値を取得
        totalAmount = getIntent().getIntExtra("amount", 0);
        members = getIntent().getIntExtra("members", 0);
        remMember = members;
        mode = getIntent().getIntExtra("mode", 0);

        textOrder = findViewById(R.id.textOrder);
        textAmount = findViewById(R.id.text_amount);
        imageTile = findViewById(R.id.imageTile);
        btnNext = findViewById(R.id.btn_next);

        btnState(true, false);
        displayText(tapNum + 1);

//        音を鳴らす
        player = MediaPlayer.create(this, R.raw.sound);
        player2 = MediaPlayer.create(this, R.raw.sound3);
        player5 = MediaPlayer.create(this, R.raw.sound5);
        player2.start();

    }

    public void tapTile(View view) {
//        計算し出力
        btnState(false, true);
        tapNum++;
        imageTile.setImageResource(R.drawable.broken_tile);
        if (tapNum == members) {
            amountList.add(totalAmount);
            textAmount.setText(String.valueOf(totalAmount) + "円");
            if (totalAmount >= 10000) {
                player5.start();
            } else {
                player.start();
            }
            btnNext.setText(R.string.btn_result);
        } else {
            amount = randomAmount(totalAmount);
            textAmount.setText(String.valueOf(amount) + "円");
            if (amount >= 10000) {
                player5.start();
            } else {
                player.start();
            }
            totalAmount -= amount;
        }
    }

    public void tapNext(View view) {
//        ゲーム中なら次の人へ、終了すると結果画面へ遷移
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

    public void displayText(int n) {
        textOrder.setText(String.valueOf(n) + "人目");
    }

//    ランダムな数字を返すメソッド
    public int randomAmount(int total) {
        if (mode == 0) {
            r = total / remMember - total / 10 + (int) Math.floor(Math.random() * total / 5);
        } else if (mode == 3) {
            r = total / remMember;
        } else {
            expectedValue = total / remMember * 2;
            r = (int) Math.floor(Math.random() * expectedValue);
        }
        amountList.add(r);
        remMember--;
        return r;
    }
}