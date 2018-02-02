package unsafe;

import java.net.SocketAddress;

//封装最低层jdk nio相关操作
//不是真正的netty的Unsafe，仅是示例
public interface Unsafe {

    void bind(SocketAddress localAddress);

    void connect(SocketAddress remoteAddress);

    void disconnect();

    void close();

    void read();

    void writeAndFlush(Object msg);

}
