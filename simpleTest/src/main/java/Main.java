public class Main {

    public static void main(String[] args) {
        B b = getB();
        b.doSomething();
    }

    public static B getB(){
        A a = new A();
        B b = new B(){
            @Override
            void doSomething() {
                a.getHello();
            }
        };
        return b;
    }
}

class A{

    public void getHello(){
        System.out.println("调用getHello");
    }

}

abstract class B{
    abstract void doSomething();
}
