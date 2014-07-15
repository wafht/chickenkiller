package com.wafht.exercise.akka.actor;

import java.util.HashMap;

import akka.actor.UntypedActor;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */
public class ReduceActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof MapData) {
            MapData data = (MapData) o;
            HashMap<String, Integer> res = new HashMap<String, Integer>();
            for (WordCount wc : data.getDataList()) {
                Integer count = res.get(wc.getWord());
                if (count == null) {
                    count = 0;
                }
                res.put(wc.getWord(), count + 1);
            }
            getSender().tell(new ReduceData(res));
        } else {
            unhandled(o);
        }
    }

}
