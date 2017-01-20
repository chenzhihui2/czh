package cat.jdkDynamicProxy;

/**
 * Created by Administrator on 2017/1/19.
 */
public class SubjectImpl implements Subject{

    @Override
    public void sayHello() {
        System.out.println("hello jdk dynamic proxy");
    }

    @Override
    public String sayGood() {
        System.out.println("good");
          return "good";
    }
}
