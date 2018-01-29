package demo3.netty4.channelHandler;

import demo3.netty4.channel.Channel;
import demo3.netty4.common.RemotingException;

//ChannelHandler最基本的实现，在使用中对ChannelHandlerAdapter中所需的方法重写即可。
public class ChannelHandlerAdapter implements ChannelHandler {

    public void connected(Channel channel) throws RemotingException {
        System.out.println("ChannelHandlerAdapter调用: " + "connected()");
    }

    public void disconnected(Channel channel) throws RemotingException {
        System.out.println("ChannelHandlerAdapter调用: " + "disconnected()");
    }

    public void sent(Channel channel, Object message) throws RemotingException {
        System.out.println("ChannelHandlerAdapter调用: " + "sent()");
        System.out.println("发送信息" + message);
    }

    public void received(Channel channel, Object message) throws RemotingException {
        System.out.println("ChannelHandlerAdapter调用: " + "received()");
        System.out.println("收到信息" + message);
        if(!((String)message).contains("~~~")) {
            channel.send(message + "~~~");
        }
    }

    public void caught(Channel channel, Throwable exception) throws RemotingException {
        System.out.println("ChannelHandlerAdapter调用: " + "caught()");
    }

}