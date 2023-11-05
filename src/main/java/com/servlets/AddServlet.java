package com.servlets;

import com.fruit.dao.FruitDAO;
import com.fruit.dao.impl.FruitDAOImpl;
import com.fruit.pojo.Fruit;
import com.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
/** https://alanli7991.github.io/2016/03/14/HTTP%E5%92%8CServlet%E7%9A%84%E5%85%B3%E7%B3%BB */
@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {// alt+enter 导包快捷键
    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override// ctrl + o
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp); p23 乱码问题
        req.setCharacterEncoding("UTF-8");
        System.out.println(" 进入到 add.do ...");

        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price")) ;
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        System.out.println(" fname = " + fname );
        System.out.println(" price = " + price);
        System.out.println(" fcount = "+ fcount );
        System.out.println(" remark = " + remark );


        Fruit fruit = new Fruit(0,fname , price , fcount , remark ) ;

        fruitDAO.addFruit(fruit);

        resp.sendRedirect("index");
       /* FruitDAO fruitDAO =  new FruitDAOImpl();

        boolean flag = fruitDAO.addFruit(new Fruit(23,fname,price,fcount,remark));
        System.out.println(flag ? "添加成功！":"添加失败！");*/
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doget...");
    }
}
