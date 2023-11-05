package com.controllers;
import com.fruit.dao.FruitDAO;
import com.fruit.dao.impl.FruitDAOImpl;
import com.fruit.pojo.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class FruitController extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String operate = req.getParameter("operate");
        if( StringUtil.isEmpty(operate) ){
            operate = "index";
        }
        Method[] methods = this.getClass().getDeclaredMethods();//获取当前类当中的所有的方法
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
       /* switch(operate){
            case "index":
                index( req, resp );
                break;
            case "add":
                add( req, resp );
                break;
            case "del":
                del( req, resp );
                break;
            case "edit":
                edit( req, resp );
                break;
            case "update":
                update( req, resp );
                break;
            default:
                throw new RuntimeException("operate值非法！");
        }*/
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        Integer fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String priceStr =  req.getParameter("price");
        int price = Integer.parseInt(priceStr);

        String fcountStr = req.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");
        fruitDAO.updateFruit( new Fruit( fid, fname, price, fcount, remark) );

        resp.sendRedirect("fruit.do");
    }
    protected void edit( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if( StringUtil.isNotEmpty(fidStr) ){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp );
        }
    }
    private void index(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
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
        session.setAttribute("pageCount", pageCount );
        super.processTemplate("index",request,response);
    }
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        resp.sendRedirect("fruit.do");
    }
    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if(StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);
            resp.sendRedirect("fruit.do");
        }
    }
}
