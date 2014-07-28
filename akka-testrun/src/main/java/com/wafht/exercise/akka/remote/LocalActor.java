package com.wafht.exercise.akka.remote;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

/**
 * @author haitao.fu
 * @since: 7/28/14
 */
public class LocalActor extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), LocalActor.class);

    ActorRef remoteActor;

    @Override
    public void preStart() {
        logger.info("init remote actor ref...");
        remoteActor = getContext().actorFor("akka://RemoteNodeApp@172.22.79.21/user/remoteActor");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        Timeout timeout = new Timeout(Duration.parse("5 second"));
        Future<Object> future = Patterns.ask(remoteActor, message.toString(), timeout);
        String result = (String) Await.result(future, timeout.duration());
        logger.info("receive message ->" + result);
    }
}
