package demo3.netty4.server;

//import demo2.netty4.netty4.codec.Codec;
//import demo2.netty4.netty4.codec.Codec2;

import demo3.netty4.channel.Channel;
import demo3.netty4.common.Constants;
import demo3.netty4.common.RemotingException;
import demo3.netty4.common.URL;
import demo3.netty4.common.threadpool.FixedThreadPool;
import demo3.netty4.util.ExecutorUtil;
import demo3.netty4.util.NetUtils;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.ExecutorService;


public abstract class AbstractServer implements Server {

    private volatile URL url;
    // closing closed分别表示关闭流程中、完成关闭
    private volatile boolean closing;
    private volatile boolean closed;
    //----
    private int timeout;
    private int connectTimeout;

    protected static final String SERVER_THREAD_POOL_NAME = "DubboServerHandler";
    ExecutorService executor;
    private InetSocketAddress localAddress;
    private Integer bindPort;
    private int accepts;
    private int idleTimeout = 600; //600 seconds

    public AbstractServer(URL url) throws RemotingException {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        this.url = url;
        this.timeout = 1000;
        this.connectTimeout = 3000;
        //--------
        localAddress = getUrl().toInetSocketAddress();
        String host = url.getParameter(Constants.ANYHOST_KEY, false)
                || NetUtils.isInvalidLocalHost(getUrl().getHost())
                ? NetUtils.ANYHOST : getUrl().getHost();
        bindPort = getUrl().getPort();
        this.accepts = 0;
        this.idleTimeout = 600 * 1000;
        try {
            doOpen();
        } catch (Throwable t) {
            throw new RemotingException(url.toInetSocketAddress(), null, "Failed to bind " + getClass().getSimpleName()
                    + " on " + getLocalAddress() + ", cause: " + t.getMessage(), t);
        }
        executor = (ExecutorService)(new FixedThreadPool().getExecutor(url));
    }

    protected abstract void doOpen() throws Throwable;

    public void startClose() {
        if (isClosed()) {
            return;
        }
        closing = true;
    }

    public void close(int timeout) {
        ExecutorUtil.gracefulShutdown(executor, timeout);
        close();
    }

    public void close() {
        ExecutorUtil.shutdownNow(executor, 100);
        try {
            closed = true;
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
        try {
            doClose();
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }

    protected abstract void doClose() throws Throwable;

    public void send(Object message) throws RemotingException {
        send(message, false);
    }

    public void send(Object message, boolean sent) throws RemotingException {
        Collection<Channel> channels = getChannels();
        for (Channel channel : channels) {
            if (channel.isConnected()) {
                channel.send(message, sent);
            }
        }
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public Integer getBindPort() {
        return bindPort;
    }

    public int getAccepts() {
        return accepts;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    protected int getTimeout() {
        return timeout;
    }

    protected int getConnectTimeout() {
        return connectTimeout;
    }

    public URL getUrl() {
        return url;
    }

    protected void setUrl(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        this.url = url;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isClosing() {
        return closing && !closed;
    }

//    public void sent(Channel ch, Object msg) throws RemotingException {
//        if (closed) {
//            return;
//        }
//        handler.sent(ch, msg);
//    }
//
//    public void received(Channel ch, Object msg) throws RemotingException {
//        if (closed) {
//            return;
//        }
//        handler.received(ch, msg);
//    }
//
//    public void caught(Channel ch, Throwable ex) throws RemotingException {
//        handler.caught(ch, ex);
//    }

}