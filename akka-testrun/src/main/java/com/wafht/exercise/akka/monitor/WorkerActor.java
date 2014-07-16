package com.wafht.exercise.akka.monitor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author haitao.fu
 * @since: 7/16/14
 */
public class WorkerActor extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), WorkerActor.class);

    private int state = 0;

    @Override
    public void preStart() {
        logger.info("starting workeractor instance hashcode #" + this.hashCode());
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof Integer) {
            Integer value = (Integer) o;
            state = value;
            logger.info("received a msg " + value);
        } else if (o instanceof Result) {
            getSender().tell(state);
        } else {
            throw new IllegalArgumentException("Wrong Argument");
        }
    }

    @Override
    public void postStop() {
        logger.info("stopping workeractor instance hashcode #" + this.hashCode());
    }
}

class Result {
}

class DeadWorker {
}

class RegisterWorker {
    ActorRef worker;
    ActorRef supervisor;

    RegisterWorker(ActorRef worker, ActorRef supervisor) {
        this.worker = worker;
        this.supervisor = supervisor;
    }

    public ActorRef getWorker() {
        return worker;
    }

    public ActorRef getSupervisor() {
        return supervisor;
    }

    @Override
    public String toString() {
        return "RegisterWorker{" +
                "worker=" + worker +
                ", supervisor=" + supervisor +
                '}';
    }
}




