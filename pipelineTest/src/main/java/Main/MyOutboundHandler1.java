package Main;

import context.ChannelHandlerContext;
import handler.ChannelOutboundHandler;

import java.net.SocketAddress;

public class MyOutboundHandler1 implements ChannelOutboundHandler {
    @Override
    public void bind(ChannelHandlerContext ctx,SocketAddress localAddress) {
        System.out.println("MyOutboundHandler1 bind调用");
        ctx.bind(localAddress);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress) {
        System.out.println("MyOutboundHandler1 connect调用");
        ctx.connect(remoteAddress);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx) {
        System.out.println("MyOutboundHandler1 disconnect调用");
        ctx.disconnect();
    }

    @Override
    public void close(ChannelHandlerContext ctx) {
        System.out.println("MyOutboundHandler1 close调用");
        ctx.close();
    }

    @Override
    public void read(ChannelHandlerContext ctx) {
        System.out.println("MyOutboundHandler1 read调用");
        ctx.read();
    }

    @Override
    public void writeAndFlush(ChannelHandlerContext ctx, Object msg) {
        System.out.println("MyOutboundHandler1 writeAndFlush调用");
        ctx.writeAndFlush(msg);
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyOutboundHandler1 handlerAdded调用");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyOutboundHandler1 handlerRemoved调用");
    }
}
