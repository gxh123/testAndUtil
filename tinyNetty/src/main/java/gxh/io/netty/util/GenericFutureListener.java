package gxh.io.netty.util;

import java.util.EventListener;


public interface GenericFutureListener<F extends Future<?>> extends EventListener {

    void operationComplete(F future) throws Exception;
}
