package util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

public class ReflectUtil {

    public static Method getMethod(ProceedingJoinPoint pjp) {
        Method targetMethod = ((MethodSignature)pjp.getSignature()).getMethod();
        return ClassUtils.getMostSpecificMethod(targetMethod, pjp.getTarget().getClass());
    }

    public static Object getFieldValue(String field, Object obj) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
        return beanWrapper.getPropertyValue(field);
    }
}
