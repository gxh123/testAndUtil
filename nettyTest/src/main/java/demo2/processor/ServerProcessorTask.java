package demo2.processor;

import io.netty.channel.Channel;

public class ServerProcessorTask implements Runnable {

    private Channel channel;
    private Object msg;

    public ServerProcessorTask(Channel channel, Object msg) {
        this.channel = channel;
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println("服务端收到msg: " + msg);
        channel.writeAndFlush(msg + "~~~");
        System.out.println("服务端发送msg: " + msg + "~~~");
    }
}
