package com.wafht.exercise.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */
public class MasterActor extends UntypedActor {

    private final ActorRef mapActor = getContext().actorOf(new Props(MapActor.class).withRouter(new RoundRobinRouter(5)), "map");
    private final ActorRef reduceActor = getContext().actorOf(new Props(ReduceActor.class).withRouter(new RoundRobinRouter(5)), "reduce");
    private final ActorRef aggregateActor = getContext().actorOf(new Props(AggregateActor.class), "aggregate");


    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof String) {
            System.out.println("receive msg:" + o);
            mapActor.tell(o, getSelf());
        } else if (o instanceof MapData) {
            reduceActor.tell(o, getSelf());
            System.out.println("receive msg:" + ((MapData) o).getDataList());
        } else if (o instanceof ReduceData) {
            aggregateActor.tell(o);
            System.out.println("receive msg:" + ((ReduceData) o).getReduceDataList());
        } else if (o instanceof Result) {
            aggregateActor.forward(o, getContext());
        } else {
            System.out.println("unhandle msg:" + o);
            unhandled(o);
        }
    }

}
