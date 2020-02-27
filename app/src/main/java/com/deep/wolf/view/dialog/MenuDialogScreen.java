package com.deep.wolf.view.dialog;

import android.widget.LinearLayout;

import com.deep.dpwork.annotation.DpLayout;
import com.deep.dpwork.annotation.DpNullAnim;
import com.deep.dpwork.annotation.DpSlidingAnim;
import com.deep.wolf.R;
import com.deep.wolf.base.TDialogScreen;

import butterknife.BindView;

@DpNullAnim
@DpSlidingAnim
@DpLayout(R.layout.menu_dialog_screen)
public class MenuDialogScreen extends TDialogScreen {

    @BindView(R.id.backBgTouch2)
    LinearLayout backBgTouch2;

    @Override
    public void init() {
        backBgTouch2.setOnClickListener(v -> close());
    }
}
