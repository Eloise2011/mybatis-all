package elo.pra.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * @author Joshua.H.Brooks
 * @description
 * @date 2024-05-15 00:57
 */
public class SqlSessionUtil {
    private SqlSessionUtil() {};
    private static SqlSessionFactory sqlSessionFactory;
    static{
        try{
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static SqlSession openSession(){
        return sqlSessionFactory.openSession();
    }
}
