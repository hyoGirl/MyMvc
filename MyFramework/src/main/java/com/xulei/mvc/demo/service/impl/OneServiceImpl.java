package com.xulei.mvc.demo.service.impl;


import com.gupaoedu.vip.mvc.demo.util.DBUtils;
import com.xulei.mvc.demo.service.OneService;
import com.xulei.mvc.framework.annotation.MyService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@MyService
public class OneServiceImpl implements OneService {


    @Override
    public String findBookNameById(int id) {
        return queryBookNameById(id);
    }

    private String queryBookNameById(int id)  {

        try {
            Connection connection = DBUtils.getConnection();

            Statement statement = connection.createStatement();

            String sql="SELECT b_name from book WHERE b_id =1;";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                String data = resultSet.getString(1);

                return data;
            }

            DBUtils.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection();
        }
        return null;
    }
}
