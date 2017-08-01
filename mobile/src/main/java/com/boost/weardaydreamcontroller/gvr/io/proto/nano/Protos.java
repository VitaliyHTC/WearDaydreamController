package com.boost.weardaydreamcontroller.gvr.io.proto.nano;

import com.google.protobuf.nano.WireFormatNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.ExtendableMessageNano;

@SuppressWarnings({"UnnecessaryContinue", "WeakerAccess", "unused"})
public interface Protos {

    final class PhoneEvent extends ExtendableMessageNano<PhoneEvent> {
        private static volatile PhoneEvent[] _emptyArray;
        public AccelerometerEvent accelerometerEvent;
        private int bitField0_;
        public DepthMapEvent depthMapEvent;
        public GyroscopeEvent gyroscopeEvent;
        public KeyEvent keyEvent;
        public MotionEvent motionEvent;
        public OrientationEvent orientationEvent;
        public PositionEvent posEvent;
        private int type_;

        public PhoneEvent() {
            this.clear();
        }

        public static PhoneEvent[] emptyArray() {
            if (PhoneEvent._emptyArray != null) {
                return PhoneEvent._emptyArray;
            } else {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (PhoneEvent._emptyArray == null) {
                        PhoneEvent._emptyArray = new PhoneEvent[0];
                    }
                    return PhoneEvent._emptyArray;
                }
            }
        }

