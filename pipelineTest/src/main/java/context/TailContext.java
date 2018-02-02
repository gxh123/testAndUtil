package context;

import handler.ChannelHandler;
import handler.ChannelInboundHandler;
import handler.ChannelOutboundHandler;
import pipeline.DefaultChannelPipeline;

//TailContext为入站事件的最后一步，不需要执行什么操作，最多捕获下之前的context的异常
//所以方法基本都是空的
public class TailContext extends AbstractChannelHandlerContext
        implements ChannelInboundHandler {

    public TailContext(DefaultChannelPipeline pipeline) {
        super(pipeline, true, false);
    }

    @Override
    public ChannelHandler handler() {
        return this;
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }
}
