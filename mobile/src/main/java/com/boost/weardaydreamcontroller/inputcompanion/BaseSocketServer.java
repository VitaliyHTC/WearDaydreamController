package com.boost.weardaydreamcontroller.inputcompanion;

public abstract class BaseSocketServer {
    public abstract void stop();

    public interface ClientConnectionCallback {
        void onConnect(final ClientConnection p0);
        void onServerStopped();
    }
}
