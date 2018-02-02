package unsafe;

import java.net.SocketAddress;

//封装最低层jdk nio相关操作
//不是真正的netty的Unsafe，仅是示例
public class DefaultUnsafe implements Unsafe {

    @Override
    public void bind(SocketAddress localAddress) {
        System.out.println("进行bind," + localAddress);
    }

    @Override
    public void connect(SocketAddress remoteAddress) {
        System.out.println("进行connect，连接到" + remoteAddress);
    }

    @Override
    public void disconnect() {
        System.out.println("进行disconnect");
    }

    @Override
    public void close() {
        System.out.println("进行close");
    }

    @Override
    public void read() {
        System.out.println("进行read");
    }

    @Override
    public void writeAndFlush(Object msg) {
        System.out.println("进行writeAndFlush: " + msg);
    }


}
