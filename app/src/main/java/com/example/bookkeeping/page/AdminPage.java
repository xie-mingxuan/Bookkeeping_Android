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

import static com.example.bookkeeping.others.ApiImpl.deleteAccount;
import static com.example.bookkeeping.others.Constant.ADMIN;

public class AdminPage extends AppCompatActivity {

    private int adminId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_admin_page);
        Bundle bundle = getIntent().getExtras();
        adminId = bundle.getInt("userId");
        username = bundle.getString("username");
        TextView usernameEdit = findViewById(R.id.ADMIN_username);
        usernameEdit.setText(username);

        Button add = findViewById(R.id.ADMIN_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddPage.class);
                intent.putExtra("adminId", adminId);
                startActivity(intent);
            }
        });

        Button query = findViewById(R.id.ADMIN_query_record);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QueryPage.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userId", adminId);
                bundle.putInt("userType", ADMIN);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button changePassword = findViewById(R.id.ADMIN_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordPage.class);
                intent.putExtra("userId", adminId);
                startActivityForResult(intent, 1);
            }
        });

        Button manageUser = findViewById(R.id.ADMIN_manage_user);
        manageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManageUserPage.class);
                intent.putExtra("adminId", adminId);
                startActivity(intent);
            }
        });

        Button logout = findViewById(R.id.ADMIN_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button delete = findViewById(R.id.ADMIN_delete_account);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPage.this);
                builder.setMessage("确认删除账户?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (adminId == 1) {
                            Toast.makeText(getApplicationContext(), "没想到吧, 这个账户你删不掉^_^", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (deleteAccount(adminId)) {
                            Toast.makeText(getApplicationContext(), "删除成功!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else Toast.makeText(getApplicationContext(), "删除失败!", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == 1 && resultCode == 0)
            finish();
    }
}