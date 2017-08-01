package com.boost.weardaydreamcontroller.inputcompanion.components;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.boost.weardaydreamcontroller.inputcompanion.ClientConnection;
import com.boost.weardaydreamcontroller.inputcompanion.KeyEventSource;
import com.boost.weardaydreamcontroller.inputcompanion.ProtoUtils;

public class ButtonsComponent implements InputComponent {
    private final View appButton;
    private final View.OnTouchListener appButtonTouchListener;
    private final View homeButton;
    private final View.OnTouchListener homeButtonTouchListener;
    private final KeyEventSource keyEventSource;
    private final View.OnKeyListener keyListener;

    public ButtonsComponent(final ClientConnection clientConnection, final KeyEventSource keyEventSource, final View appButton, final View homeButton) {
        this.keyEventSource = keyEventSource;
        this.appButton = appButton;
        this.homeButton = homeButton;
        this.appButtonTouchListener = new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                return ButtonsComponent.this.handleTouch(clientConnection, motionEvent, KeyEvent.KEYCODE_MENU);
            }
        };
        this.homeButtonTouchListener = new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                return ButtonsComponent.this.handleTouch(clientConnection, motionEvent, KeyEvent.KEYCODE_HOME);
            }
        };
        this.keyListener = new View.OnKeyListener() {
            public boolean onKey(final View view, final int keyCode, final KeyEvent keyEvent) {
                boolean isButtonPressed = keyEvent.getAction() == KeyEvent.ACTION_DOWN;
                switch (keyCode) {
                    default: {
                        return false;
                    }
                    case KeyEvent.KEYCODE_VOLUME_UP:
                    case KeyEvent.KEYCODE_VOLUME_DOWN: {
                        ButtonsComponent.this.sendKeyEvent(clientConnection, keyCode, isButtonPressed);
                        return true;
                    }
                }
            }
        };
    }

    private boolean handleTouch(ClientConnection clientConnection, MotionEvent motionEvent, int keyCode) {
        final int action = motionEvent.getAction();
        if (action == KeyEvent.ACTION_DOWN) {
            this.sendKeyEvent(clientConnection, keyCode, true);
        }
        else if (action == KeyEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            this.sendKeyEvent(clientConnection, keyCode, false);
            return false;
        }
        return false;
    }

    private void sendKeyEvent(ClientConnection clientConnection, int keyCode, boolean isButtonPressed) {
        clientConnection.sendMessage(ProtoUtils.keyToProto(keyCode, isButtonPressed));
    }

    @Override
    public void start() {
        this.appButton.setOnTouchListener(this.appButtonTouchListener);
        this.homeButton.setOnTouchListener(this.homeButtonTouchListener);
        this.keyEventSource.setOnKeyListener(this.keyListener);
    }

    @Override
    public void stop() {
        this.appButton.setOnTouchListener(null);
        this.homeButton.setOnTouchListener(null);
        this.keyEventSource.setOnKeyListener(null);
    }
}
