package cat.spring;

/**
 * Created by Administrator on 2017/1/26.
 */
public class ApplicationContextTest {
    public static void main(String args[]) throws Exception {
         Class<ApplicationContextTest> clazz= (Class<ApplicationContextTest>) Class.forName("cat.spring.ApplicationContextTest");
         clazz.newInstance();
    }
    public ApplicationContextTest(){
        System.out.println("call construct");
    }
}
