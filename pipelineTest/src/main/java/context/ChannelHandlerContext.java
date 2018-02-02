package context;

import channel.ChannelInboundInvoker;
import channel.ChannelOutboundInvoker;
import handler.ChannelHandler;
import handler.ChannelInboundHandler;
import handler.ChannelOutboundHandler;
import pipeline.ChannelPipeline;

public interface ChannelHandlerContext extends ChannelInboundInvoker
        , ChannelOutboundInvoker{

    /**
     * The {@link ChannelHandler} that is bound this {@link ChannelHandlerContext}.
     */
    ChannelHandler handler();

    /**
     * Return the assigned {@link ChannelPipeline}
     */
    ChannelPipeline pipeline();


    //入站方法接口ChannelInboundInvoker返回值改为ChannelHandlerContext
    @Override
    ChannelHandlerContext fireChannelRegistered();

    @Override
    ChannelHandlerContext fireChannelActive();

    @Override
    ChannelHandlerContext fireExceptionCaught(Throwable cause);

    @Override
    ChannelHandlerContext fireChannelRead(Object msg);

}
