package com.myspringmvc;

import com.myspringmvc.ViewBaseServlet;
import com.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * [1] 根据 请求的url，获取你的servlet path；
 * [2] 根据 你的servlet path去解析 截取 你的 字符串；===》解析出 servlet的一个名字；
 * [3] 解析 加载配置 文件 Application Context， 把配置文件里面的Bean都读出来；信息存在map中： bean,class;
 * [4] 通过 [2]，去[3]中的map找到 controller
 *
 * ===》单单就为为了 简化 多个servlet 里面的 反射的代码
 */
@WebServlet("*.do") // 拦截所有 以点do为结尾的请求
public class DispatcherServlet extends ViewBaseServlet {
    private Map<String,Object> beanMap = new HashMap<>();
    public DispatcherServlet(){
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

            NodeList beanNodeList = document.getElementsByTagName("bean");
            for( int i = 0; i < beanNodeList.getLength() ; i++ ){
                Node beanNode = beanNodeList.item(i);
                if( beanNode.getNodeType() == Node.ELEMENT_NODE ){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");

                    Object beanObj = Class.forName(className).newInstance();

                    beanMap.put( beanId,beanObj );
                }
            }
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // e.g. /hello.do 把过来的servlet的名称 截下来

        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastDotIndex);

        Object controllerBeanObj = beanMap.get(servletPath);// 已经 把 servlet 这个类加载进来了

        String operate = req.getParameter("operate");// 通过 req，来 调用 特定servlet里面的 特定方法;
        if( StringUtil.isEmpty(operate) ){
            operate = "index";
        }
        try {
            Method method = controllerBeanObj.getClass().getDeclaredMethod( operate, HttpServletRequest.class, HttpServletResponse.class );
            if( method != null ){
                method.setAccessible(true);
                method.invoke(controllerBeanObj,req,resp);
            } else {
                throw new RuntimeException("operate值非法！");
            }
        } catch ( NoSuchMethodException e ) {
            throw new RuntimeException(e);
        } catch ( InvocationTargetException e ) {
            throw new RuntimeException(e);
        } catch ( IllegalAccessException e ) {
            throw new RuntimeException(e);
        }

        /*
        Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();//获取当前类当中的所有的方法
        for( Method m:methods ){
            String methodName = m.getName() ;
            if( operate.equals(methodName) ){
                try {
                    m.invoke(this,req,resp);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("operate值非法");
        */
    }
}
