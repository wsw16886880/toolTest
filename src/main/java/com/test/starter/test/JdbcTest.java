package com.test.starter.test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: xq
 * @Date: 2021/03/06 9:31
 */
public class JdbcTest {
    public static void main(String[] args) throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "dmgyz,chlry";
        String url = "jdbc:mysql://localhost:3306/nhcar?serverTimezone=UTC&characterEncoding=utf8";

        Class.forName(driver);

        System.out.println("连接数据库...");
        Connection conn = DriverManager.getConnection(url,username,password);

        System.out.println("实例化statement对象...");
        PreparedStatement preparedStatement = conn.prepareStatement("select * from admin where aname = '111'");

        System.out.println("执行预加载statement...");
        ResultSet resultSet = preparedStatement.executeQuery();


        List<String> mtlParts = new LinkedList<>();
        while (resultSet.next()) {
            String aname = resultSet.getString("aname");
            mtlParts.add(aname);
            System.out.println("aname: " + aname);
        }

        for (int i = 0; i < mtlParts.size(); i++) {
            System.out.println(mtlParts.get(i));
        }

    }
}
