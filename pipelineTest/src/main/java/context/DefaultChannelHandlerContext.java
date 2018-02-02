package context;

import handler.ChannelHandler;
import handler.ChannelInboundHandler;
import handler.ChannelOutboundHandler;
import pipeline.DefaultChannelPipeline;

import java.net.SocketAddress;

public class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {

    private final ChannelHandler handler;

    public DefaultChannelHandlerContext(DefaultChannelPipeline pipeline,
                                        ChannelHandler handler) {
        super(pipeline, isInbound(handler), isOutbound(handler));
        this.handler = handler;
    }

    @Override
    public ChannelHandler handler() {
        return handler;
    }

    private static boolean isInbound(ChannelHandler handler) {
        return handler instanceof ChannelInboundHandler;
    }

    private static boolean isOutbound(ChannelHandler handler) {
        return handler instanceof ChannelOutboundHandler;
    }

}
