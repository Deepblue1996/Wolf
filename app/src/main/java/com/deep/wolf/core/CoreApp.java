package com.deep.wolf.core;

import com.deep.dpwork.DpWorkApplication;
import com.deep.dpwork.annotation.DpBugly;
import com.deep.dpwork.annotation.DpCrash;
import com.deep.dpwork.annotation.DpDataBase;
import com.deep.dpwork.annotation.DpLang;
import com.deep.dpwork.lang.LanguageType;
import com.deep.wolf.data.AppBean;

/**
 * Class - 应用入口
 * <p>
 * Created by Deepblue on 2018/9/29 0029.
 */
@DpCrash
@DpLang(LanguageType.LANGUAGE_FOLLOW_SYSTEM)
@DpBugly("1d60b91a82")
public class CoreApp extends DpWorkApplication {

    @DpDataBase(AppBean.class)
    public static AppBean appData;

    /**
     * 初始化函数
     * Bugly ID
     */
    @Override
    protected void initApplication() {
    }

}
