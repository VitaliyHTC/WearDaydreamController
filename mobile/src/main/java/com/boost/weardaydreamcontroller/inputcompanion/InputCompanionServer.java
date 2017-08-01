package com.boost.weardaydreamcontroller.inputcompanion;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.util.List;

import javax.annotation.Nullable;

import android.util.Log;
import android.os.Looper;
import android.os.Handler;

import com.boost.weardaydreamcontroller.inputcompanion.components.InputComponent;
import com.google.common.collect.ImmutableList;

public class InputCompanionServer implements Runnable {
    private static final String TAG = InputCompanionServer.class.getSimpleName();
    private final ClientConnection clientConnection;
    private final ImmutableList<InputComponent> components;
    private Handler handler;
    private final Listener listener;

    public InputCompanionServer(ClientConnection clientConnection, List<? extends InputComponent> list, @Nullable Listener listener) {
        this.clientConnection = clientConnection;
        this.components = ImmutableList.copyOf(list);
        this.listener = listener;
        this.handler = new Handler(Looper.getMainLooper());
        for (InputComponent inputComponent : list) {
            inputComponent.start();
        }
        new Thread(this, "Read Thread").start();
    }

    @Override
    public void run() {
        Log.i(InputCompanionServer.TAG, "Read thread started");
        while (true) {
            try {
                while (true) {
                    this.clientConnection.receiveMessage();
                }
            } catch (AsynchronousCloseException ex2) {
                for (InputComponent component : this.components) {
                    component.stop();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Throwable t) {
                Log.e(InputCompanionServer.TAG, "Unexpected error during read thread (stack trace follows).");
                t.printStackTrace();
                continue;
            }
            break;
        }
        this.clientConnection.close();
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                @Override
                public void run() {
                    InputCompanionServer.this.listener.onInputCompanionServerFinished();
                }
            });
        }
    }

    public interface Listener {
        void onInputCompanionServerFinished();
    }
}
