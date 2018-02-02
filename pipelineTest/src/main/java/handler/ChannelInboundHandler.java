package handler;

import context.ChannelHandlerContext;

/**
 * {@link ChannelHandler} which adds callbacks for state changes. This allows the user
 * to hook in to state changes easily.
 */
public interface ChannelInboundHandler extends ChannelHandler {

    //handler最终调用的入站事件处理方法，一般用户自己定义
    void channelRegistered(ChannelHandlerContext ctx);

    void channelActive(ChannelHandlerContext ctx);

    void channelRead(ChannelHandlerContext ctx, Object msg);

    void channelReadComplete(ChannelHandlerContext ctx);

    @SuppressWarnings("deprecation")
    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause);
}
