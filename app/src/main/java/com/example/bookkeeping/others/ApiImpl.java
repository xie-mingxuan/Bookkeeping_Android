package com.example.bookkeeping.others;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.bookkeeping.others.Constant.*;

public class ApiImpl {

    /**
     * 获取 HTTPURLConnection 方法
     *
     * @param api           需要访问的 API
     * @param requestMethod 访问方法
     * @return connection
     * @throws Exception e
     */
    public static HttpURLConnection getHTTPConnection(String api, String requestMethod) throws Exception {
        URL url = new URL(api);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        if (requestMethod.equals("POST")) {
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
        }
        connection.setRequestProperty("Connection", "close");
        return connection;
    }

    /**
     * 修改密码方法(通用)
     *
     * @param userId      修改密码账户
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 成功返回 0, 网络错误返回 -1, 原密码错误返回 1
     */
    public static int changePassword(int userId, String oldPassword, String newPassword) {
        try {
            HttpURLConnection connection = getHTTPConnection(API_USER_CHANGE_PASSWORD, "POST");
            String body = "userId=" + userId + "&password=" + oldPassword + "&newPassword=" + newPassword;
            connection.getOutputStream().write(body.getBytes());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return -1;

            String s;
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((s = bufferedReader.readLine()) != null) sb.append(s);
            JSONObject object = new JSONObject(sb.toString());
            if (object.getInt("status") == 0) return 0;
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 注册用户方法
     *
     * @param username 用户名
     * @param decimal  初始金钱
     * @return 成功返回 0, 用户名被占用返回 1, 失败返回 2
     */
    public static int registerUser(String username, BigDecimal decimal) {
        try {
            HttpURLConnection connection = getHTTPConnection(API_USER_REGISTER, "POST");
            String body = "username=" + username + "&money=" + decimal;
            connection.getOutputStream().write(body.getBytes());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return NETWORK_ERROR;

            String s;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((s = reader.readLine()) != null) sb.append(s);
            JSONObject object = new JSONObject(sb.toString());

            if (object.getInt("status") == 0) return SUCCESS;
            if (object.getString("message").equals("用户名已被注册")) return USERNAME_OCCUPIED;
            return NETWORK_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return NETWORK_ERROR;
        }
    }

    /**
     * 管理员注册用户方法
     *
     * @param adminId  管理员 id
     * @param username 用户名
     * @param decimal  初始金额
     * @return 成功返回 0, 用户名被占用返回 1, 失败返回 2
     */
    public static int registerUserByAdmin(int adminId, String username, BigDecimal decimal) {
        try {
            HttpURLConnection connection = getHTTPConnection(API_ADMIN_ADD_USER, "POST");
            String body = "adminId=" + adminId + "&username=" + username + "&decimal=" + decimal;
            connection.getOutputStream().write(body.getBytes());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return NETWORK_ERROR;

            String s;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((s = reader.readLine()) != null) sb.append(s);
            JSONObject object = new JSONObject(sb.toString());

            if (object.getInt("status") == 0) return SUCCESS;
            if (object.getString("message").equals("用户名已被注册")) return USERNAME_OCCUPIED;
            return NETWORK_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return NETWORK_ERROR;
        }
    }

    /**
     * 管理员注册管理员方法
     *
     * @param adminId  管理员 id
     * @param username 用户名
     * @return 成功返回 0, 用户名被占用返回 1, 失败返回 2
     */
    public static int registerAdminByAdmin(int adminId, String username) {
        try {
            HttpURLConnection connection = getHTTPConnection(API_ADMIN_ADD_ADMIN, "POST");
            String body = "adminId=" + adminId + "&username=" + username;
            connection.getOutputStream().write(body.getBytes());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return NETWORK_ERROR;

            String s;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((s = reader.readLine()) != null) sb.append(s);
            JSONObject object = new JSONObject(sb.toString());

            if (object.getInt("status") == 0) return SUCCESS;
            if (object.getString("message").equals("用户名已被注册")) return USERNAME_OCCUPIED;
            return NETWORK_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return NETWORK_ERROR;
        }
    }

    /**
     * 修改账户余额方法
     *
     * @param userId      账户 id
     * @param decimal     修改金额
     * @param explanatory 备注
     * @return 成功返回 true
     */
    public static boolean manageMoney(int userId, final BigDecimal decimal, String explanatory) {
        try {
            HttpURLConnection connection = getHTTPConnection(API_USER_MANAGE_MONEY, "POST");
            String body = "userId=" + userId + "&decimal=" + decimal + "&explanatory=" + explanatory;
            connection.getOutputStream().write(body.getBytes());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return false;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;
            StringBuilder sb = new StringBuilder();
            while ((s = reader.readLine()) != null) sb.append(s);
            JSONObject object = new JSONObject(sb.toString());
            return object.getInt("status") == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除用户方法(通用)
     *
     * @param accountId 账户 id
     * @return 成功返回 true
     */
    public static boolean deleteAccount(int accountId) {
        try {
            HttpURLConnection connection = getHTTPConnection(API_USER_DELETE_ACCOUNT, "POST");
            String body = "userId=" + accountId;
            connection.getOutputStream().write(body.getBytes());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return false;

            String s;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((s = reader.readLine()) != null) builder.append(s);
            JSONObject object = new JSONObject(builder.toString());
            return object.getInt("status") == 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
