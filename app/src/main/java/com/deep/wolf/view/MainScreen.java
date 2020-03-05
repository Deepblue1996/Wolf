package com.deep.wolf.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deep.dpwork.annotation.DpLayout;
import com.deep.dpwork.annotation.DpMainScreen;
import com.deep.dpwork.annotation.DpStatus;
import com.deep.dpwork.dialog.DialogScreen;
import com.deep.dpwork.dialog.DpDialogScreen;
import com.deep.dpwork.util.DBUtil;
import com.deep.wolf.R;
import com.deep.wolf.base.TBaseScreen;
import com.deep.wolf.core.CoreApp;
import com.deep.wolf.data.WindowBean;
import com.deep.wolf.data.WindowData;
import com.deep.wolf.event.HomeEvent;
import com.deep.wolf.event.SearchEvent;
import com.deep.wolf.util.CommonUtil;
import com.deep.wolf.view.dialog.MenuDialogScreen;
import com.deep.wolf.view.dialog.SearchDialogScreen;
import com.deep.wolf.view.dialog.WindowDialogScreen;
import com.deep.wolf.weight.ScanLoadingView;
import com.github.florent37.viewanimator.ViewAnimator;
import com.luck.picture.lib.tools.ScreenUtils;
import com.prohua.roundlayout.RoundAngleFrameLayout;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@DpMainScreen
@DpStatus(blackFont = false)
@DpLayout(R.layout.main_screen_layout)
public class MainScreen extends TBaseScreen {

