package com.wafht.exercise.akka.remote;

import akka.actor.UntypedActor;

/**
 * @author haitao.fu
 * @since: 7/27/14
 */
public class RemoteActor extends UntypedActor {
    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof String) {
            getSender().tell(o + " got something!");
        }
    }
}
