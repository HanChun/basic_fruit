package com.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/demo01")
public class Demo01Servlet extends HttpServlet {
    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp);
        System.out.println(" 进入demo1的 servlet ");
        req.setAttribute("uname","lili");
        //resp.sendRedirect("demo02");
        req.getRequestDispatcher("demo02").forward(req,resp);
    }
    //@Override//ctrl+O

    //演示Servlet的生命周期
    public static class Demo02Servlet extends HttpServlet {

        public Demo02Servlet(){
            System.out.println("正在实例化....");
        }

        @Override
        public void init() throws ServletException {
            System.out.println("正在初始化.....");
        }

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("正在服务.....");
        }

        @Override
        public void destroy() {
            System.out.println("正在销毁......");
        }
    }
}
