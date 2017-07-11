package cn.mbean;

import cn.mbean.Hello;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/22
 * comments:
 */
public class Main {
    public static void main(String[] args)
            throws Exception {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.example:type=cn.mbean.Hello");
        Hello mbean = new Hello();
        mbs.registerMBean(mbean, name);


        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
