package com.boost.weardaydreamcontroller.inputcompanion;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.util.UUID;
import java.io.IOException;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

import com.google.common.base.Preconditions;

import com.google.common.annotations.VisibleForTesting;

public class BluetoothSocketServer extends BaseSocketServer implements Runnable {
    private static final long RETRY_INTERVAL_MILLIS = 5000L;
    @VisibleForTesting
    static final String SERVICE_NAME = "Controller Emulator";
    @VisibleForTesting
    static final String SERVICE_UUID = "ab001ac1-d740-4abb-a8e6-1cb5a49628fa";
    private static final String TAG = BluetoothSocketServer.class.getSimpleName();
    private final BluetoothAdapter bluetoothAdapter;
    @GuardedBy("lock")
    private BluetoothServerSocket bluetoothServerSocket;
    private final ClientConnectionCallback callback;
    private final ClientConnectionFactory clientConnectionFactory;
    private final Thread listenThread;
    private final Object lock;
    @GuardedBy("lock")
    private boolean shouldStop;
    private final ThreadSleepMethod sleepMethod;

    public BluetoothSocketServer(final ClientConnectionCallback clientConnectionCallback) {
        this(clientConnectionCallback, null, null, null);
    }

    BluetoothSocketServer(
            ClientConnectionCallback clientConnectionCallback,
            @Nullable BluetoothAdapter defaultAdapter,
            @Nullable ThreadSleepMethod sleepMethod,
            @Nullable ClientConnectionFactory clientConnectionFactory
    ) {
        this.lock = new Object();
        this.shouldStop = false;
        this.callback = Preconditions.checkNotNull(clientConnectionCallback);
        if (defaultAdapter == null) {
            defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        this.bluetoothAdapter = defaultAdapter;
        if (sleepMethod == null) {
            sleepMethod = new ThreadSleepMethod() {
                @Override
                public void sleep(final long n) throws InterruptedException {
                    Thread.sleep(n);
                }
            };
        }
        this.sleepMethod = sleepMethod;
        if (clientConnectionFactory == null) {
            clientConnectionFactory = new ClientConnectionFactory() {
                @Override
                public ClientConnection createFromBluetoothSocket(final BluetoothSocket bluetoothSocket) throws IOException {
                    return ClientConnection.createFromBluetoothSocket(bluetoothSocket);
                }
            };
        }
        this.clientConnectionFactory = clientConnectionFactory;
        this.listenThread = new Thread(this, "BluetoothSocketServer Listen Thread");
        if (this.bluetoothAdapter == null) {
            Log.e(BluetoothSocketServer.TAG, "No bluetooth adapter!");
            return;
        }
        this.listenThread.start();
    }

    private void closeServerSocket() {
        synchronized (this.lock) {
            if (this.bluetoothServerSocket != null) {
                this.closeSilently(this.bluetoothServerSocket);
                this.bluetoothServerSocket = null;
            }
        }
    }

    private void closeSilently(@Nullable final BluetoothServerSocket bluetoothServerSocket) {
        if (bluetoothServerSocket == null) {
            return;
        }
        try {
            bluetoothServerSocket.close();
        } catch (IOException ex) {
        }
    }

    private void doOneConnectionAttempt() throws IOException {
        final BluetoothServerSocket listenUsingRfcommWithServiceRecord =
                this.bluetoothAdapter.listenUsingRfcommWithServiceRecord(SERVICE_NAME, UUID.fromString(SERVICE_UUID));
        if (listenUsingRfcommWithServiceRecord == null) {
            Log.w(BluetoothSocketServer.TAG, "Failed to create bluetooth server socket.");
            return;
        }
        if (!this.setServerSocket(listenUsingRfcommWithServiceRecord)) {
            this.closeSilently(listenUsingRfcommWithServiceRecord);
            return;
        }
        Log.i(BluetoothSocketServer.TAG, "Awaiting bluetooth connection.");
        final BluetoothSocket accept = listenUsingRfcommWithServiceRecord.accept();
        this.closeServerSocket();
        if (accept == null) {
            Log.w(BluetoothSocketServer.TAG, "Failed to accept bluetooth socket.");
            return;
        }
        Log.i(BluetoothSocketServer.TAG, "Connected to bluetooth socket.");
        this.callback.onConnect(this.clientConnectionFactory.createFromBluetoothSocket(accept));
    }

    private boolean setServerSocket(final BluetoothServerSocket bluetoothServerSocket) {
        synchronized (this.lock) {
            if (this.shouldStop) {
                return false;
            }
            this.bluetoothServerSocket = bluetoothServerSocket;
            return true;
        }
    }

    private boolean shouldStop() {
        synchronized (this.lock) {
            return this.shouldStop;
        }
    }

    private void sleep(final long n) {
        try {
            this.sleepMethod.sleep(n);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        int n = 0;
        Label_0054_Outer:
        while (!this.shouldStop()) {
            if (this.bluetoothAdapter.isEnabled()) {
                n = 0;
                while (true) {
                    try {
                        this.doOneConnectionAttempt();
                        this.closeServerSocket();
                        continue Label_0054_Outer;
                    } catch (IOException ex) {
                        if (!this.shouldStop()) {
                            ex.printStackTrace();
                        }
                        continue;
                    }
                }
            }
            int n2;
            if ((n2 = n) == 0) {
                Log.w(BluetoothSocketServer.TAG, "Bluetooth not enabled. Will keep trying.");
                n2 = 1;
            }
            this.sleep(RETRY_INTERVAL_MILLIS);
            n = n2;
        }
        Log.i(BluetoothSocketServer.TAG, "Bluetooth server socket stopped.");
    }

    @Override
    public void stop() {
        Log.i(TAG, "Stopping bluetooth socket server.");
        this.shouldStop = true;
        synchronized (this.lock) {
            BluetoothServerSocket bluetoothServerSocket = this.bluetoothServerSocket;
            if (bluetoothServerSocket != null) {
                try {
                    this.bluetoothServerSocket.close();
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
        }
        if (Thread.currentThread() != this.listenThread) {
            try {
                this.listenThread.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        this.callback.onServerStopped();
        Log.i(TAG, "Stopped bluetooth socket server.");
    }

    //@Override
    public void stop1() {
        //
        // This method could not be decompiled.
        //
        // Original Bytecode:
        //
        //     0: getstatic       com/google/vr/inputcompanion/BluetoothSocketServer.TAG:Ljava/lang/String;
        //     3: ldc             "Stopping bluetooth socket server."
        //     5: invokestatic    android/util/Log.i:(Ljava/lang/String;Ljava/lang/String;)I
        //     8: pop
        //     9: aload_0
        //    10: getfield        com/google/vr/inputcompanion/BluetoothSocketServer.lock:Ljava/lang/Object;
        //    13: astore_1
        //    14: aload_1
        //    15: monitorenter
        //    16: aload_0
        //    17: iconst_1
        //    18: putfield        com/google/vr/inputcompanion/BluetoothSocketServer.shouldStop:Z
        //    21: aload_0
        //    22: getfield        com/google/vr/inputcompanion/BluetoothSocketServer.bluetoothServerSocket:Landroid/bluetooth/BluetoothServerSocket;
        //    25: astore_2
        //    26: aload_2
        //    27: ifnull          37
        //    30: aload_0
        //    31: getfield        com/google/vr/inputcompanion/BluetoothSocketServer.bluetoothServerSocket:Landroid/bluetooth/BluetoothServerSocket;
        //    34: invokevirtual   android/bluetooth/BluetoothServerSocket.close:()V
        //    37: aload_1
        //    38: monitorexit
        //    39: invokestatic    java/lang/Thread.currentThread:()Ljava/lang/Thread;
        //    42: aload_0
        //    43: getfield        com/google/vr/inputcompanion/BluetoothSocketServer.listenThread:Ljava/lang/Thread;
        //    46: if_acmpeq       56
        //    49: aload_0
        //    50: getfield        com/google/vr/inputcompanion/BluetoothSocketServer.listenThread:Ljava/lang/Thread;
        //    53: invokevirtual   java/lang/Thread.join:()V
        //    56: aload_0
        //    57: getfield        com/google/vr/inputcompanion/BluetoothSocketServer.callback:Lcom/google/vr/inputcompanion/BaseSocketServer$ClientConnectionCallback;
        //    60: invokeinterface com/google/vr/inputcompanion/BaseSocketServer$ClientConnectionCallback.onServerStopped:()V
        //    65: getstatic       com/google/vr/inputcompanion/BluetoothSocketServer.TAG:Ljava/lang/String;
        //    68: ldc             "Stopped bluetooth socket server."
        //    70: invokestatic    android/util/Log.i:(Ljava/lang/String;Ljava/lang/String;)I
        //    73: pop
        //    74: return
        //    75: astore_2
        //    76: aload_1
        //    77: monitorexit
        //    78: aload_2
        //    79: athrow
        //    80: astore_1
        //    81: aload_1
        //    82: invokevirtual   java/lang/InterruptedException.printStackTrace:()V
        //    85: goto            56
        //    88: astore_2
        //    89: goto            37
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  --------------------------------
        //  16     26     75     80     Any
        //  30     37     88     92     Ljava/io/IOException;
        //  30     37     75     80     Any
        //  37     39     75     80     Any
        //  49     56     80     88     Ljava/lang/InterruptedException;
        //  76     78     75     80     Any
        //
        // The error that occurred was:
        //
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0037:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        //
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }

    interface ClientConnectionFactory {
        ClientConnection createFromBluetoothSocket(final BluetoothSocket p0) throws IOException;
    }

    interface ThreadSleepMethod {
        void sleep(final long p0) throws InterruptedException;
    }
}
