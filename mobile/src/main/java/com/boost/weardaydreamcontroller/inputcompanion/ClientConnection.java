package com.boost.weardaydreamcontroller.inputcompanion;

import com.google.common.annotations.VisibleForTesting;
import android.util.Log;
import com.google.protobuf.nano.MessageNano;
import android.os.HandlerThread;
import com.google.common.base.Preconditions;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.io.InputStream;
import android.os.Handler;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import javax.annotation.Nullable;
import android.bluetooth.BluetoothSocket;

public class ClientConnection {
    private static final String TAG = ClientConnection.class.getSimpleName();
    @Nullable
    private final BluetoothSocket bluetoothSocket;
    private final Object closeLock;
    private volatile boolean isClosed;
    @Nullable
    private final SocketChannel networkSocket;
    private final ReadableByteChannel readChannel;
    private final WritableByteChannel writeChannel;
    private final Handler writeHandler;
    private final Thread writeThread;

    ClientConnection(
            @Nullable final SocketChannel writeChannel,
            @Nullable final BluetoothSocket bluetoothSocket,
            @Nullable final Thread writeThread,
            @Nullable final Handler writeHandler,
            @Nullable final ChannelFactory channelFactory
    ) throws IOException {
        boolean booleanFlag = true;
        this.closeLock = new Object();
        this.networkSocket = writeChannel;
        this.bluetoothSocket = bluetoothSocket;
        Object o = channelFactory;
        if (channelFactory == null) {
            o = new ChannelFactory() {
                @Override
                public ReadableByteChannel createReadableChannel(final InputStream inputStream) throws IOException {
                    return Channels.newChannel(inputStream);
                }

                @Override
                public WritableByteChannel createWritableChannel(final OutputStream outputStream) throws IOException {
                    return Channels.newChannel(outputStream);
                }
            };
        }
        int n;
        if (writeChannel == null) {
            n = 1;
        }
        else {
            n = 0;
        }
        int n2;
        if (bluetoothSocket == null) {
            n2 = 1;
        }
        else {
            n2 = 0;
        }
        if (n == n2) {
            booleanFlag = false;
        }
        Preconditions.checkArgument(booleanFlag, "Exactly one of networkSocket or bluetoothSocket must be non-null.");
        if (writeThread != null) {
            Preconditions.checkNotNull(writeHandler, "If writeThread is not null, writeHandler must also be not null.");
        }
        if (writeChannel != null) {
            this.readChannel = writeChannel;
            this.writeChannel = writeChannel;
        }
        else {
            this.readChannel = ((ChannelFactory)o).createReadableChannel(bluetoothSocket.getInputStream());
            this.writeChannel = ((ChannelFactory)o).createWritableChannel(bluetoothSocket.getOutputStream());
        }
        if (writeThread == null) {
            final HandlerThread writeThread2 = new HandlerThread("Write thread");
            (this.writeThread = writeThread2).start();
            this.writeHandler = new Handler(writeThread2.getLooper());
            return;
        }
        if (writeHandler == null) {
            throw new IllegalArgumentException("If a writeThread is provided to ClientConnection, a writeHandler must also be provided.");
        }
        this.writeThread = writeThread;
        this.writeHandler = writeHandler;
    }

    public static ClientConnection createFromBluetoothSocket(final BluetoothSocket bluetoothSocket) throws IOException {
        return new ClientConnection(null, bluetoothSocket, null, null, null);
    }

    public static ClientConnection createFromNetworkSocket(final SocketChannel socketChannel) throws IOException {
        return new ClientConnection(socketChannel, null, null, null, null);
    }

    private boolean sendMessageOnWriteThread(final MessageNano messageNano) {
        if (this.isClosed) {
            return false;
        }
        try {
            ProtoUtils.writeToChannel(this.writeChannel, messageNano);
            return true;
        }
        catch (IOException ex) {
            final String tag = ClientConnection.TAG;
            final String value = String.valueOf(ex);
            Log.e(tag, new StringBuilder(String.valueOf(value).length() + 27).append("Failed to write to socket: ").append(value).toString());
            this.close();
            return false;
        }
    }

    public void close() {
        final Object closeLock = this.closeLock;
        synchronized (closeLock) {
            if (!this.isClosed) {
                try {
                    if (this.networkSocket != null) {
                        this.networkSocket.close();
                    }
                    if (this.bluetoothSocket != null) {
                        this.bluetoothSocket.close();
                    }
                    this.writeChannel.close();
                    this.readChannel.close();
                    this.writeHandler.getLooper().quit();
                    this.isClosed = true;
                    // monitorexit(closeLock)
                    if (Thread.currentThread() == this.writeThread) {
                        return;
                    }
                    try {
                        this.writeThread.join();
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Nullable
    @VisibleForTesting
    BluetoothSocket getBluetoothSocket() {
        return this.bluetoothSocket;
    }

    @Nullable
    @VisibleForTesting
    SocketChannel getNetworkSocket() {
        return this.networkSocket;
    }

    @Nullable
    public Handler getWriteHandler() {
        if (this.isClosed) {
            return null;
        }
        return this.writeHandler;
    }

    public boolean isBluetooth() {
        return this.bluetoothSocket != null;
    }

    public boolean isOpen() {
        return !this.isClosed;
    }

    @Nullable
    public MessageNano receiveMessage() throws IOException {
        if (this.isClosed) {
            return null;
        }
        return ProtoUtils.readFromChannel(this.readChannel);
    }

    public boolean sendMessage(final MessageNano messageNano) {
        if (this.isClosed) {
            return false;
        }
        if (Thread.currentThread() == this.writeThread) {
            return this.sendMessageOnWriteThread(messageNano);
        }
        this.writeHandler.post(new Runnable() {
            @Override
            public void run() {
                ClientConnection.this.sendMessageOnWriteThread(messageNano);
            }
        });
        return true;
    }

    @VisibleForTesting
    interface ChannelFactory {
        ReadableByteChannel createReadableChannel(InputStream inputStream) throws IOException;
        WritableByteChannel createWritableChannel(OutputStream outputStream) throws IOException;
    }
}

