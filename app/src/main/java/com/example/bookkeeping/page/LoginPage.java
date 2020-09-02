package com.example.bookkeeping.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookkeeping.R;
import com.example.bookkeeping.entity.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.bookkeeping.others.ApiImpl.getHTTPConnection;
import static com.example.bookkeeping.others.Constant.API_USER_LOGIN;

public class LoginPage extends AppCompatActivity {
    private String username;
    private String password;
    private final User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_login_page);

        Button login = findViewById(R.id.LOGIN_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEdit = findViewById(R.id.LOGIN_please_input_username);
                if (usernameEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                username = usernameEdit.getText().toString();

                EditText passwordEdit = findViewById(R.id.LOGIN_please_input_password);
                if (passwordEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                password = passwordEdit.getText().toString();

                if (username.equals("") || password.equals("")) return;

                if (!loginByUsername(username, password)) {
                    Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                Intent intent;
                if (user.getUserType() == 1) {
                    intent = new Intent(getApplicationContext(), UserPage.class);
                } else intent = new Intent(getApplicationContext(), AdminPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", user.getUsername());
                bundle.putInt("userId", user.getUserId());
                bundle.putInt("userType", user.getUserType());
                bundle.putString("money", user.getMoney().toString());
                intent.putExtras(bundle);
                startActivity(intent);

                user.setUsername(null);
                passwordEdit.setText(null);
            }
        });

        Button exit = findViewById(R.id.LOGIN_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        Button register = findViewById(R.id.LOGIN_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(intent);
            }
        });
    }

    private boolean loginByUsername(final String username, final String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = getHTTPConnection(API_USER_LOGIN, "POST");
                    String body = "username=" + username + "&password=" + password;
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(body.getBytes());
                    outputStream.close();

                    int res = connection.getResponseCode();
                    if (res == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String string;
                        StringBuilder sb = new StringBuilder();
                        while ((string = reader.readLine()) != null) sb.append(string);
                        JSONObject object = new JSONObject(sb.toString());
                        if (object.getInt("status") != 0) return;
                        JSONObject data = (JSONObject) object.get("data");
                        user.setUserId(data.getInt("userId"));
                        user.setUsername(data.getString("username"));
                        user.setUserType(data.getInt("userType"));
                        user.setMoney(new BigDecimal(data.getString("money")));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            return false;
        }
        return user.getUsername() != null;
    }
}
