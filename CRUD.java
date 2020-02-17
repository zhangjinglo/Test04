package crawler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUD {
    private CRUD() {
    }

    static Connection conn = null;
    static PreparedStatement ps = null;//这里使用PreparedStatement（预编译的数据库操作对象）可以防止SQL注入现象！！！
    static ResultSet rs = null;

    public static void newNovel(Novel a) {
        try {
            //1、注册驱动  2、获取连接
            conn = Tool.getConnection();
            //3、获取预编译的数据库操作对象
            String sql = "insert into Novel(num) values(?)";//SQL语句的框架，其中，一个?代表一个占位符，一个?将来接收一个值 注意：占位符不能用''括起来
            ps = conn.prepareStatement(sql);//sql这条语句到这儿就编译了
            //给占位符?传值（第1个问号下标是1，第2个下标是2，JDBC中所有下标从1开始）
            ps.setInt(1, a.getNum());
            //4、执行sql
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            Tool.close(conn, ps, rs);
        }
    }

    public static void updateInfo(Novel a) {
        try {
            //1、注册驱动  2、获取连接
            conn = Tool.getConnection();
            //3、获取预编译的数据库操作对象
            String sql = "update Novel set novel = ? , novelist = ? , intro = ? , cover = ? where num = ?";
            ps = conn.prepareStatement(sql);//sql这条语句到这儿就编译了
            //给占位符?传值（第1个问号下标是1，第2个下标是2，JDBC中所有下标从1开始）
            ps.setString(1, a.getNovel());
            ps.setString(2, a.getNovelist());
            ps.setString(3, a.getIntro());
            ps.setString(4, a.getCover());
            ps.setInt(5, a.getNum());
            //4、执行sql
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            Tool.close(conn, ps, rs);
        }
    }

    public static void updateNovel(Novel a, int i) {
        try {
            //1、注册驱动  2、获取连接
            conn = Tool.getConnection();
            //3、获取预编译的数据库操作对象
            String sql = "insert into chapter(novelNum,chapterNum,chapter,content) values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            //给占位符传值
            ps.setInt(1, a.getNum());
            ps.setInt(2, i);
            ps.setString(3, a.getChapter());
            ps.setString(4, a.getContent());
            //4、执行sql
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            Tool.close(conn, ps, rs);
        }
    }

    public static void deleteNovel(Novel a) {
        try {
            //1、注册驱动  2、获取连接
            conn = Tool.getConnection();
            //3、获取预编译的数据库操作对象
            String sql = "delete from novel where num=?";
            ps = conn.prepareStatement(sql);
            //给占位符传值
            ps.setInt(1, a.getNum());
            //4、执行sql
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            Tool.close(conn, ps, rs);
        }
    }

    public static void newMovie(Movie a){
        try {
            //1、注册驱动  2、获取连接
            conn = Tool.getConnection();
            //3、获取预编译的数据库操作对象
            String sql = "insert into movie(num,movie,cover,intro,director,scriptwriter,actor,length,rate,rating_people) values(?,?,?,?,?,?,?,?,?,?)";//SQL语句的框架，其中，一个?代表一个占位符，一个?将来接收一个值 注意：占位符不能用''括起来
            ps = conn.prepareStatement(sql);//sql这条语句到这儿就编译了
            //给占位符?传值（第1个问号下标是1，第2个下标是2，JDBC中所有下标从1开始）
            ps.setInt(1, a.getNum());
            ps.setString(2,a.getMovie());
            ps.setString(3,a.getCover());
            ps.setString(4,a.getIntro());
            ps.setString(5,a.getDirector());
            ps.setString(6,a.getScriptwriter());
            ps.setString(7,a.getActor());
            ps.setString(8,a.getLength());
            ps.setString(9,a.getRate());
            ps.setString(10,a.getRating_people());
            //4、执行sql
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            Tool.close(conn, ps, rs);
        }
    }

    public static void updateMovieInfo(Movie a){
        try {
            //1、注册驱动  2、获取连接
            conn = Tool.getConnection();
            //3、获取预编译的数据库操作对象
            String sql = "insert into stars(num,stars5,stars4,stars3,stars2,stars1) values(?,?,?,?,?,?)";//SQL语句的框架，其中，一个?代表一个占位符，一个?将来接收一个值 注意：占位符不能用''括起来
            ps = conn.prepareStatement(sql);//sql这条语句到这儿就编译了
            //给占位符?传值（第1个问号下标是1，第2个下标是2，JDBC中所有下标从1开始）
            ps.setInt(1, a.getNum());
            ps.setString(2,a.getStars5());
            ps.setString(3,a.getStars4());
            ps.setString(4,a.getStars3());
            ps.setString(5,a.getStars2());
            ps.setString(6,a.getStars1());
            //4、执行sql
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            Tool.close(conn, ps, rs);
        }
    }

    public static void commet(Movie a) {
        try {
            //1、注册驱动  2、获取连接
            conn = Tool.getConnection();
            //3、获取预编译的数据库操作对象
            String sql = "insert into commets(movieNum,commet) values(?,?)";
            ps = conn.prepareStatement(sql);
            //给占位符传值
            ps.setInt(1, a.getNum());
            ps.setString(2, a.getCommet());
            //4、执行sql
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            Tool.close(conn, ps, rs);
        }
    }

}
