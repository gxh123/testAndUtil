package demo3.netty4.client;

import demo3.netty4.channel.Channel;
import demo3.netty4.channelHandler.ChannelHandler;
import demo3.netty4.common.RemotingException;
import demo3.netty4.common.URL;

import java.net.InetSocketAddress;


public interface Client extends Channel {

    /**
     * get url.
     *
     * @return url
     */
    URL getUrl();

    /**
     * get channel handler.
     *
     * @return channel handler
     */
    ChannelHandler getChannelHandler();

    /**
     * get local address.
     *
     * @return local address.
     */
    InetSocketAddress getLocalAddress();

    /**
     * send message.
     *
     * @param message
     * @throws RemotingException
     */
    void send(Object message) throws RemotingException;

    /**
     * send message.
     *
     * @param message
     * @param sent    是否已发送完成
     */
    void send(Object message, boolean sent) throws RemotingException;

    /**
     * close the channel.
     */
    void close();

    /**
     * Graceful close the channel.
     */
    void close(int timeout);

    void startClose();

    /**
     * is closed.
     *
     * @return closed
     */
    boolean isClosed();

    /**
     * reconnect.
     */
    void reconnect() throws RemotingException;

}