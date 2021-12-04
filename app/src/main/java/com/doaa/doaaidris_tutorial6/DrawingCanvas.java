package com.doaa.doaaidris_tutorial6;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import java.util.LinkedList;

public class DrawingCanvas extends View {

    private Paint mPaint;
    private Path mPath;
    LinkedList<Paint> paintContainer;
    LinkedList<Path> pathsContainer;

    public int pathColour;

    public int pathWidth;

    public DrawingCanvas(Context context, AttributeSet attrs){
        super(context, attrs);

        paintContainer = new LinkedList<Paint>();
        pathsContainer = new LinkedList<Path>();

        mPaint = new Paint();
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(pathsContainer.size() > 0){
            for(int i = 0; i < pathsContainer.size(); i++) {
                canvas.drawPath(pathsContainer.get(i), paintContainer.get(i));
                super.onDraw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int touchCount = event.getPointerCount();

        float pressure = event.getPressure();
       // System.out.println(pressure);

        if(touchCount == 1){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);

                    mPaint.setStrokeWidth(pressure * pathWidth);

                    pathsContainer.addLast(mPath);
                    paintContainer.addLast(mPaint);

                    pathsContainer.getLast().moveTo(event.getX(), event.getY());

                    break;

                case MotionEvent.ACTION_MOVE:
                    pathsContainer.getLast().lineTo(event.getX(), event.getY());
                    invalidate();
                    break;

                case MotionEvent.ACTION_UP:
                    mPaint = new Paint();
                    mPath = new Path();

                    break;


            }
        }
        else if (touchCount == 2){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);

                    mPaint.setStrokeWidth(pressure * pathWidth);

                    pathsContainer.addLast(mPath);
                    paintContainer.addLast(mPaint);

                    pathsContainer.getLast().moveTo(event.getX(0), event.getY(0));
                    pathsContainer.getLast().lineTo(event.getX(1), event.getY(1));

                    break;

                case MotionEvent.ACTION_MOVE:
                    pathsContainer.getLast().reset();
                    pathsContainer.getLast().moveTo(event.getX(0), event.getY(0));
                    pathsContainer.getLast().lineTo(event.getX(1), event.getY(1));
                    invalidate();
                    break;

            }
        }
        else if (touchCount == 3){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);

                    mPaint.setStrokeWidth(pressure * pathWidth);

                    pathsContainer.addLast(mPath);
                    paintContainer.addLast(mPaint);

                    pathsContainer.getLast().moveTo(event.getX(0), event.getY(0));
                    pathsContainer.getLast().lineTo(event.getX(1), event.getY(1));
                    pathsContainer.getLast().lineTo(event.getX(2), event.getY(2));
                    pathsContainer.getLast().lineTo(event.getX(0), event.getY(0));

                    break;

                case MotionEvent.ACTION_MOVE:
                    pathsContainer.getLast().reset();
                    pathsContainer.getLast().moveTo(event.getX(0), event.getY(0));
                    pathsContainer.getLast().lineTo(event.getX(1), event.getY(1));
                    pathsContainer.getLast().lineTo(event.getX(2), event.getY(2));
                    pathsContainer.getLast().lineTo(event.getX(0), event.getY(0));
                    invalidate();
                    break;

            }
        }



        return true;
    }


}
