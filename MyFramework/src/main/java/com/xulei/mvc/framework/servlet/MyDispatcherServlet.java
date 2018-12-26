package com.xulei.mvc.framework.servlet;

import com.xulei.mvc.framework.annotation.MyController;
import com.xulei.mvc.framework.annotation.MyRequestMapping;
import com.xulei.mvc.framework.annotation.MyRequestParam;
import com.xulei.mvc.framework.context.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyDispatcherServlet extends HttpServlet {


    private static final String LOCATION = "contextConfigLocation";

    // 这个list里面保存的handler是我们自定义的Handler
    private List<Handler> handlerMappings = new ArrayList<Handler>();

    // 这个map里保存了 handler 和对应的适配器
    private Map<Handler, HandlerAdapter> adapterMapping = new HashMap<Handler, HandlerAdapter>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req,resp);
    }

    //仿照写doDispatch
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {

        //1: 通过请求的uri来取出来一个Handler，从HandlerMapping取
        Handler handler = getHandler(request);

        //2: 再根据这个handler去取出一个适配器，
        HandlerAdapter ha = getHandlerAdapter(handler);
        //3：由适配器来反射调用我们具体的方法。
        ha.handle(request, response, handler);

    }

    /**
     * 根据Handler 来取出具体的Handler
     *
     * @param handler
     * @return
     */
    private HandlerAdapter getHandlerAdapter(Handler handler) {
        if (adapterMapping.isEmpty()) {
            return null;
        }
        return adapterMapping.get(handler);
    }

    /**
     * 主要是通过·url来获取匹配的handler
     *
     * @param request
     * @return
     */
    private Handler getHandler(HttpServletRequest request) {


        if (handlerMappings.isEmpty()) {
            return null;
        }
        // 假如项目为： http://localhost:8080/news/main/list
        // request.getRequestURI() 获取到的就是 /news/main/list

        // request.getServletPath() 获取到的就是 /main/list

        // request.getContextPath() 获取到的就是  /news

        // getRequestURL:  http://localhost:8080/news/main/list
        String uri = request.getRequestURI();

        //小心这个地方为空，会获取不到handler
        String contextPath = request.getContextPath();
        uri = uri.replaceAll(contextPath, "").replaceAll("/+", "/");

        //根据Handler中的正则匹配器来进行匹配
        for (Handler handler : handlerMappings) {
            Matcher matcher = handler.getPattern().matcher(uri);
            if (matcher.matches()) {
                // 如果输入的url能够匹配到方法上requestMapping的值，那么就返回对应的handler
                return handler;
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        MyApplicationContext context = new MyApplicationContext(config.getInitParameter(LOCATION));

        // 请求解析
        this.initMultipartResolver(context);
        // 多语言 国际化
        this.initLocaleResolver(context);
        // 主题View层
        this.initThemeResolver(context);

        //============== 重要 ================
        // 解析url和Method之间的关系
        this.initHandlerMappings(context);
        // 适配器 (匹配过程)
        this.initHandlerAdapters(context);
        //============== 重要 ================


        // 异常解析
        this.initHandlerExceptionResolvers(context);
        // 视图转发
        this.initRequestToViewNameTranslator(context);
        //解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
        this.initViewResolvers(context);

        this.initFlashMapManager(context);


        System.out.println("我的MVCFramework 开始运行了");
    }


    // 解析url和method之间的关系，

    /**
     * 初始化HandlerAdapters。这个主要用来初始化一个list。这个主要是用来初始化handler和对应的HandlerAdapter之间的关系
     *
     * @param context
     */
    private void initHandlerAdapters(MyApplicationContext context) {

        if (handlerMappings.isEmpty()) {
            return;
        }
        //参数类型作为key，参数的索引号作为值
        Map<String, Integer> paramMapping = new HashMap<String, Integer>();
        //方法上的参数是有顺序的，通过反射我们可以获取参数的类型，但是参数的具体名字还是需要通过我们的自定义注解中的value来限定好
        for (Handler handler : handlerMappings) {
            //把这个方法上面所有的参数全部获取到
            Class<?>[] types = handler.getMethod().getParameterTypes();

            //获取自定义的HttpServletRequest 以及 HttpServletResponse
            for (int i = 0; i < types.length; i++) {
                //这个地方是先获取这个type类型，然后才去map中设置value，也就是下标的，不会重复
                Class<?> type = types[i];
                //循环遍历所有的type来设置
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramMapping.put(type.getName(), i);
                }
            }
            // 获取请求参数上的注解,这个是二维数组
            Annotation[][] parameterAnnotations = handler.getMethod().getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation a : parameterAnnotations[i]) {
                    if (a instanceof MyRequestParam) {
                        String value = ((MyRequestParam) a).value();

                        if (!"".equals(value.trim())) {
                            paramMapping.put(value, i);
                        }
                    }
                }
            }
            adapterMapping.put(handler, new HandlerAdapter(paramMapping));
        }
    }

    //适配器（匹配的过程）
    //主要是用来动态匹配我们参数的

    /**
     * 初始化HandlerMappings  该map中保存了handler。这个handler保存了uri和对应的controller关系
     *
     * @param context
     */
    private void initHandlerMappings(MyApplicationContext context) {

        //这个就是一个类似IOC的map

        Map<String, Object> instanceMap = context.getInstanceMap();

        if (instanceMap == null) {
            return;
        }

        //获取controller上的myRequestMapping
        //只要是由Cotroller修饰类，里面方法全部找出来
        //而且这个方法上应该要加了RequestMaping注解，如果没加这个注解，这个方法是不能被外界来访问的

        //requestMapping 会配置一个url，
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(MyController.class)) {
                continue;
            }
            //先获取类上的myrequestMapping中的值
            String uri = "";
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                String value = clazz.getAnnotation(MyRequestMapping.class).value();
                uri = value.trim();
            }
            //扫描这个类下面的方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(MyRequestMapping.class)) {
                    continue;
                }
                MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
                String trim = annotation.value().trim();
                //如果两个都写了单斜杠，必须转换为一个单斜杠
                String regex = (uri + trim).replaceAll("/+", "/");
                //这里使用正则是为了满足pathvaliable这个注解
                Pattern pattern = Pattern.compile(regex);

                //handMappings里面保存的就是handler

                //Handler里面保存的就是拦截器。执行链

                //我们自定义的Handler类里面其实包含着从类似的IOCmap中获取实例化好的类 比如controller和 service  以及这个类中具备的方法，
                // 还有一个包含了当前路径的一个正则比较器

                handlerMappings.add(new Handler(pattern, entry.getValue(), method));

                System.out.println("Mapping: " + regex + " " + method.toString());
            }
        }
    }


    private void initFlashMapManager(MyApplicationContext context) {
    }

    private void initViewResolvers(MyApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(MyApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(MyApplicationContext context) {
    }

    private void initThemeResolver(MyApplicationContext context) {
    }

    private void initLocaleResolver(MyApplicationContext context) {
    }

    private void initMultipartResolver(MyApplicationContext context) {
    }

}
