package cat.tbq.hospital.sock;

import jdk.nashorn.internal.objects.annotations.Where;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author czh 453775810@qq.com
 * date: 2017/7/10
 * comments:
 */
public class SServer {
    public static  ExecutorService executorService= Executors.newFixedThreadPool(1);
    public static void main(String args[]) throws IOException, InterruptedException {
        Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(8081);
                    while(true){
                        System.out.println("Socket open");
                        Socket socket=ss.accept();

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    System.out.println("Socket accept");
                                    socket.getInputStream();
                                    InputStream inputStream=socket.getInputStream();
                                    byte inArray[]=new byte[1024*1000];
                                    int length=inputStream.read(inArray);
                                    System.out.println("input data =" +new String(inArray,0,length));
                                    Thread.sleep(1000);
                                    OutputStream outputStream=socket.getOutputStream();
                                    outputStream.write("yes".getBytes());
                                    socket.close();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        },1000);

    }
}
