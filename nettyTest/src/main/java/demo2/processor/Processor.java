package demo2.processor;

import io.netty.channel.Channel;

public interface Processor {

    void handleRequest(Channel channel, Object msg);

    void handleResponse(Object msg);

}
