public interface LoginWebService {
      String webLogin(String loginName,String password);
 
public class LoginWebServiceImpl extends BaseCodServiceImpl implements LoginWebService {
       private UserService userService ;
       private GoodsService goodsService ;
 
public class BaseCodServiceImpl extends BaseServiceImpl {
       protected AreaDAO areaDAO ;
       protected CarInfoDAO carInfoDAO ;
       protected CarKeepDAO carKeepDAO ;
 
public class BaseServiceImpl{
       protected AuthorityDAO authorityDAO ;
       protected CompanyDAO companyDAO ;
 
 
applicationContext-webservice-common.xml
 
<?xml version="1.0" encoding= "UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" 
xmlns:aop="http://www.springframework.org/schema/aop" xmlns:context="http://www.springframework.org/schema/context" 
xmlns:p="http://www.springframework.org/schema/p" xmlns:tx="http://www.springframework.org/schema/tx" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:cache="http://www.springframework.org/schema/cache"
xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd 
http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd 
http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd 
http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
http://www.springframework.org/schema/cache http://www.springframework.org/schema/cache/spring-cache-3.1.xsd">
       <aop:config>
             <aop:pointcut id= "wsServiceManagerMethod" expression="execution(* com.xulin.webservice.impl.*.*(..))" />
             <aop:advisor advice-ref="txAdvice" pointcut-ref="wsServiceManagerMethod" />
       </aop:config>
</beans>
 
xfire-servlet.xml
 
<?xml version="1.0" encoding= "UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN"
"http://www.springframework.org/dtd/spring-beans.dtd" >
<beans>
       <bean id= "crossWebService" class="com.xulin.webservice.impl.CrossWebServiceImpl" >
             <property name="orderService" ref="orderService"></ property>
             <property name="userService" ref="userService"></ property>
       </bean>
       <bean id= "appWebService" class="com.xulin.webservice.impl.AppWebServiceImpl" >
             <property name="orderService" ref="orderService"></ property>
             <property name="userService" ref="userService"></ property>
             <property name="systemInfoService" ref="systemInfoService" ></property>
             <property name="costDetailService" ref="costDetailService" ></property>
             <property name="companyService" ref="companyService" ></property>
             <property name="roleService" ref="roleService"></ property>
             <property name="userRoleService" ref="userRoleService" ></property>
             <property name="roleAuthorityService" ref="roleAuthorityService" ></property>
             <property name="departmentService" ref="departmentService" ></property>
             <property name="clientService" ref="clientService" ></property>
             <property name="logService" ref="logService"></ property>
             <property name="workFlowAuditService" ref="workFlowAuditService" ></property>
             <property name="messageService" ref="messageService" ></property>
             <property name="areaService" ref="areaService"></ property>
             <property name="sendcarService" ref="sendcarService" ></property>
             <property name="goodsService" ref="goodsService"></ property>
             <property name="outboundService" ref="outboundService" ></property>
       </bean>
       <bean id= "loginWebService" class="com.xulin.webservice.impl.LoginWebServiceImpl" >
             <property name="userService" ref="userService"></ property>
             <property name="goodsService" ref="goodsService"></ property>
             <property name="warehouseService" ref="warehouseService" ></property>
             <property name="goodsAllocationService" ref="goodsAllocationService" ></property>
             <property name="systemInfoService" ref="systemInfoService" ></property>
             <property name="outboundService" ref="outboundService" ></property>
             <property name="inboundService" ref="inboundService" ></property>
             <property name="inventoryService" ref="inventoryService" ></property>
             <property name="goodsTypeService" ref="goodsTypeService" ></property>
             <property name="goodsBrandService" ref="goodsBrandService" ></property>
             <property name="carInfoService" ref="carInfoService" ></property>
             <property name="orderService" ref="orderService"></ property>
             <property name="interceptRulesService" ref="interceptRulesService" ></property>
             <property name="workFlowService" ref="workFlowService" ></property>
             <property name="planService" ref="planService"></ property>
             <property name="workFlowAuditService" ref="workFlowAuditService" ></property>
             <property name="messageService" ref="messageService" ></property>
             <property name="goodsDetailService" ref="goodsDetailService" ></property>
             <property name="logService" ref="logService"></ property>
             <property name="costProjectDetailService" ref="costProjectDetailService" ></property>
             <property name="clientService" ref="clientService" ></property>
             <property name="areaService" ref="areaService"></ property>
             <property name="importRecordService" ref="importRecordService" ></property>
             <property name="endLinkManService" ref="endLinkManService" ></property>
             <property name="goodsSupplierService" ref="goodsSupplierService" ></property>
             <property name="orderMergeService" ref="orderMergeService" ></property>
             <property name="orderContainerService" ref="orderContainerService" ></property>
       </bean>
       <!-- 引入XFire预配置信息 -->
       <import resource="classpath:org/codehaus/xfire/spring/xfire.xml" />
       <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping" >
             <property name="urlMap" >
                   <map>
                         <entry key="/CrossWebService.ws" >
                               <ref bean="CrossWebService" />
                         </entry>
                         <entry key="/LoginWebService.ws" >
                               <ref bean="LoginWebService" />
                         </entry>
                         <entry key="/AppWebService.ws" >
                               <ref bean="AppWebService" />
                         </entry>
                   </map>
             </property>
       </bean>
       <!-- 使用XFire导出器 -->
       <bean id= "baseWebService" class="org.codehaus.xfire.spring.remoting.XFireExporter"
             lazy-init="false" abstract= "true">
             <!-- 引用xfire.xml中定义的工厂 -->
             <property name="serviceFactory" ref="xfire.serviceFactory" />
             <!-- 引用xfire.xml中的 xfire实例 -->
             <property name="xfire" ref="xfire" />
       </bean>
       <bean id= "CrossWebService" parent ="baseWebService">
             <!-- 业务服务bean -->
             <property name="serviceBean" ref="crossWebService" />
             <!-- 业务服务bean的窄接口类 -->
             <property name="serviceClass" value="com.xulin.webservice.CrossWebService" />
       </bean>
       <bean id= "LoginWebService" parent ="baseWebService">
             <!-- 业务服务bean -->
             <property name="serviceBean" ref="loginWebService" />
             <!-- 业务服务bean的窄接口类 -->
             <property name="serviceClass" value="com.xulin.webservice.LoginWebService" />
       </bean>
       <bean id= "AppWebService" parent ="baseWebService">
             <!-- 业务服务bean -->
             <property name="serviceBean" ref="appWebService" />
             <!-- 业务服务bean的窄接口类 -->
             <property name="serviceClass" value="com.xulin.webservice.AppWebService" />
       </bean>
</beans>
 
web.xml
 
