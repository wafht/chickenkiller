package com.wafht.exercise.akka.remote;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

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
        remoteActor = getContext().actorFor("akka.tcp://RemoteNodeApp@127.0.0.1:2551/user/remoteActor");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(remoteActor, message.toString(), timeout);
        String result = (String) Await.result(future, timeout.duration());
        logger.info("receive message ->" + result);
    }
}