        public static PhoneEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new PhoneEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static PhoneEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new PhoneEvent(), array);
        }

        public PhoneEvent clear() {
            this.bitField0_ = 0;
            this.type_ = 1;
            this.motionEvent = null;
            this.gyroscopeEvent = null;
            this.accelerometerEvent = null;
            this.depthMapEvent = null;
            this.orientationEvent = null;
            this.keyEvent = null;
            this.posEvent = null;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        public PhoneEvent clearType() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.type_ = 1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int computeSerializedSize;
            final int n = computeSerializedSize = super.computeSerializedSize();
            if ((this.bitField0_ & 0x1) != 0x0) {
                computeSerializedSize = n + CodedOutputByteBufferNano.computeInt32Size(1, this.type_);
            }
            int n2 = computeSerializedSize;
            if (this.motionEvent != null) {
                n2 = computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(2, this.motionEvent);
            }
            int n3 = n2;
            if (this.gyroscopeEvent != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(3, this.gyroscopeEvent);
            }
            int n4 = n3;
            if (this.accelerometerEvent != null) {
                n4 = n3 + CodedOutputByteBufferNano.computeMessageSize(4, this.accelerometerEvent);
            }
            int n5 = n4;
            if (this.depthMapEvent != null) {
                n5 = n4 + CodedOutputByteBufferNano.computeMessageSize(5, this.depthMapEvent);
            }
            int n6 = n5;
            if (this.orientationEvent != null) {
                n6 = n5 + CodedOutputByteBufferNano.computeMessageSize(6, this.orientationEvent);
            }
            int n7 = n6;
            if (this.keyEvent != null) {
                n7 = n6 + CodedOutputByteBufferNano.computeMessageSize(7, this.keyEvent);
            }
            int n8 = n7;
            if (this.posEvent != null) {
                n8 = n7 + CodedOutputByteBufferNano.computeMessageSize(8, this.posEvent);
            }
            return n8;
        }

        public int getType() {
            return this.type_;
        }

        public boolean hasType() {
            return (this.bitField0_ & 0x1) != 0x0;
        }

        @Override
        public PhoneEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            Label_0097:
            while (true) {
                final int tag = codedInputByteBufferNano.readTag();
                switch (tag) {
                    default: {
                        if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                            break Label_0097;
                        }
                        continue;
                    }
                    case 0: {
                        break Label_0097;
                    }
                    case 8: {
                        this.bitField0_ |= 0x1;
                        final int position = codedInputByteBufferNano.getPosition();
                        final int int32 = codedInputByteBufferNano.readInt32();
                        switch (int32) {
                            default: {
                                codedInputByteBufferNano.rewindToPosition(position);
                                this.storeUnknownField(codedInputByteBufferNano, tag);
                                continue;
                            }
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7: {
                                this.type_ = int32;
                                this.bitField0_ |= 0x1;
                                continue;
                            }
                        }
                    }
                    case 18: {
                        if (this.motionEvent == null) {
                            this.motionEvent = new MotionEvent();
                        }
                        codedInputByteBufferNano.readMessage(this.motionEvent);
                        continue;
                    }
                    case 26: {
                        if (this.gyroscopeEvent == null) {
                            this.gyroscopeEvent = new GyroscopeEvent();
                        }
                        codedInputByteBufferNano.readMessage(this.gyroscopeEvent);
                        continue;
                    }
                    case 34: {
                        if (this.accelerometerEvent == null) {
                            this.accelerometerEvent = new AccelerometerEvent();
                        }
                        codedInputByteBufferNano.readMessage(this.accelerometerEvent);
                        continue;
                    }
                    case 42: {
                        if (this.depthMapEvent == null) {
                            this.depthMapEvent = new DepthMapEvent();
                        }
                        codedInputByteBufferNano.readMessage(this.depthMapEvent);
                        continue;
                    }
                    case 50: {
                        if (this.orientationEvent == null) {
                            this.orientationEvent = new OrientationEvent();
                        }
                        codedInputByteBufferNano.readMessage(this.orientationEvent);
                        continue;
                    }
                    case 58: {
                        if (this.keyEvent == null) {
                            this.keyEvent = new KeyEvent();
                        }
                        codedInputByteBufferNano.readMessage(this.keyEvent);
                        continue;
                    }
                    case 66: {
                        if (this.posEvent == null) {
                            this.posEvent = new PositionEvent();
                        }
                        codedInputByteBufferNano.readMessage(this.posEvent);
                        continue;
                    }
                }
            }
            return this;
        }

        public PhoneEvent setType(final int type_) {
            this.type_ = type_;
            this.bitField0_ |= 0x1;
            return this;
        }

        @Override
        public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                codedOutputByteBufferNano.writeInt32(1, this.type_);
            }
            if (this.motionEvent != null) {
                codedOutputByteBufferNano.writeMessage(2, this.motionEvent);
            }
            if (this.gyroscopeEvent != null) {
                codedOutputByteBufferNano.writeMessage(3, this.gyroscopeEvent);
            }
            if (this.accelerometerEvent != null) {
                codedOutputByteBufferNano.writeMessage(4, this.accelerometerEvent);
            }
            if (this.depthMapEvent != null) {
                codedOutputByteBufferNano.writeMessage(5, this.depthMapEvent);
            }
            if (this.orientationEvent != null) {
                codedOutputByteBufferNano.writeMessage(6, this.orientationEvent);
            }
            if (this.keyEvent != null) {
                codedOutputByteBufferNano.writeMessage(7, this.keyEvent);
            }
            if (this.posEvent != null) {
                codedOutputByteBufferNano.writeMessage(8, this.posEvent);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class AccelerometerEvent extends ExtendableMessageNano<AccelerometerEvent> {
            private static volatile AccelerometerEvent[] _emptyArray;
            private int bitField0_;
            private long timestamp_;
            private float x_;
            private float y_;
            private float z_;

            public AccelerometerEvent() {
                this.clear();
            }

            public static AccelerometerEvent[] emptyArray() {
                if (AccelerometerEvent._emptyArray != null) {
                    return AccelerometerEvent._emptyArray;
                } else {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (AccelerometerEvent._emptyArray == null) {
                            AccelerometerEvent._emptyArray = new AccelerometerEvent[0];
                        }
                        return AccelerometerEvent._emptyArray;
                    }
                }
            }

            public static AccelerometerEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new AccelerometerEvent().mergeFrom(codedInputByteBufferNano);
            }

            public static AccelerometerEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new AccelerometerEvent(), array);
            }

            public AccelerometerEvent clear() {
                this.bitField0_ = 0;
                this.timestamp_ = 0L;
                this.x_ = 0.0f;
                this.y_ = 0.0f;
                this.z_ = 0.0f;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            public AccelerometerEvent clearTimestamp() {
                this.timestamp_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public AccelerometerEvent clearX() {
                this.x_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            public AccelerometerEvent clearY() {
                this.y_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            public AccelerometerEvent clearZ() {
                this.z_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int computeSerializedSize;
                final int n = computeSerializedSize = super.computeSerializedSize();
                if ((this.bitField0_ & 0x1) != 0x0) {
                    computeSerializedSize = n + CodedOutputByteBufferNano.computeInt64Size(1, this.timestamp_);
                }
                int n2 = computeSerializedSize;
                if ((this.bitField0_ & 0x2) != 0x0) {
                    n2 = computeSerializedSize + CodedOutputByteBufferNano.computeFloatSize(2, this.x_);
                }
                int n3 = n2;
                if ((this.bitField0_ & 0x4) != 0x0) {
                    n3 = n2 + CodedOutputByteBufferNano.computeFloatSize(3, this.y_);
                }
                int n4 = n3;
                if ((this.bitField0_ & 0x8) != 0x0) {
                    n4 = n3 + CodedOutputByteBufferNano.computeFloatSize(4, this.z_);
                }
                return n4;
            }

            public long getTimestamp() {
                return this.timestamp_;
            }

            public float getX() {
                return this.x_;
            }

            public float getY() {
                return this.y_;
            }

            public float getZ() {
                return this.z_;
            }

            public boolean hasTimestamp() {
                return (this.bitField0_ & 0x1) != 0x0;
            }

            public boolean hasX() {
                return (this.bitField0_ & 0x2) != 0x0;
            }

            public boolean hasY() {
                return (this.bitField0_ & 0x4) != 0x0;
            }

            public boolean hasZ() {
                return (this.bitField0_ & 0x8) != 0x0;
            }

            @Override
            public AccelerometerEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                Label_0065:
                while (true) {
                    final int tag = codedInputByteBufferNano.readTag();
                    switch (tag) {
                        default: {
                            if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                break Label_0065;
                            }
                            continue;
                        }
                        case 0: {
                            break Label_0065;
                        }
                        case 8: {
                            this.timestamp_ = codedInputByteBufferNano.readInt64();
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 21: {
                            this.x_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 29: {
                            this.y_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 37: {
                            this.z_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                    }
                }
                return this;
            }

            public AccelerometerEvent setTimestamp(final long timestamp_) {
                this.bitField0_ |= 0x1;
                this.timestamp_ = timestamp_;
                return this;
            }

            public AccelerometerEvent setX(final float x_) {
                this.bitField0_ |= 0x2;
                this.x_ = x_;
                return this;
            }

            public AccelerometerEvent setY(final float y_) {
                this.bitField0_ |= 0x4;
                this.y_ = y_;
                return this;
            }

            public AccelerometerEvent setZ(final float z_) {
                this.bitField0_ |= 0x8;
                this.z_ = z_;
                return this;
            }

            @Override
            public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    codedOutputByteBufferNano.writeInt64(1, this.timestamp_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(2, this.x_);
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(3, this.y_);
                }
                if ((this.bitField0_ & 0x8) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(4, this.z_);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class DepthMapEvent extends ExtendableMessageNano<DepthMapEvent> {
            private static volatile DepthMapEvent[] _emptyArray;
            private int bitField0_;
            private int height_;
            private long timestamp_;
            private int width_;
            public float[] zDistances;

            public DepthMapEvent() {
                this.clear();
            }

            public static DepthMapEvent[] emptyArray() {
                if (DepthMapEvent._emptyArray != null) {
                    return DepthMapEvent._emptyArray;
                } else {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (DepthMapEvent._emptyArray == null) {
                            DepthMapEvent._emptyArray = new DepthMapEvent[0];
                        }
                        return DepthMapEvent._emptyArray;
                    }
                }
            }

            public static DepthMapEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new DepthMapEvent().mergeFrom(codedInputByteBufferNano);
            }

            public static DepthMapEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new DepthMapEvent(), array);
            }

            public DepthMapEvent clear() {
                this.bitField0_ = 0;
                this.timestamp_ = 0L;
                this.width_ = 0;
                this.height_ = 0;
                this.zDistances = WireFormatNano.EMPTY_FLOAT_ARRAY;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            public DepthMapEvent clearHeight() {
                this.height_ = 0;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            public DepthMapEvent clearTimestamp() {
                this.timestamp_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public DepthMapEvent clearWidth() {
                this.width_ = 0;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int computeSerializedSize;
                final int n = computeSerializedSize = super.computeSerializedSize();
                if ((this.bitField0_ & 0x1) != 0x0) {
                    computeSerializedSize = n + CodedOutputByteBufferNano.computeInt64Size(1, this.timestamp_);
                }
                int n2 = computeSerializedSize;
                if ((this.bitField0_ & 0x2) != 0x0) {
                    n2 = computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(2, this.width_);
                }
                int n3 = n2;
                if ((this.bitField0_ & 0x4) != 0x0) {
                    n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(3, this.height_);
                }
                int n4 = n3;
                if (this.zDistances != null) {
                    n4 = n3;
                    if (this.zDistances.length > 0) {
                        final int n5 = this.zDistances.length * 4;
                        n4 = n3 + n5 + 1 + CodedOutputByteBufferNano.computeRawVarint32Size(n5);
                    }
                }
                return n4;
            }

            public int getHeight() {
                return this.height_;
            }

            public long getTimestamp() {
                return this.timestamp_;
            }

            public int getWidth() {
                return this.width_;
            }

            public boolean hasHeight() {
                return (this.bitField0_ & 0x4) != 0x0;
            }

            public boolean hasTimestamp() {
                return (this.bitField0_ & 0x1) != 0x0;
            }

            public boolean hasWidth() {
                return (this.bitField0_ & 0x2) != 0x0;
            }

            @Override
            public DepthMapEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                Label_0073:
                while (true) {
                    final int tag = codedInputByteBufferNano.readTag();
                    switch (tag) {
                        default: {
                            if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                break Label_0073;
                            }
                            continue;
                        }
                        case 0: {
                            break Label_0073;
                        }
                        case 8: {
                            this.timestamp_ = codedInputByteBufferNano.readInt64();
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            this.width_ = codedInputByteBufferNano.readInt32();
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 24: {
                            this.height_ = codedInputByteBufferNano.readInt32();
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 37: {
                            final int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 37);
                            int length;
                            if (this.zDistances == null) {
                                length = 0;
                            }
                            else {
                                length = this.zDistances.length;
                            }
                            final float[] zDistances = new float[length + repeatedFieldArrayLength];
                            int i = length;
                            if (length != 0) {
                                System.arraycopy(this.zDistances, 0, zDistances, 0, length);
                                i = length;
                            }
                            while (i < zDistances.length - 1) {
                                zDistances[i] = codedInputByteBufferNano.readFloat();
                                codedInputByteBufferNano.readTag();
                                ++i;
                            }
                            zDistances[i] = codedInputByteBufferNano.readFloat();
                            this.zDistances = zDistances;
                            continue;
                        }
                        case 34: {
                            final int rawVarint32 = codedInputByteBufferNano.readRawVarint32();
                            final int pushLimit = codedInputByteBufferNano.pushLimit(rawVarint32);
                            final int n = rawVarint32 / 4;
                            int length2;
                            if (this.zDistances == null) {
                                length2 = 0;
                            }
                            else {
                                length2 = this.zDistances.length;
                            }
                            final float[] zDistances2 = new float[length2 + n];
                            int j = length2;
                            if (length2 != 0) {
                                System.arraycopy(this.zDistances, 0, zDistances2, 0, length2);
                                j = length2;
                            }
                            while (j < zDistances2.length) {
                                zDistances2[j] = codedInputByteBufferNano.readFloat();
                                ++j;
                            }
                            this.zDistances = zDistances2;
                            codedInputByteBufferNano.popLimit(pushLimit);
                            continue;
                        }
                    }
                }
                return this;
            }

            public DepthMapEvent setHeight(final int height_) {
                this.bitField0_ |= 0x4;
                this.height_ = height_;
                return this;
            }

            public DepthMapEvent setTimestamp(final long timestamp_) {
                this.bitField0_ |= 0x1;
                this.timestamp_ = timestamp_;
                return this;
            }

            public DepthMapEvent setWidth(final int width_) {
                this.bitField0_ |= 0x2;
                this.width_ = width_;
                return this;
            }

            @Override
            public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    codedOutputByteBufferNano.writeInt64(1, this.timestamp_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    codedOutputByteBufferNano.writeInt32(2, this.width_);
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    codedOutputByteBufferNano.writeInt32(3, this.height_);
                }
                if (this.zDistances != null && this.zDistances.length > 0) {
                    final int length = this.zDistances.length;
                    codedOutputByteBufferNano.writeRawVarint32(34);
                    codedOutputByteBufferNano.writeRawVarint32(length * 4);
                    for (int i = 0; i < this.zDistances.length; ++i) {
                        codedOutputByteBufferNano.writeFloatNoTag(this.zDistances[i]);
                    }
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class GyroscopeEvent extends ExtendableMessageNano<GyroscopeEvent> {
            private static volatile GyroscopeEvent[] _emptyArray;
            private int bitField0_;
            private long timestamp_;
            private float x_;
            private float y_;
            private float z_;

            public GyroscopeEvent() {
                this.clear();
            }

            public static GyroscopeEvent[] emptyArray() {
                if (GyroscopeEvent._emptyArray != null) {
                    return GyroscopeEvent._emptyArray;
                } else {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (GyroscopeEvent._emptyArray == null) {
                            GyroscopeEvent._emptyArray = new GyroscopeEvent[0];
                        }
                        return GyroscopeEvent._emptyArray;
                    }
                }
            }

            public static GyroscopeEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new GyroscopeEvent().mergeFrom(codedInputByteBufferNano);
            }

            public static GyroscopeEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new GyroscopeEvent(), array);
            }

            public GyroscopeEvent clear() {
                this.bitField0_ = 0;
                this.timestamp_ = 0L;
                this.x_ = 0.0f;
                this.y_ = 0.0f;
                this.z_ = 0.0f;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            public GyroscopeEvent clearTimestamp() {
                this.timestamp_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public GyroscopeEvent clearX() {
                this.x_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            public GyroscopeEvent clearY() {
                this.y_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            public GyroscopeEvent clearZ() {
                this.z_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int computeSerializedSize;
                final int n = computeSerializedSize = super.computeSerializedSize();
                if ((this.bitField0_ & 0x1) != 0x0) {
                    computeSerializedSize = n + CodedOutputByteBufferNano.computeInt64Size(1, this.timestamp_);
                }
                int n2 = computeSerializedSize;
                if ((this.bitField0_ & 0x2) != 0x0) {
                    n2 = computeSerializedSize + CodedOutputByteBufferNano.computeFloatSize(2, this.x_);
                }
                int n3 = n2;
                if ((this.bitField0_ & 0x4) != 0x0) {
                    n3 = n2 + CodedOutputByteBufferNano.computeFloatSize(3, this.y_);
                }
                int n4 = n3;
                if ((this.bitField0_ & 0x8) != 0x0) {
                    n4 = n3 + CodedOutputByteBufferNano.computeFloatSize(4, this.z_);
                }
                return n4;
            }

            public long getTimestamp() {
                return this.timestamp_;
            }

            public float getX() {
                return this.x_;
            }

            public float getY() {
                return this.y_;
            }

            public float getZ() {
                return this.z_;
            }

            public boolean hasTimestamp() {
                return (this.bitField0_ & 0x1) != 0x0;
            }

            public boolean hasX() {
                return (this.bitField0_ & 0x2) != 0x0;
            }

            public boolean hasY() {
                return (this.bitField0_ & 0x4) != 0x0;
            }

            public boolean hasZ() {
                return (this.bitField0_ & 0x8) != 0x0;
            }

            @Override
            public GyroscopeEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                Label_0065:
                while (true) {
                    final int tag = codedInputByteBufferNano.readTag();
                    switch (tag) {
                        default: {
                            if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                break Label_0065;
                            }
                            continue;
                        }
                        case 0: {
                            break Label_0065;
                        }
                        case 8: {
                            this.timestamp_ = codedInputByteBufferNano.readInt64();
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 21: {
                            this.x_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 29: {
                            this.y_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 37: {
                            this.z_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                    }
                }
                return this;
            }

            public GyroscopeEvent setTimestamp(final long timestamp_) {
                this.bitField0_ |= 0x1;
                this.timestamp_ = timestamp_;
                return this;
            }

            public GyroscopeEvent setX(final float x_) {
                this.bitField0_ |= 0x2;
                this.x_ = x_;
                return this;
            }

            public GyroscopeEvent setY(final float y_) {
                this.bitField0_ |= 0x4;
                this.y_ = y_;
                return this;
            }

            public GyroscopeEvent setZ(final float z_) {
                this.bitField0_ |= 0x8;
                this.z_ = z_;
                return this;
            }

            @Override
            public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    codedOutputByteBufferNano.writeInt64(1, this.timestamp_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(2, this.x_);
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(3, this.y_);
                }
                if ((this.bitField0_ & 0x8) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(4, this.z_);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class KeyEvent extends ExtendableMessageNano<KeyEvent> {
            private static volatile KeyEvent[] _emptyArray;
            private int action_;
            private int bitField0_;
            private int code_;

            public KeyEvent() {
                this.clear();
            }

            public static KeyEvent[] emptyArray() {
                if (KeyEvent._emptyArray != null) {
                    return KeyEvent._emptyArray;
                }
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (KeyEvent._emptyArray == null) {
                        KeyEvent._emptyArray = new KeyEvent[0];
                    }
                    return KeyEvent._emptyArray;
                }
            }

            public static KeyEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new KeyEvent().mergeFrom(codedInputByteBufferNano);
            }

            public static KeyEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new KeyEvent(), array);
            }

            public KeyEvent clear() {
                this.bitField0_ = 0;
                this.action_ = 0;
                this.code_ = 0;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            public KeyEvent clearAction() {
                this.action_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public KeyEvent clearCode() {
                this.code_ = 0;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int computeSerializedSize;
                final int n = computeSerializedSize = super.computeSerializedSize();
                if ((this.bitField0_ & 0x1) != 0x0) {
                    computeSerializedSize = n + CodedOutputByteBufferNano.computeInt32Size(1, this.action_);
                }
                int n2 = computeSerializedSize;
                if ((this.bitField0_ & 0x2) != 0x0) {
                    n2 = computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(2, this.code_);
                }
                return n2;
            }

            public int getAction() {
                return this.action_;
            }

            public int getCode() {
                return this.code_;
            }

            public boolean hasAction() {
                return (this.bitField0_ & 0x1) != 0x0;
            }

            public boolean hasCode() {
                return (this.bitField0_ & 0x2) != 0x0;
            }

            @Override
            public KeyEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                Label_0049:
                while (true) {
                    final int tag = codedInputByteBufferNano.readTag();
                    switch (tag) {
                        default: {
                            if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                break Label_0049;
                            }
                            continue;
                        }
                        case 0: {
                            break Label_0049;
                        }
                        case 8: {
                            this.action_ = codedInputByteBufferNano.readInt32();
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            this.code_ = codedInputByteBufferNano.readInt32();
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                    }
                }
                return this;
            }

            public KeyEvent setAction(final int action_) {
                this.bitField0_ |= 0x1;
                this.action_ = action_;
                return this;
            }

            public KeyEvent setCode(final int code_) {
                this.bitField0_ |= 0x2;
                this.code_ = code_;
                return this;
            }

            @Override
            public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    codedOutputByteBufferNano.writeInt32(1, this.action_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    codedOutputByteBufferNano.writeInt32(2, this.code_);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class MotionEvent extends ExtendableMessageNano<MotionEvent> {
            private static volatile MotionEvent[] _emptyArray;
            private int action_;
            private int bitField0_;
            public Pointer[] pointers;
            private long timestamp_;

            public MotionEvent() {
                this.clear();
            }

            public static MotionEvent[] emptyArray() {
                if (MotionEvent._emptyArray != null) {
                    return MotionEvent._emptyArray;
                } else {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (MotionEvent._emptyArray == null) {
                            MotionEvent._emptyArray = new MotionEvent[0];
                        }
                        return MotionEvent._emptyArray;
                    }
                }
            }

            public static MotionEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new MotionEvent().mergeFrom(codedInputByteBufferNano);
            }

            public static MotionEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new MotionEvent(), array);
            }

            public MotionEvent clear() {
                this.bitField0_ = 0;
                this.timestamp_ = 0L;
                this.action_ = 0;
                this.pointers = Pointer.emptyArray();
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            public MotionEvent clearAction() {
                this.action_ = 0;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            public MotionEvent clearTimestamp() {
                this.timestamp_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int computeSerializedSize;
                final int n = computeSerializedSize = super.computeSerializedSize();
                if ((this.bitField0_ & 0x1) != 0x0) {
                    computeSerializedSize = n + CodedOutputByteBufferNano.computeInt64Size(1, this.timestamp_);
                }
                int n2 = computeSerializedSize;
                if ((this.bitField0_ & 0x2) != 0x0) {
                    n2 = computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(2, this.action_);
                }
                int n3 = n2;
                if (this.pointers != null) {
                    n3 = n2;
                    if (this.pointers.length > 0) {
                        int n4 = 0;
                        while (true) {
                            n3 = n2;
                            if (n4 >= this.pointers.length) {
                                break;
                            }
                            final Pointer pointer = this.pointers[n4];
                            int n5 = n2;
                            if (pointer != null) {
                                n5 = n2 + CodedOutputByteBufferNano.computeMessageSize(3, pointer);
                            }
                            ++n4;
                            n2 = n5;
                        }
                    }
                }
                return n3;
            }

            public int getAction() {
                return this.action_;
            }

            public long getTimestamp() {
                return this.timestamp_;
            }

            public boolean hasAction() {
                return (this.bitField0_ & 0x2) != 0x0;
            }

            public boolean hasTimestamp() {
                return (this.bitField0_ & 0x1) != 0x0;
            }

            @Override
            public MotionEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                Label_0057:
                while (true) {
                    final int tag = codedInputByteBufferNano.readTag();
                    switch (tag) {
                        default: {
                            if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                break Label_0057;
                            }
                            continue;
                        }
                        case 0: {
                            break Label_0057;
                        }
                        case 8: {
                            this.timestamp_ = codedInputByteBufferNano.readInt64();
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            this.action_ = codedInputByteBufferNano.readInt32();
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 26: {
                            final int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                            int length;
                            if (this.pointers == null) {
                                length = 0;
                            }
                            else {
                                length = this.pointers.length;
                            }
                            final Pointer[] pointers = new Pointer[length + repeatedFieldArrayLength];
                            int i = length;
                            if (length != 0) {
                                System.arraycopy(this.pointers, 0, pointers, 0, length);
                                i = length;
                            }
                            while (i < pointers.length - 1) {
                                codedInputByteBufferNano.readMessage(pointers[i] = new Pointer());
                                codedInputByteBufferNano.readTag();
                                ++i;
                            }
                            codedInputByteBufferNano.readMessage(pointers[i] = new Pointer());
                            this.pointers = pointers;
                            continue;
                        }
                    }
                }
                return this;
            }

            public MotionEvent setAction(final int action_) {
                this.bitField0_ |= 0x2;
                this.action_ = action_;
                return this;
            }

            public MotionEvent setTimestamp(final long timestamp_) {
                this.bitField0_ |= 0x1;
                this.timestamp_ = timestamp_;
                return this;
            }

            @Override
            public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    codedOutputByteBufferNano.writeInt64(1, this.timestamp_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    codedOutputByteBufferNano.writeInt32(2, this.action_);
                }
                if (this.pointers != null && this.pointers.length > 0) {
                    for (int i = 0; i < this.pointers.length; ++i) {
                        final Pointer pointer = this.pointers[i];
                        if (pointer != null) {
                            codedOutputByteBufferNano.writeMessage(3, pointer);
                        }
                    }
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static final class Pointer extends ExtendableMessageNano<Pointer> {
                private static volatile Pointer[] _emptyArray;
                private int bitField0_;
                private int id_;
                private float normalizedX_;
                private float normalizedY_;

                public Pointer() {
                    this.clear();
                }

                public static Pointer[] emptyArray() {
                    if (Pointer._emptyArray != null) {
                        return Pointer._emptyArray;
                    } else {
                        synchronized (InternalNano.LAZY_INIT_LOCK) {
                            if (Pointer._emptyArray == null) {
                                Pointer._emptyArray = new Pointer[0];
                            }
                            return Pointer._emptyArray;
                        }
                    }
                }

                public static Pointer parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    return new Pointer().mergeFrom(codedInputByteBufferNano);
                }

                public static Pointer parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                    return MessageNano.mergeFrom(new Pointer(), array);
                }

                public Pointer clear() {
                    this.bitField0_ = 0;
                    this.id_ = 0;
                    this.normalizedX_ = 0.0f;
                    this.normalizedY_ = 0.0f;
                    this.unknownFieldData = null;
                    this.cachedSize = -1;
                    return this;
                }

                public Pointer clearId() {
                    this.id_ = 0;
                    this.bitField0_ &= 0xFFFFFFFE;
                    return this;
                }

                public Pointer clearNormalizedX() {
                    this.normalizedX_ = 0.0f;
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }

                public Pointer clearNormalizedY() {
                    this.normalizedY_ = 0.0f;
                    this.bitField0_ &= 0xFFFFFFFB;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int computeSerializedSize;
                    final int n = computeSerializedSize = super.computeSerializedSize();
                    if ((this.bitField0_ & 0x1) != 0x0) {
                        computeSerializedSize = n + CodedOutputByteBufferNano.computeInt32Size(1, this.id_);
                    }
                    int n2 = computeSerializedSize;
                    if ((this.bitField0_ & 0x2) != 0x0) {
                        n2 = computeSerializedSize + CodedOutputByteBufferNano.computeFloatSize(2, this.normalizedX_);
                    }
                    int n3 = n2;
                    if ((this.bitField0_ & 0x4) != 0x0) {
                        n3 = n2 + CodedOutputByteBufferNano.computeFloatSize(3, this.normalizedY_);
                    }
                    return n3;
                }

                public int getId() {
                    return this.id_;
                }

                public float getNormalizedX() {
                    return this.normalizedX_;
                }

                public float getNormalizedY() {
                    return this.normalizedY_;
                }

                public boolean hasId() {
                    return (this.bitField0_ & 0x1) != 0x0;
                }

                public boolean hasNormalizedX() {
                    return (this.bitField0_ & 0x2) != 0x0;
                }

                public boolean hasNormalizedY() {
                    return (this.bitField0_ & 0x4) != 0x0;
                }

                @Override
                public Pointer mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    Label_0057:
                    while (true) {
                        final int tag = codedInputByteBufferNano.readTag();
                        switch (tag) {
                            default: {
                                if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                    break Label_0057;
                                }
                                continue;
                            }
                            case 0: {
                                break Label_0057;
                            }
                            case 8: {
                                this.id_ = codedInputByteBufferNano.readInt32();
                                this.bitField0_ |= 0x1;
                                continue;
                            }
                            case 21: {
                                this.normalizedX_ = codedInputByteBufferNano.readFloat();
                                this.bitField0_ |= 0x2;
                                continue;
                            }
                            case 29: {
                                this.normalizedY_ = codedInputByteBufferNano.readFloat();
                                this.bitField0_ |= 0x4;
                                continue;
                            }
                        }
                    }
                    return this;
                }

                public Pointer setId(final int id_) {
                    this.bitField0_ |= 0x1;
                    this.id_ = id_;
                    return this;
                }

                public Pointer setNormalizedX(final float normalizedX_) {
                    this.bitField0_ |= 0x2;
                    this.normalizedX_ = normalizedX_;
                    return this;
                }

                public Pointer setNormalizedY(final float normalizedY_) {
                    this.bitField0_ |= 0x4;
                    this.normalizedY_ = normalizedY_;
                    return this;
                }

                @Override
                public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    if ((this.bitField0_ & 0x1) != 0x0) {
                        codedOutputByteBufferNano.writeInt32(1, this.id_);
                    }
                    if ((this.bitField0_ & 0x2) != 0x0) {
                        codedOutputByteBufferNano.writeFloat(2, this.normalizedX_);
                    }
                    if ((this.bitField0_ & 0x4) != 0x0) {
                        codedOutputByteBufferNano.writeFloat(3, this.normalizedY_);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }
            }
        }

        public static final class OrientationEvent extends ExtendableMessageNano<OrientationEvent> {
            private static volatile OrientationEvent[] _emptyArray;
            private int bitField0_;
            private long timestamp_;
            private float w_;
            private float x_;
            private float y_;
            private float z_;

            public OrientationEvent() {
                this.clear();
            }

            public static OrientationEvent[] emptyArray() {
                if (OrientationEvent._emptyArray != null) {
                    return OrientationEvent._emptyArray;
                } else {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (OrientationEvent._emptyArray == null) {
                            OrientationEvent._emptyArray = new OrientationEvent[0];
                        }
                        return OrientationEvent._emptyArray;
                    }
                }
            }

            public static OrientationEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new OrientationEvent().mergeFrom(codedInputByteBufferNano);
            }

            public static OrientationEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new OrientationEvent(), array);
            }

            public OrientationEvent clear() {
                this.bitField0_ = 0;
                this.timestamp_ = 0L;
                this.x_ = 0.0f;
                this.y_ = 0.0f;
                this.z_ = 0.0f;
                this.w_ = 0.0f;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            public OrientationEvent clearTimestamp() {
                this.timestamp_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public OrientationEvent clearW() {
                this.w_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFEF;
                return this;
            }

            public OrientationEvent clearX() {
                this.x_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            public OrientationEvent clearY() {
                this.y_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            public OrientationEvent clearZ() {
                this.z_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int computeSerializedSize;
                final int n = computeSerializedSize = super.computeSerializedSize();
                if ((this.bitField0_ & 0x1) != 0x0) {
                    computeSerializedSize = n + CodedOutputByteBufferNano.computeInt64Size(1, this.timestamp_);
                }
                int n2 = computeSerializedSize;
                if ((this.bitField0_ & 0x2) != 0x0) {
                    n2 = computeSerializedSize + CodedOutputByteBufferNano.computeFloatSize(2, this.x_);
                }
                int n3 = n2;
                if ((this.bitField0_ & 0x4) != 0x0) {
                    n3 = n2 + CodedOutputByteBufferNano.computeFloatSize(3, this.y_);
                }
                int n4 = n3;
                if ((this.bitField0_ & 0x8) != 0x0) {
                    n4 = n3 + CodedOutputByteBufferNano.computeFloatSize(4, this.z_);
                }
                int n5 = n4;
                if ((this.bitField0_ & 0x10) != 0x0) {
                    n5 = n4 + CodedOutputByteBufferNano.computeFloatSize(5, this.w_);
                }
                return n5;
            }

            public long getTimestamp() {
                return this.timestamp_;
            }

            public float getW() {
                return this.w_;
            }

            public float getX() {
                return this.x_;
            }

            public float getY() {
                return this.y_;
            }

            public float getZ() {
                return this.z_;
            }

            public boolean hasTimestamp() {
                return (this.bitField0_ & 0x1) != 0x0;
            }

            public boolean hasW() {
                return (this.bitField0_ & 0x10) != 0x0;
            }

            public boolean hasX() {
                return (this.bitField0_ & 0x2) != 0x0;
            }

            public boolean hasY() {
                return (this.bitField0_ & 0x4) != 0x0;
            }

            public boolean hasZ() {
                return (this.bitField0_ & 0x8) != 0x0;
            }

            @Override
            public OrientationEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                Label_0073:
                while (true) {
                    final int tag = codedInputByteBufferNano.readTag();
                    switch (tag) {
                        default: {
                            if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                break Label_0073;
                            }
                            continue;
                        }
                        case 0: {
                            break Label_0073;
                        }
                        case 8: {
                            this.timestamp_ = codedInputByteBufferNano.readInt64();
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 21: {
                            this.x_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 29: {
                            this.y_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 37: {
                            this.z_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                        case 45: {
                            this.w_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x10;
                            continue;
                        }
                    }
                }
                return this;
            }

            public OrientationEvent setTimestamp(final long timestamp_) {
                this.bitField0_ |= 0x1;
                this.timestamp_ = timestamp_;
                return this;
            }

            public OrientationEvent setW(final float w_) {
                this.bitField0_ |= 0x10;
                this.w_ = w_;
                return this;
            }

            public OrientationEvent setX(final float x_) {
                this.bitField0_ |= 0x2;
                this.x_ = x_;
                return this;
            }

            public OrientationEvent setY(final float y_) {
                this.bitField0_ |= 0x4;
                this.y_ = y_;
                return this;
            }

            public OrientationEvent setZ(final float z_) {
                this.bitField0_ |= 0x8;
                this.z_ = z_;
                return this;
            }

            @Override
            public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    codedOutputByteBufferNano.writeInt64(1, this.timestamp_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(2, this.x_);
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(3, this.y_);
                }
                if ((this.bitField0_ & 0x8) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(4, this.z_);
                }
                if ((this.bitField0_ & 0x10) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(5, this.w_);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class PositionEvent extends ExtendableMessageNano<PositionEvent> {
            private static volatile PositionEvent[] _emptyArray;
            private int bitField0_;
            private long timestamp_;
            private float x_;
            private float y_;
            private float z_;

            public PositionEvent() {
                this.clear();
            }

            public static PositionEvent[] emptyArray() {
                if (PositionEvent._emptyArray != null) {
                    return PositionEvent._emptyArray;
                } else {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (PositionEvent._emptyArray == null) {
                            PositionEvent._emptyArray = new PositionEvent[0];
                        }
                        return PositionEvent._emptyArray;
                    }
                }
            }

            public static PositionEvent parseFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new PositionEvent().mergeFrom(codedInputByteBufferNano);
            }

            public static PositionEvent parseFrom(final byte[] array) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new PositionEvent(), array);
            }

            public PositionEvent clear() {
                this.bitField0_ = 0;
                this.timestamp_ = 0L;
                this.x_ = 0.0f;
                this.y_ = 0.0f;
                this.z_ = 0.0f;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            public PositionEvent clearTimestamp() {
                this.timestamp_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public PositionEvent clearX() {
                this.x_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            public PositionEvent clearY() {
                this.y_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            public PositionEvent clearZ() {
                this.z_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int computeSerializedSize;
                final int n = computeSerializedSize = super.computeSerializedSize();
                if ((this.bitField0_ & 0x1) != 0x0) {
                    computeSerializedSize = n + CodedOutputByteBufferNano.computeInt64Size(1, this.timestamp_);
                }
                int n2 = computeSerializedSize;
                if ((this.bitField0_ & 0x2) != 0x0) {
                    n2 = computeSerializedSize + CodedOutputByteBufferNano.computeFloatSize(2, this.x_);
                }
                int n3 = n2;
                if ((this.bitField0_ & 0x4) != 0x0) {
                    n3 = n2 + CodedOutputByteBufferNano.computeFloatSize(3, this.y_);
                }
                int n4 = n3;
                if ((this.bitField0_ & 0x8) != 0x0) {
                    n4 = n3 + CodedOutputByteBufferNano.computeFloatSize(4, this.z_);
                }
                return n4;
            }

            public long getTimestamp() {
                return this.timestamp_;
            }

            public float getX() {
                return this.x_;
            }

            public float getY() {
                return this.y_;
            }

            public float getZ() {
                return this.z_;
            }

            public boolean hasTimestamp() {
                return (this.bitField0_ & 0x1) != 0x0;
            }

            public boolean hasX() {
                return (this.bitField0_ & 0x2) != 0x0;
            }

            public boolean hasY() {
                return (this.bitField0_ & 0x4) != 0x0;
            }

            public boolean hasZ() {
                return (this.bitField0_ & 0x8) != 0x0;
            }

            @Override
            public PositionEvent mergeFrom(final CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                Label_0065:
                while (true) {
                    final int tag = codedInputByteBufferNano.readTag();
                    switch (tag) {
                        default: {
                            if (!super.storeUnknownField(codedInputByteBufferNano, tag)) {
                                break Label_0065;
                            }
                            continue;
                        }
                        case 0: {
                            break Label_0065;
                        }
                        case 8: {
                            this.timestamp_ = codedInputByteBufferNano.readInt64();
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 21: {
                            this.x_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 29: {
                            this.y_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 37: {
                            this.z_ = codedInputByteBufferNano.readFloat();
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                    }
                }
                return this;
            }

            public PositionEvent setTimestamp(final long timestamp_) {
                this.bitField0_ |= 0x1;
                this.timestamp_ = timestamp_;
                return this;
            }

            public PositionEvent setX(final float x_) {
                this.bitField0_ |= 0x2;
                this.x_ = x_;
                return this;
            }

            public PositionEvent setY(final float y_) {
                this.bitField0_ |= 0x4;
                this.y_ = y_;
                return this;
            }

            public PositionEvent setZ(final float z_) {
                this.bitField0_ |= 0x8;
                this.z_ = z_;
                return this;
            }

            @Override
            public void writeTo(final CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    codedOutputByteBufferNano.writeInt64(1, this.timestamp_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(2, this.x_);
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(3, this.y_);
                }
                if ((this.bitField0_ & 0x8) != 0x0) {
                    codedOutputByteBufferNano.writeFloat(4, this.z_);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public interface Type {
            public static final int ACCELEROMETER = 3;
            public static final int DEPTH_MAP = 4;
            public static final int GYROSCOPE = 2;
            public static final int KEY = 6;
            public static final int MOTION = 1;
            public static final int ORIENTATION = 5;
            public static final int POSITION = 7;
        }
    }
}
