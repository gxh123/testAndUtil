package core;

public class Result {

    private Object result;
    private Throwable exception;

    public Result(Object result) {
        this.result = result;
    }

    public Result(Throwable exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
