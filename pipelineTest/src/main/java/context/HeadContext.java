package context;

import handler.ChannelHandler;
import handler.ChannelInboundHandler;
import handler.ChannelOutboundHandler;
import pipeline.DefaultChannelPipeline;
import unsafe.Unsafe;

import java.net.SocketAddress;

//本身实现了ChannelHandler接口，也是handler
//HeadContext为出站事件的最后一步，出站相关会调用unsafe执行相关操作
//HeadContext为入站事件的第一步，入站相关会调用传播方法将事件传递下去
public class HeadContext extends AbstractChannelHandlerContext
        implements ChannelOutboundHandler, ChannelInboundHandler {

    private final Unsafe unsafe;

    public HeadContext(DefaultChannelPipeline pipeline) {
        super(pipeline, false, true);
        this.unsafe = pipeline.getChannel().unsafe();
    }

    @Override
    public ChannelHandler handler() {
        return this;
    }

    //入站-----
    @Override
    public void channelRegistered(ChannelHandlerContext ctx){
        ctx.fireChannelRegistered();  //传给下个context，调用其handler的对应方法
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //....
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
    }
    //-----

    //出站-----
    @Override
    public void bind(ChannelHandlerContext ctx,SocketAddress localAddress) {
        unsafe.bind(localAddress);
    }

    @Override
    public void connect(ChannelHandlerContext ctx,SocketAddress remoteAddress) {
        unsafe.connect(remoteAddress);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx) {
        unsafe.disconnect();
    }

    @Override
    public void close(ChannelHandlerContext ctx) {
        unsafe.close();
    }

    @Override
    public void read(ChannelHandlerContext ctx){
        unsafe.read();
    }

    @Override
    public void writeAndFlush(ChannelHandlerContext ctx,Object msg){
        unsafe.writeAndFlush(msg);
    }
    //-----

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //nothing
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //nothing
    }
}
