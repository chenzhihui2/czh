package cat.tbq.hospital.executor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/9.
 */
public class TestCpuUsage {
    public static void main(String args[]){
        BigDecimal t=new BigDecimal("1");
        testBig(t);
      System.out.println(t);


//        Executor executor=Executors.newFixedThreadPool(1000);
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("test cpu usage");
//            }
//        });
    }

    public static void testBig(BigDecimal bigDecimal){
        System.out.println(bigDecimal.subtract(new BigDecimal(1)));
    }
}
