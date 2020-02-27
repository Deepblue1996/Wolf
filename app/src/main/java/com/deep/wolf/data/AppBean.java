package com.deep.wolf.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class -
 * <p>
 * Created by Deepblue on 2019/3/4 0004.
 */

public class AppBean implements Serializable {
    // 皮肤
    public boolean huState = false;
    // 当前搜索引擎
    public int searchInt;
    // 搜索历史
    public List<HistoryBean> hostString = new ArrayList<>();
}
