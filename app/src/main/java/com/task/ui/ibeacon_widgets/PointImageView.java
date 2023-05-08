package com.task.ui.ibeacon_widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.List;

public class PointImageView extends AppCompatImageView {

    public PointImageView(Context context) {
        super(context);
    }

    public PointImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PointImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private List<Point> mPoints = new ArrayList<>();

    private Paint mPointPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPointPaint.setColor(Color.RED);

        if (mPoints.size() >0) {
            canvas.drawCircle(mPoints.get(0).getX(), mPoints.get(0).getY(), mPoints.get(0).getRadius(), mPointPaint);

            mPointPaint.setColor(Color.BLUE);
            for (int i = 1; i < mPoints.size(); i++) {
                canvas.drawCircle(mPoints.get(i).getX(), mPoints.get(i).getY(), 20, mPointPaint);
            }
        }
    }

    public void drawPoint(List<Point> points ) {
        mPoints.clear();
        mPoints.addAll(points);
        invalidate();
    }

    public void drawPoint(Point point ) {
        mPoints.clear();
        mPoints.add(point);
        invalidate();
    }
}
