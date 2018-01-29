package core;

import bytecode.Wrapper;
import bytecode.WrapperFactory;

//参考java动态代理，生成构造器输入对象的代理对象
public class Proxy implements Invoke {

    private Class type;      //接口
    private Object object;   //用于接收接口具体实现类的实例对象
    private Wrapper wrapper; //

    public Proxy(Class type, Object obj) {
        this.object = obj;
        this.type = type;
        this.wrapper = WrapperFactory.getWrapper(type);
    }

    @Override
    public Result invoke(Invocation invocation) {
        try {
            return new Result(wrapper.invokeMethod(object, invocation.getMethodName(),
                    invocation.getParameterTypes(), invocation.getArguments()));
        }catch (Exception e){
            return new Result(e);
        }
    }
}
