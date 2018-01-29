package demo2.processor;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultProcessor implements Processor {

    private ExecutorService executor = Executors.newFixedThreadPool(4);

    @Override
    public void handleRequest(Channel channel, Object msg) {
        executor.submit(new ServerProcessorTask(channel, msg));
    }

    @Override
    public void handleResponse(Object msg) {
        executor.submit(new ClientProcessorTask(msg));
    }
}
