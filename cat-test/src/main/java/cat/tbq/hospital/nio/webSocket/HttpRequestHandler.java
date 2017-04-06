package cat.tbq.hospital.nio.webSocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2016/12/20.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    private final String wsUri;

    public HttpRequestHandler(String wsUri){
        this.wsUri=wsUri;
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if(wsUri.equalsIgnoreCase(msg.getUri())){
            ctx.fireChannelRead(msg.retain());
        }else {
            if(HttpHeaders.is100ContinueExpected(msg)){
                send100Countinue(ctx);
            }
//            System.out.println(this.getClass().getClassLoader().getResource("index.html").getPath());
            RandomAccessFile file=new RandomAccessFile("D:\\workspace\\czh\\cat-test\\src\\main\\resources\\index.html","r");
            HttpResponse response=new DefaultHttpResponse(msg.getProtocolVersion(),HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/plain;charset=UTF-8");
            boolean keepAlive=HttpHeaders.isKeepAlive(msg);
            if(keepAlive){
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);
            if(ctx.pipeline().get(SslHandler.class)==null){
                ctx.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
            }else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future=ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

            if(!keepAlive){
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    private static void send100Countinue(ChannelHandlerContext ctx){
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable throwable){
        throwable.printStackTrace();
        ctx.close();
    }
}
