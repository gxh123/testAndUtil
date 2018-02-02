package channel;

import pipeline.ChannelPipeline;
import unsafe.Unsafe;

//channel启动出站事件
public interface Channel extends ChannelOutboundInvoker{

    Unsafe unsafe();

    ChannelPipeline pipeline();

}