 <!-- begin XFire 配置 -->
 <servlet >
  <servlet-name >xfire</ servlet-name>
  <servlet-class >
   org.springframework.web.servlet.DispatcherServlet
  </servlet-class >
 </servlet >
 <servlet-mapping >
  <servlet-name >xfire</ servlet-name>
  <url-pattern >*.ws</url-pattern >
 </servlet-mapping >
 <servlet >
  <!-- 配合Spring容器中XFire一起工作的 Servlet-->
  <servlet-name >xfireServlet</ servlet-name>
  <servlet-class >
   org.codehaus.xfire.spring.XFireSpringServlet
  </servlet-class >
 </servlet >
 <servlet-mapping >
  <servlet-name >xfireServlet</ servlet-name>
  <!-- 在这个URI下开放Web Service服务 -->
  <url-pattern >/webservice/* </url-pattern>
 </servlet-mapping >
 <!-- end XFire 配置 -->
 
--- 完整代码
<?xml version="1.0" encoding= "UTF-8"?>
<web-app version="2.5"
       xmlns="http://java.sun.com/xml/ns/javaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
      http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" >
       <error-page>
                   <error-code> 500</error-code >
                   <location> /err500.jsp</location >
       </error-page>
       <error-page>
                   <error-code> 404</error-code >
                   <location> /err404.jsp</location >
       </error-page>
    <context-param >
             <param-name> contextConfigLocation</param-name >
             <param-value> classpath:applicationContext-*.xml,/WEB-INF/xfire-servlet.xml </param-value>
       </context-param>
       <listener>
             <listener-class> org.springframework.web.context.ContextLoaderListener </listener-class>
       </listener>
       <!-- 自定义监听器 -->
       <listener>
             <listener-class> com.djzhou.gmms.util.listener.StaticListener</listener-class >
       </listener>
       <listener>
             <listener-class> com.xulin.fycod.listener.OrderImportListener</listener-class >
       </listener>
       <listener>
             <listener-class> com.xulin.fycod.listener.OrderImportFromServletListener </listener-class>
       </listener>
<!-- 管理延迟加载 -->
       <filter>
        <filter-name> hibernateFilter</filter-name >
        <filter-class>
            org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
        </filter-class>
    </filter >
    <filter-mapping >
        <filter-name> hibernateFilter</filter-name >
        <url-pattern> /*</ url-pattern>
    </filter-mapping >
      <filter >
          <filter-name> GzipFilter</filter-name >
          <filter-class> net.sf.ehcache.constructs.web.filter.GzipFilter</filter-class >
    </filter >
    <filter-mapping >
          <filter-name> GzipFilter</filter-name >
          <url-pattern> *.*</url-pattern >
    </filter-mapping >
    <filter-mapping >
          <filter-name> GzipFilter</filter-name >
          <url-pattern> /*Servlet </url-pattern>
    </filter-mapping >
  
<!-- 加载struts2 -->
       <display-name> Struts Blank</display-name >
    <filter >
        <filter-name> struts2</filter-name >
        <filter-class> org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter </filter-class>
    </filter >
    <filter-mapping >
        <filter-name> struts2</filter-name >
        <url-pattern> *.action</url-pattern >
    </filter-mapping >
    <context-param >
      <param-name >log4jConfigLocation</ param-name>
      <param-value >/WEB-INF/classes/log4j.properties</ param-value>
   </context-param >
    <listener >
      <listener-class >
        org.springframework.web.util.Log4jConfigListener
      </listener-class >
   </listener >
    
     <servlet >
             <description> 文件上传Serlvet</description>
             <display-name> 文件上传Serlvet</display-name>
             <servlet-name> FileUploadServlet</servlet-name >
             <servlet-class> com.djzhou.gmms.util.servlet.FileUploadServlet</servlet-class >
             <init-param>
                   <param-name> fileSizeLimit</param-name >
                   <param-value> 50</ param-value>
             </init-param>
       </servlet>
       <servlet>
             <description> 文件删除Servlet</description>
             <display-name> 文件删除Servlet</display-name>
             <servlet-name> DeleteFileServlet</servlet-name >
             <servlet-class> com.djzhou.gmms.util.servlet.DeleteFileServlet</servlet-class >
       </servlet>
  <servlet >
    <description >验证码</ description>
    <display-name >验证码</ display-name>
    <servlet-name >ImageServlet</ servlet-name>
    <servlet-class> com.djzhou.gmms.util.servlet.ImageServlet</servlet-class >
  </servlet >
       <servlet-mapping>
             <servlet-name> FileUploadServlet</servlet-name >
             <url-pattern> /FileUploadServlet</url-pattern >
       </servlet-mapping>
       <servlet-mapping>
             <servlet-name> DeleteFileServlet</servlet-name >
             <url-pattern> /DeleteFileServlet</url-pattern >
       </servlet-mapping>
  <servlet-mapping >
    <servlet-name >ImageServlet</ servlet-name>
    <url-pattern >/ImageServlet</ url-pattern>
  </servlet-mapping >
  
  
  <servlet > 
       <servlet-name> OrderServlet</servlet-name > 
       <servlet-class> com.xulin.fycod.listener.OrderServlet</servlet-class > 
  </servlet > 
  <servlet-mapping > 
       <servlet-name> OrderServlet</servlet-name > 
       <url-pattern> /OrderServlet</url-pattern > 
  </servlet-mapping >
  <!-- begin XFire 配置 -->
 <servlet >
  <servlet-name >xfire</ servlet-name>
  <servlet-class >
   org.springframework.web.servlet.DispatcherServlet
  </servlet-class >
 </servlet >
 <servlet-mapping >
  <servlet-name >xfire</ servlet-name>
  <url-pattern >*.ws</url-pattern >
 </servlet-mapping >
 <servlet >
  <!-- 配合Spring容器中XFire一起工作的 Servlet-->
  <servlet-name >xfireServlet</ servlet-name>
  <servlet-class >
   org.codehaus.xfire.spring.XFireSpringServlet
  </servlet-class >
 </servlet >
 <servlet-mapping >
  <servlet-name >xfireServlet</ servlet-name>
  <!-- 在这个URI下开放Web Service服务 -->
  <url-pattern >/webservice/* </url-pattern>
 </servlet-mapping >
 <!-- end XFire 配置 -->
  <mime-mapping > 
       <extension> manifest</extension > 
       <mime-type> text/cache-manifest</mime-type > 
       </mime-mapping>
    <jsp-config > 
        <taglib>  
            <taglib-uri> /gmms-tags </taglib-uri>     
            <taglib-location> /WEB-INF/gmms-tags.tld </taglib-location>     
        </taglib>
          <taglib>  
            <taglib-uri> /elf-tags</taglib-uri >    
            <taglib-location> /WEB-INF/elfuns-tags.tld </taglib-location>     
        </taglib>
    </jsp-config >
    <session-config >
        <session-timeout> 0</ session-timeout>
    </session-config >
    <welcome-file-list >
    <welcome-file >default.jsp</ welcome-file>
  </welcome-file-list >
  
  </web-app >
 
 
测试用的java类 Start.java
 
 
package com.xulin.webservice.run;
 
import java.net.URL;
import java.util.Date;
 
import org.codehaus.xfire.client.Client;
 
import com.djzhou.util.MD5;
 
public class Start {
 
       public static void main(String[] args) throws Exception {
            Client client = new Client( new URL("http://23.12.123.12:3111/sjjj/LoginWebService.ws?wsdl" ));
//         
            Object[] result = client.invoke("addGoods", new Object[]{"33333" ,"管理员" ,MD5.toMD5( "123456")});
//        
             */
            Date date1= new Date();
            Date date2= new Date();
             //System.out.println(result[0]);
            System. out.println("调用结果\n" +result[0].toString());
        try {
            //毫秒ms
            long diff = date2.getTime() - date1.getTime();
            System. out.println("两个时间相差：" +diff+ " 毫秒.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
        
      }
 
}