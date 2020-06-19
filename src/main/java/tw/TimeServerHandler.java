package tw;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ChannelRead triggered");
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println(buf.toString(Charset.defaultCharset()));

        UnixTime time = (UnixTime) msg;
        System.out.println(time);

        ctx.close();
        System.out.println("Closed connection");
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        System.out.println("ChannelActive triggered");
        ctx.writeAndFlush(new UnixTime());
//        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
//        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}