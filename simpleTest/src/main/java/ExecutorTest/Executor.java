package ExecutorTest;

//执行提交的Runnable task，解耦任务的提交方与执行方
public interface Executor {

    void execute(Runnable command);

}
