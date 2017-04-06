package cat.tbq.hospital.nio.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2016/12/29.
 */
public class MemcachedRequestEncoder extends MessageToByteEncoder<MemcahedRequest>{
    @Override
    protected void encode(ChannelHandlerContext ctx, MemcahedRequest msg, ByteBuf out) throws Exception {
        byte[] key=msg.getKey().getBytes(CharsetUtil.UTF_8);
        byte[] body=msg.getBody().getBytes(CharsetUtil.UTF_8);
        int bodySize=key.length+body.length+(msg.isHasExtras()?8:0);
        out.writeByte(msg.getMagic());
        out.writeByte(msg.getOpCode());
        out.writeShort(key.length);
        int extraSize=msg.isHasExtras()?0x08:0x0;
        out.writeByte(extraSize);
        out.writeByte(0);
        out.writeShort(0);
        out.writeInt(bodySize);
        out.writeInt(msg.getId());
        out.writeLong(msg.getCas());
        if(msg.isHasExtras()){
            out.writeInt(msg.getFlags());
            out.writeInt(msg.getExpires());
        }
        out.writeBytes(key);
        out.writeBytes(body);
    }
}
