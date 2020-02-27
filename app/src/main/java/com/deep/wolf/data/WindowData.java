package com.deep.wolf.data;

import com.deep.dpwork.data.BaseData;

import java.util.List;

public class WindowData extends BaseData {
    public List<WindowBean> windowBeanList;

    public WindowData(List<WindowBean> windowBeanList) {
        this.windowBeanList = windowBeanList;
    }
}
