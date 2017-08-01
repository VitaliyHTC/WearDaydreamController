package com.boost.weardaydreamcontroller.inputcompanion.fogale;

import android.graphics.Point;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class HoverHeatmapProvider {
    private static final String HEATMAP_FILE_PATH = "/dev/fs_heatmap";
    private static final String PROPERTIES_FILE_PATH = "/dev/fs_mt_prop";
    private static final float SENSOR_MIN_VALUE = 16384.0f;
    private static final float SENSOR_VALUES_RANGE = 16384.0f;
    private static final String TAG = HoverHeatmapProvider.class.getSimpleName();
    private final File heatmapFile;
    private final Point heatmapSize;

    private HoverHeatmapProvider() {
        this.heatmapFile = new File(HEATMAP_FILE_PATH);
        this.heatmapSize = readHeatmapSize();
    }

    public static HoverHeatmapProvider create() {
        final HoverHeatmapProvider hoverHeatmapProvider = new HoverHeatmapProvider();
        if (hoverHeatmapProvider.getWidth() > 0) {
            final HoverHeatmapProvider hoverHeatmapProvider2 = hoverHeatmapProvider;
            if (hoverHeatmapProvider.getHeight() > 0) {
                return hoverHeatmapProvider2;
            }
        }
        return null;
    }

    private static Point readHeatmapSize() {
        final File file = new File(PROPERTIES_FILE_PATH);
        final Point point = new Point(0, 0);
        if (!file.exists()) {
            return point;
        }
        String s = "";
        String s2 = "";
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                final String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                final String[] split = line.split(" : ");
                if (split[0].contentEquals("fs.sdp.NbElectrodes.nx")) {
                    s = split[1];
                }
                else {
                    if (!split[0].contentEquals("fs.sdp.NbElectrodes.ny")) {
                        continue;
                    }
                    s2 = split[1];
                }
            }
            bufferedReader.close();
            point.x = Integer.parseInt(s);
            point.y = Integer.parseInt(s2);
            return point;
        }
        catch (IOException ex) {
            final String tag = HoverHeatmapProvider.TAG;
            final String value = String.valueOf(ex);
            Log.e(tag, new StringBuilder(String.valueOf(value).length() + 27).append("Reading properties failed: ").append(value).toString());
            return point;
        }
    }

    public int getHeight() {
        return this.heatmapSize.y;
    }

    public float[] getLatestHeatmap() {
        byte[] var2 = new byte[(int)this.heatmapFile.length()];

        try {
            DataInputStream var3 = new DataInputStream(new FileInputStream(this.heatmapFile));
            var3.readFully(var2);
            var3.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        short[] var7 = new short[var2.length / 2];
        ByteBuffer.wrap(var2).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(var7);
        float[] var6 = new float[var7.length];

        for(int var1 = 0; var1 < var6.length; ++var1) {
            if(var7[var1] == -1 * (SENSOR_MIN_VALUE + SENSOR_VALUES_RANGE)) {
                var6[var1] = 1.0F;
            } else {
                var6[var1] = ((float)var7[var1] - SENSOR_MIN_VALUE) / SENSOR_VALUES_RANGE;
            }
        }

        return var6;
    }

    public int getWidth() {
        return this.heatmapSize.x;
    }
}
