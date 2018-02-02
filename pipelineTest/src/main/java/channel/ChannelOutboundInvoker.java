package channel;

import java.net.SocketAddress;

//出站事件传播方法，（写这么几个，示例）
//把ChannelFuture先去掉了
//出站传播方法，实际上就是调用下个入站的context的handler的对应处理方法
//因此主要有两步：1、找到下个出站的context  2、调用context的handler的对应处理方法
public interface ChannelOutboundInvoker {

    /**
     * Request to bind to the given {@link SocketAddress}
     */
    void bind(SocketAddress localAddress);

    /**
     * Request to connect to the given {@link SocketAddress}
     */
    void connect(SocketAddress remoteAddress);

    /**
     * Request to disconnect from the remote peer
     */
    void disconnect();

    /**
     * Request to close the {@link DefaultChannel}
     */
    void close();


    //这个read方法要仔细理解下！
    void read();


    void writeAndFlush(Object msg);
}
