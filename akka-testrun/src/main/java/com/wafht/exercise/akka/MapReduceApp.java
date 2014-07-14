package com.wafht.exercise.akka;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */
public class MapReduceApp {
    public static void main(String[] args) throws Exception {
        Timeout timeout = new Timeout(Duration.parse("5 seconds"));
        ActorSystem _system = ActorSystem.create("MapReduceApp");
        ActorRef master = _system.actorOf(new Props(MasterActor.class), "master");
        master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog");
        master.tell("Dog is man's best friend");
        master.tell("Dog and Fox belong to the same family");
        Thread.sleep(5000);
        Future<Object> future = Patterns.ask(master, new Result(), timeout);
        String result = (String) Await.result(future, timeout.duration());
        System.out.println(result);
        _system.shutdown();
    }
}
