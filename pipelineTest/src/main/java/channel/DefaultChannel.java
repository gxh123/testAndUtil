package channel;

import pipeline.ChannelPipeline;
import pipeline.DefaultChannelPipeline;
import unsafe.DefaultUnsafe;
import unsafe.Unsafe;

import java.net.SocketAddress;

//仅为示例
public class DefaultChannel implements Channel{

    private Unsafe unsafe = new DefaultUnsafe();
    private ChannelPipeline pipeline;

    public DefaultChannel() {
        this.pipeline = new DefaultChannelPipeline(this);
    }

    @Override
    public Unsafe unsafe() {
        return unsafe;
    }

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    //-----封装一层，使得channel具有启动出站事件的功能，都是通过pipeline
    @Override
    public void bind(SocketAddress localAddress) {
        pipeline.bind(localAddress);
    }

    @Override
    public void connect(SocketAddress remoteAddress) {
        pipeline.connect(remoteAddress);
    }

    @Override
    public void disconnect() {
        pipeline.disconnect();
    }

    @Override
    public void close() {
        pipeline.close();
    }

    @Override
    public void read() {
        pipeline.read();
    }

    @Override
    public void writeAndFlush(Object msg) {
        pipeline.writeAndFlush(msg);
    }

    //------------

}
