package cat.tbq.hospital.cglib.noInterface;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/1/19.
 */
public class MyProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("star proxy process...");
        Object obj=methodProxy.invokeSuper(o,objects);
        System.out.println("end proxy process...");
        return obj;
    }
    public static void main(String args[]){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(TableDAO.class);
        enhancer.setCallback(new MyProxy());
        TableDAO tableDAOProxy= (TableDAO) enhancer.create();
        tableDAOProxy.create();
        tableDAOProxy.query();
    }
}
