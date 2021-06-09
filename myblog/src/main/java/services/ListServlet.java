package services;

import Dao.ArticleInfoDao;
import models.Vo.ArticleInfoVo;
import utils.ResultUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/list")
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int succ = -1;
        String msg = "";
        List<ArticleInfoVo> list = null;

        int page = Integer.parseInt(req.getParameter("page"));
        int psize = Integer.parseInt(req.getParameter("psize"));

        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            list = articleInfoDao.getListByPage(page,psize);
            succ = 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        HashMap<String,Object> result = new HashMap<>();
        result.put("succ",succ);
        result.put("msg",msg);
        result.put("list",list);
        ResultUtils.write(resp,result);
    }
}
