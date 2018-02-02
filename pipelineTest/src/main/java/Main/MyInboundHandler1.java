package Main;

import context.ChannelHandlerContext;
import handler.ChannelInboundHandler;

public class MyInboundHandler1 implements ChannelInboundHandler {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        System.out.println("MyInboundHandler1 channelRegistered调用");
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("MyInboundHandler1 channelActive调用");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("MyInboundHandler1 channelRead调用");
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("MyInboundHandler1 channelReadComplete调用");
        //没写
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("MyInboundHandler1 exceptionCaught调用");
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyInboundHandler1 handlerAdded调用");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyInboundHandler1 handlerRemoved调用");
    }
}
