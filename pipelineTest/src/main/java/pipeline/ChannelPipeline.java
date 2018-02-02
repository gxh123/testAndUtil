package pipeline;

import channel.ChannelInboundInvoker;
import channel.ChannelOutboundInvoker;
import handler.ChannelHandler;

public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker{

    ChannelPipeline addFirst(String name, ChannelHandler handler);

    ChannelPipeline addLast(String name, ChannelHandler handler);
}
