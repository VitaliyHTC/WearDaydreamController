package com.boost.weardaydreamcontroller.inputcompanion;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.util.AttributeSet;
import android.content.Context;
import android.view.GestureDetector;
import android.graphics.Paint;
import android.view.View;

import com.boost.weardaydreamcontroller.R;

public class TouchpadView extends View {
    private static final long TOUCH_CIRCLE_DURATION_MILLIS = 300L;
    private static final float TOUCH_CIRCLE_RADIUS_FRACTION = 0.2f;
    private boolean clickButtonDown;
    private Paint clickCirclePaint;
    private GestureDetector gestureDetector;
    private long lastTouchTimeMillis;
    private int lastTouchX;
    private int lastTouchY;
    private Paint touchCirclePaint;
    private boolean touching;
    private TouchpadListener touchpadListener;

    public TouchpadView(final Context context, final AttributeSet set) {
        super(context, set);
        this.lastTouchX = -1;
        this.lastTouchY = -1;
        (this.gestureDetector = new GestureDetector(
                this.getContext(),
                new GestureDetector.SimpleOnGestureListener()
        )).setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onDoubleTap(final MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(final MotionEvent motionEvent) {
                final int actionMasked = motionEvent.getActionMasked();
                if (!TouchpadView.this.clickButtonDown && actionMasked == 0) {
                    TouchpadView.this.clickButtonDown = true;
                    if (TouchpadView.this.touchpadListener != null) {
                        TouchpadView.this.touchpadListener.onClickButtonStateChanged(true);
                    }
                } else {
                    if (!TouchpadView.this.clickButtonDown || actionMasked != 1) {
                        return false;
                    }
                    TouchpadView.this.clickButtonDown = false;
                    if (TouchpadView.this.touchpadListener != null) {
                        TouchpadView.this.touchpadListener.onClickButtonStateChanged(false);
                        return true;
                    }
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(final MotionEvent motionEvent) {
                return false;
            }
        });
    }

    protected void onDraw(final Canvas canvas) {
        if (this.touchCirclePaint == null) {
            (this.touchCirclePaint = new Paint()).setColor(this.getContext().getResources().getColor(R.color.colorAccent));
        }
        if (this.clickCirclePaint == null) {
            (this.clickCirclePaint = new Paint()).setColor(this.getContext().getResources().getColor(R.color.click_circle_color));
        }
        final long n = System.currentTimeMillis() - this.lastTouchTimeMillis;
        Paint paint;
        if (this.clickButtonDown) {
            paint = this.clickCirclePaint;
        } else {
            paint = this.touchCirclePaint;
        }
        final float touchCircleRadius = TOUCH_CIRCLE_RADIUS_FRACTION * canvas.getWidth();
        if (this.lastTouchX > 0 && this.lastTouchY > 0 && n < 300.0f) {
            final float centerX = canvas.getWidth() / 2;
            final float centerY = canvas.getHeight() / 2;
            final float centerToSideShift = centerX - touchCircleRadius;
            final float xDeltaToCenter = this.lastTouchX - centerX;
            final float yDeltaToCenter = this.lastTouchY - centerY;
            final float distanceToTouchViewCenter = (float) Math.sqrt(xDeltaToCenter * xDeltaToCenter + yDeltaToCenter * yDeltaToCenter);
            float xResult;
            float yResult;
            if (distanceToTouchViewCenter <= centerToSideShift) {
                xResult = this.lastTouchX;
                yResult = this.lastTouchY;
            } else {
                xResult = centerX + xDeltaToCenter / distanceToTouchViewCenter * centerToSideShift;
                yResult = centerY + yDeltaToCenter / distanceToTouchViewCenter * centerToSideShift;
            }
            float radiusMultiplier;
            if (this.touching || this.clickButtonDown) {
                radiusMultiplier = 1.0f;
            } else {
                radiusMultiplier = 1.0f - n / 300.0f;
            }
            canvas.drawCircle(xResult, yResult, touchCircleRadius * radiusMultiplier, paint);
            this.invalidate();
        }
    }

    public boolean onTouchEvent(final MotionEvent motionEvent) {
        this.gestureDetector.onTouchEvent(motionEvent);
        final int action = motionEvent.getAction();
        this.touching = (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE);
        this.lastTouchX = (int) motionEvent.getX();
        this.lastTouchY = (int) motionEvent.getY();
        this.lastTouchTimeMillis = System.currentTimeMillis();
        this.invalidate();
        if (this.touchpadListener != null) {
            this.touchpadListener.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void setTouchpadListener(final TouchpadListener touchpadListener) {
        this.touchpadListener = touchpadListener;
    }

    public interface TouchpadListener {
        void onClickButtonStateChanged(final boolean p0);
        void onTouchEvent(final MotionEvent p0);
    }
}
