package demo;

import core.Invocation;
import core.Proxy;

public class Main {

    public static void main(String[] args) {
        //准备invocation
        Invocation invocation = new Invocation();
        invocation.setMethodName("sayHello");
        invocation.setParameterTypes(new Class[]{String.class});
        invocation.setArguments(new Object[]{"gxh"});
        //准备接口实现类实例
        HelloService instance = new HelloServiceImpl();
        Proxy proxy = new Proxy(HelloService.class, instance);

        System.out.println(proxy.invoke(invocation).getResult());
    }


}
