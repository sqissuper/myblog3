package services;

import Dao.ArticleInfoDao;
import models.ArticleInfo;
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

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int succ = -1;//操作失败与否
        String msg = "";//信息说明
        ArticleInfoVo articleInfo = new ArticleInfoVo();
        //从前端获取参数
        int id = Integer.parseInt(req.getParameter("id"));
        //调用数据库
        if(id > 0) {
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                articleInfo = articleInfoDao.getArtById(id);
                succ = 1;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            msg = "无效参数";
        }
        //返回前端
        HashMap<String,Object> result = new HashMap<>();
        result.put("succ",succ);
        result.put("msg",msg);
        result.put("art",articleInfo);
        ResultUtils.write(resp,result);
    }
}
