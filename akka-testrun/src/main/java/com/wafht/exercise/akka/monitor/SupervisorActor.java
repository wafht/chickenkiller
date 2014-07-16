package com.wafht.exercise.akka.monitor;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.util.Duration;

import static akka.actor.SupervisorStrategy.*;

/**
 * @author haitao.fu
 * @since: 7/16/14
 */
public class SupervisorActor extends UntypedActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), SupervisorActor.class);

    ActorRef workerActor = getContext().actorOf(new Props(WorkerActor.class), "worker");

    ActorRef monitor = getContext().system().actorOf(new Props(MonitorActor.class), "monitor");

    private SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.parse("5 second"), new Function<Throwable, Directive>() {
        @Override
        public Directive apply(Throwable throwable) throws Exception {
            if (throwable instanceof IllegalArgumentException) {
                return stop();
            } else {
                return resume();
            }
        }
    });


    @Override
    public void preStart() {
        logger.info("starting supervisor actor #" + this.hashCode());
        monitor.tell(new RegisterWorker(workerActor, self()));
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof Result) {
            workerActor.tell(o, getSender());
        } else if (o instanceof DeadWorker) {
            logger.info("receive dead worker msg ");
            workerActor = getContext().actorOf(new Props(WorkerActor.class), "workerActor");
            monitor.tell(new RegisterWorker(workerActor, self()));
        } else {
            workerActor.tell(o);
        }
    }

    @Override
    public void postStop() {
        logger.info("stoping supervisor actor #" + this.hashCode());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
