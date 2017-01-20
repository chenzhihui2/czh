package cat.cglib.generateBean;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/1/20.
 */
public class CglibBean {
    public Object object=null;
    public BeanMap beanMap=null;
    public CglibBean(){
        super();
    }
    @SuppressWarnings("unchecked")
    public CglibBean(Map<String,Class> propertyMap){
        this.object=generateBean(propertyMap);
        this.beanMap=BeanMap.create(this.object);
    }
    public void setValue(String property,Object value){
        beanMap.put(property,value);
    }
    public Object getValue(String property){
        return  beanMap.get(property);
    }
    public Object getObject(){
        return this.object;
    }

    private Object generateBean(Map<String,Class> propertyMap){
        BeanGenerator generator=new BeanGenerator();
        Set keySet=propertyMap.keySet();
        for(Iterator i=keySet.iterator();i.hasNext();){
            String key= (String) i.next();
            generator.addProperty(key,propertyMap.get(key));
        }
        return generator.create();
    }

}
