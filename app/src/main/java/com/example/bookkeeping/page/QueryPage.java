package com.example.bookkeeping.page;

import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bookkeeping.R;
import com.example.bookkeeping.entity.RecordAdmin;
import com.example.bookkeeping.entity.RecordAdminAdapter;
import com.example.bookkeeping.entity.RecordUser;
import com.example.bookkeeping.entity.RecordUserAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.example.bookkeeping.others.ApiImpl.getHTTPConnection;
import static com.example.bookkeeping.others.Constant.*;

public class QueryPage extends AppCompatActivity {

    private int userType;
    private int userId;
    private List<?> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_query_page);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userType = bundle.getInt("userType");
        userId = bundle.getInt("userId");

        ListAdapter adapter;
        if (userType == USER) {
            list = getRecordUser();
            adapter = new RecordUserAdapter(getApplicationContext(), R.layout.record_user, (List<RecordUser>) list);
        } else {
            list = getRecordAdmin();
            adapter = new RecordAdminAdapter(getApplicationContext(), R.layout.record_admin, (List<RecordAdmin>) list);
        }
        if (list == null)
            Toast.makeText(getApplicationContext(), "获取记录失败", Toast.LENGTH_SHORT).show();
        else {
            ListView listView = findViewById(R.id.QUERY_records);
            listView.setAdapter(adapter);
        }

        Button back = findViewById(R.id.QUERY_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<RecordUser> getRecordUser() {
        List<RecordUser> list = new ArrayList<>();
        try {
            HttpURLConnection connection = getHTTPConnection(API_USER_QUERY_RECORDS + "?userId=" + userId, "GET");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = reader.readLine()) != null) sb.append(str);
            JSONObject object = new JSONObject(sb.toString());
            if (object.getInt("status") != 0) return null;

            JSONArray array = object.getJSONArray("data");
            if (array.length() == 0) return list;
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.getJSONObject(i);
                RecordUser record = new RecordUser();
                record.setDecimal(new BigDecimal(object1.getString("option")).setScale(2));
                record.setTime(object1.getString("time").replace('T',' ').substring(0,19));
                list.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<RecordAdmin> getRecordAdmin() {
        List<RecordAdmin> list = new ArrayList<>();
        try {
            HttpURLConnection connection = getHTTPConnection(API_ADMIN_QUERY_RECORDS + "?adminId=" + userId, "GET");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = reader.readLine()) != null) sb.append(str);
            JSONObject object = new JSONObject(sb.toString());
            if (object.getInt("status") != 0) return null;

            JSONArray array = object.getJSONArray("data");
            if (array.length() == 0) return list;
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.getJSONObject(i);
                RecordAdmin record = new RecordAdmin();
                record.setOptionType(object1.getInt("optionType"));
                record.setDecimal(new BigDecimal(object1.getString("option")).setScale(2));
                record.setTime(object1.getString("time").replace('T',' ').substring(0,19));
                record.setUsername(object1.getString("optionUsername"));
                list.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}