package Main;

import channel.Channel;
import channel.DefaultChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        Channel channel = new DefaultChannel();
        channel.pipeline().addLast("inboundHandler1", new MyInboundHandler1());
        channel.pipeline().addLast("outboundHandler1", new MyOutboundHandler1());
        channel.pipeline().addLast("inboundHandler2", new MyInboundHandler2());
        channel.pipeline().addLast("outboundHandler2", new MyOutboundHandler2());

        //出站,从后往前
        System.out.println("\n##准备进行connect 出站操作-------------");
        channel.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("\n##准备进行writeAndFlush 出站操作-------------");
        channel.writeAndFlush("123");

        System.out.println("==============================");
        System.out.println("==============================");

        //入站，从前往后
        System.out.println("\n##准备进行ChannelActive入站操作-------------");
        channel.pipeline().fireChannelActive();
        System.out.println("\n##准备进行ChannelRead入站操作-------------");
        channel.pipeline().fireChannelRead("223");

    }


}
