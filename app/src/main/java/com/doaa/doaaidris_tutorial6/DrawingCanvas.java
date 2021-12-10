package com.doaa.doaaidris_tutorial6;

import android.graphics.RectF;
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

    //variables used
    private Paint mPaint;
    public Path mPath;
    LinkedList<Paint> paintContainer;
    public LinkedList<Path> pathsContainer;

    public int pathColour;
    public int pathWidth;

    //for creating a circle
    float radius;
    float x;
    float y;

    //to switch between different drawing states
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
                super.onDraw(canvas);
            }

        }

    }

    //method to clear canvas (path arraylist items)
    public void clearCanvas(){
        pathsContainer.clear();
        paintContainer.clear();
        invalidate();
    }

    //method to remove last added item to arraylist (undo)
    public void undoCanvas(){
        if (pathsContainer.size() > 0){
            pathsContainer.removeLast();
            paintContainer.removeLast();
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

        //checks which mode user is on and executes touch events accordingly

        //draw mode
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

                    //System.out.println(pathsContainer.size() - id);
                   //System.out.println(id);

                    pathsContainer.add(pathsContainer.size() - id, mPath);
                    paintContainer.addLast(mPaint);

                    mPath.moveTo(event.getX(index), event.getY(index));

                    break;

                case MotionEvent.ACTION_MOVE:
                    for(int i = 0; i < event.getPointerCount(); i++){
                        id = event.getPointerId(i);
                        mPath = pathsContainer.get((pathsContainer.size()-1) - id);
                        mPath.lineTo(event.getX(i), event.getY(i));
                    }

                    invalidate();
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    break;

            }
        }

        //eraser mode
        if (mode == 1){
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

        //line drawing mode
        else if (mode == 2) {
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
                    pathsContainer.getLast().lineTo(event.getX(), event.getY());
                    invalidate();

                    break;

                case MotionEvent.ACTION_MOVE:

                    break;

                case MotionEvent.ACTION_UP:
                    mPath = new Path();
                    mPaint = new Paint();

                    break;

            }
        }

        //circle mode
        else if (mode == 3) {

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


                        break;

                    case MotionEvent.ACTION_UP:
                        mPaint = new Paint();
                        mPath = new Path();

                        break;

                }
            }

            //if 2 touch points are detected, one finger will determine the center of the circle, and the other will determine its radius
            else if (touchCount == 2){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        x = event.getX(0);
                        y = event.getY(0);
                        radius = (float) Math.sqrt(Math.pow(event.getX(0) - event.getX(1), 2) + Math.pow(event.getY(0)- event.getY(1), 2));

                        pathsContainer.addLast(mPath);
                        paintContainer.addLast(mPaint);

                        pathsContainer.getLast().moveTo(x, y);
                        pathsContainer.getLast().addCircle(x, y, radius, Path.Direction.CW);

                        break;

                    case MotionEvent.ACTION_MOVE:
                        pathsContainer.getLast().reset();

                        x = event.getX(0);
                        y = event.getY(0);
                        radius = (float) Math.sqrt(Math.pow(event.getX(0) - event.getX(1), 2) + Math.pow(event.getY(0)- event.getY(1), 2));

                        pathsContainer.getLast().moveTo(x, y);
                        pathsContainer.getLast().addCircle(x, y, radius, Path.Direction.CW);
                        invalidate();
                        break;


                }
            }
        }

        //rectangle
        else if (mode == 4) {

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

                        break;

                    case MotionEvent.ACTION_UP:
                        mPaint = new Paint();
                        mPath = new Path();

                        break;

                }
            }

            //if 2 touch points are detected, fingers will determine the the top left corner, and bottom right corners of the radius
            //make sure the first touch point is to the left of & top of the second touch point
            else if (touchCount == 2){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        pathsContainer.addLast(mPath);
                        paintContainer.addLast(mPaint);

                        pathsContainer.getLast().moveTo(x, y);
                        pathsContainer.getLast().addRect(event.getX(0), event.getY(0), event.getX(1), event.getY(1), Path.Direction.CW);

                        break;

                    case MotionEvent.ACTION_MOVE:
                        pathsContainer.getLast().reset();

                        pathsContainer.getLast().moveTo(x, y);
                        pathsContainer.getLast().addRect(event.getX(0), event.getY(0), event.getX(1), event.getY(1), Path.Direction.CW);

                        invalidate();
                        break;


                }
            }
        }

        return true;
    }


}