    @BindView(R.id.backBg)
    RelativeLayout backBg;
    @BindView(R.id.lightTouch)
    ImageView lightTouch;
    @BindView(R.id.logoImg)
    ImageView logoImg;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.turnEdit)
    TextView turnEdit;
    @BindView(R.id.bottomLine)
    View bottomLine;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.goImg)
    ImageView goImg;
    @BindView(R.id.menuImg)
    ImageView menuImg;
    @BindView(R.id.windowsImg)
    ImageView windowsImg;
    @BindView(R.id.homeImg)
    ImageView homeImg;

    @BindView(R.id.refreshImg)
    ImageView refreshImg;

    @BindView(R.id.backTouBg)
    View backTouBg;

    @BindView(R.id.scanLoadingView)
    ScanLoadingView scanLoadingView;

    @BindView(R.id.backTouch)
    LinearLayout backTouch;
    @BindView(R.id.goTouch)
    LinearLayout goTouch;
    @BindView(R.id.menuTouch)
    LinearLayout menuTouch;
    @BindView(R.id.windowsTouch)
    LinearLayout windowsTouch;
    @BindView(R.id.homeTouch)
    LinearLayout homeTouch;
    @BindView(R.id.bottomLin)
    LinearLayout bottomLin;
    @BindView(R.id.roundAngleFrameLayout)
    RoundAngleFrameLayout roundAngleFrameLayout;
    @BindView(R.id.TopLin)
    RelativeLayout TopLin;
    @BindView(R.id.TopLinText)
    TextView TopLinText;

    @BindView(R.id.backImgView)
    RelativeLayout backImgView;

    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @BindView(R.id.animView)
    View animView;

    private boolean huState = true; // 日夜 默认夜间
    public static int state = 0; // 0 主页 1 搜索 2 网页

    private boolean isReloadData = false;

    private List<WindowBean> windowBeanList = new ArrayList<>();

    @Override
    public void init() {
        huState = CoreApp.appData.huState;
        initHu();
        initWebView();
        initTouchEvent();
    }

    private void initHu() {
        if (huState) {
            backTouBg.setBackgroundColor(Color.parseColor("#eeFFFFFF"));
            backBg.setBackgroundColor(Color.parseColor("#FFFFFF"));
            bottomLin.setBackgroundColor(Color.parseColor("#11000033"));
            TopLin.setBackgroundColor(Color.parseColor("#11000033"));
            turnEdit.setTextColor(Color.parseColor("#666666"));
            TopLinText.setTextColor(Color.parseColor("#666666"));
            bottomLine.setBackgroundColor(Color.parseColor("#667D7DFF"));
            lightTouch.setImageResource(R.mipmap.light_ico);
            logoImg.setImageResource(R.mipmap.wolf_logo);
            backImg.setImageResource(R.mipmap.back_ico);
            goImg.setImageResource(R.mipmap.go_ico);
            homeImg.setImageResource(R.mipmap.home_ico);
            menuImg.setImageResource(R.mipmap.menu_ico);
            windowsImg.setImageResource(R.mipmap.window_ico);
            refreshImg.setImageResource(R.mipmap.refresh_ico);
            setStatusBarBlackFont(true);
        } else {
            backTouBg.setBackgroundColor(Color.parseColor("#bb000000"));
            backBg.setBackgroundColor(Color.parseColor("#000000"));
            bottomLin.setBackgroundColor(Color.parseColor("#66333333"));
            TopLin.setBackgroundColor(Color.parseColor("#66ffffff"));
            turnEdit.setTextColor(Color.parseColor("#ffffff"));
            TopLinText.setTextColor(Color.parseColor("#ffffff"));
            bottomLine.setBackgroundColor(Color.parseColor("#999999"));
            lightTouch.setImageResource(R.mipmap.night_ico);
            logoImg.setImageResource(R.mipmap.wolf_night);
            backImg.setImageResource(R.mipmap.back_night_ico);
            goImg.setImageResource(R.mipmap.go_night_ico);
            homeImg.setImageResource(R.mipmap.home_night_ico);
            menuImg.setImageResource(R.mipmap.menu_night_ico);
            windowsImg.setImageResource(R.mipmap.window_night_ico);
            refreshImg.setImageResource(R.mipmap.refresh_night_ico);
            setStatusBarBlackFont(false);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTouchEvent() {
        lightTouch.setOnClickListener(v -> {
            huState = !huState;
            CoreApp.appData.huState = huState;
            DBUtil.save(CoreApp.appData);
            if (huState) {
                animView.setAlpha(0.0f);
                animView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                ViewAnimator.animate(animView).alpha(1.0f).duration(200).onStop(() -> {
                    initHu();
                    ViewAnimator.animate(animView).alpha(0.0f).duration(400).start();
                }).start();

            } else {
                animView.setAlpha(0.0f);
                animView.setBackgroundColor(Color.parseColor("#000000"));
                ViewAnimator.animate(animView).alpha(1.0f).duration(200).onStop(() -> {
                    initHu();
                    ViewAnimator.animate(animView).alpha(0.0f).duration(400).start();
                }).start();
            }
        });
        relativeLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                turnEdit.setAlpha(0.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                turnEdit.setAlpha(1.0f);
                SearchDialogScreen.prepare(SearchDialogScreen.class).open(fragmentManager());
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                turnEdit.setAlpha(1.0f);
            }
            return true;
        });
        backTouch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (huState) {
                    backTouch.setBackgroundColor(Color.parseColor("#22000000"));
                } else {
                    backTouch.setBackgroundColor(Color.parseColor("#22ffffff"));
                }
                backImg.setAlpha(0.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                backTouch.setBackgroundColor(Color.parseColor("#00333333"));
                backImg.setAlpha(1.0f);
                if (state == 2) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    }
                }
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                backTouch.setBackgroundColor(Color.parseColor("#00333333"));
                backImg.setAlpha(1.0f);
            }
            return true;
        });
        goTouch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (huState) {
                    goTouch.setBackgroundColor(Color.parseColor("#22000000"));
                } else {
                    goTouch.setBackgroundColor(Color.parseColor("#22ffffff"));
                }
                goImg.setAlpha(0.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                goTouch.setBackgroundColor(Color.parseColor("#00333333"));
                goImg.setAlpha(1.0f);
                if (state == 2) {
                    if (webView.canGoForward()) {
                        webView.goForward();
                    }
                }
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                goTouch.setBackgroundColor(Color.parseColor("#00333333"));
                goImg.setAlpha(1.0f);
            }
            return true;
        });
        menuTouch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (huState) {
                    menuTouch.setBackgroundColor(Color.parseColor("#22000000"));
                } else {
                    menuTouch.setBackgroundColor(Color.parseColor("#22ffffff"));
                }
                menuImg.setAlpha(0.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                menuTouch.setBackgroundColor(Color.parseColor("#00333333"));
                menuImg.setAlpha(1.0f);
                MenuDialogScreen.prepare(MenuDialogScreen.class).open(fragmentManager());
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                menuTouch.setBackgroundColor(Color.parseColor("#00333333"));
                menuImg.setAlpha(1.0f);
            }
            return true;
        });
        windowsTouch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (huState) {
                    windowsTouch.setBackgroundColor(Color.parseColor("#22000000"));
                } else {
                    windowsTouch.setBackgroundColor(Color.parseColor("#22ffffff"));
                }
                windowsImg.setAlpha(0.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                windowsTouch.setBackgroundColor(Color.parseColor("#00333333"));
                windowsImg.setAlpha(1.0f);

                if (state == 2) {
                    int index = -1;
                    for (int i = 0; i < windowBeanList.size(); i++) {
                        if (windowBeanList.get(i).name.equals(webView.getTitle())) {
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        windowBeanList.get(index).bitmap = getFullWebViewSnapshot(webView);
                        windowBeanList.get(index).url = webView.getUrl();
                        windowBeanList.get(index).name = webView.getTitle();
                    } else {
                        WindowBean windowBean = new WindowBean();
                        windowBean.bitmap = getFullWebViewSnapshot(webView);
                        windowBean.name = webView.getTitle();
                        windowBean.url = webView.getUrl();
                        windowBeanList.add(windowBean);
                    }

                    WindowDialogScreen.prepare(WindowDialogScreen.class, new WindowData(windowBeanList)).open(fragmentManager());

                }
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                windowsTouch.setBackgroundColor(Color.parseColor("#00333333"));
                windowsImg.setAlpha(1.0f);
            }
            return true;
        });
        homeTouch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (huState) {
                    homeTouch.setBackgroundColor(Color.parseColor("#22000000"));
                } else {
                    homeTouch.setBackgroundColor(Color.parseColor("#22ffffff"));
                }
                homeImg.setAlpha(0.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                homeTouch.setBackgroundColor(Color.parseColor("#00333333"));
                homeImg.setAlpha(1.0f);
                webView.clearHistory();
                webView.clearCache(true);
                isReloadData = true;
                EventBus.getDefault().post(new HomeEvent());
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                homeTouch.setBackgroundColor(Color.parseColor("#00333333"));
                homeImg.setAlpha(1.0f);
            }
            return true;
        });
        TopLin.setOnClickListener(v -> SearchDialogScreen.prepare(SearchDialogScreen.class).open(fragmentManager()));

        refreshImg.setOnClickListener(v -> webView.reload());

        webView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (lastTime == 0 || lastTime < System.currentTimeMillis() - 300) {

                if (oldScrollY < scrollY) {
                    roundAngleFrameLayout.setVisibility(View.GONE);
                } else {
                    roundAngleFrameLayout.setVisibility(View.VISIBLE);
                }
                lastTime = System.currentTimeMillis();
            }
        });

    }

    private long lastTime = 0;

    public Bitmap getFullWebViewSnapshot(WebView webView) {
        //重新调用WebView的measure方法测量实际View的大小（将测量模式设置为UNSPECIFIED模式也就是需要多大就可以获得多大的空间）
        //webView.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
        //View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //调用layout方法设置布局（使用新测量的大小）
        //webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        //开启WebView的缓存(当开启这个开关后下次调用getDrawingCache()方法的时候会把view绘制到一个bitmap上)
        webView.setDrawingCacheEnabled(true);
        //强制绘制缓存（必须在setDrawingCacheEnabled(true)之后才能调用，否者需要手动调用destroyDrawingCache()清楚缓存）
        webView.buildDrawingCache();
        //根据测量结果创建一个大小一样的bitmap
        int height = ScreenUtils.getScreenHeight(_dpActivity);
        Bitmap picture = Bitmap.createBitmap(webView.getMeasuredWidth(),
                height, Bitmap.Config.ARGB_8888);
        //已picture为背景创建一个画布
        Canvas canvas = new Canvas(picture); // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        //设置画笔的定点位置，也就是左上角
        canvas.drawBitmap(picture, 0, webView.getMeasuredHeight(), paint);
        //将webview绘制在刚才创建的画板上
        webView.draw(canvas);
        return picture;
    }


    @Override
    public void onBack() {
        if (state == 0) {
            DpDialogScreen.create().setMsg("Exit Wolf?")
                    .addButton(getContext(), "Yes", dialogScreen -> dialogScreen.close(() -> System.exit(0)))
                    .addButton(getContext(), "No", DialogScreen::close)
                    .open(fragmentManager());

        } else {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (webView != null) {
            webView.onResume();
            webView.resumeTimers();
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (webView != null) {
            webView.onPause();
            webView.pauseTimers();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (webView != null) {
            webView.destroy();
        }
    }

    private ViewAnimator viewAnimator;

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        if (webView == null) {
            return;
        }

        WebSettings settings = webView.getSettings();

        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                roundAngleFrameLayout.setVisibility(View.VISIBLE);
                TopLinText.setText(url);

                scanLoadingView.setAlpha(0.0f);
                ViewAnimator.animate(scanLoadingView).alpha(1.0f).duration(500).start();
                scanLoadingView.start();

                if (viewAnimator != null) {

                    viewAnimator.cancel();

                    viewAnimator = null;
                }
                viewAnimator = ViewAnimator.animate(refreshImg).rotation(-360f, 0f)
                        .interpolator(new LinearInterpolator()).repeatCount(-1).duration(1000).start();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                //重载url以后，清空历史，设置为初始值，如果一直清空，canGoBack()会一直返回false
                if (isReloadData) {
                    view.clearHistory();
                }
                TopLinText.setText(webView.getTitle());
                isReloadData = false;

                scanLoadingView.stop();

                ViewAnimator.animate(scanLoadingView).alpha(0.0f).duration(500).start();

                if (viewAnimator != null) {
                    viewAnimator.cancel();

                    viewAnimator = null;
                }
                refreshImg.setRotation(0f);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //用javascript隐藏系统定义的404页面信息

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {//处理https请
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }

        });

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        //设置支持js
        settings.setJavaScriptEnabled(true);
        //设置渲染效果优先级，高
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        String cacheDirPath = CommonUtil.getDiskCachePath(getContext());
        //设置数据库缓存路径
        settings.setDatabasePath(cacheDirPath);
        //设置 应用 缓存目录
        settings.setAppCachePath(cacheDirPath);
        //开启 DOM 存储功能
        settings.setDomStorageEnabled(true);
        //开启 数据库 存储功能
        settings.setDatabaseEnabled(true);
        //开启 应用缓存 功能
        settings.setAppCacheEnabled(true);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HomeEvent event) {
        backImgView.setVisibility(View.VISIBLE);
        lightTouch.setVisibility(View.VISIBLE);
        logoImg.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        TopLin.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        turnEdit.setText(getString(R.string.turn_edit_text));
        state = 0;
        if (huState) {
            bottomLin.setBackgroundColor(Color.parseColor("#11000033"));
        } else {
            bottomLin.setBackgroundColor(Color.parseColor("#66333333"));
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {

        CoreApp.appData.searchInt = event.content.searchInt;

        ViewAnimator.animate(turnEdit).alpha(0.0f).duration(200).onStop(() -> {
            turnEdit.setText(event.content.content);
            ViewAnimator.animate(turnEdit).alpha(1.0f).duration(200).onStop(() -> {
                ViewAnimator.animate(turnEdit).alpha(0.0f).duration(200).onStop(() -> {
                    turnEdit.setAlpha(1.0f);
                    relativeLayout.setVisibility(View.GONE);
                }).start();
                ViewAnimator.animate(logoImg).alpha(0.0f).duration(200).onStop(() -> {
                    logoImg.setAlpha(1.0f);
                    logoImg.setVisibility(View.GONE);
                }).start();
                ViewAnimator.animate(lightTouch).alpha(0.0f).duration(200).onStop(() -> {
                    lightTouch.setAlpha(1.0f);
                    lightTouch.setVisibility(View.GONE);
                }).start();
                ViewAnimator.animate(backImgView).alpha(0.0f).duration(200).onStop(() -> {
                    backImgView.setAlpha(1.0f);
                    backImgView.setVisibility(View.GONE);

                    if (huState) {
                        bottomLin.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    } else {
                        bottomLin.setBackgroundColor(Color.parseColor("#66333333"));
                    }
                }).start();
                TopLinText.setText(event.content.content);
                TopLin.setAlpha(0.0f);
                TopLin.setVisibility(View.VISIBLE);
                ViewAnimator.animate(TopLin).alpha(1.0f).duration(200).start();
                webView.setAlpha(0.0f);
                webView.setVisibility(View.VISIBLE);
                ViewAnimator.animate(webView).alpha(1.0f).duration(200).start();
                if (event.content.content.length() > "http://".length()) {
                    if (event.content.content.substring(0, "http://".length()).equals("http://")
                            || event.content.content.substring(0, "https://".length()).equals("https://")) {
                        webView.loadUrl(event.content.content);
                    } else {
                        switch (CoreApp.appData.searchInt) {
                            case 0:
                                webView.loadUrl("https://www.google.com/search?q=" + event.content.content);
                                break;
                            case 1:
                                webView.loadUrl("https://m.baidu.com/s?wd=" + event.content.content);
                                break;
                            case 2:
                                webView.loadUrl("https://wap.sogou.com/web/searchList.jsp?keyword=" + event.content.content);
                                break;
                            case 3:
                                webView.loadUrl("https://cn.bing.com/search?q=" + event.content.content);
                                break;
                        }
                    }
                } else {
                    switch (CoreApp.appData.searchInt) {
                        case 0:
                            webView.loadUrl("https://www.google.com/search?q=" + event.content.content);
                            break;
                        case 1:
                            webView.loadUrl("https://m.baidu.com/s?wd=" + event.content.content);
                            break;
                        case 2:
                            webView.loadUrl("https://wap.sogou.com/web/searchList.jsp?keyword=" + event.content.content);
                            break;
                        case 3:
                            webView.loadUrl("https://cn.bing.com/search?q=" + event.content.content);
                            break;
                    }
                }
                TopLinText.setText(event.content.content + " - " + webView.getUrl());
            }).start();
        }).start();


//        backImgView.setVisibility(View.GONE);
//        lightTouch.setVisibility(View.GONE);
//        logoImg.setVisibility(View.GONE);
//        relativeLayout.setVisibility(View.GONE);
//        TopLin.setVisibility(View.VISIBLE);
        state = 2;
    }
}
