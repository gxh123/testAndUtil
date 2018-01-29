package demo2.client;

import demo2.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler {

    private Processor processor;

    public NettyClientHandler setProcessor(Processor processor){
        this.processor = processor;
        return this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        processor.handleResponse(msg);
    }
}
