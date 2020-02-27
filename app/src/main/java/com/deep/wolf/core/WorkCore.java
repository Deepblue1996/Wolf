package com.deep.wolf.core;

import android.Manifest;

import com.deep.dpwork.DpWorkCore;
import com.deep.dpwork.annotation.DpPermission;

/**
 * Class - 框架入口
 * <p>
 * Created by Deepblue on 2018/9/29 0029.
 */
@DpPermission({
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.INTERNET,
        Manifest.permission.READ_PHONE_STATE})
public class WorkCore extends DpWorkCore {

    /**
     * 框架初始化，并设置第一页面
     */
    @Override
    protected void initCore() {
    }


}
