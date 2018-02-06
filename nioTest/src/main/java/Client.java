import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Client {

    public static void main(String[] args) throws IOException {

        startClient();
    }

    public static void startClient() throws IOException {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        // 设置为非阻塞模式，这个方法必须在实际连接之前调用
        channel.configureBlocking(false);
        //连接远程服务器，非阻塞
        //If this channel is in non-blocking mode then an invocation of this
        //method initiates a non-blocking connection operation.  If the connection
        //is established immediately, as can happen with a local connection, then
        //this method returns <tt>true</tt>.  Otherwise this method returns
        //<tt>false</tt> and the connection operation must later be completed by
        //invoking the {@link #finishConnect finishConnect} method.
        if(channel.connect(new InetSocketAddress("127.0.0.1",8889))){
            //如果直接连接成功了，就关注OP_READ事件，看看服务器会不会发什么过来
            channel.register(selector, SelectionKey.OP_READ);
            doWrite(channel, "66666666");  //客户端发点数据
        }else {
            //没有马上连接成功，就关注OP_CONNECT事件，看啥时候连接成功
            channel.register(selector, SelectionKey.OP_CONNECT);
        }

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
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                    System.out.println("key.isConnectable");
                    //就像上面说的一样，调用finishConnect
                    channel.finishConnect();
                    channel.register(selector, SelectionKey.OP_READ);
                    doWrite(channel, "66666666");  //客户端发点数据
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                    System.out.println("key.isReadable");
                    //处理服务器的回应
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(buffer);
                    String content = "";
                    if (readBytes > 0){
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        content += new String(bytes);
                        System.out.println(content);
                    }
                    //key.interestOps(SelectionKey.OP_READ);
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                    System.out.println("key.isWritable");
                }
                keyIterator.remove();
            }
        }
    }

    private static void doWrite(SocketChannel sc,String data) throws IOException{
        byte[] req = data.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        sc.write(byteBuffer);
    }
}

