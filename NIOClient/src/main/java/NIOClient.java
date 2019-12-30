import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {
    private static final String host= "127.0.0.1";
    private static final int port = 8080;
    private Selector selector;

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            new Thread(()->{
                NIOClient nioClient = new NIOClient();
                nioClient.connect(host,port);
                nioClient.listen();
            }).start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接远程服务
     * @param host
     * @param port
     */
    public void connect(String host,int port){
       try{
           SocketChannel socketChannel = SocketChannel.open();
           socketChannel.configureBlocking(false);
           this.selector=Selector.open();
           socketChannel.register(selector, SelectionKey.OP_CONNECT);
           socketChannel.connect(new InetSocketAddress(host,port));
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    /**
     * 监听
     */
    public void listen(){
        while (true){
            try{
                int events = selector.select();
                if(events>0){
                    Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                    while (selectionKeyIterator.hasNext()){
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        selectionKeyIterator.remove();

                        //连接事件
                        if(selectionKey.isConnectable()){
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            if(socketChannel.isConnectionPending()){
                                socketChannel.finishConnect();
                            }

                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            socketChannel.write(ByteBuffer.wrap(("Hello this is " + Thread.currentThread().getName()).getBytes()));
                        }else if(selectionKey.isReadable()){
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            socketChannel.read(byteBuffer);
                            byteBuffer.flip();
                            System.out.println("收到服务端的数据:"+new String(byteBuffer.array()));
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
