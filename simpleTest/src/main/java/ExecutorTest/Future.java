package ExecutorTest;


import java.util.concurrent.ExecutionException;

//代表异步任务的执行结果，提供了检查任务是否执行完成，获取结果，取消任务等方法
public interface Future<V> {

    boolean cancel(boolean mayInterruptIfRunning);

    boolean isCancelled();

    boolean isDone();

    V get() throws InterruptedException, ExecutionException;
}