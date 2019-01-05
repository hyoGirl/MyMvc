package com.gupaoedu.vip.mvc.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {


    private static final String driver="com.mysql.jdbc.Driver";
    private static final String url="jdbc:mysql://127.0.0.1:3306/dataBook?useUnicode=true&amp;characterEncoding=utf-8";
    private static final String userName="root";
    private static final String password="root";


//    private static Connection conn=null;

    //如果在多线程情况下，如果所有线程都公用一个连接。会可能出现线程1关闭线程2的连接。所以需要每一个线程都拥有自己的连接
    //使用ThreadLocal来实现

    private static ThreadLocal<Connection> connContainers=new ThreadLocal<Connection>();



    //1:获取连接
    public static Connection getConnection(){

        System.out.println("-----------------开始获取数据库连接了--------------------------");

        Connection conn = connContainers.get();
        try {
            if(conn==null){
                //1:注册驱动
                Class.forName(driver);
                //2:获得数据库链接
                conn = DriverManager.getConnection(url,userName,password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connContainers.set(conn);
        }
        return conn;

    }


    //关闭连接
    public static void closeConnection() {
        Connection conn = connContainers.get();
        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connContainers.remove();
        }
    }
}
