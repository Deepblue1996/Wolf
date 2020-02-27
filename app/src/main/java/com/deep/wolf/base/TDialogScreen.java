package com.deep.wolf.base;

import com.deep.dpwork.dialog.DialogScreen;

import butterknife.ButterKnife;

/**
 * Class -
 * <p>
 * Created by Deepblue on 2019/5/13 0013.
 */

public abstract class TDialogScreen extends DialogScreen {

    @Override
    protected void initView() {
        ButterKnife.bind(this, superView);
        init();
    }

    public abstract void init();
}
