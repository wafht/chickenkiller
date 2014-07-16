package com.wafht.exercise.akka.actor;

/**
 * @author haitao.fu
 * @since: 14-7-9
 */
public final class WordCount {
    private final String word;
    private final Integer count;

    public WordCount(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }
}
