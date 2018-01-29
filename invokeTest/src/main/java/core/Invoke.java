package core;

//统一封装对类中方法的调用，输入invocation，输出result
public interface Invoke {

    Result invoke(Invocation invocation);

}
