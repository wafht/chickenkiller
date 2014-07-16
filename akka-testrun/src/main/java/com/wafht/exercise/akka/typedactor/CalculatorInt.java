package com.wafht.exercise.akka.typedactor;

import akka.dispatch.Future;
import akka.japi.Option;

/**
 * @author haitao.fu
 * @since: 7/15/14
 */
public interface CalculatorInt {
    public Future<Integer> add(Integer first, Integer second);

    public Future<Integer> subtract(Integer first, Integer second);

    public void incrementCount();

    public Option<Integer> incrementAndReturn();
}
