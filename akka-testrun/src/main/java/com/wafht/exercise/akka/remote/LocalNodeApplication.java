package com.wafht.exercise.akka.remote;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author haitao.fu
 * @since: 7/28/14
 */
public class LocalNodeApplication {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("LocalNodeApp", ConfigFactory.load().getConfig("LocalSys"));
        ActorRef localActor = system.actorOf(new Props(LocalActor.class), "localActor");

        localActor.tell("hello!");
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.shutdown();
    }
}
