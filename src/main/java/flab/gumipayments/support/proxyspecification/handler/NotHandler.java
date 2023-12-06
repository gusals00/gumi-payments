package flab.gumipayments.support.proxyspecification.handler;

import flab.gumipayments.support.proxyspecification.Condition2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class NotHandler implements InvocationHandler {
    private Condition2 left;
    private Condition2 right;

    public NotHandler(Condition2 left) {
        this.left = left;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("isSatisfiedBy")) {
            return !left.isSatisfiedBy(args[0]);
        } else {
            return InvocationHandler.invokeDefault(proxy, method, args);
        }
    }
}


