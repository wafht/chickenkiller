package com.wafht.exercise.akka.typedactor;

import akka.actor.TypedActor;
import akka.dispatch.Future;
import akka.dispatch.Futures;
import akka.japi.Option;

/**
 * @author haitao.fu
 * @since: 7/15/14
 */
public class Calculator implements CalculatorInt {
    private Integer counter = 0;

    @Override
    public Future<Integer> add(Integer first, Integer second) {
        return Futures.successful(first + second, TypedActor.dispatcher());
    }

    @Override
    public Future<Integer> subtract(Integer first, Integer second) {
        return Futures.successful(first - second, TypedActor.dispatcher());
    }

    @Override
    public void incrementCount() {
        counter++;
    }

    @Override
    public Option<Integer> incrementAndReturn() {
        return Option.some(++counter);
    }
}
