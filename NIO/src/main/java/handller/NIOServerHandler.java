package handller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NIOServerHandler implements Runnable{

    private SelectionKey selectionKey;

    public NIOServerHandler(SelectionKey selectionKey){
        this.selectionKey= selectionKey;
    }
    @Override
    public void run() {
        try{
            if(selectionKey.isReadable()){
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer);
                byteBuffer.flip();
                System.out.println("收到客户端"+socketChannel.socket().getInetAddress()+"的数据:"+new String(byteBuffer.array()));

                ByteBuffer outBuffer = ByteBuffer.wrap(byteBuffer.array());
                socketChannel.write(outBuffer);
                selectionKey.cancel();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
