package com.deep.wolf.view.dialog;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.dpwork.adapter.DpAdapter;
import com.deep.dpwork.annotation.DpLayout;
import com.deep.dpwork.annotation.DpNullAnim;
import com.deep.dpwork.annotation.DpSlidingAnim;
import com.deep.wolf.R;
import com.deep.wolf.base.TDialogScreen;
import com.deep.wolf.data.WindowData;

import butterknife.BindView;

@DpNullAnim
@DpSlidingAnim
@DpLayout(R.layout.window_dialog_screen)
public class WindowDialogScreen extends TDialogScreen {

    @BindView(R.id.backBgTouch3)
    RelativeLayout backBgTouch3;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void init() {

        backBgTouch3.setOnClickListener(v -> close());

        WindowData windowData = (WindowData) this.baseData;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivityContext(), RecyclerView.HORIZONTAL, false));

        DpAdapter dpAdapter = DpAdapter.newLine(getContext(), windowData.windowBeanList, R.layout.window_item_layout)
                .itemView((universalViewHolder, i) -> {
                    ((ImageView)universalViewHolder.vbi(R.id.contentImg)).setImageBitmap(windowData.windowBeanList.get(i).bitmap);
                    universalViewHolder.setText(R.id.contentText, windowData.windowBeanList.get(i).name);
                })
                .itemClick((view, i) -> {

                });

        recyclerView.setAdapter(dpAdapter);
    }

    @Override
    public void onBack() {
        close();
    }
}
