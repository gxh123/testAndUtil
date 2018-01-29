package test2;

public class A {
    int i = 1;

    public void run(){
        System.out.println("外部类run");
    }

    public A() {

        Thread thread = new Thread() {
            public void run()
            {
                System.out.println("内部类run");
                A.this.run();//调用外部类的run方法。
            }
        };
        thread.start();

    }

    public static void main(String[] args) {
        new A();
    }

}