package com.google.vr.inputcompanion;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class SensorFusionJni {
    private boolean initialized;
    private long nativeSensorFusionPtr;
    private final SensorFusionAlgorithm sensorFusionAlgorithm;

    static {
        System.loadLibrary("sensor_fusion_jni");
    }

    public SensorFusionJni(final SensorFusionAlgorithm sensorFusionAlgorithm) {
        System.loadLibrary("sensor_fusion_jni");
        this.sensorFusionAlgorithm = sensorFusionAlgorithm;
    }

    private native void nativeAddAccelMeasurement(final long p0, final float[] p1, final long p2);

    private native void nativeAddGyroMeasurement(final long p0, final float[] p1, final long p2);

    private native void nativeAddMagMeasurement(final long p0, final float[] p1, final long p2);

    private native void nativeDestroy(final long p0);

    private native void nativeGetOrientation(final long p0, final float[] p1);

    private native long nativeInit(final int p0);

    public void addAccelMeasurement(final float[] array, final long n) {
        synchronized (this) {
            if (this.initialized) {
                this.nativeAddAccelMeasurement(this.nativeSensorFusionPtr, array, n);
            }
        }
    }

    public void addGyroMeasurement(final float[] array, final long n) {
        synchronized (this) {
            if (this.initialized) {
                this.nativeAddGyroMeasurement(this.nativeSensorFusionPtr, array, n);
            }
        }
    }

    public void addMagMeasurement(final float[] array, final long n) {
        synchronized (this) {
            if (this.initialized) {
                this.nativeAddMagMeasurement(this.nativeSensorFusionPtr, array, n);
            }
        }
    }

    public void deInit() {
        synchronized (this) {
            if (this.initialized) {
                this.nativeDestroy(this.nativeSensorFusionPtr);
                this.initialized = false;
            }
        }
    }

    public void getOrientation(final float[] array) {
        synchronized (this) {
            if (this.initialized) {
                this.nativeGetOrientation(this.nativeSensorFusionPtr, array);
            }
        }
    }

    public void init() {
        synchronized (this) {
            if (!this.initialized) {
                this.nativeSensorFusionPtr = this.nativeInit(this.sensorFusionAlgorithm.ordinal());
                this.initialized = true;
            }
        }
    }

    public enum SensorFusionAlgorithm {
        EKF_WITH_BIAS_ESTIMATOR,
        MAHONY;
    }
}
