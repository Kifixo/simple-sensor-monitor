package info.androidhive.sensors.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Observable;

public class RotationController extends Observable implements SensorEventListener {

    public static final String VALUE = "ROTATION_VALUE";
    private static final String TAG = "RotationController";
    private SensorManager sensorManager;
    private Sensor rotationSensor;

    public RotationController(SensorManager sm) {
        this.sensorManager = sm;
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        if(rotationSensor == null) {
            Log.e(TAG, "Gyroscope sensor not available.");
        }
        sensorManager.registerListener(this,
                rotationSensor, 2 * 1000 * 1000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String values = "";
        for (float v : sensorEvent.values) {
            values += v + ", ";
        }

        Log.e(TAG, values);

        setChanged();
        notifyObservers(new Tuple<>(VALUE, values));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
