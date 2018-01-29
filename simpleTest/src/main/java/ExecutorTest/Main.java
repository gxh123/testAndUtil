package ExecutorTest;

import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new CallableTest());
        Thread.sleep(1000);
        MyThread thread = new MyThread(future);
        thread.setName("myThread");
        thread.start();
        Thread.sleep(1000);
        String s = null;
        try {
            s = future.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }

    static class MyThread extends Thread{
        private Future future;

        public MyThread(Future future){
            this.future = future;
        }

        @Override
        public void run(){
            try {
                System.out.println(Thread.currentThread().getName()+"调用MyThread");
                this.future.get(2,TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    static class CallableTest implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("进入call");
            Thread.sleep(10000000);
            return "This is a call from " + Thread.currentThread().getName();
        }
    }


}
