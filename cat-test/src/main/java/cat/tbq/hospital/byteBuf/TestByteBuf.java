package cat.tbq.hospital.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/12/14.
 */
public class TestByteBuf {
    public static void main(String args[]) throws InterruptedException {
        // get name representing the running Java virtual machine.
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        // get pid
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);
        Thread.sleep(5000);

        for(;;){
                  System.out.print("1");
        }
//        System.out.println(Double.valueOf("100,000000".replace(",","")));
//        Charset utf8=Charset.forName("UTF-8");
//        ByteBuf buf= Unpooled.copiedBuffer("Netty in action rock",utf8);
//        ByteBuf copy=buf.copy(0,14);
//        System.out.println(copy.toString(utf8));
//        buf.setByte(0,(byte)'J');
//        boolean f= buf.getByte(0)==copy.getByte(0);
//        System.out.println((char) 5);
    }
}
