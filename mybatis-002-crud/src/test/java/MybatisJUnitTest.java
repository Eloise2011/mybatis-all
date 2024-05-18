import elo.pra.pojo.Car;
import elo.pra.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


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
        Car car = new Car();
        car.setName("BMW425i Coupe");
        car.setPrice(45000);
        car.setColor("Black");
        car.setBrand("BMW");
        car.setType("Coupe");
        car.setCreatedDate("2024-05-14 22:08");
        car.setLastUpdated("2024-05-14 22:08");
        int insertCar = sqlSession.insert("insertCar",car);
        //int insertCar = sqlSession.insert("insertCar");
        System.out.println("No of Cars added = " + insertCar);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("Hello world!");
    }
    @Test
    public void test_D(){
        int deleteCar = sqlSession.delete("deleteById","8");
        System.out.println("No of Cars deleted = " + deleteCar);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("Hello world!");
    }

    @Test
    public void test_U(){
        Car car = new Car();
        car.setBrand("Tyoto");
        car.setColor("Pink");
        int updateByBrand = sqlSession.update("updateByBrand", car);
        System.out.println("更新条数: = " + updateByBrand);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test_R(){
        Car car =(Car) sqlSession.selectOne("selectById", 1);
        System.out.println("car = " + car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test_RM(){
        List<Car> cars = sqlSession.selectList("selectByIdRange", 1);
        cars.forEach(System.out::println);
        sqlSession.commit();
        sqlSession.close();
    }
}
