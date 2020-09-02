package com.example.bookkeeping.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookkeeping.R;

import static com.example.bookkeeping.others.ApiImpl.changePassword;

public class ChangePasswordPage extends AppCompatActivity {

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_change_password_page);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);

        Button confirm = findViewById(R.id.CHANGE_PASSWORD_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText oldPasswordEdit = findViewById(R.id.CHANGE_PASSWORD_please_input_old_password);
                EditText newPasswordEdit = findViewById(R.id.CHANGE_PASSWORD_please_input_new_password);
                if (oldPasswordEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入原始密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPasswordEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String oldPassword = oldPasswordEdit.getText().toString();
                String newPassword = newPasswordEdit.getText().toString();

                if (oldPassword.equals(newPassword)) {
                    Toast.makeText(getApplicationContext(), "新密码与原始密码不能相同", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (changePassword(userId, oldPassword, newPassword)) {
                    setResult(0);
                    Toast.makeText(getApplicationContext(), "修改成功, 请重新登陆", Toast.LENGTH_SHORT).show();
                    finish();
                } else Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
            }
        });

        Button back = findViewById(R.id.CHANGE_PASSWORD_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(1);
                finish();
            }
        });
    }
}