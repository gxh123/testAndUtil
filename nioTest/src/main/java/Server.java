import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {

        startServer();
    }

    public static void startServer() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8889));

        Selector selector = Selector.open();
        ssc.configureBlocking(false);
        //ServerSocketChannel只有OP_ACCEPT可用，OP_CONNECT,OP_READ,OP_WRITE用于SocketChannel
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) {
                System.out.println("应该是阻塞的。。。。");
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()) {
                    // 得到与客户端的套接字通道
                    SocketChannel channel = ssc.accept();
                    System.out.println("收到客户端" + channel.getRemoteAddress() + "的连接");
                    channel.configureBlocking(false);
                    //向selector注册这个通道
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isConnectable()) {
                    //a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    String content = "";
                    try {
                        int readBytes = channel.read(byteBuffer);
                        if (readBytes > 0) {
                            byteBuffer.flip(); //转为写模式，为write()准备
                            byte[] bytes = new byte[byteBuffer.remaining()];
                            byteBuffer.get(bytes);
                            content += new String(bytes);
                            System.out.println("客户端发来信息：" + content);
                            //回应客户端
                            doWrite(channel);
                        }
                    } catch (IOException i) {
                        //如果捕获到该SelectionKey对应的Channel时出现了异常,即表明该Channel对应的Client出现了问题
                        //所以从Selector中取消该SelectionKey的注册
                        key.cancel();
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    }
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                    System.out.println("key.isWritable");
                }
                //key处理后我们需要从里面把key去掉
                keyIterator.remove();
            }
        }
    }

    private static void doWrite(SocketChannel sc) throws IOException{
        byte[] req ="服务器已接受".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        sc.write(byteBuffer);
    }

}
