package com.servlets;

import com.fruit.dao.FruitDAO;
import com.fruit.dao.impl.FruitDAOImpl;
import com.fruit.pojo.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//Servlet从3.0版本开始支持注解方式的注册
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse reps) throws ServletException, IOException{
        doGet(req,reps);
    }
    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        String oper = request.getParameter("oper");
        String keyword = null ;
        Integer pageNo = 1;

        if( StringUtil.isNotEmpty(oper) && "search".equals(oper) ){
            pageNo = 1 ;
            keyword = request.getParameter("keyword");
            if( StringUtil.isEmpty(keyword) ){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        } else {
            String pageNoStr = request.getParameter("pageNo");
            if( StringUtil.isNotEmpty(pageNoStr) ){
                pageNo = Integer.parseInt(pageNoStr);
            }
            Object keywordObj = session.getAttribute("keyword");
            if( keywordObj !=null ){
                keyword = (String)keywordObj;
            }else {
                keyword = "";
            }
        }

        session.setAttribute("pageNo", pageNo);

        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword,pageNo);

        session.setAttribute("fruitList",fruitList);
        int fruitCount = fruitDAO.getFruitCount(keyword);
        int pageCount = ( fruitCount + 4 )/5;
        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        session.setAttribute("pageCount", pageCount );
        super.processTemplate("index",request,response);
    }
}