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
    public Path mPath;
    LinkedList<Paint> paintContainer;
    public LinkedList<Path> pathsContainer;

    public int pathColour;
    public int pathWidth;

    float radius;
    float x;
    float y;

    public int mode = 0;

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
                pathsContainer.getFirst().addCircle(x, y, radius, Path.Direction.CW);
                super.onDraw(canvas);
            }

        }

    }

    public void clearCanvas(){
        pathsContainer.clear();
        paintContainer.clear();
        invalidate();
    }

    public void undoCanvas(){
        if (pathsContainer.size() > 0){
            pathsContainer.removeFirst();
            paintContainer.removeFirst();
            invalidate();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int touchCount = event.getPointerCount();
        float pressure = event.getPressure();
        // System.out.println(pressure);
        int index = event.getActionIndex();
        int id = event.getPointerId(index);

        if(mode == 0) {
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:

                case MotionEvent.ACTION_POINTER_DOWN:
                    mPath = new Path();
                    mPaint = new Paint();

                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);
                    mPaint.setStrokeWidth(pressure * pathWidth);

                    mPath.moveTo(event.getX(index), event.getY(index));
                    pathsContainer.add(id, mPath);
                    paintContainer.add(id, mPaint);

                    break;

                case MotionEvent.ACTION_MOVE:
                    for(int i = 0; i < event.getPointerCount(); i++){
                        id = event.getPointerId(i);
                        mPath = pathsContainer.get(id);
                        mPath.lineTo(event.getX(i), event.getY(i));
                    }

                    invalidate();
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mPath = pathsContainer.get(id);
                    break;

            }
        }

        else if (mode == 1){
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    mPath = new Path();
                    mPaint = new Paint();

                    mPaint.setColor(Color.parseColor("#FFFFFF"));
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

                    break;

            }
        }

        else if (mode == 2){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:

                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);
                    mPaint.setStrokeWidth(pressure * pathWidth);

                    pathsContainer.addFirst(mPath);
                    paintContainer.addFirst(mPaint);

                    pathsContainer.getFirst().moveTo(event.getX(), event.getY());

                    break;

                case MotionEvent.ACTION_MOVE:

                    break;

                case MotionEvent.ACTION_UP:

                    mPath = new Path();
                    mPaint = new Paint();

                    pathsContainer.getLast().lineTo(event.getX(), event.getY());

                    invalidate();

                    break;

            }
        }


//        if (touchCount == 2){
//            switch (event.getAction()){
//                case MotionEvent.ACTION_DOWN:
//                    mPaint = new Paint();
//                    mPath = new Path();
//
//                    mPaint.setColor(pathColour);
//                    mPaint.setStyle(Paint.Style.STROKE);
//                    mPaint.setStrokeJoin(Paint.Join.ROUND);
//                    mPaint.setStrokeCap(Paint.Cap.ROUND);
//                    mPaint.setStrokeWidth(pressure * pathWidth);
//
//                    x = event.getX(0);
//                    y = event.getY(0);
//                    radius = (float) Math.hypot(event.getX(0) - event.getX(1), event.getY(0) - event.getY(1));
//
//                    //pathsContainer.addFirst(mPath);
//                   // paintContainer.addFirst(mPaint);
//
//                    //pathsContainer.getFirst().addCircle(x, y, radius, Path.Direction.CW);
//                    invalidate();
//
//                    break;
//
//                case MotionEvent.ACTION_MOVE:
//                    //pathsContainer.getLast().reset();
//
//                    x = event.getX(0);
//                    y = event.getY(0);
//                    radius = (float) Math.hypot(event.getX(0) - event.getX(1), event.getY(0) - event.getY(1));
//
//
//                    break;
//
//                case MotionEvent.ACTION_UP:
//
//                    break;
//
//
//            }
        //}

        return true;
    }


}
