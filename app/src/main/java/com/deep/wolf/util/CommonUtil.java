package com.deep.wolf.util;

import android.content.Context;
import android.os.Environment;

/**
 * Class -
 * <p>
 * Created by Deepblue on 2018/12/28 0028.
 */

public class CommonUtil {
    /**
     * 获取cache路径
     *
     * @param context
     * @return
     */
    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }
}
