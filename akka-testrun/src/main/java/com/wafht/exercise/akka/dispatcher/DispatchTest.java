package com.wafht.exercise.akka.dispatcher;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinRouter;

/**
 * @author haitao.fu
 * @since: 14-7-15
 */
public class DispatchTest {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("dispatcher", ConfigFactory.load().getConfig("MyDispatcherExample"));
        ActorRef actor = system.actorOf(new Props(MsgEchoActor.class).withDispatcher("thread-pool-dispatcher").withRouter(new RoundRobinRouter(5)));

        for (int i = 0; i < 1000; i++)
            actor.tell("hello," + i);

        system.shutdown();
    }
}
