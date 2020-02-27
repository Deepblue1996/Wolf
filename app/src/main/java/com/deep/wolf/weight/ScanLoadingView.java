package com.deep.wolf.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.deep.dpwork.util.DisplayUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class -
 * <p>
 * Created by Deepblue on 2019/5/8 0008.
 */

public class ScanLoadingView extends View {

    private float speed;
    private float speedBar;
    private long speedTimer;

    private Paint scanPaint;
    private RectF bRectF;

    private float WIDTH_BAR;
    private float HEIGHT_BAR;
    private int beginColor, endColor;

    private float widthBar;
    private float widthBarOne;
    private float LongWidthBar;

    private float x;
    private float y;

    private LinearGradient linearGradientRight;
    private LinearGradient linearGradientLeft;

    private Context context;

    private boolean isRight = true;

    private Timer timer;

    public ScanLoadingView(Context context) {
        super(context);
        init(context);
    }

    public ScanLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScanLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        widthBar = (float) DisplayUtil.dip2px(context, 40);

        beginColor = 0x005599ff;
        endColor = 0xff5599ff;

        linearGradientRight = new LinearGradient(0, 0, widthBar, 0, beginColor, endColor, Shader.TileMode.CLAMP);
        linearGradientLeft = new LinearGradient(0, 0, widthBar, 0, endColor, beginColor, Shader.TileMode.CLAMP);

        scanPaint = new Paint();
        scanPaint.setAntiAlias(true);
        scanPaint.setDither(true);
        scanPaint.setStyle(Paint.Style.FILL);
        scanPaint.setStrokeCap(Paint.Cap.ROUND);
        scanPaint.setColor(Color.parseColor("#5599ff"));

        x = 0;
        y = 0;

        bRectF = new RectF(x, y, x + widthBar, y + HEIGHT_BAR);

        LongWidthBar = WIDTH_BAR / 3;
        widthBarOne = WIDTH_BAR / 3 * 2 / 100;

        speed = 6;
        speedBar = 3;
        speedTimer = 8;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRight) {
            scanPaint.setShader(linearGradientRight);
        } else {
            scanPaint.setShader(linearGradientLeft);
        }
        canvas.drawRect(bRectF, scanPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);

        init(context);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);


        switch (specMode) {
            case MeasureSpec.AT_MOST:

                WIDTH_BAR = DisplayUtil.dip2px(context, 200);

                defaultWidth = (int) (WIDTH_BAR + getPaddingLeft() + getPaddingRight());

                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;

                WIDTH_BAR = defaultWidth;

                defaultWidth = (int) (WIDTH_BAR + getPaddingLeft() + getPaddingRight());

                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                //defaultHeight = (int) (TOUCH_HEIGHT_BAR + getPaddingTop() + getPaddingBottom());

                HEIGHT_BAR = DisplayUtil.dip2px(context, 50);

                defaultHeight = (int) (HEIGHT_BAR + getPaddingTop() + getPaddingBottom());

                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;

                HEIGHT_BAR = defaultHeight;

                defaultHeight = (int) (HEIGHT_BAR + getPaddingTop() + getPaddingBottom());

                break;
            case MeasureSpec.UNSPECIFIED:
                //defaultHeight = Math.max(defaultHeight, specSize);

                defaultHeight = Math.max(defaultHeight, specSize);

                break;
        }
        return defaultHeight;
    }

    public void start() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isRight) {
                    if (x < WIDTH_BAR - LongWidthBar + speed) {
                        x += speed;
                        if (widthBar < LongWidthBar) {
                            widthBar += widthBarOne;
                            x -= widthBarOne;
                        }
                    } else {
                        if (widthBar > 0) {
                            widthBar -= speedBar;
                            x += speedBar;
                        } else {
                            isRight = false;
                            widthBar = 0;
                            x = WIDTH_BAR;
                        }
                    }
                } else {
                    if (x > -speed) {
                        x -= speed;
                        if (widthBar < LongWidthBar) {
                            widthBar += widthBarOne;
                        }
                    } else {
                        if (widthBar > 0) {
                            widthBar -= speedBar;
                        } else {
                            isRight = true;
                            widthBar = 0;
                        }
                    }
                }

                linearGradientRight = new LinearGradient(x, 0, x + widthBar, 0, beginColor, endColor, Shader.TileMode.CLAMP);
                linearGradientLeft = new LinearGradient(x, 0, x + widthBar, 0, endColor, beginColor, Shader.TileMode.CLAMP);
                bRectF = new RectF(x, y, x + widthBar, y + HEIGHT_BAR);

                ScanLoadingView.this.post(() -> invalidate());
            }
        }, 0, speedTimer);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
