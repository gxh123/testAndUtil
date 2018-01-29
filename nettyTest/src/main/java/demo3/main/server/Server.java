package demo3.main.server;

import demo3.netty4.common.RemotingException;
import demo3.netty4.common.URL;
import demo3.netty4.server.NettyServer;

import java.io.IOException;
import java.util.HashMap;

public class Server {

    public static void main(String[] args) throws RemotingException, InterruptedException, IOException {

        //服务端要绑定的端口，host用不到，这里是统一封装为url
        URL serviceAddress = new URL("localhost",8080,
                            "helloService",new HashMap<>());
        new NettyServer(serviceAddress);
        System.out.println("server start！");
        System.in.read();
    }


}
