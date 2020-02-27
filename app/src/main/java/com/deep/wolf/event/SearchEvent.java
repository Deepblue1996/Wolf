package com.deep.wolf.event;

import com.deep.wolf.data.HistoryBean;

public class SearchEvent {
    public HistoryBean content;

    public SearchEvent(HistoryBean content) {
        this.content = content;
    }
}
