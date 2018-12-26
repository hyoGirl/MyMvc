package com.xulei.mvc.framework.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class HandlerAdapter {

    // 这个map里保存的就是controller上的参数信息,就是参数名称和参数的索引（其实有重复的地方）
    private Map<String,Integer> paramMapping;

    public HandlerAdapter(Map<String,Integer> paramMapping){
        this.paramMapping = paramMapping;
    }


    /**
     *  根据request 以及自定义的handler来进行处理 主要目的是用反射调用url对应的method
     * @param request
     * @param response
     * @param handler
     */
    public void handle(HttpServletRequest request, HttpServletResponse response, Handler handler) {


        /**
         *
         * 1：现在已经找到了url中对应的handler。
         * 2： 获取request中传入的请求参数，和参数的值
         * 3： paramMapping中包含了方法中的参数以及参数的下标。要一个个对应起来把值赋进去。
         * 3：然后使用invoke方法来调用对应的method对象，以及传递方法的参数
         */

        Method method = handler.getMethod();



        Parameter[] parameters = method.getParameters();


        //获取请求的参数，传递到对应的方法中
       // 对request.getParameterMap()的返回值使用泛型时应该是Map<String,String[]>形式，
        // 因为有时像checkbox这样的组件会有一个name对应对个value的时候，所以该Map中键值对是<String-->String[]>的实现

//        request中的参数t1=1&t1=2&t2=3形成的map结构：
//
//        key=t1;value[0]=1,value[1]=2
//
//        key=t2;value[0]=3

        //获取请求参数，这个参数并不包含httprequest
        Map<String,String[]> parameterMap = request.getParameterMap();


        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        Object[] paramValues=new Object[parameterTypes.length];


        //要想给参数赋值，需要找到对应的参数

        for(Map.Entry<String,String[]> entry: parameterMap.entrySet()){
            //获取的结果可能就是[1, 2]
            String value = Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
            if(this.paramMapping.isEmpty()){continue;}
                //获取当前参数再map中的下标
             Integer index = paramMapping.get(entry.getKey());
             // 通过创建一个和当前参数相等的数组，来一一赋值
            paramValues[index]=castValue(value,parameterTypes[index]);

        }

        //解决了自定义的参数后，还需要针对这个httpservletrequest response来做处理，所以上面需要传递httpservletrequest response


        String requestName = HttpServletRequest.class.getName();
        if(this.paramMapping.containsKey(requestName)){
            Integer index = paramMapping.get(requestName);
            paramValues[index]=request;
        }

        String responseName = HttpServletResponse.class.getName();
        if(this.paramMapping.containsKey(responseName)){
            Integer index = paramMapping.get(responseName);
            paramValues[index]=response;
        }

        System.out.println(Arrays.toString(paramValues));
        try {
            handler.getMethod().invoke(handler.getController(),paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据参数的类型，将string转换为不同的类型
     * @param value
     * @param parameterType
     * @return
     */
    private Object castValue(String value, Class<?> parameterType) {
            if(parameterType ==  String.class){
                return value;
            }else if(parameterType ==Integer.class){
                return Integer.valueOf(value);
            }else if(parameterType == int.class){
                return Integer.parseInt(value);
            }else{
                return  null;
            }
    }
}
