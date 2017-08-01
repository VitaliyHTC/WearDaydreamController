package com.boost.weardaydreamcontroller.inputcompanion.components;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

import com.boost.weardaydreamcontroller.gvr.io.proto.nano.Protos;
import com.boost.weardaydreamcontroller.inputcompanion.ClientConnection;
import com.boost.weardaydreamcontroller.inputcompanion.ProtoUtils;

public class SensorsComponent implements InputComponent {
    private final Handler eventHandler;
    private final SensorEventListener sensorEventListener;
    private final SensorManager sensorManager;

    public SensorsComponent(final SensorManager sensorManager, final Handler eventHandler, final SensorEventListener sensorEventListener) {
        this.sensorManager = sensorManager;
        this.eventHandler = eventHandler;
        this.sensorEventListener = sensorEventListener;
    }

    public SensorsComponent(final ClientConnection clientConnection, final SensorManager sensorManager) {
        this(sensorManager, clientConnection.getWriteHandler(), new SensorEventListener() {
            public void onAccuracyChanged(final Sensor sensor, final int n) {
            }

            public void onSensorChanged(final SensorEvent sensorEvent) {
                final Protos.PhoneEvent sensorEventToProto = ProtoUtils.sensorEventToProto(sensorEvent);
                if (sensorEventToProto == null) {
                    return;
                }
                clientConnection.sendMessage(sensorEventToProto);
            }
        });
    }

    @Override
    public void start() {
        this.sensorManager.registerListener(this.sensorEventListener, this.sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 0, this.eventHandler);
        this.sensorManager.registerListener(this.sensorEventListener, this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 0, this.eventHandler);
        this.sensorManager.registerListener(this.sensorEventListener, this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED), 0, this.eventHandler);
    }

    @Override
    public void stop() {
        this.sensorManager.unregisterListener(this.sensorEventListener);
    }
}

