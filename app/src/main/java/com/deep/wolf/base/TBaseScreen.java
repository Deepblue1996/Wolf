package com.deep.wolf.base;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.deep.dpwork.base.BaseScreen;

import butterknife.ButterKnife;

/**
 * Class -
 * <p>
 * Created by Deepblue on 2019/5/13 0013.
 */

public abstract class TBaseScreen extends BaseScreen {

    /**
     * 判断是否是全面屏
     */
    private volatile static boolean mHasCheckAllScreen;
    private volatile static boolean mIsAllScreenDevice;

    public static boolean isAllScreenDevice(Context context) {
        if (mHasCheckAllScreen) {
            return mIsAllScreenDevice;
        }
        mHasCheckAllScreen = true;
        mIsAllScreenDevice = false;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            if (height / width >= 1.97f) {
                mIsAllScreenDevice = true;
            }
        }
        return mIsAllScreenDevice;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, superView);
        init();
    }

    @Override
    public boolean onBackPressedSupport() {
        onBack();
        return true;
    }

    public abstract void init();
}
