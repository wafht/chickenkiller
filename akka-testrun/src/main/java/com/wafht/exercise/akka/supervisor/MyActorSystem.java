package com.wafht.exercise.akka.supervisor;

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
 * @since: 14-7-15
 */
public class MyActorSystem {
    private static ActorSystem system = ActorSystem.create("supervisorTest");
    private static final LoggingAdapter logger = Logging.getLogger(system, MyActorSystem.class);

    public MyActorSystem() {
        super();
    }

    public static void main(String[] args) throws Exception {
        Integer originalValue = Integer.valueOf(0);

        ActorRef supervisor = system.actorOf(new Props(SupervisorActor.class), "supervisor");

        logger.info("Sending value 8, no exceptions should be thrown! ");
        supervisor.tell(Integer.valueOf(8));

        Integer result = (Integer) Await.result(
                Patterns.ask(supervisor, new WorkerActor.Result(), 5000),
                Duration.create(5000, TimeUnit.MILLISECONDS));
        logger.info("Value Recieved->" + result);
        assert result.equals(Integer.valueOf(8));


        logger.info("Sending value -8, ArithmeticException should be thrown! Our Supervisor strategy says resume !");
        supervisor.tell(Integer.valueOf(-8));

        result = (Integer) Await.result(
                Patterns.ask(supervisor, new WorkerActor.Result(), 5000),
                Duration.create(5000, TimeUnit.MILLISECONDS));

        logger.info("Value Recieved->" + result);
        assert result.equals(Integer.valueOf(8));

        logger.info("Sending value null, NullPointerException should be thrown! Our Supervisor strategy says restart !");
        supervisor.tell(null);

        result = (Integer) Await.result(
                Patterns.ask(supervisor, new WorkerActor.Result(), 5000),
                Duration.create(5000, TimeUnit.MILLISECONDS));

        logger.info("Value Recieved->" + result);
        assert originalValue.equals(result);
        logger.info("Sending value \"String\", NullPointerException should be thrown! Our Supervisor strategy says Stop !");

        supervisor.tell(String.valueOf("Do Something"));

        logger.info("Worker Actor shutdown !");
        system.shutdown();
    }
}
