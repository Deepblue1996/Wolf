package com.deep.wolf.view.dialog;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.dpwork.adapter.DpAdapter;
import com.deep.dpwork.annotation.DpBlur;
import com.deep.dpwork.annotation.DpLayout;
import com.deep.dpwork.dialog.DialogScreen;
import com.deep.dpwork.dialog.DpDialogScreen;
import com.deep.dpwork.util.DBUtil;
import com.deep.dpwork.util.InputManagerUtil;
import com.deep.wolf.R;
import com.deep.wolf.base.TDialogScreen;
import com.deep.wolf.core.CoreApp;
import com.deep.wolf.data.HistoryBean;
import com.deep.wolf.event.SearchEvent;
import com.deep.wolf.view.MainScreen;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

@DpLayout(R.layout.search_dialog_screen)
public class SearchDialogScreen extends TDialogScreen {

    @BindView(R.id.backBgTouch)
    LinearLayout backBgTouch;

    @BindView(R.id.googleLin)
    RelativeLayout googleLin;
    @BindView(R.id.googleText)
    TextView googleText;
    @BindView(R.id.baiduLin)
    RelativeLayout baiduLin;
    @BindView(R.id.baiduText)
    TextView baiduText;
    @BindView(R.id.sogouLin)
    RelativeLayout sogouLin;
    @BindView(R.id.sogouText)
    TextView sogouText;
    @BindView(R.id.biyingLin)
    RelativeLayout biyingLin;
    @BindView(R.id.biyingText)
    TextView biyingText;
    @BindView(R.id.clearTouch)
    TextView clearTouch;
    @BindView(R.id.nullText)
    TextView nullText;

    @BindView(R.id.searchEdit)
    EditText searchEdit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void init() {
        MainScreen.state = 1;

        selectLin(CoreApp.appData.searchInt);

        backBgTouch.setOnClickListener(v -> {
            InputManagerUtil.hiddenSoftInputFromWindow(getActivityContext(), searchEdit);
            close();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DpAdapter dpAdapter = DpAdapter.newLine(getContext(), CoreApp.appData.hostString, R.layout.history_item_layout)
                .itemView((universalViewHolder, i) -> {
                    universalViewHolder.setImgRes(R.id.contentImg, getSearchImgId(CoreApp.appData.hostString.get(i).searchInt));
                    universalViewHolder.setText(R.id.contentText, CoreApp.appData.hostString.get(i).content);
                })
                .itemClick((view, i) -> {
                    InputManagerUtil.hiddenSoftInputFromWindow(getActivityContext(), searchEdit);
                    close(() -> EventBus.getDefault().post(new SearchEvent(CoreApp.appData.hostString.get(i))));
                });

        recyclerView.setAdapter(dpAdapter);
        if (CoreApp.appData.hostString.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            nullText.setVisibility(View.VISIBLE);
        } else {
            nullText.setVisibility(View.GONE);
        }

        searchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == 0 || actionId == 3) && event != null) {

                HistoryBean historyBean = new HistoryBean();
                historyBean.content = searchEdit.getText().toString();
                historyBean.searchInt = CoreApp.appData.searchInt;
                CoreApp.appData.hostString.add(historyBean);
                DBUtil.save(CoreApp.appData);

                InputManagerUtil.hiddenSoftInputFromWindow(getActivityContext(), searchEdit);
                close(() -> EventBus.getDefault().post(new SearchEvent(historyBean)));
            }
            return false;
        });

        InputManagerUtil.showSoftInputFromWindow(getActivityContext(), searchEdit);

        googleLin.setOnClickListener(v -> selectLin(0));
        baiduLin.setOnClickListener(v -> selectLin(1));
        sogouLin.setOnClickListener(v -> selectLin(2));
        biyingLin.setOnClickListener(v -> selectLin(3));

        clearTouch.setOnClickListener(v -> {
            InputManagerUtil.hiddenSoftInputFromWindow(getActivityContext(), searchEdit);
            DpDialogScreen.create().setMsg("Empty history?")
                    .addButton(getContext(), "Yes", dialogScreen -> {
                        CoreApp.appData.hostString.clear();
                        DBUtil.save(CoreApp.appData);
                        recyclerView.setVisibility(View.GONE);
                        nullText.setVisibility(View.VISIBLE);
                        dialogScreen.close();
                    })
                    .addButton(getContext(), "No", DialogScreen::close)
                    .open(fragmentManager());
        });
    }

    private void selectLin(int i) {
        googleLin.setBackgroundColor(Color.parseColor("#ffffff"));
        baiduLin.setBackgroundColor(Color.parseColor("#ffffff"));
        sogouLin.setBackgroundColor(Color.parseColor("#ffffff"));
        biyingLin.setBackgroundColor(Color.parseColor("#ffffff"));
        googleText.setTextColor(Color.parseColor("#000000"));
        baiduText.setTextColor(Color.parseColor("#000000"));
        sogouText.setTextColor(Color.parseColor("#000000"));
        biyingText.setTextColor(Color.parseColor("#000000"));
        switch (i) {
            case 0:
                googleLin.setBackgroundColor(Color.parseColor("#000000"));
                googleText.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                baiduLin.setBackgroundColor(Color.parseColor("#000000"));
                baiduText.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                sogouLin.setBackgroundColor(Color.parseColor("#000000"));
                sogouText.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 3:
                biyingLin.setBackgroundColor(Color.parseColor("#000000"));
                biyingText.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
        CoreApp.appData.searchInt = i;
        DBUtil.save(CoreApp.appData);
    }

    private int getSearchImgId(int id) {
        switch (id) {
            case 0:
                return R.mipmap.google_ico;
            case 1:
                return R.mipmap.baidu_ico;
            case 2:
                return R.mipmap.sougou_ico;
            case 3:
                return R.mipmap.biying_ico;
        }
        return R.mipmap.google_ico;
    }

    @Override
    public void onBack() {

        InputManagerUtil.hiddenSoftInputFromWindow(getActivityContext(), searchEdit);
        close();
    }
}
