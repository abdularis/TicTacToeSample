package com.aar.app.tictactoe.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.aar.app.tictactoe.R;

/**
 * Created by abdularis on 22/06/17.
 *
 * Digunakan untuk merender grid line
 *  ____________
 * |   |   |   |
 * |---|---|---|
 * |___|___|___|
 * |   |   |   |
 */

public class GridLine extends View {

    private int mLineWidth;
    private int mColCount;
    private int mRowCount;
    private Paint mPaint;
    private int mGridWidth;
    private int mGridHeight;

    public GridLine(Context context) {
        super(context);
        init(context, null);
    }

    public GridLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public int getColCount() {
        return mColCount;
    }

    public int getRowCount() {
        return mRowCount;
    }

    public int getLineWidth() {
        return mLineWidth;
    }

    public int getLineColor() {
        return mPaint.getColor();
    }

    public void setLineWidth(int lineWidth) {
        mLineWidth = lineWidth;
        invalidate();
    }

    public void setLineColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void setColCount(int colCount) {
        mColCount = colCount;
        invalidate();
        requestLayout();
    }

    public void setRowCount(int rowCount) {
        mRowCount = rowCount;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateGridDimension();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int pLeft = getPaddingLeft();
        int pTop = getPaddingTop();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();

        int viewWidth = getWidth() - pLeft - pRight;
        int viewHeight = getHeight() - pTop - pBottom;

        float y = pTop + mGridHeight;
        // horizontal lines
        for (int i = 0; i < getRowCount(); i++) {
            canvas.drawRect(pLeft, y, viewWidth + mLineWidth - pRight, y + mLineWidth, mPaint);

            y += mGridHeight;
        }

        float x = pLeft + mGridWidth;
        // vertical lines
        for (int i = 0; i < getColCount(); i++) {
            canvas.drawRect(x, pTop, x + mLineWidth, viewHeight + mLineWidth - pBottom, mPaint);

            x += mGridWidth;
        }
    }

    private void updateGridDimension() {
        mGridWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / mColCount;
        mGridHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mRowCount;
    }

    private void init(Context context, AttributeSet attrs) {
        mLineWidth = 2;
        mColCount = 3;
        mRowCount = 3;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridLine, 0, 0);

            mLineWidth = a.getDimensionPixelSize(R.styleable.GridLine_lineWidth, mLineWidth);
            mPaint.setColor(a.getColor(R.styleable.GridLine_lineColor, Color.GRAY));
            mColCount = a.getInteger(R.styleable.GridLine_columnCount, mColCount);
            mRowCount = a.getInteger(R.styleable.GridLine_rowCount, mRowCount);

            a.recycle();
        }
    }
}
