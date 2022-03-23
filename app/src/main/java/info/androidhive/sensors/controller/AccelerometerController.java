package info.androidhive.sensors.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Observable;

public class AccelerometerController extends Observable implements SensorEventListener {

    public static final String VALUE = "ACCELEROMETER_VALUE";
    private static final String TAG = "AccelerometerController";
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    public AccelerometerController(SensorManager sm) {
        this.sensorManager = sm;
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometerSensor == null) {
            Log.e(TAG, "Gyroscope sensor not available.");
        }
        sensorManager.registerListener(this,
                accelerometerSensor, 2 * 1000 * 1000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String values = "";
        for (float v : sensorEvent.values) {
            values += v + ", ";
        }

        Log.e(TAG, values);

        setChanged();
        notifyObservers(new Tupla<>(VALUE, values));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
