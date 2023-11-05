package com.servlets;
import com.fruit.dao.FruitDAO;
import com.fruit.dao.impl.FruitDAOImpl;
import com.fruit.pojo.Fruit;
import com.myspringmvc.ViewBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * GET方法: The GET method means retrieve whatever information(in the form of an entity) is identified by the Request-URI.
 *
 * POST方法: The POST method is used to request that the origin server accept the entity
 * enclosed in the request as a new subordinate of the resource identified by the Request-URI in the Request-Line.
 */

@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        req.setCharacterEncoding("utf-8");
        System.out.println(" 进入 update.do ===》 servlet.");
        String fidStr = req.getParameter("fid");
        Integer fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String priceStr =  req.getParameter("price");
        int price = Integer.parseInt(priceStr);

        String fcountStr = req.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");
        fruitDAO.updateFruit( new Fruit( fid, fname, price, fcount, remark) );

        resp.sendRedirect("index");// ***更新完应该 跳转回 indexServelet 重新 更新表单

    }
}
