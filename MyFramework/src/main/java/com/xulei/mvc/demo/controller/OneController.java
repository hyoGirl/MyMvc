package com.xulei.mvc.demo.controller;

import com.xulei.mvc.demo.service.OneService;
import com.xulei.mvc.framework.annotation.MyAutowired;
import com.xulei.mvc.framework.annotation.MyController;
import com.xulei.mvc.framework.annotation.MyRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MyController
@MyRequestMapping("/web")
public class OneController {

    @MyAutowired
    OneService oneService;

    @MyRequestMapping("/add.json")
    public String add(HttpServletRequest request, HttpServletResponse response){

        String bookName = oneService.findBookNameById(1);

        out(response,"this is json string"+System.lineSeparator());
        out(response,"The BookName  is： "+bookName+System.lineSeparator());
        return null;
    }

    private void out(HttpServletResponse response, String data) {

        try {
            response.getWriter().write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
