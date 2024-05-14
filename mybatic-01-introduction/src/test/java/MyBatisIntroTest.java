import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Joshua.H.Brooks
 * @description
 * @date 2024-05-14 22:35
 */

public class MyBatisIntroTest {
    public static void main(String[] args) throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssf = ssfb.build(is);
        SqlSession sqlSession = ssf.openSession();// 如果使用的事务管理：是JDBC的话，底层实际上会执行：conn。setAutoCommit（false);
        //SqlSession sqlSession = ssf.openSession(true);// 这种方式是不开启事务，也就是说任意DML一旦执行就会立即commit。因此该方式不被推崇
        int count = sqlSession.insert("insertCar");
        sqlSession.commit();
        System.out.println("Rows affected = " + count);
    }
}
