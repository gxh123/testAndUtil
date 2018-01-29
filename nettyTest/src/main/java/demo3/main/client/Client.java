package demo3.main.client;

import demo3.netty4.channelHandler.ChannelHandlerAdapter;
import demo3.netty4.client.NettyClient;
import demo3.netty4.common.RemotingException;
import demo3.netty4.common.URL;

import java.io.IOException;
import java.util.HashMap;

public class Client {

    public static void main(String[] args) throws RemotingException, IOException {

        //要连接的服务端的ip，端口
        URL url = new URL("127.0.0.1",8080,
                "helloService",new HashMap<>());
//        Transporters.connect(url, new ChannelHandlerAdapter());
        NettyClient client = new NettyClient(url, new ChannelHandlerAdapter());
        client.send("123456");
        System.out.println("client start！");
        System.in.read();
    }


}
