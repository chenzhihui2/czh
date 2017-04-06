package cat.tbq.hospital.cglib.generateBean;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/20.
 */
public class CglibTest {
    public static void main(String args[]){
        HashMap<String,Class> propertyMap=new HashMap<String,Class>();
        propertyMap.put("id",Integer.class);
        propertyMap.put("name",String.class);
        propertyMap.put("address",String.class);
        CglibBean bean=new CglibBean(propertyMap);
        bean.setValue("id",new Integer(123));
        bean.setValue("name","czh");
        bean.setValue("address","caj");
        System.out.println("id "+bean.getValue("id"));
        System.out.println("name "+bean.getValue("name"));
        System.out.println("address "+bean.getValue("address"));
        Object object=bean.getObject();
        Class clazz=object.getClass();
        System.out.println(clazz.getName());
        Method[] methods=clazz.getDeclaredMethods();
        for(int i=0;i<methods.length;i++){
            System.out.println(methods[i].getName());
        }

    }
}
