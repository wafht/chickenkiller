package com.wafht.exercise.akka.actor;

import java.util.HashMap;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */

public final class ReduceData {
    private final HashMap<String, Integer> reduceDataList;

    public HashMap<String, Integer> getReduceDataList() {
        return reduceDataList;
    }

    public ReduceData(HashMap<String, Integer> reduceDataList) {
        this.reduceDataList = reduceDataList;
    }
}

