package com.example.bookkeeping.page;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookkeeping.R;

import java.math.BigDecimal;

import static com.example.bookkeeping.others.ApiImpl.registerUser;
import static com.example.bookkeeping.others.Constant.*;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_register_page);

        Button register = findViewById(R.id.REGISTER_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEdit = findViewById(R.id.REGISTER_please_input_username);
                if (usernameEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = usernameEdit.getText().toString();

                EditText decimalEdit = findViewById(R.id.REGISTER_please_input_decimal);
                if (decimalEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入初始金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                BigDecimal decimal = new BigDecimal(decimalEdit.getText().toString()).setScale(2);

                if (decimal.compareTo(new BigDecimal("0.00")) < 0 || decimal.compareTo(new BigDecimal("99999999")) > 0) {
                    Toast.makeText(getApplicationContext(), "请输入正确的金额", Toast.LENGTH_SHORT).show();
                    decimalEdit.setText(null);
                    return;
                }

                int statusCode = registerUser(username, decimal);
                if (statusCode == SUCCESS) {
                    Toast.makeText(getApplicationContext(), "注册成功! 初始密码为 123456, 请登陆后尽快修改密码", Toast.LENGTH_LONG).show();
                    finish();
                } else if (statusCode == USERNAME_OCCUPIED) {
                    Toast.makeText(getApplicationContext(), "用户名被占用!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "网络错误!", Toast.LENGTH_SHORT).show();
            }
        });

        Button back = findViewById((R.id.REGISTER_back));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}