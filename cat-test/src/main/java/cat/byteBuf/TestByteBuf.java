package cat.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/12/14.
 */
public class TestByteBuf {
    public static void main(String args[]){
        Charset utf8=Charset.forName("UTF-8");
        ByteBuf buf= Unpooled.copiedBuffer("Netty in action rock",utf8);
        ByteBuf copy=buf.copy(0,14);
        System.out.println(copy.toString(utf8));
        buf.setByte(0,(byte)'J');
        boolean f= buf.getByte(0)==copy.getByte(0);
        System.out.println((char) 5);
    }
}
