import elo.pra.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua.H.Brooks
 * @description
 * @date 2024-05-15 01:03
 */

public class MybatisJUnitTest {
    SqlSession sqlSession = SqlSessionUtil.openSession();
    @Before
    public void before(){
        System.out.println("before");
    }

    @Test
    public void test_C(){
        int insertCar = sqlSession.insert("insertCar");
        System.out.println("No of Cars added = " + insertCar);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("Hello world!");
    }
    @Test
    public void test_R(){
        int deleteCar = sqlSession.delete("deleteById","8");
        System.out.println("No of Cars deleted = " + deleteCar);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("Hello world!");
    }
}
