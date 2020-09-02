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

import static com.example.bookkeeping.others.ApiImpl.*;
import static com.example.bookkeeping.others.Constant.*;

public class AdminAddPage extends AppCompatActivity {

    private int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_add_page);

        adminId = getIntent().getIntExtra("adminId", 0);

        final EditText usernameEdit = findViewById(R.id.ADMIN_ADD_please_input_username);
        final EditText decimalEdit = findViewById(R.id.ADMIN_ADD_please_input_decimal);

        Button addUser = findViewById(R.id.ADMIN_ADD_add_user);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (decimalEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入初始金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                String username = usernameEdit.getText().toString();
                BigDecimal decimal = new BigDecimal(decimalEdit.getText().toString()).setScale(2);

                if (decimal.compareTo(new BigDecimal("0.00")) < 0 || decimal.compareTo(new BigDecimal("99999999")) > 0) {
                    Toast.makeText(getApplicationContext(), "请输入正确的金额", Toast.LENGTH_SHORT).show();
                    decimalEdit.setText(null);
                    return;
                }

                int statusCode = registerUserByAdmin(adminId, username, decimal);
                if (statusCode == SUCCESS) {
                    Toast.makeText(getApplicationContext(), "注册成功! 初始密码为 123456, 请登录后修改密码", Toast.LENGTH_LONG).show();
                    usernameEdit.setText(null);
                    decimalEdit.setText(null);
                } else if (statusCode == USERNAME_OCCUPIED) {
                    Toast.makeText(getApplicationContext(), "用户名被占用", Toast.LENGTH_SHORT).show();
                    usernameEdit.setText(null);
                } else Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

        Button addAdmin = findViewById(R.id.ADMIN_ADD_add_admin);
        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = usernameEdit.getText().toString();
                int statusCode = registerAdminByAdmin(adminId, username);
                if (statusCode == SUCCESS) {
                    Toast.makeText(getApplicationContext(), "注册成功! 初始密码为 123456, 请登录后修改密码", Toast.LENGTH_LONG).show();
                    usernameEdit.setText(null);
                    decimalEdit.setText(null);
                } else if (statusCode == USERNAME_OCCUPIED) {
                    Toast.makeText(getApplicationContext(), "用户名被占用", Toast.LENGTH_SHORT).show();
                    usernameEdit.setText(null);
                } else Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }

        });

        Button back = findViewById(R.id.ADMIN_ADD_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}