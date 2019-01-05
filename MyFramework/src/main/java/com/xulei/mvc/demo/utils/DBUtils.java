package com.gupaoedu.vip.mvc.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {


    private static final String driver="com.mysql.jdbc.Driver";
    private static final String url="jdbc:mysql://127.0.0.1:3306/dataBook?useUnicode=true&amp;characterEncoding=utf-8";
    private static final String userName="root";
    private static final String password="root";


    private static Connection conn=null;

    //1:获取连接

    public static Connection getConnection(){

        System.out.println("-----------------开始获取数据库连接了--------------------------");
        try {
            //1:注册驱动
            Class.forName(driver);
            //2:获得数据库链接
            conn = DriverManager.getConnection(url,userName,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }


//    public static  Object executeQuerySQl(String sql) throws SQLException {
//
//        Connection connection = getConnection();
//
//        Statement statement = connection.createStatement();
//
//        ResultSet resultSet = statement.executeQuery(sql);
//
//        while (resultSet.next()){
//
//            String data = resultSet.getString(1);
//            return data;
//        }
//
//        return null;
//    }

    //关闭连接
    public static void closeConnection() {

        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
