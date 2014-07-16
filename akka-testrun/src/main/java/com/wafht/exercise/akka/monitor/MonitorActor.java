package com.wafht.exercise.akka.monitor;

import java.util.Map;

import com.google.common.collect.Maps;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author haitao.fu
 * @since: 7/16/14
 */
public class MonitorActor extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), MonitorActor.class);

    Map<ActorRef, ActorRef> monitoredActors = Maps.newHashMap();

    @Override
    public void preStart() {
        logger.info("starting monitor actor " + getSelf().path());
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof Terminated) {
            final Terminated t = (Terminated) o;
            logger.info(" receiced worker actor termination message ->" + t.getActor().path());
            if (monitoredActors.containsKey(t.getActor())) {
                monitoredActors.get(t.getActor()).tell(new DeadWorker());
            }
        } else if (o instanceof RegisterWorker) {
            RegisterWorker registerWorker = (RegisterWorker) o;
            getContext().watch(registerWorker.getWorker());
            monitoredActors.put(registerWorker.getWorker(), registerWorker.getSupervisor());
            logger.info("received monitor register msg -->" + registerWorker.toString());
        } else {
            unhandled(o);
        }
    }

    @Override
    public void postStop() {
        logger.info("stopping monitor actor " + getSelf().path());
    }

}
