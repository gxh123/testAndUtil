package ExecutorTest;

//A Future that is  Runnable
//
public interface RunnableFuture<V> extends Runnable, Future<V> {

    void run();
}
