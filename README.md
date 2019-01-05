1：异常BUg
    
    当关闭Tomcat的时候，连接池可能没有及时释放，导致出现报错信息如下：
    
    
    05-Jan-2019 18:37:19.648 信息 [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
    05-Jan-2019 18:37:19.656 信息 [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["ajp-nio-8009"]
    05-Jan-2019 18:37:19.658 信息 [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
    05-Jan-2019 18:37:19.658 信息 [main] org.apache.catalina.startup.Catalina.load Initialization processed in 323 ms
    05-Jan-2019 18:37:19.678 信息 [main] org.apache.catalina.core.StandardService.startInternal Starting service [Catalina]
    05-Jan-2019 18:37:19.678 信息 [main] org.apache.catalina.core.StandardEngine.startInternal Starting Servlet Engine: Apache Tomcat/8.5.35
    05-Jan-2019 18:37:19.686 信息 [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8080"]
    05-Jan-2019 18:37:19.693 信息 [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
    05-Jan-2019 18:37:19.695 信息 [main] org.apache.catalina.startup.Catalina.start Server startup in 36 ms
    Connected to server
    [2019-01-05 06:37:20,114] Artifact MyFramework:war: Artifact is being deployed, please wait...
    05-Jan-2019 18:37:20.597 信息 [RMI TCP Connection(3)-127.0.0.1] org.apache.jasper.servlet.TldScanner.scanJars At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
    名称： [interface com.xulei.mvc.framework.annotation.MyService]
    名称： [interface com.xulei.mvc.demo.service.OneService]
    Mapping: /web/add.json public java.lang.String com.xulei.mvc.demo.controller.OneController.add(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
    我的MVCFramework 开始运行了
    [2019-01-05 06:37:20,643] Artifact MyFramework:war: Artifact is deployed successfully
    [2019-01-05 06:37:20,643] Artifact MyFramework:war: Deploy took 529 milliseconds
    [org.apache.catalina.connector.RequestFacade@64614396, org.apache.catalina.connector.ResponseFacade@325fc49e]
    -----------------开始获取数据库连接了--------------------------
    05-Jan-2019 18:37:29.690 信息 [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory [D:\JavaSoft\apache-tomcat-8.5.35\webapps\manager]
    05-Jan-2019 18:37:29.732 信息 [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [D:\JavaSoft\apache-tomcat-8.5.35\webapps\manager] has finished in [41] ms
    D:\JavaSoft\apache-tomcat-8.5.35\bin\catalina.bat stop
    Using CATALINA_BASE:   "C:\Users\ashura1110\.IntelliJIdea2017.3\system\tomcat\Unnamed_Mymvc"
    Using CATALINA_HOME:   "D:\JavaSoft\apache-tomcat-8.5.35"
    Using CATALINA_TMPDIR: "D:\JavaSoft\apache-tomcat-8.5.35\temp"
    Using JRE_HOME:"F:\java\jdk1.8.0_102\jre"
    Using CLASSPATH:   "D:\JavaSoft\apache-tomcat-8.5.35\bin\bootstrap.jar;D:\JavaSoft\apache-tomcat-8.5.35\bin\tomcat-juli.jar"
    05-Jan-2019 18:37:46.982 信息 [main] org.apache.catalina.core.StandardServer.await A valid shutdown command was received via the shutdown port. Stopping the Server instance.
    05-Jan-2019 18:37:46.983 信息 [main] org.apache.coyote.AbstractProtocol.pause Pausing ProtocolHandler ["http-nio-8080"]
    05-Jan-2019 18:37:47.236 信息 [main] org.apache.coyote.AbstractProtocol.pause Pausing ProtocolHandler ["ajp-nio-8009"]
    05-Jan-2019 18:37:47.493 信息 [main] org.apache.catalina.core.StandardService.stopInternal Stopping service [Catalina]
    05-Jan-2019 18:37:47.497 警告 [localhost-startStop-2] org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesJdbc The web application [ROOT] registered the JDBC driver [com.mysql.jdbc.Driver] but failed to unregister it when the web application was stopped. To prevent a memory leak, the JDBC Driver has been forcibly unregistered.
    05-Jan-2019 18:37:47.498 警告 [localhost-startStop-2] org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesThreads The web application [ROOT] appears to have started a thread named [MySQL Statement Cancellation Timer] but has failed to stop it. This is very likely to create a memory leak. Stack trace of thread:
     java.lang.Object.wait(Native Method)
     java.lang.Object.wait(Object.java:502)
     java.util.TimerThread.mainLoop(Timer.java:526)
     java.util.TimerThread.run(Timer.java:505)
    05-Jan-2019 18:37:47.498 严重 [localhost-startStop-2] org.apache.catalina.loader.WebappClassLoaderBase.checkThreadLocalMapForLeaks The web application [ROOT] created a ThreadLocal with key of type [java.lang.ThreadLocal] (value [java.lang.ThreadLocal@528df931]) and a value of type [com.mysql.jdbc.JDBC4Connection] (value [com.mysql.jdbc.JDBC4Connection@f843cb2]) but failed to remove it when the web application was stopped. Threads are going to be renewed over time to try and avoid a probable memory leak.
    05-Jan-2019 18:37:47.507 信息 [main] org.apache.coyote.AbstractProtocol.stop Stopping ProtocolHandler ["http-nio-8080"]
    05-Jan-2019 18:37:47.508 信息 [main] org.apache.coyote.AbstractProtocol.stop Stopping ProtocolHandler ["ajp-nio-8009"]
    05-Jan-2019 18:37:47.517 信息 [main] org.apache.coyote.AbstractProtocol.destroy Destroying ProtocolHandler ["http-nio-8080"]
    05-Jan-2019 18:37:47.518 信息 [main] org.apache.coyote.AbstractProtocol.destroy Destroying ProtocolHandler ["ajp-nio-8009"]
    05-Jan-2019 18:37:47.543 信息 [Finalizer] org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading Illegal access: this web application instance has been stopped already. Could not load [java.net.BindException]. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
     java.lang.IllegalStateException: Illegal access: this web application instance has been stopped already. Could not load [java.net.BindException]. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
    	at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading(WebappClassLoaderBase.java:1348)
    	at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForClassLoading(WebappClassLoaderBase.java:1336)
    	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1195)
    	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1156)
    	at com.mysql.jdbc.SQLError.createLinkFailureMessageBasedOnHeuristics(SQLError.java:1223)
    	at com.mysql.jdbc.exceptions.jdbc4.CommunicationsException.<init>(CommunicationsException.java:57)
    	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
    	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
    	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
    	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
    	at com.mysql.jdbc.Util.handleNewInstance(Util.java:406)
    	at com.mysql.jdbc.SQLError.createCommunicationsException(SQLError.java:1074)
    	at com.mysql.jdbc.MysqlIO.send(MysqlIO.java:3313)
    	at com.mysql.jdbc.MysqlIO.quit(MysqlIO.java:1667)
    	at com.mysql.jdbc.ConnectionImpl.realClose(ConnectionImpl.java:4409)
    	at com.mysql.jdbc.ConnectionImpl.cleanup(ConnectionImpl.java:1315)
    	at com.mysql.jdbc.ConnectionImpl.finalize(ConnectionImpl.java:2761)
    	at java.lang.System$2.invokeFinalize(System.java:1270)
    	at java.lang.ref.Finalizer.runFinalizer(Finalizer.java:98)
    	at java.lang.ref.Finalizer.access$100(Finalizer.java:34)
    	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:210)
    
    05-Jan-2019 18:37:47.545 信息 [Finalizer] org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading Illegal access: this web application instance has been stopped already. Could not load [java.lang.ExceptionInInitializerError]. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
     java.lang.IllegalStateException: Illegal access: this web application instance has been stopped already. Could not load [java.lang.ExceptionInInitializerError]. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
    	at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading(WebappClassLoaderBase.java:1348)
    	at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForClassLoading(WebappClassLoaderBase.java:1336)
    	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1195)
    	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1156)
    	at com.mysql.jdbc.Util.handleNewInstance(Util.java:426)
    	at com.mysql.jdbc.SQLError.createCommunicationsException(SQLError.java:1074)
    	at com.mysql.jdbc.MysqlIO.send(MysqlIO.java:3313)
    	at com.mysql.jdbc.MysqlIO.quit(MysqlIO.java:1667)
    	at com.mysql.jdbc.ConnectionImpl.realClose(ConnectionImpl.java:4409)
    	at com.mysql.jdbc.ConnectionImpl.cleanup(ConnectionImpl.java:1315)
    	at com.mysql.jdbc.ConnectionImpl.finalize(ConnectionImpl.java:2761)
    	at java.lang.System$2.invokeFinalize(System.java:1270)
    	at java.lang.ref.Finalizer.runFinalizer(Finalizer.java:98)
    	at java.lang.ref.Finalizer.access$100(Finalizer.java:34)
    	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:210)
    
    05-Jan-2019 18:37:47.589 信息 [Finalizer] org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading Illegal access: this web application instance has been stopped already. Could not load [com.mysql.jdbc.profiler.ProfilerEventHandlerFactory]. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
     java.lang.IllegalStateException: Illegal access: this web application instance has been stopped already. Could not load [com.mysql.jdbc.profiler.ProfilerEventHandlerFactory]. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
    	at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading(WebappClassLoaderBase.java:1348)
    	at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForClassLoading(WebappClassLoaderBase.java:1336)
    	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1195)
    	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1156)
    	at com.mysql.jdbc.ConnectionImpl.realClose(ConnectionImpl.java:4433)
    	at com.mysql.jdbc.ConnectionImpl.cleanup(ConnectionImpl.java:1315)
    	at com.mysql.jdbc.ConnectionImpl.finalize(ConnectionImpl.java:2761)
    	at java.lang.System$2.invokeFinalize(System.java:1270)
    	at java.lang.ref.Finalizer.runFinalizer(Finalizer.java:98)
    	at java.lang.ref.Finalizer.access$100(Finalizer.java:34)
    	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:210)
    
    Disconnected from server
    
原因：

ThreadLocal的报错 估计是某些线程的ThreadLocal无法释放，为什么无法释放，因为那些线程还没停掉，每个ThreadLocal都是被一个Thread的ThreadMap下以<ThreadLocalObject, Object>的entry形式维护着，

这些entry继承了WeakReference，

参考文章：https://fourfireliu.iteye.com/blog/2187584