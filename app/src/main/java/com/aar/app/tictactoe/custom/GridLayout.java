package com.aar.app.tictactoe.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.aar.app.tictactoe.R;


public class GridLayout extends ViewGroup {

    private int mColCount;
    private int mRowCount;
    private int mGridWidth;
    private int mGridHeight;
    private OnCellTouchListener mTouchListener;

    public GridLayout(Context context) {
        super(context);
        init(context, null);
    }

    public GridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setOnCellTouchListener(OnCellTouchListener listener) {
        mTouchListener = listener;
    }

    public OnCellTouchListener getOnCellTouchListener() {
        return mTouchListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateGridDimension();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.isValidPosition()) {
                    int top = getPaddingTop() + (lp.row * mGridHeight) + lp.topMargin;
                    int left = getPaddingLeft() + (lp.col * mGridWidth) + lp.leftMargin;
                    int right = left + mGridWidth - lp.leftMargin - lp.rightMargin;
                    int bottom = top + mGridHeight - lp.topMargin - lp.bottomMargin;
                    child.layout(left, top, right, bottom);
                }
            }
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mTouchListener != null) {
                int row = getRowIndex((int) event.getY());
                int col = getColIndex((int) event.getX());
                View child = findViewByPosition(row, col);
                mTouchListener.onTouch(child, row, col);
                handled = true;
            }
        }

        return handled;
    }

    private View findViewByPosition(int row, int col) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.row == row && lp.col == col)
                return child;
        }

        return null;
    }

    private void updateGridDimension() {
        mGridWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / mColCount;
        mGridHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mRowCount;
    }

    private void init(Context context, AttributeSet attrs) {
        mColCount = 3;
        mRowCount = 3;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridLayout, 0, 0);
            mColCount = a.getInteger(R.styleable.GridLayout_columnCount, mColCount);
            mRowCount = a.getInteger(R.styleable.GridLayout_rowCount, mRowCount);
            a.recycle();
        }
    }

    public int getRowIndex(int y) {
        return Math.max( Math.min((y - getPaddingTop()) / mGridHeight, mRowCount - 1), 0 );
    }

    public int getColIndex(int x) {
        return Math.max( Math.min((x - getPaddingLeft()) / mGridWidth, mColCount - 1), 0 );
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public int row = -1;
        public int col = -1;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.GridLayout);
            row = a.getInteger(R.styleable.GridLayout_row, -1);
            col = a.getInteger(R.styleable.GridLayout_col, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height, int row, int col) {
            super(width, height);
            this.row = row;
            this.col = col;
        }

        public boolean isValidPosition() {
            return row >= 0 && col >= 0;
        }
    }

    public interface OnCellTouchListener {
        void onTouch(View view, int row, int col);
    }
}
