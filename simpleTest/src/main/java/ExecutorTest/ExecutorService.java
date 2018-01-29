package ExecutorTest;

import java.util.concurrent.Callable;

//提供结束方法来结束Executor的执行，提供Future类来跟踪异步任务的执行情况
public interface ExecutorService extends Executor {

    void shutdown();

    boolean isShutdown();

    <T> Future<T> submit(Callable<T> task);

    <T> Future<T> submit(Runnable task, T result);

    Future<?> submit(Runnable task);
}