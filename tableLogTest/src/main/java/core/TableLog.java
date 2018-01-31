package core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableLog {

    String cause();       //引发改变的原因,唯一

    int argIndex() default 0;       //所需参数在输入参数中的位置(第几个)，从0开始

    TableLogType type();   //操作类型

}
