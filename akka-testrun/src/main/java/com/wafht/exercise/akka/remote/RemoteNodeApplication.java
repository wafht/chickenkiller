package com.wafht.exercise.akka.remote;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.kernel.Bootable;

/**
 * @author haitao.fu
 * @since: 7/27/14
 */
public class RemoteNodeApplication implements Bootable {

    ActorSystem system = ActorSystem.create("RemoteNodeApp", ConfigFactory.load().getConfig("RemoteSys"));

    @Override
    public void startup() {
        system.actorOf(new Props(RemoteActor.class), "remoteActor");
    }

    @Override
    public void shutdown() {
        system.shutdown();
    }
}
