package server;

import had.NIOServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOServer {

    private int port;
    private Selector selector;
    private ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
         new NIOServer(8080).start();
    }

    public NIOServer(int port){
        this.port=port;
    }

    //初始化
    public void init(){
        ServerSocketChannel serverSocketChannel = null;

        try{
           serverSocketChannel= ServerSocketChannel.open();
           serverSocketChannel.configureBlocking(false);
           serverSocketChannel.bind(new InetSocketAddress(port));
           selector=Selector.open();
           serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("NIOServer is started ..............");
        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }
    }

    public void accept(SelectionKey key){
        try{
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_READ);
            System.out.println("Accept a client:"+socketChannel.socket().getInetAddress().getHostName());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //启动
    public void start(){
        this.init();
        while(true){
            try{
                int events = selector.select();
                if(events>0){
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();

                    while (selectionKeys.hasNext()){
                        SelectionKey key = selectionKeys.next();
                        selectionKeys.remove();
                        if(key.isAcceptable()){
                            accept(key);
                        }else {
                            service.submit(new NIOServerHandler(key));
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
