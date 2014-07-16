package com.wafht.exercise.akka.monitor;


import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Duration;

/**
 * @author haitao.fu
 * @since: 7/16/14
 */
public class MonitorTest {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("monitor-test");
        LoggingAdapter logger = Logging.getLogger(system, MonitorTest.class);

        ActorRef supervisor = system.actorOf(new Props(SupervisorActor.class), "supervisor");

        supervisor.tell(Integer.valueOf(100));
        supervisor.tell("10");

        Thread.sleep(5000l);

        supervisor.tell(Integer.valueOf(100));

        Integer result = (Integer) Await.result(
                Patterns.ask(supervisor, new Result(), 5000),
                Duration.create(5000, TimeUnit.MILLISECONDS));
        logger.info("result -> " + result);


        supervisor.tell("10");

        Thread.sleep(5000l);

        system.shutdown();

    }
}
