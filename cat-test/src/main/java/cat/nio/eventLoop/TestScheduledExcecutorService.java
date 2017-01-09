package cat.nio.eventLoop;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2016/12/30.
 */
public class TestScheduledExcecutorService {
   private static   ExecutorService executorService= Executors.newFixedThreadPool(1);
    public static void main(String args[]) throws InterruptedException, ExecutionException, TimeoutException {

          Future future= executorService.submit(new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println(Thread.currentThread().getName()+":xxx");
               }
           }));

            future.get(60,TimeUnit.MILLISECONDS);


    }
}
