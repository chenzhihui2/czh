package cat.jdkDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/1/19.
 */
public class Test {
    public static void main(String args[]){
        Subject subject=new SubjectImpl();
        InvocationHandler invocationHandler=new DynamicInvocationHandler(subject);
        Subject proxySubject= (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(),subject.getClass().getInterfaces(),invocationHandler);
        proxySubject.sayHello();
    }
}
