package com.spring.test;

import java.util.Arrays;

public class Test01 {

    public static void main(String[] args) {

//        String[] arr={"1","2"};
//
//
//        String s = Arrays.toString(arr);
//
//        s.replaceAll("\\[\\]","");
//
//        System.out.println(Arrays.toString(arr));

        int[] arr={1,5,2,6,8};

        for(int i=0;i<arr.length-1;i++){

            for(int j=0;j<arr.length-1-i;j++){
                int temp;
                if(arr[j]>arr[j+1]){

                    temp=arr[j];

                    arr[j]=arr[j+1];

                    arr[j+1]=temp;
                }
            }
        }



        System.out.println(Arrays.toString(arr));

    }
}
