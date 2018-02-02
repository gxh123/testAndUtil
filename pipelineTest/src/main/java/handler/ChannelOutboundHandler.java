package handler;

import context.ChannelHandlerContext;
import pipeline.ChannelPipeline;

import java.net.SocketAddress;

/**
 * {@link ChannelHandler} which will get notified for IO-outbound-operations.
 */
public interface ChannelOutboundHandler extends ChannelHandler {

    void bind(ChannelHandlerContext ctx,SocketAddress localAddress);


    void connect(ChannelHandlerContext ctx,SocketAddress remoteAddress);


    void disconnect(ChannelHandlerContext ctx);


    void close(ChannelHandlerContext ctx);


    void read(ChannelHandlerContext ctx);


    void writeAndFlush(ChannelHandlerContext ctx, Object msg);
}
