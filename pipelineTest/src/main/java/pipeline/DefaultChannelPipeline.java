package pipeline;

import channel.DefaultChannel;
import context.AbstractChannelHandlerContext;
import context.DefaultChannelHandlerContext;
import context.HeadContext;
import context.TailContext;
import handler.ChannelHandler;

import java.net.SocketAddress;

public class DefaultChannelPipeline implements ChannelPipeline {

    private final DefaultChannel channel;

    final AbstractChannelHandlerContext head;
    final AbstractChannelHandlerContext tail;

    public DefaultChannelPipeline(DefaultChannel channel) {
        this.channel = channel;

        this.head = new HeadContext(this);
        this.tail = new TailContext(this);

        head.next = tail;
        tail.prev = head;
    }

    public DefaultChannel getChannel() {
        return channel;
    }

    @Override
    public ChannelPipeline addFirst(String name, ChannelHandler handler) {
        final AbstractChannelHandlerContext newCtx;
        synchronized (this) {
            newCtx = newContext(handler);
            addFirst0(newCtx);
        }
        callHandlerAdded0(newCtx);
        return this;
    }

    private AbstractChannelHandlerContext newContext(ChannelHandler handler) {
        return new DefaultChannelHandlerContext(this, handler);
    }

    private void addFirst0(AbstractChannelHandlerContext newCtx) {
        AbstractChannelHandlerContext nextCtx = head.next;
        newCtx.prev = head;
        newCtx.next = nextCtx;
        head.next = newCtx;
        nextCtx.prev = newCtx;
    }

    @Override
    public ChannelPipeline addLast(String name, ChannelHandler handler) {
        final AbstractChannelHandlerContext newCtx;
        synchronized (this) {
            newCtx = newContext(handler);
            addLast0(newCtx);
        }
        callHandlerAdded0(newCtx);
        return this;
    }

    private void addLast0(AbstractChannelHandlerContext newCtx) {
        AbstractChannelHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
    }

    private void callHandlerAdded0(final AbstractChannelHandlerContext ctx) {
        try {
            ctx.handler().handlerAdded(ctx);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //------------------------------------------------
    //-------启动入站事件-----------
    public final ChannelPipeline fireChannelRegistered() {
        AbstractChannelHandlerContext.invokeChannelRegistered(head);
        return this;
    }

    public final ChannelPipeline fireChannelActive() {
        AbstractChannelHandlerContext.invokeChannelActive(head);
        return this;
    }

    public final ChannelPipeline fireExceptionCaught(Throwable cause) {
        AbstractChannelHandlerContext.invokeExceptionCaught(head, cause);
        return this;
    }

    public final ChannelPipeline fireChannelRead(Object msg) {
        AbstractChannelHandlerContext.invokeChannelRead(head, msg);
        return this;
    }

    //-----启动出站事件----------
    @Override
    public void bind(SocketAddress localAddress) {
        tail.bind(localAddress);
    }

    @Override
    public void connect(SocketAddress remoteAddress) {
        tail.connect(remoteAddress);
    }

    @Override
    public void disconnect() {
        tail.disconnect();
    }

    @Override
    public void close() {
        tail.close();
    }

    @Override
    public void read() {
        tail.read();
    }

    @Override
    public void writeAndFlush(Object msg) {
        tail.writeAndFlush(msg);
    }




}
