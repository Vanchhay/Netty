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
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString(Charset.defaultCharset()));


        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        ctx.writeAndFlush(time); // (3)
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        System.out.println("ChannelActive Triggered");
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        ctx.writeAndFlush(time); // (3)
//        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}