package context;

import handler.ChannelInboundHandler;
import handler.ChannelOutboundHandler;
import pipeline.ChannelPipeline;
import pipeline.DefaultChannelPipeline;

import java.net.SocketAddress;

public abstract class AbstractChannelHandlerContext implements ChannelHandlerContext {

    private final DefaultChannelPipeline pipeline;

    public volatile AbstractChannelHandlerContext next;
    public volatile AbstractChannelHandlerContext prev;

    private final boolean inbound;
    private final boolean outbound;

    public AbstractChannelHandlerContext(DefaultChannelPipeline pipeline,
                                         boolean inbound, boolean outbound) {
        this.pipeline = pipeline;
        this.inbound = inbound;
        this.outbound = outbound;
    }

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    protected AbstractChannelHandlerContext findContextInbound() {
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!ctx.inbound);
        return ctx;
    }

    protected AbstractChannelHandlerContext findContextOutbound() {
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!ctx.outbound);
        return ctx;
    }

    //入站事件传播方法------------------
    //接下来3个方法完成了调用下个入站context的handler的channelRegistered方法
    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        //方法findContextInbound()是找到下一个入站的context
        invokeChannelRegistered(findContextInbound());
        return this;
    }

    public static void invokeChannelRegistered(final AbstractChannelHandlerContext next) {
        next.invokeChannelRegistered();
    }

    //执行handler的channelRegistered方法
    private void invokeChannelRegistered() {
        ((ChannelInboundHandler) handler()).channelRegistered(this);
    }
    //------------------

    //------------------
    @Override
    public ChannelHandlerContext fireChannelActive() {
        invokeChannelActive(findContextInbound());
        return this;
    }

    public static void invokeChannelActive(final AbstractChannelHandlerContext next) {
        next.invokeChannelActive();
    }

    //执行handler的channelActive方法
    private void invokeChannelActive() {
        ((ChannelInboundHandler) handler()).channelActive(this);
    }
    //------------------

    //------------------
    @Override
    public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
        invokeExceptionCaught(findContextInbound(),cause);
        return this;
    }

    public static void invokeExceptionCaught(final AbstractChannelHandlerContext next,Throwable cause) {
        next.invokeExceptionCaught(cause);
    }

    private void invokeExceptionCaught(Throwable cause) {
        ((ChannelInboundHandler) handler()).exceptionCaught(this, cause);
    }
    //------------------

    //------------------
    @Override
    public ChannelHandlerContext fireChannelRead(Object msg) {
        invokeChannelRead(findContextInbound(), msg);
        return this;
    }

    public static void invokeChannelRead(final AbstractChannelHandlerContext next, Object msg) {
        next.invokeChannelRead(msg);
    }

    private void invokeChannelRead(Object msg) {
        ((ChannelInboundHandler) handler()).channelRead(this, msg);
    }
    //------------------

    //----------------------------------------------
    //出站事件传播方法------------------
    @Override
    public void bind(SocketAddress localAddress) {
        if (localAddress == null) {
            throw new NullPointerException("localAddress");
        }

        final AbstractChannelHandlerContext next = findContextOutbound();
        next.invokeBind(next,localAddress);
    }

    private void invokeBind(ChannelHandlerContext ctx,SocketAddress localAddress) {
        ((ChannelOutboundHandler) handler()).bind(ctx,localAddress);
    }

    @Override
    public void connect(SocketAddress remoteAddress) {
        if (remoteAddress == null) {
            throw new NullPointerException("remoteAddress");
        }

        final AbstractChannelHandlerContext next = findContextOutbound();
        next.invokeConnect(next,remoteAddress);
    }

    private void invokeConnect(ChannelHandlerContext ctx,SocketAddress remoteAddress) {
        ((ChannelOutboundHandler) handler()).connect(ctx,remoteAddress);
    }

    @Override
    public void disconnect() {
        final AbstractChannelHandlerContext next = findContextOutbound();
        next.invokeDisconnect(next);
    }

    private void invokeDisconnect(ChannelHandlerContext ctx) {
        ((ChannelOutboundHandler) handler()).disconnect(ctx);
    }

    @Override
    public void close() {
        final AbstractChannelHandlerContext next = findContextOutbound();
        next.invokeClose(next);
    }

    private void invokeClose(ChannelHandlerContext ctx) {
        ((ChannelOutboundHandler) handler()).close(ctx);
    }

    @Override
    public void read() {
        final AbstractChannelHandlerContext next = findContextOutbound();
        next.invokeRead(next);
    }

    private void invokeRead(ChannelHandlerContext ctx) {
        ((ChannelOutboundHandler) handler()).read(ctx);
    }

    @Override
    public void writeAndFlush(Object msg) {
        final AbstractChannelHandlerContext next = findContextOutbound();
        next.invokeWriteAndFlush(next,msg);
    }

    private void invokeWriteAndFlush(ChannelHandlerContext ctx,Object msg) {
        ((ChannelOutboundHandler) handler()).writeAndFlush(ctx,msg);
    }
}
