package com.xulei.mvc.framework.context;

import com.xulei.mvc.framework.annotation.MyAutowired;
import com.xulei.mvc.framework.annotation.MyController;
import com.xulei.mvc.framework.annotation.MyService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {


      /*
        1：读取配置文件
        2：扫描指定包下面的文件
        3: 把这些扫描到的包下面的class实例化 BeanFactory
        4：根据注解来进行依赖关系的建立。实现自动依赖
        5：构建请求的url和访问的method之间的关系
        6：结束请求，输出响应

     */

    private Properties properties = new Properties();


    ////检查看有没有注册信息,注册信息里面保存了所有的class名字
    private List<String> cache = new ArrayList<String>();

    //类似IOC的map
    private Map<String, Object> instanceMap = new ConcurrentHashMap<String, Object>();


    public Map<String, Object> getInstanceMap(){

        return instanceMap;
    }


    public MyApplicationContext(String location) {

        InputStream ins = null;

        //1:定位
        ins = this.getClass().getClassLoader().getResourceAsStream(location);

        //2：载入
        try {
            properties.load(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3:注册 找到所有的class
        String scanPackage = properties.getProperty("scanPackage");
        doRegister(scanPackage);
        //4: 实例化需要ioc的对象(就是加了@Service，@Controller)，只要循环class了
        doCreateBean();
        //5、注入
        populate();

    }


    /**
     * 加载所有需要进行扫描的类
     *
     * @param scanPackage
     */
    private void doRegister(String scanPackage) {

        //将配置的包名转换为实际文件存在的路径，然后逐步加载

        //都是加载为class文件了
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.", "/"));

        File dir = new File(url.getFile());
        if (!dir.exists()) {
            return;
        }
        ;
        for (File file1 : dir.listFiles()) {
            if (file1.isDirectory()) {
                doRegister(scanPackage + "." + file1.getName());
            } else {
                //如果是文件，那么就开始
                cache.add(scanPackage + "." + file1.getName().replaceAll(".class", "").trim());
            }
        }
    }

    /**
     * 实现依赖注入
     */
    private void populate() {

        //简单点就是获取了属性字段的名字来设置到实体类中去
        try {
            if(instanceMap ==null){ return;}
            for (Map.Entry<String,Object> entry:instanceMap.entrySet()) {
                Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if(!declaredField.isAnnotationPresent(MyAutowired.class)){
                        continue;
                    }
                    MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);

                    String id = annotation.value().trim();
                    if("".equals(id)){
                        id=declaredField.getType().getName();
                    }
                    declaredField.setAccessible(true);
                    declaredField.set(entry.getValue(),instanceMap.get(id));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    /**
     * 实例化每一个bean。就是判断这个类上面有没有自定义的注解比如@service  @controller
     */
    private void doCreateBean() {

        //检查那些类需要初始化

        try {
            if (cache.size() == 0) {
                return;
            }

            for (String className : cache) {
                Class<?> clazz = Class.forName(className);
//                if (!clazz.isAnnotation()) {
//                    continue;
//                }
                //如果上面有


                //如果上面有mycontroller的注解。那么就将类名和类实例保存到map中
                if (clazz.isAnnotationPresent(MyController.class)) {
                    //获取类名首字母小写。
                    String id = getFirstLower(clazz.getSimpleName());
                    instanceMap.put(id, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(MyService.class)) {

                    //如果加上了service的注解，实际上是要获取了接口的类型。注解是放在实体类上的。
                    MyService myService = clazz.getAnnotation(MyService.class);
                    String value = myService.value();
                    if (!"".equals(value)) {
                        instanceMap.put(value, clazz.newInstance());
                    }

                    //如果是空的，就用默认规则
                    //1、类名首字母小写
                    //如果这个类是接口
                    //2、可以根据类型类匹配
                    System.out.println("名称： "+ Arrays.toString(myService.getClass().getInterfaces()));
                    System.out.println("名称： "+Arrays.toString(clazz.getInterfaces()));

                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
//                        instanceMap.put(getFirstLower(anInterface.getName()), clazz.newInstance());
                        instanceMap.put(anInterface.getName(), clazz.newInstance());
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String getFirstLower(String simpleName) {

        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);


    }
}
