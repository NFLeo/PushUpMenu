
package com.peter.pushupmenu.view.algorithm;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * 直线的定义 y=kx+b
 * 
 * @author jiangbin
 */
public class Line {
    private float k;
    private float b;
    public PointF mStartPointF;
    public PointF mEndPointF;

    public Line(PointF startPointF, PointF endPointF) {
        k = (startPointF.y - endPointF.y) / (startPointF.x - endPointF.x);
        b = startPointF.y - k * startPointF.x;
        this.mStartPointF = startPointF;
        this.mEndPointF = endPointF;
    }

    public float getY(float x){
        return  k*x+b;
    }

    public float getX(float y){
        return  (y-b)/k;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawLine(mStartPointF.x,mStartPointF.y,mEndPointF.x,mEndPointF.y,paint);
    }

}
