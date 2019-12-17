package com.endows.app.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.endows.app.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class RoundedPagerIndicator extends LinearLayoutCompat implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {

    private ViewPager mPager;
    @ColorInt
    private int mSelectedColor;
    @ColorInt
    private int mDeselectedColor;
    @DrawableRes
    private int mSelectedDrawable;
    @DrawableRes
    private int mDeselectedDrawable;
    @Dimension
    private int mIndicatorSpacing;
    @Dimension
    private int mBarWidth = RoundedPagerDefaultValues.DEFAULT_BAR_WIDTH;
    @Dimension
    private int mBarHeight = RoundedPagerDefaultValues.DEFAULT_BAR_HEIGHT;

    private boolean mAutoScroll;

    private int mCurrentPosition;

    private Timer mTimer;

    private Handler mTimerHandler;

    public RoundedPagerIndicator(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public RoundedPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedPagerIndicator);
            this.mSelectedColor = a.getColor(R.styleable.RoundedPagerIndicator_selectedRoundColor, -1);
            this.mDeselectedColor = a.getColor(R.styleable.RoundedPagerIndicator_deselectedRoundColor, -1);
            this.mSelectedDrawable = a.getResourceId(R.styleable.RoundedPagerIndicator_selectedRoundDrawable, -1);
            this.mDeselectedDrawable = a.getResourceId(R.styleable.RoundedPagerIndicator_deselectedRoundDrawable, -1);
            this.mIndicatorSpacing = (int) a.getDimension(R.styleable.RoundedPagerIndicator_indicatorSpacing, 5.0F);
            this.mBarWidth = (int) a.getDimension(R.styleable.RoundedPagerIndicator_barWidth, RoundedPagerDefaultValues.DEFAULT_BAR_WIDTH);
            this.mBarHeight = (int) a.getDimension(R.styleable.RoundedPagerIndicator_barHeight, RoundedPagerDefaultValues.DEFAULT_BAR_HEIGHT);
            this.mAutoScroll = a.getBoolean(R.styleable.RoundedPagerIndicator_autoScroll, false);
            a.recycle();
        }

        setGravity(Gravity.CENTER);

        if (mAutoScroll) {
            mTimerHandler = new Handler();
            mTimer = new Timer();
            mTimer.schedule(mTimerTask, 8000, 8000);
        }
    }

    @ColorInt
    private int getThemeColor(@AttrRes int attributeColor) {
        TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(attributeColor, value, true);
        return value.data;
    }

    private void initializeIndicatorBar(int num) {
        this.removeAllViewsInLayout();

        for (int i = 0; i < num; ++i) {
            TextView bullet = new TextView(getContext());
            bullet.setTag(i);
            LayoutParams lp = new LayoutParams(mBarWidth, mBarHeight);
            lp.setMargins(this.mIndicatorSpacing / 2, 0, this.mIndicatorSpacing / 2, 0);
            lp.gravity = Gravity.CENTER;
            addView(bullet, lp);
        }

        this.setSelectedIndicator(this.mPager != null ? this.mPager.getCurrentItem() : 0);
    }

    private void setSelectedIndicator(int selected) {
        int num = getChildCount();

        for (int i = 0; i < num; ++i) {
            TextView bullet = (TextView) this.getChildAt(i);
            if (bullet != null) {

                if (mDeselectedDrawable != -1) {
                    bullet.setBackgroundResource(mDeselectedDrawable);
                } else if (mDeselectedColor != -1) {
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setColor(mDeselectedColor);
                    drawable.setCornerRadius(8f);
                    bullet.setBackground(drawable);
                } else {
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setColor(getThemeColor(R.attr.colorAccent));
                    drawable.setCornerRadius(8f);
                    bullet.setBackground(drawable);
                }
            }
        }

        TextView selectedView = (TextView) this.getChildAt(selected);
        if (selectedView != null) {
            if (mSelectedDrawable != -1) {
                selectedView.setBackgroundResource(mSelectedDrawable);
            } else if (mSelectedColor != -1) {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(mSelectedColor);
                drawable.setCornerRadius(8f);
                selectedView.setBackground(drawable);
            } else {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(getThemeColor(R.attr.colorPrimary));
                drawable.setCornerRadius(8f);
                selectedView.setBackground(drawable);
            }
        }
    }

    public final void setPager(ViewPager pager) {

        if (mPager != null) {
            mPager.removeOnPageChangeListener(this);
            mPager.removeOnAdapterChangeListener(this);
        }

        mPager = pager;
        mPager.addOnPageChangeListener(this);
        mPager.addOnAdapterChangeListener(this);

        PagerAdapter var6 = pager.getAdapter();
        this.initializeIndicatorBar(var6 != null ? var6.getCount() : 0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mCurrentPosition = i;
        setSelectedIndicator(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter1) {
        if (pagerAdapter1 == null) {
            return;
        }
        this.initializeIndicatorBar(pagerAdapter1.getCount());
    }

    interface RoundedPagerDefaultValues {
        int DEFAULT_BAR_WIDTH = 24;
        int DEFAULT_BAR_HEIGHT = 6;
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            mTimerHandler.post(changePage);
        }
    };

    private Runnable changePage = new Runnable() {
        @Override
        public void run() {
            if (mPager != null) {
                ++mCurrentPosition;
                int count = mPager.getAdapter().getCount() -1;
                if (mCurrentPosition > count) {
                    mCurrentPosition = 0;
                }

                mPager.setCurrentItem(mCurrentPosition);
            } else {
                mTimer.cancel();
            }
        }
    };

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if (mTimer != null) {
//            mTimer.cancel();
//        }
//
//        if (mTimerHandler != null) {
//            mTimerHandler.removeCallbacks(changePage);
//            mTimerHandler = null;
//        }
//    }
}
