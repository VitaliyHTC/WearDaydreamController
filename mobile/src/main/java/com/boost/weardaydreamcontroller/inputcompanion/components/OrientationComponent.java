package com.boost.weardaydreamcontroller.inputcompanion.components;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

import com.boost.weardaydreamcontroller.gvr.io.proto.nano.Protos;
import com.boost.weardaydreamcontroller.inputcompanion.ClientConnection;
import com.boost.weardaydreamcontroller.inputcompanion.ProtoUtils;
import com.google.vr.inputcompanion.SensorFusionJni;

public class OrientationComponent implements InputComponent {
    private final OrientationListener orientationListener;
    private final SensorEventListener sensorEventListener;
    private final SensorFusionJni sensorFusionJni;
    private final SensorProvider sensorProvider;

    public OrientationComponent(final Handler handler, final Context context, final SensorFusionJni sensorFusionJni, final OrientationListener orientationListener) {
        this.sensorEventListener = new SensorEventListener() {
            private final float[] quaternion = new float[4];

            public void onAccuracyChanged(final Sensor sensor, final int n) {
            }

            public void onSensorChanged(final SensorEvent sensorEvent) {
                switch (sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_GYROSCOPE: {
                        OrientationComponent.this.sensorFusionJni.addGyroMeasurement(sensorEvent.values, sensorEvent.timestamp);
                        break;
                    }
                    case Sensor.TYPE_ACCELEROMETER: {
                        OrientationComponent.this.sensorFusionJni.addAccelMeasurement(sensorEvent.values, sensorEvent.timestamp);
                        break;
                    }
                }
                if (sensorEvent.sensor.getType() != Sensor.TYPE_GYROSCOPE) {
                    return;
                }
                OrientationComponent.this.sensorFusionJni.getOrientation(this.quaternion);
                OrientationComponent.this.orientationListener.onOrientationEvent(sensorEvent.timestamp, this.quaternion);
            }
        };
        this.orientationListener = orientationListener;
        this.sensorProvider = new SensorProvider((SensorManager)context.getSystemService(Context.SENSOR_SERVICE), handler);
        this.sensorFusionJni = sensorFusionJni;
    }

    public OrientationComponent(final ClientConnection clientConnection, final Context context, final SensorFusionJni sensorFusionJni) {
        this(clientConnection.getWriteHandler(), context, sensorFusionJni, new OrientationListener() {
            @Override
            public void onOrientationEvent(final long n, final float[] array) {
                final Protos.PhoneEvent orientationToProto = ProtoUtils.orientationToProto(n, array);
                if (orientationToProto != null) {
                    clientConnection.sendMessage(orientationToProto);
                }
            }
        });
    }

    @Override
    public void start() {
        this.sensorFusionJni.init();
        this.sensorProvider.registerListener(this.sensorEventListener);
    }

    @Override
    public void stop() {
        this.sensorProvider.unregisterListener(this.sensorEventListener);
        this.sensorFusionJni.deInit();
    }

    public interface OrientationListener {
        void onOrientationEvent(final long p0, final float[] p1);
    }

    private static class SensorProvider {
        private final Handler handler;
        private final SensorManager sensorManager;

        SensorProvider(final SensorManager sensorManager, final Handler handler) {
            this.sensorManager = sensorManager;
            this.handler = handler;
        }

        public void registerListener(final SensorEventListener sensorEventListener) {
            this.sensorManager.registerListener(sensorEventListener, this.sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 0, this.handler);
            this.sensorManager.registerListener(sensorEventListener, this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 0, this.handler);
        }

        public void unregisterListener(final SensorEventListener sensorEventListener) {
            this.sensorManager.unregisterListener(sensorEventListener);
        }
    }
}

