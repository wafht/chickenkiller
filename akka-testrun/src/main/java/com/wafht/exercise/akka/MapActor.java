package com.wafht.exercise.akka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import akka.actor.UntypedActor;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */
public class MapActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof String) {
            System.out.println("mapactor receive msg:" + o);
            String msg = (String) o;
            getSender().tell(evaluateExpression(msg));
        } else {
            unhandled(o);
        }
    }

    private MapData evaluateExpression(String msg) {
        List<WordCount> wordsCount = new ArrayList<WordCount>();
        if (msg == null || "".equals(msg)) {
            return new MapData(Collections.<WordCount>emptyList());
        }
        for (String word : msg.split("\\s+")) {
            WordCount wordCount = new WordCount(word, 1);
            wordsCount.add(wordCount);
        }
        return new MapData(wordsCount);
    }
}
