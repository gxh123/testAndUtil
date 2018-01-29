package test2;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        DefaultEventExecutor eventExecutor = new DefaultEventExecutor();
        eventExecutor.execute(new MyThread(eventExecutor));

        Thread.sleep(10000);
        eventExecutor.shutdownGracefully();
    }

    static class MyThread extends Thread{

        final private EventExecutor eventExecutor;

        public MyThread(EventExecutor eventExecutor){
            this.eventExecutor = eventExecutor;
        }

        @Override
        public void run(){
            System.out.println("当前线程："+Thread.currentThread().getName());
            System.out.println(new Random().nextInt());
            if(eventExecutor != null)
                eventExecutor.execute(new MyThread(null));
        }
    }

}
