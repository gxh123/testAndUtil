package bytecode;

public abstract class Wrapper {

    abstract public Object invokeMethod(Object instance, String mn, Class<?>[] types, Object[] args) ;

}