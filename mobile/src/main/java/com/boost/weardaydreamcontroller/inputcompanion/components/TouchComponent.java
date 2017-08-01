package com.boost.weardaydreamcontroller.inputcompanion.components;

import android.content.Context;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.boost.weardaydreamcontroller.inputcompanion.ClientConnection;
import com.boost.weardaydreamcontroller.inputcompanion.ProtoUtils;
import com.boost.weardaydreamcontroller.inputcompanion.TouchpadView;
import com.google.protobuf.nano.MessageNano;

import javax.annotation.Nullable;

public class TouchComponent implements InputComponent {
    private static final String TAG = TouchComponent.class.getSimpleName();
    private static final int VIBRATE_DURATION_MS = 16;
    private final View.OnHoverListener hoverListener;
    //private float lastTouchX;
    //private float lastTouchY;
    private final TouchpadView.TouchpadListener touchpadListener;
    private final TouchpadView view;

    public TouchComponent(final ClientConnection clientConnection, final Context context, final TouchpadView view, @Nullable final Vibrator vibrator) {
        this.view = view;
        final Point point = new Point(view.getWidth(), view.getHeight());
        this.touchpadListener = new TouchpadView.TouchpadListener() {
            @Override
            public void onClickButtonStateChanged(final boolean down) {
                clientConnection.sendMessage(ProtoUtils.keyToProto(KeyEvent.KEYCODE_ENTER, down));
            }

            @Override
            public void onTouchEvent(final MotionEvent motionEvent) {
                final int actionMasked = motionEvent.getActionMasked();
                //TouchComponent.this.lastTouchX = motionEvent.getX();
                //TouchComponent.this.lastTouchY = motionEvent.getY();
                if (vibrator != null && (actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_POINTER_DOWN)) {
                    vibrator.vibrate(VIBRATE_DURATION_MS);
                }
//                Log.e(TAG, "onTouchEvent: " + motionEvent.getX() + " " + motionEvent.getY());
//                MessageNano messageNano = ProtoUtils.motionToProto(motionEvent, point);
//                Log.e(TAG, "onTouchEvent: " + messageNano.hashCode());
//                clientConnection.sendMessage(messageNano);
                clientConnection.sendMessage(ProtoUtils.motionToProto(motionEvent, point));
            }
        };
        this.hoverListener = new View.OnHoverListener() {
            public boolean onHover(final View view, final MotionEvent motionEvent) {
                clientConnection.sendMessage(ProtoUtils.motionToProto(motionEvent, point));
                return true;
            }
        };
    }

    @Override
    public void start() {
        this.view.setTouchpadListener(this.touchpadListener);
        this.view.setOnHoverListener(this.hoverListener);
    }

    @Override
    public void stop() {
        this.view.setTouchpadListener(null);
        this.view.setOnHoverListener(null);
    }
}

