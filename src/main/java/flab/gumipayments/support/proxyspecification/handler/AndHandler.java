package flab.gumipayments.support.proxyspecification.handler;

import flab.gumipayments.support.proxyspecification.Condition2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AndHandler implements InvocationHandler {
    private Condition2 left;
    private Condition2 right;

    public AndHandler(Condition2 left, Condition2 right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("isSatisfiedBy")) {
            return left.isSatisfiedBy(args[0]) && right.isSatisfiedBy(args[0]);
        } else {
            return InvocationHandler.invokeDefault(proxy, method, args);
        }
    }
}


