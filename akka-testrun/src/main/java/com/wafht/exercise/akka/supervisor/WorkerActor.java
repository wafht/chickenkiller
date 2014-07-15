package com.wafht.exercise.akka.supervisor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author haitao.fu
 * @since: 14-7-15
 */
public class WorkerActor extends UntypedActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), WorkerActor.class);

    private int state = 0;

    public static class Result {
    }

    @Override
    public void preStart() {
        logger.info("Starting WorkerActor instance hashcode # {}",
                this.hashCode());

    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o == null) {
            throw new NullPointerException("Null Value Passed");
        } else if (o instanceof Integer) {
            Integer value = (Integer) o;
            if (value <= 0) {
                throw new ArithmeticException("Number equal or less than zero");
            } else
                state = value;
        } else if (o instanceof Result) {
            getSender().tell(state);
        } else {
            throw new IllegalArgumentException("Wrong Argument");
        }

    }


    @Override
    public void postStop() {
        logger.info("Stopping WorkerActor instance hashcode # {}",
                this.hashCode());
    }

}
