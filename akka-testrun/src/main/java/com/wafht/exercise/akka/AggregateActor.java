package com.wafht.exercise.akka;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */
public class AggregateActor extends UntypedActor {

    private final Map<String, Integer> state = new HashMap<String, Integer>();

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ReduceData) {
            ReduceData data = (ReduceData) o;
            HashMap<String, Integer> change = data.getReduceDataList();
            for (Map.Entry<String, Integer> entry : change.entrySet()) {
                String key = entry.getKey();
                Integer orig = state.get(key);
                if (orig == null) {
                    orig = 0;
                }
                state.put(key, orig + entry.getValue());
            }
        } else if (o instanceof Result) {
            getSender().tell(state.toString());
        } else {
            unhandled(o);
        }
    }

}
