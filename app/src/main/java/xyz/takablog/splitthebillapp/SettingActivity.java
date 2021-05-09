package xyz.takablog.splitthebillapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    EditText editAmount, editMembers;
    int amount, members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        editAmount = findViewById(R.id.edit_totalAmount);
        editMembers = findViewById(R.id.edit_members);
    }

//    マイルドモード
    public void tapMild(View view) {
        intent(0);
    }

//    ハードモード
    public void tapHard(View view) {
        intent(1);
    }

//    一人無料モード
    public void tapFree(View view) {
        try {
            amount = Integer.parseInt(editAmount.getText().toString());
            members = Integer.parseInt(editMembers.getText().toString());
            if (members == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_write).show();
                return;
            }
            Intent intent = new Intent(this, FreeModeActivity.class);
            intent.putExtra("amount", amount);
            intent.putExtra("members", members);
            startActivity(intent);
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setMessage(R.string.dialog_writeNumber).show();
        }
    }

//    きっちりモード
    public void tapSharp(View view) {
        intent(3);
    }

//    画面遷移するときの例外処理
    public void intent(int mode) {
        try {
            amount = Integer.parseInt(editAmount.getText().toString());
            members = Integer.parseInt(editMembers.getText().toString());
            if (members == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage(R.string.dialog_write).show();
                return;
            }
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("amount", amount);
            intent.putExtra("members", members);
            intent.putExtra("mode", mode);
            startActivity(intent);
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setMessage(R.string.dialog_writeNumber).show();
        }
    }

//    ホーム画面へ遷移
    public void tapHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}