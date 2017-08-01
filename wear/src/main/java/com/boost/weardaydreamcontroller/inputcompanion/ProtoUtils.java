package com.boost.weardaydreamcontroller.inputcompanion;

import java.nio.channels.WritableByteChannel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import com.boost.weardaydreamcontroller.gvr.io.proto.nano.Protos;
import com.google.protobuf.nano.MessageNano;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.MotionEvent;
import javax.annotation.Nullable;
import android.os.SystemClock;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ProtoUtils {
    private static final int MAX_PROTO_LENGTH = 32767;
    private static final String TAG = ProtoUtils.class.getSimpleName();

    public static Protos.PhoneEvent accelToProto(float[] array, long timestamp) {
        Protos.PhoneEvent phoneEvent = new Protos.PhoneEvent();
        phoneEvent.setType(Protos.PhoneEvent.Type.ACCELEROMETER);
        (phoneEvent.accelerometerEvent = new Protos.PhoneEvent.AccelerometerEvent()).setTimestamp(timestamp);
        phoneEvent.accelerometerEvent.setX(array[0]);
        phoneEvent.accelerometerEvent.setY(array[1]);
        phoneEvent.accelerometerEvent.setZ(array[2]);
        return phoneEvent;
    }

    private static int blockingRead(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int i;
        for (i = 0; i < byteBuffer.limit() - byteBuffer.position(); i += readableByteChannel.read(byteBuffer)) {}
        return i;
    }

    public static Protos.PhoneEvent gyroToProto(float[] array, long timestamp) {
        Protos.PhoneEvent phoneEvent = new Protos.PhoneEvent();
        phoneEvent.setType(Protos.PhoneEvent.Type.GYROSCOPE);
        (phoneEvent.gyroscopeEvent = new Protos.PhoneEvent.GyroscopeEvent()).setTimestamp(timestamp);
        phoneEvent.gyroscopeEvent.setX(array[0]);
        phoneEvent.gyroscopeEvent.setY(array[1]);
        phoneEvent.gyroscopeEvent.setZ(array[2]);
        return phoneEvent;
    }

    @Nullable
    public static Protos.PhoneEvent hoverHeatmapToProto(float[] zDistances, int width, int height) {
        if (width <= 0 || height <= 0 || zDistances == null || zDistances.length != width * height) {
            return null;
        }
        Protos.PhoneEvent phoneEvent = new Protos.PhoneEvent();
        phoneEvent.setType(Protos.PhoneEvent.Type.DEPTH_MAP);
        (phoneEvent.depthMapEvent = new Protos.PhoneEvent.DepthMapEvent()).setTimestamp(SystemClock.uptimeMillis());
        phoneEvent.depthMapEvent.setWidth(width);
        phoneEvent.depthMapEvent.setHeight(height);
        phoneEvent.depthMapEvent.zDistances = zDistances;
        return phoneEvent;
    }

    public static Protos.PhoneEvent keyToProto(int keyCode, boolean isButtonPressed) {
        Protos.PhoneEvent phoneEvent = new Protos.PhoneEvent();
        phoneEvent.setType(Protos.PhoneEvent.Type.KEY);
        phoneEvent.keyEvent = new Protos.PhoneEvent.KeyEvent();
        phoneEvent.keyEvent.setCode(keyCode);
        Protos.PhoneEvent.KeyEvent keyEvent = phoneEvent.keyEvent;
        keyCode = isButtonPressed ? KeyEvent.ACTION_DOWN : KeyEvent.ACTION_UP;
        keyEvent.setAction(keyCode);
        return phoneEvent;
    }

    public static Protos.PhoneEvent motionToProto(MotionEvent motionEvent, Point point) {
        Protos.PhoneEvent phoneEvent = new Protos.PhoneEvent();
        phoneEvent.setType(Protos.PhoneEvent.Type.MOTION);
        (phoneEvent.motionEvent = new Protos.PhoneEvent.MotionEvent()).setTimestamp(motionEvent.getEventTime());
        phoneEvent.motionEvent.setAction(motionEvent.getAction());
        int pointerCount = motionEvent.getPointerCount();
        phoneEvent.motionEvent.pointers = new Protos.PhoneEvent.MotionEvent.Pointer[pointerCount];
        for (int i = 0; i < pointerCount; ++i) {
            (phoneEvent.motionEvent.pointers[i] = new Protos.PhoneEvent.MotionEvent.Pointer()).setId(motionEvent.getPointerId(i));
            phoneEvent.motionEvent.pointers[i].setNormalizedX(motionEvent.getX(i) / point.x);
            phoneEvent.motionEvent.pointers[i].setNormalizedY(motionEvent.getY(i) / point.y);
        }
        return phoneEvent;
    }

    @Nullable
    public static Protos.PhoneEvent orientationToProto(long timestamp, float[] array) {
        if (array == null || array.length < 4) {
            return null;
        }
        Protos.PhoneEvent phoneEvent = new Protos.PhoneEvent();
        phoneEvent.setType(Protos.PhoneEvent.Type.ORIENTATION);
        (phoneEvent.orientationEvent = new Protos.PhoneEvent.OrientationEvent()).setTimestamp(timestamp);
        phoneEvent.orientationEvent.setX(array[0]);
        phoneEvent.orientationEvent.setY(array[1]);
        phoneEvent.orientationEvent.setZ(array[2]);
        phoneEvent.orientationEvent.setW(array[3]);
        return phoneEvent;
    }

    public static Protos.PhoneEvent readFromByteBuffer(ByteBuffer byteBuffer) throws IOException {
        return MessageNano.mergeFrom(
                new Protos.PhoneEvent(),
                byteBuffer.array(),
                byteBuffer.arrayOffset() + byteBuffer.position(),
                byteBuffer.limit() - byteBuffer.position()
        );
    }

    @Nullable
    public static Protos.PhoneEvent readFromChannel(ReadableByteChannel readableByteChannel) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        if (blockingRead(readableByteChannel, allocate) >= 4) {
            allocate.rewind();
            int int1 = allocate.getInt();
            if (int1 < 1 || int1 > MAX_PROTO_LENGTH) {
                Log.w(ProtoUtils.TAG, new StringBuilder(36).append("Invalid protobuf length: ").append(int1).toString());
                return null;
            }
            ByteBuffer allocate2 = ByteBuffer.allocate(int1);
            if (blockingRead(readableByteChannel, allocate2) >= int1) {
                allocate2.rewind();
                return readFromByteBuffer(allocate2);
            }
        }
        return null;
    }

    @Nullable
    public static Protos.PhoneEvent sensorEventToProto(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            return gyroToProto(sensorEvent.values, sensorEvent.timestamp);
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            return accelToProto(sensorEvent.values, sensorEvent.timestamp);
        }
        return null;
    }

    public static void writeToChannel(WritableByteChannel writableByteChannel, MessageNano messageNano) throws IOException {
        int serializedSize = messageNano.getSerializedSize();
        ByteBuffer allocate = ByteBuffer.allocate(serializedSize + 4);
        allocate.putInt(serializedSize);
        MessageNano.toByteArray(messageNano, allocate.array(), allocate.arrayOffset() + 4, serializedSize);
        allocate.rewind();
        writableByteChannel.write(allocate);
    }
}
