package channel;

//入站事件传播方法，（写这么几个，示例）
//入站传播方法，实际上就是调用下个入站的context的handler的对应处理方法
//因此主要有两步：1、找到下个入站的context  2、调用context的handler的对应处理方法
public interface ChannelInboundInvoker {


    ChannelInboundInvoker fireChannelRegistered();

    /**
     * A {@link DefaultChannel} is active now, which means it is connected.
     */
    ChannelInboundInvoker fireChannelActive();


    ChannelInboundInvoker fireExceptionCaught(Throwable cause);


    ChannelInboundInvoker fireChannelRead(Object msg);

}
