package cat.tbq.hospital.sock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author czh 453775810@qq.com
 * date: 2017/7/10
 * comments:
 */
public class SClient {
    public static ExecutorService executorService= Executors.newFixedThreadPool(1000);

    public static void main(String args[]) throws IOException {
        for(int i=0;i<10000;i++){
            String name=String.valueOf(i);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket=new Socket("localhost",8081);
                        OutputStream outputStream=socket.getOutputStream();
                        outputStream.write(name.getBytes());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }
    }
}
