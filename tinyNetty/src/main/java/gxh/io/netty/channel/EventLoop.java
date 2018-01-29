package gxh.io.netty.channel;

import gxh.io.netty.util.EventExecutor;

public interface EventLoop extends EventExecutor, EventLoopGroup {
    @Override
    EventLoopGroup parent();
}
