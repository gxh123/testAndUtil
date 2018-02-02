package Main;

import context.ChannelHandlerContext;
import handler.ChannelOutboundHandler;

import java.net.SocketAddress;

public class MyOutboundHandler2 implements ChannelOutboundHandler {
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress) {
        System.out.println("MyOutboundHandler2 bind调用");
        ctx.connect(localAddress);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress) {
        System.out.println("MyOutboundHandler2 connect调用");
        ctx.connect(remoteAddress);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx) {
        System.out.println("MyOutboundHandler2 disconnect调用");
        ctx.disconnect();
    }

    @Override
    public void close(ChannelHandlerContext ctx) {
        System.out.println("MyOutboundHandler2 close调用");
        ctx.close();
    }

    @Override
    public void read(ChannelHandlerContext ctx) {
        System.out.println("MyOutboundHandler2 read调用");
        ctx.read();
    }

    @Override
    public void writeAndFlush(ChannelHandlerContext ctx, Object msg) {
        System.out.println("MyOutboundHandler2 writeAndFlush调用");
        ctx.writeAndFlush(msg);
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyOutboundHandler2 handlerAdded调用");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyOutboundHandler2 handlerRemoved调用");
    }
}
