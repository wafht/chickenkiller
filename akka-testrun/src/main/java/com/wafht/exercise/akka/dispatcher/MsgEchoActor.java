package com.wafht.exercise.akka.dispatcher;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author haitao.fu
 * @since: 14-7-15
 */
public class MsgEchoActor extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), MsgEchoActor.class);

    private int counter = 0;

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof String) {
            logger.info(this.toString() + " receive msg: count=" + (++counter) + ", using thread:" + Thread.currentThread());
        }
    }
}
