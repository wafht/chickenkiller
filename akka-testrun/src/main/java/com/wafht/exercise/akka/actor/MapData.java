package com.wafht.exercise.akka.actor;

import java.util.List;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */
public final class MapData {
    private final List<WordCount> dataList;

    public List<WordCount> getDataList() {
        return dataList;
    }

    public MapData(List<WordCount> dataList) {
        this.dataList = dataList;
    }
}
