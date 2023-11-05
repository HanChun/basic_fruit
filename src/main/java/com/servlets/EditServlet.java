package com.servlets;

import com.fruit.dao.FruitDAO;
import com.fruit.dao.impl.FruitDAOImpl;
import com.fruit.pojo.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.util.StringUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit.do")//ctrl+o
public class EditServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println(" 进入 edit.do ===》 servlet.");
        String fidStr = req.getParameter("fid");
        if( StringUtil.isNotEmpty(fidStr) ){
            int fid = Integer.parseInt(fidStr);
            System.out.println(" 进入 edit.do  servlet,得到的.fid"+ fid);
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            req.setAttribute("fruit", fruit);
            System.out.println(" fname : "  + fruit.getFname());
            super.processTemplate("edit", req, resp );//把 req 里面的结果 传走，跳转到edit.html
        }
    }
}













