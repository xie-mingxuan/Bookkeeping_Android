package com.example.bookkeeping.page;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookkeeping.R;
import com.example.bookkeeping.entity.User;

import java.math.BigDecimal;

import static com.example.bookkeeping.others.ApiImpl.deleteAccount;
import static com.example.bookkeeping.others.ApiImpl.manageMoney;
import static com.example.bookkeeping.others.Constant.USER;

public class UserPage extends AppCompatActivity {
    private final User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_user_page);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user.setUserId(bundle.getInt("userId"));
        user.setUsername(bundle.getString("username"));
        user.setUserType(bundle.getInt("userType"));
        user.setMoney(new BigDecimal(bundle.getString("money")));

        TextView username = findViewById(R.id.USER_username);
        username.setText(user.getUsername());
        final TextView money = findViewById(R.id.USER_money);
        money.setText(user.getMoney().setScale(2).toString());

        final Button confirm = findViewById(R.id.USER_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText change = findViewById(R.id.USER_please_input_manage_money);
                String str = change.getText().toString();
                if (str.equals("")) return;
                if (Double.parseDouble(str) == 0.0) {
                    Toast.makeText(getApplicationContext(), "请输入非零值", Toast.LENGTH_SHORT).show();
                    return;
                }
                BigDecimal decimal = new BigDecimal(str);
                EditText explanatory = findViewById(R.id.USER_please_input_explanatory);
                String explain = explanatory.getText().toString();
                if (explain.equals("")) explain = "null";
                if (!manageMoney(user.getUserId(), decimal, explain)) {
                    Toast.makeText(getApplicationContext(), "余额变动失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setMoney(user.getMoney().add(decimal).setScale(2));
                change.setText(null);
                explanatory.setText(null);
                money.setText(user.getMoney().toString());
            }
        });

        Button query = findViewById(R.id.USER_query_record);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("userType", USER);
                bundle.putInt("userId", user.getUserId());
                Intent intent = new Intent(getApplicationContext(), QueryPage.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button changePassword = findViewById(R.id.USER_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordPage.class);
                intent.putExtra("userId", user.getUserId());
                startActivityForResult(intent, 0);
            }
        });

        Button logout = findViewById(R.id.USER_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "注销成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button delete = findViewById(R.id.USER_delete_account);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPage.this);
                builder.setMessage("确认删除账户?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (deleteAccount(user.getUserId())) {
                            Toast.makeText(getApplicationContext(), "删除成功!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else Toast.makeText(getApplicationContext(), "删除失败!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == 0 && resultCode == 0)
            finish();
    }
}
