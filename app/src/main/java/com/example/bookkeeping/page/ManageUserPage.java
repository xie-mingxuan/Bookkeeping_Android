package com.example.bookkeeping.page;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bookkeeping.R;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import static com.example.bookkeeping.others.ApiImpl.*;
import static com.example.bookkeeping.others.Constant.*;

public class ManageUserPage extends AppCompatActivity {

    private int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_manage_user_page);

        adminId = getIntent().getIntExtra("adminId", 0);

        Button confirm = findViewById(R.id.MANAGE_USER_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEdit = findViewById(R.id.MANAGE_USER_please_input_username);
                if (usernameEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = usernameEdit.getText().toString();
                EditText decimalEdit = findViewById(R.id.MANAGE_USER_please_input_decimal);
                if (decimalEdit.getText().toString().equals("") || Double.parseDouble(decimalEdit.getText().toString()) == 0.0) {
                    Toast.makeText(getApplicationContext(), "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                BigDecimal decimal = new BigDecimal(decimalEdit.getText().toString());
                try {
                    HttpURLConnection connection = getHTTPConnection(API_ADMIN_MANAGE_MONEY, "POST");
                    String body = "adminId=" + adminId + "&username=" + username + "&decimal=" + decimal;
                    connection.getOutputStream().write(body.getBytes());

                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String s;
                    StringBuilder sb = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((s = reader.readLine()) != null) sb.append(s);
                    JSONObject object = new JSONObject(sb.toString());
                    if (object.getInt("status") != 0)
                        Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        decimalEdit.setText(null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button back = findViewById(R.id.MANAGE_USER_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}