package Dao;

import models.ArticleInfo;
import models.Vo.ArticleInfoVo;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleInfoDao {
    //查询功能
    public List<ArticleInfo> getListByUid(int uid,int page,int psize) throws SQLException {
        List<ArticleInfo> list = new ArrayList<>();
        Connection connection = DBUtils.getConnection();
        String sql = "select a.*,u.username from articleform a left join userform u on a.uid = u.id where a.uid=? limit ?,?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        statement.setInt(2, (page - 1) * psize);
        statement.setInt(3, psize);

        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfo.setId(resultSet.getInt("id"));
            articleInfo.setRcount(resultSet.getInt("rcount"));
            articleInfo.setTitle(resultSet.getString("title"));
            articleInfo.setContent(resultSet.getString("Content"));
            articleInfo.setCreatetime(resultSet.getDate("createtime"));
            list.add(articleInfo);
        }
        DBUtils.close(connection,statement,resultSet);
        return list;
    }

    //删除功能
    public int del(int id) throws SQLException {
        int result = 0;
        if(id > 0) {
            Connection connection = DBUtils.getConnection();
            String sql = "delete from articleform where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            result = statement.executeUpdate();
            DBUtils.close(connection,statement,null);
        }
        return result;
    }

    //根据文章id查询

    public ArticleInfoVo getArtById(int id) throws SQLException {
        ArticleInfoVo articleInfo = new ArticleInfoVo();
        if(id > 0) {
            Connection connection = DBUtils.getConnection();
            String sql = "select a.*,u.username from articleform a left join userform u on a.uid = u.id where a.uid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                articleInfo.setId(resultSet.getInt(id));
                articleInfo.setTitle(resultSet.getString("title"));
                articleInfo.setContent(resultSet.getString("content"));
                articleInfo.setCreatetime(resultSet.getDate("createtime"));
                articleInfo.setUsername(resultSet.getString("username"));
                articleInfo.setRcount(resultSet.getInt("rcount"));
            }
            DBUtils.close(connection,statement,resultSet);
        }
        return articleInfo;
    }

    //文章修改
    public int upArt(int id, String title, String content) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnection();
        String sql = "update articleform set title=?,content=? where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, id);
        result = statement.executeUpdate();
        DBUtils.close(connection, statement, null);
        return result;

    }

    //文章添加
    public int add(String title, String content, int uid) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnection();
        String sql = "insert into articleform(title,content,uid) values(?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, uid);
        result = statement.executeUpdate();
        DBUtils.close(connection, statement, null);

        return result;

    }

    //分页功能
    public List<ArticleInfoVo> getListByPage(int page, int psize) throws SQLException {
        List<ArticleInfoVo> list = new ArrayList<>();
        Connection connection = DBUtils.getConnection();
        String sql = "select a.*,u.username from articleform a left join userform u on a.uid=u.id limit ?,?";
        PreparedStatement statement = connection.prepareStatement(sql);
        // 公式：(n-1)*pagesize
        statement.setInt(1, (page - 1) * psize);
        statement.setInt(2, psize);
        // 执行数据库查询
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleInfoVo vo = new ArticleInfoVo();
            vo.setId(resultSet.getInt("id"));
            vo.setTitle(resultSet.getString("title"));
            vo.setCreatetime(resultSet.getDate("createtime"));
            vo.setRcount(resultSet.getInt("rcount"));
            vo.setUsername(resultSet.getString("username"));
            list.add(vo);
        }
        DBUtils.close(connection, statement, resultSet);
        return list;
    }

    //增加阅读量
    public int upRcount(int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnection();
        String sql = "update articleform set rcount = rcount + 1 where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        result = statement.executeUpdate();
        return result;
    }
}
