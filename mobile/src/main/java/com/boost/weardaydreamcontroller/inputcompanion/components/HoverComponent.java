package com.boost.weardaydreamcontroller.inputcompanion.components;

import android.util.Log;
import android.view.Choreographer;

import com.boost.weardaydreamcontroller.gvr.io.proto.nano.Protos;
import com.boost.weardaydreamcontroller.inputcompanion.ClientConnection;
import com.boost.weardaydreamcontroller.inputcompanion.ProtoUtils;
import com.boost.weardaydreamcontroller.inputcompanion.fogale.HoverHeatmapProvider;

import java.util.concurrent.CountDownLatch;

public class HoverComponent implements InputComponent {
    private static final String TAG = HoverComponent.class.getSimpleName();
    private final ClientConnection clientConnection;
    private Choreographer frameStartScheduler;
    private final HoverHeatmapProvider provider;
    private volatile boolean shouldStop;

    public HoverComponent(final ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        this.shouldStop = false;
        this.provider = HoverHeatmapProvider.create();
    }

    @Override
    public void start() {
        if (this.provider == null) {
            Log.e(HoverComponent.TAG, "Unable to create a HoverHeatmapProvider.");
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.clientConnection.getWriteHandler().post(new Runnable() {
            @Override
            public void run() {
                HoverComponent.this.frameStartScheduler = Choreographer.getInstance();
                countDownLatch.countDown();
            }
        });
        while (true) {
            try {
                countDownLatch.await();
                this.frameStartScheduler.postFrameCallback(new Choreographer.FrameCallback() {
                    public void doFrame(final long n) {
                        final Protos.PhoneEvent hoverHeatmapToProto =
                                ProtoUtils.hoverHeatmapToProto(
                                        HoverComponent.this.provider.getLatestHeatmap(),
                                        HoverComponent.this.provider.getWidth(),
                                        HoverComponent.this.provider.getHeight()
                                );
                        boolean sendMessage = false;
                        if (hoverHeatmapToProto != null) {
                            sendMessage = HoverComponent.this.clientConnection.sendMessage(hoverHeatmapToProto);
                        }
                        if (sendMessage && !HoverComponent.this.shouldStop) {
                            HoverComponent.this.frameStartScheduler.postFrameCallback(this);
                        }
                    }
                });
            } catch (InterruptedException ex) {
                final String value = String.valueOf(ex);
                Log.e(HoverComponent.TAG, new StringBuilder(String.valueOf(value).length() + 53).append("Interrupted while waiting for write thread to start: ").append(value).toString());
                continue;
            }
            break;
        }
    }

    @Override
    public void stop() {
        this.shouldStop = true;
    }
}
