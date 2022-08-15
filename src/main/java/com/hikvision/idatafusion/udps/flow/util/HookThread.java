package com.hikvision.idatafusion.udps.flow.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;

public class HookThread extends Thread{

    private static final Logger log = LoggerFactory.getLogger(HookThread.class);

    @Override
    public void run() {
        log.info("start");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }
        String url = "jdbc:mysql://localhost:3306/mujunpeng";//连接到自己的数据库，我链接的数据库为student_data
        String user = "root";//数据库登录用户名
        String password = "113270";//数据库登录密码
        String sql_name = "tahookTestTable";//需要插入的表名

        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String format = df.format(date);
            String sql = "insert into " + sql_name + "(id,name) values (1," + format + ")";
            System.out.println("insert sql is " + sql);
            boolean resultSet = stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            log.info("exception is " + throwables);
        }finally {

        }

    }
}
