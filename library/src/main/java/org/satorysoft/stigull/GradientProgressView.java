/**
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.satorysoft.stigull;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author viacheslavokolitiy on 03.10.2015.
 */
public class GradientProgressView extends View {
    private Paint mNormalPaint;
    private Paint mGradientPaint;
    private final RectF rectF = new RectF();
    private float mStrokeWidth;
    private int mProgress = 0;
    private int mMax;
    private int mUnfinishedColor;
    private int mFinishedColor;
    private float mAngle;
    private final int mDefaultFinishedColor = ContextCompat
                                                .getColor(getContext(), R.color.stigull_grey_50);
    private final int mDefaultUnfinishedColor = Color.rgb(72, 106, 176);
    private final float mDefaultStrokeWidth;
    private final int mDefaultMaxProgress = 100;
    private final float mDefaultAngle = 360;
    private final int mMinimumSize;
    private SweepGradient mSweepGradient;
    private int mStartColor;
    private int mCenterColor;
    private int mEndColor;
    private final int defaultStartColor = R.color.stigull_white;
    private final int defaultCenterColor = R.color.stigull_light_blue;
    private final int defaultEndColor = R.color.stigull_light_blue;

    public GradientProgressView(Context context) {
        this(context, null);

    }

    public GradientProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientProgressView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.mDefaultStrokeWidth = DimensionUtil.dp2px(getResources(), Constants.STROKE_WIDTH);
        this.mMinimumSize = (int) DimensionUtil.dp2px(getResources(), Constants.MINIMUM_SIZE_DP);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.GradientProgressView, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    public int getStartColor() {
        return mStartColor;
    }

    public void setStartColor(int mStartColor) {
        this.mStartColor = mStartColor;
    }

    public int getCenterColor() {
        return mCenterColor;
    }

    public void setCenterColor(int mCenterColor) {
        this.mCenterColor = mCenterColor;
    }

    public int getEndColor() {
        return mEndColor;
    }

    public void setEndColor(int mEndColor) {
        this.mEndColor = mEndColor;
    }

    private void initPainters() {
        mNormalPaint = new Paint();
        mNormalPaint.setColor(mDefaultUnfinishedColor);
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setStrokeWidth(mStrokeWidth);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setStrokeCap(Paint.Cap.ROUND);

        mSweepGradient = new SweepGradient(0, 0, new int[]{
                mStartColor,
                mCenterColor,
                mEndColor
        }, new float[]{0, 1, 2});

        mGradientPaint = new Paint();
        mGradientPaint.setColor(mDefaultUnfinishedColor);
        mGradientPaint.setAntiAlias(true);
        mGradientPaint.setStrokeWidth(mStrokeWidth);
        mGradientPaint.setStyle(Paint.Style.STROKE);
        mGradientPaint.setStrokeCap(Paint.Cap.ROUND);

        mGradientPaint.setShader(mSweepGradient);
    }

    private void initByAttributes(TypedArray attributes) {
        mFinishedColor = attributes.getColor(R.styleable.GradientProgressView_arc_finished_color,
                mDefaultFinishedColor);
        mUnfinishedColor = attributes.getColor(R.styleable.GradientProgressView_arc_unfinished_color,
                mDefaultUnfinishedColor);
        mStartColor = attributes.getColor(R.styleable.GradientProgressView_start_color,
                ContextCompat.getColor(getContext(), defaultStartColor));
        mCenterColor = attributes.getColor(R.styleable.GradientProgressView_center_color,
                ContextCompat.getColor(getContext(),defaultCenterColor));
        mEndColor = attributes.getColor(R.styleable.GradientProgressView_end_color,
                ContextCompat.getColor(getContext(), defaultEndColor));
        mAngle = attributes.getFloat(R.styleable.GradientProgressView_arc_angle, mDefaultAngle);
        setMax(attributes.getInt(R.styleable.GradientProgressView_arc_max, mDefaultMaxProgress));
        setProgress(attributes.getInt(R.styleable.GradientProgressView_arc_progress, 0));
        mStrokeWidth = attributes.getDimension(R.styleable.GradientProgressView_arc_stroke_width, mDefaultStrokeWidth);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        this.invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        if (this.mProgress > getMax()) {
            this.mProgress %= getMax();
        }
        invalidate();
    }

    private void setMax(int value) {
        if (value > 0) {
            this.mMax = value;
            invalidate();
        }
    }

    public int getMax() {
        return mMax;
    }

    public int getFinishedColor() {
        return mFinishedColor;
    }

    public int getUnfinishedColor() {
        return mUnfinishedColor;
    }

    public void setFinishedColor(int mFinishedColor) {
        this.mFinishedColor = mFinishedColor;
        this.invalidate();
    }

    public void setUnfinishedColor(int mUnfinishedColor) {
        this.mUnfinishedColor = mUnfinishedColor;
        this.invalidate();
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float mAngle) {
        this.mAngle = mAngle;
        this.invalidate();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return mMinimumSize;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return mMinimumSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        rectF.set(mStrokeWidth / 2f, mStrokeWidth / 2f,
                width - mStrokeWidth / 2f, MeasureSpec.getSize(heightMeasureSpec) - mStrokeWidth / 2f);
        float radius = width / 2f;
        float angle = (360 - mAngle) / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = -90;
        float finishedSweepAngle = mProgress / (float) getMax() * mAngle;
        float finishedStartAngle = startAngle;
        mNormalPaint.setColor(mUnfinishedColor);
        canvas.drawArc(rectF, startAngle, mAngle, false, mNormalPaint);

        mGradientPaint.setColor(mFinishedColor);
        canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, mGradientPaint);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(Constants.INSTANCE_STROKE_WIDTH, getStrokeWidth());
        bundle.putInt(Constants.INSTANCE_PROGRESS, getProgress());
        bundle.putInt(Constants.INSTANCE_MAX, getMax());
        bundle.putInt(Constants.INSTANCE_FINISHED_STROKE_COLOR, getFinishedColor());
        bundle.putInt(Constants.INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedColor());
        bundle.putFloat(Constants.INSTANCE_ARC_ANGLE, getAngle());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            final Bundle bundle = (Bundle) state;
            mStrokeWidth = bundle.getFloat(Constants.INSTANCE_STROKE_WIDTH);
            setMax(bundle.getInt(Constants.INSTANCE_MAX));
            setProgress(bundle.getInt(Constants.INSTANCE_PROGRESS));
            mFinishedColor = bundle.getInt(Constants.INSTANCE_FINISHED_STROKE_COLOR);
            mUnfinishedColor = bundle.getInt(Constants.INSTANCE_UNFINISHED_STROKE_COLOR);
            initPainters();
            super.onRestoreInstanceState(bundle.getParcelable(Constants.INSTANCE_STATE));
            return;
        }

        super.onRestoreInstanceState(state);
    }

    private static class DimensionUtil {
        public static float dp2px(Resources resources, float dp) {
            final float scale = resources.getDisplayMetrics().density;
            return  dp * scale + 0.5f;
        }

        public static float sp2px(Resources resources, float sp){
            final float scale = resources.getDisplayMetrics().scaledDensity;
            return sp * scale;
        }
    }

    private interface Constants {
        String INSTANCE_STATE = "saved_instance";
        String INSTANCE_STROKE_WIDTH = "stroke_width";
        String INSTANCE_PROGRESS = "progress";
        String INSTANCE_MAX = "max";
        String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
        String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
        String INSTANCE_ARC_ANGLE = "arc_angle";
        float STROKE_WIDTH = 4;
        float MINIMUM_SIZE_DP = 100;
    }
}
