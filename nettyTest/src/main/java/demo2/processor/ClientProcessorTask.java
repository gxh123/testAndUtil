package demo2.processor;

import io.netty.channel.Channel;

public class ClientProcessorTask implements Runnable {

    private Object msg;

    public ClientProcessorTask(Object msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println("客户端收到msg: " + msg);
    }
}
