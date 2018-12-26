package com.spring.test;

import java.util.Arrays;

public class Test01 {

    public static void main(String[] args) {

        String[] arr={"1","2"};


        String s = Arrays.toString(arr);

        s.replaceAll("\\[\\]","");

        System.out.println(Arrays.toString(arr));

    }
}
