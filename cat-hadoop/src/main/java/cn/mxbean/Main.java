package cn.mxbean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/22
 * comments:
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MBeanServer mbs =
                ManagementFactory.getPlatformMBeanServer();

        ObjectName mxbeanName = new ObjectName("com.example:type=QueueSampler");

        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        QueueSampler mxbean = new QueueSampler(queue);

        mbs.registerMBean(mxbean, mxbeanName);

        System.out.println("Waiting...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
