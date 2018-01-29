package demo2.server;

import demo2.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler {

    private Processor processor;

    public NettyServerHandler setProcessor(Processor processor){
        this.processor = processor;
        return this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        processor.handleRequest(ctx.channel(),msg);
    }
}
