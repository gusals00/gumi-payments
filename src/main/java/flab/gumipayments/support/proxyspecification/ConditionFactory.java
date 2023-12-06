package flab.gumipayments.support.proxyspecification;

import flab.gumipayments.support.proxyspecification.handler.AndHandler;
import flab.gumipayments.support.proxyspecification.handler.NotHandler;
import flab.gumipayments.support.proxyspecification.handler.OrHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ConditionFactory<T, C> {

    public static <T> Condition2<T> createAndCondition(Condition2<T> left, Condition2<T> right) {
        return (Condition2<T>) createInstance(getInterface(left), new AndHandler(left, right));
    }

    public static <T> Condition2<T> createOrCondition(Condition2<T> left, Condition2<T> right) {
        return (Condition2<T>) createInstance(getInterface(left), new OrHandler(left, right));
    }

    public static <T> Condition2<T> createNotCondition(Condition2<T> condition) {
        return (Condition2<T>) createInstance(getInterface(condition), new NotHandler(condition));
    }

    private static Object createInstance(Class<?> targetClass, InvocationHandler handler) {
        return Proxy.newProxyInstance(
                targetClass.getClassLoader(),
                new Class[]{targetClass},
                handler
        );
    }

    private static <T> Class<?> getInterface(Condition2<T> object) {
        return Arrays.stream(object.getClass().getInterfaces()).findFirst().get();
    }
}
