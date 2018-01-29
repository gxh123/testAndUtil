package test;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

//通过executor启动一个线程，在线程里不断轮训任务队列taskQueue，有就拿出来执行
//并且不断判断是否关闭，如果关闭了就跳出线程的循环
public class SingleThreadEventExecutor {

    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_TERMINATED = 5;

    private final Queue<Runnable> taskQueue;//任务队列
    private final Executor executor;  //通过调用execute()来启动线程
    private volatile Thread thread;   //指向当前线程的引用
    private final RejectedExecutionHandler rejectedExecutionHandler;//任务拒绝handler
    private volatile int state = ST_NOT_STARTED;

    private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER
            = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");;

    public SingleThreadEventExecutor() {
        this.taskQueue = new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE);
        this.executor = new ThreadPerTaskExecutor();
        this.rejectedExecutionHandler = new RejectedExecutionHandler() {
            @Override
            public void rejected(Runnable task, SingleThreadEventExecutor executor) {
                throw new RuntimeException("reject");
            }
        };
    }

    //第一次是启动线程、提交任务，之后执行都是提交任务
    public void execute(Runnable task) {
        boolean inEventLoop = inEventLoop();
        if (inEventLoop) {   //如果是由SingleThreadEventExecutor对应的线程执行的execute()
            addTask(task);
        } else {            //如果是由外部的其余线程执行的execute()
            startThread();
            addTask(task);
        }
    }

    public boolean inEventLoop() {
        return Thread.currentThread() == this.thread;
    }

    protected void addTask(Runnable task) {
        if (!offerTask(task)) {
            reject(task);
        }
    }

    //启动内部线程
    private void startThread(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                thread = Thread.currentThread();

                try {
                    SingleThreadEventExecutor.this.run();  //调用外部类的run方法
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    System.out.println("需要做一些结束工作");
                }
            }
        });
    }

    final boolean offerTask(Runnable task) {
        return taskQueue.offer(task);
    }

    protected final void reject(Runnable task) {
        rejectedExecutionHandler.rejected(task, this);
    }

    //内部线程的执行主体，不断获取任务然后执行
    protected void run() {
        for (;;) {
            Runnable task = takeTask();
            if (task != null) {
                task.run();
            }

            if (confirmShutdown()) {
                break;
            }
        }
    }

    //ScheduledFutureTask先不考虑
    protected Runnable takeTask() {
        BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>) this.taskQueue;
        for (;;) {
            Runnable task = null;
            try {
                task = taskQueue.take();
            } catch (InterruptedException e) {
                // Ignore
            }
            return task;
        }
    }

    protected boolean confirmShutdown() {
        if (!isShuttingDown()) {
            return false;
        }
        return true;
    }

    public boolean isShuttingDown() {
        return STATE_UPDATER.get(this) >= ST_SHUTTING_DOWN;
    }

    public void shutdownGracefully() {
        STATE_UPDATER.getAndSet(this,ST_SHUTTING_DOWN);
    }


}
