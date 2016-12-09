package cat.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/9.
 */
public class TestCpuUsage {
    public static void main(String args[]){
        Executor executor=Executors.newFixedThreadPool(1000);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("test cpu usage");
            }
        });
    }
}
