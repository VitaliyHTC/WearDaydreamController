package com.boost.weardaydreamcontroller;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.boost.weardaydreamcontroller.inputcompanion.BaseSocketServer;
import com.boost.weardaydreamcontroller.inputcompanion.BluetoothSocketServer;
import com.boost.weardaydreamcontroller.inputcompanion.ClientConnection;
import com.boost.weardaydreamcontroller.inputcompanion.InputCompanionServer;
import com.boost.weardaydreamcontroller.inputcompanion.TouchpadView;
import com.boost.weardaydreamcontroller.inputcompanion.components.HoverComponent;
import com.boost.weardaydreamcontroller.inputcompanion.components.InputComponent;
import com.boost.weardaydreamcontroller.inputcompanion.components.OrientationComponent;
import com.boost.weardaydreamcontroller.inputcompanion.components.SensorsComponent;
import com.boost.weardaydreamcontroller.inputcompanion.components.TouchComponent;
import com.google.common.collect.Lists;
import com.google.vr.inputcompanion.SensorFusionJni;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private static final int SOCKET_PORT = 7003;
    private static final String TAG;
    private static final String INTENT_ENABLE_BLUETOOTH_REQUEST = "android.bluetooth.adapter.action.REQUEST_ENABLE";
    private static final int INTENT_ENABLE_BLUETOOTH_REQUEST_CODE = 9999;

    private BluetoothSocketServer bluetoothSocketServer;
    private Handler handler;

    private TextView mTextView;

    static {
        TAG = MainActivity.class.getSimpleName();
        //System.loadLibrary("controller_jni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.handler = new Handler();
        mTextView = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onPause() {
        this.stopCapturingTouchAndSensors();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.startCapturingTouchAndSensors(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null && !defaultAdapter.isEnabled()) {
            new AlertDialog.Builder(this)
                    .setMessage(this.getString(R.string.bluetooth_off_message))
                    .setPositiveButton(
                            this.getString(R.string.bluetooth_off_message_positive_button),
                            new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialogInterface, final int n) {
                                    try {
                                        MainActivity.this.startActivityForResult(
                                                new Intent(INTENT_ENABLE_BLUETOOTH_REQUEST), INTENT_ENABLE_BLUETOOTH_REQUEST_CODE);
                                    } catch (ActivityNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            })
                    .setNegativeButton(this.getString(R.string.bluetooth_off_message_negative_button), null).create().show();
        }
    }


    private void startCapturingTouchAndSensors(final boolean booleanFlag) {
        if (mTextView != null) {
            mTextView.setText(this.getString(R.string.status_waiting_for_connection));
            mTextView.setBackgroundResource(R.drawable.disconnected_status_background);
        }
        final BaseSocketServer.ClientConnectionCallback clientConnectionCallback = new BaseSocketServer.ClientConnectionCallback() {
            private ClientConnection existingConnection;
            //final /* synthetic */ View valappButton = findViewById(R.id.app_button);
            //final /* synthetic */ View valhomeButton = findViewById(R.id.home_button);
            final /* synthetic */ InputCompanionServer.Listener val$inputCompanionListener = new InputCompanionServer.Listener() {
                @Override
                public void onInputCompanionServerFinished() {
                    if (mTextView != null) {
                        mTextView.setText(MainActivity.this.getString(R.string.status_disconnected));
                        mTextView.setBackgroundResource(R.drawable.disconnected_status_background);
                    }
                }
            };

            @Override
            public void onConnect(final ClientConnection existingConnection) {
                if (this.existingConnection != null && this.existingConnection.isOpen()) {
                    Log.e(MainActivity.TAG, "Cannot process 2nd client while 1st is still connected. Closing 2nd client.");
                    existingConnection.close();
                    return;
                }
                this.existingConnection = existingConnection;
                MainActivity.this.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (existingConnection.isBluetooth()) {
                            if (mTextView != null)
                                mTextView.setText(R.string.status_connected_bluetooth);
                        } else {
                            if (mTextView != null)
                                mTextView.setText(R.string.status_connected_network);
                        }
                        if (mTextView != null)
                            mTextView.setBackgroundResource(R.drawable.connected_status_background);
                    }
                });
                ArrayList<? extends InputComponent> list;
                // TODO: add input components here!
                list = new ArrayList<>();

                final SensorsComponent sensorsComponent =
                        new SensorsComponent(
                                existingConnection,
                                (SensorManager) MainActivity.this.getSystemService(Context.SENSOR_SERVICE)
                        );
                final OrientationComponent orientationComponent =
                        new OrientationComponent(
                                existingConnection,
                                MainActivity.this,
                                new SensorFusionJni(SensorFusionJni.SensorFusionAlgorithm.EKF_WITH_BIAS_ESTIMATOR)
                        );
                final TouchpadView touchpadView = (TouchpadView) MainActivity.this.findViewById(R.id.touchpad_view);
                Vibrator vibrator;
                if (booleanFlag) {
                    vibrator = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                } else {
                    vibrator = null;
                }
                list = Lists.newArrayList(
                        sensorsComponent,
                        orientationComponent,
                        new TouchComponent(existingConnection, MainActivity.this, touchpadView, vibrator),
                        new HoverComponent(existingConnection)
                        //new ButtonsComponent(existingConnection, MainActivity.this, this.valappButton, this.valhomeButton)
                );


                new InputCompanionServer(existingConnection, list, this.val$inputCompanionListener);
            }

            @Override
            public void onServerStopped() {
                if (this.existingConnection != null) {
                    this.existingConnection.close();
                    this.existingConnection = null;
                }
            }
        };
        this.bluetoothSocketServer = new BluetoothSocketServer(clientConnectionCallback);
    }

    private void stopCapturingTouchAndSensors() {
        if (this.bluetoothSocketServer != null) {
            this.bluetoothSocketServer.stop();
            this.bluetoothSocketServer = null;
        }
        //final TextView textView = (TextView) this.findViewById(R.id.status_text);
        if (mTextView != null) {
            mTextView.setText(this.getString(R.string.status_disconnected));
            mTextView.setBackgroundResource(R.drawable.disconnected_status_background);
        }
    }

}
