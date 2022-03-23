package info.androidhive.sensors.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Observable;

public class LightController extends Observable implements SensorEventListener {

    public static final String VALUE = "LIGHT_VALUE";
    private static final String TAG = "LightController";
    private SensorManager sensorManager;
    private Sensor lightSensor;

    public LightController(SensorManager sm) {
        this.sensorManager = sm;
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor == null) {
            Log.e(TAG, "Gyroscope sensor not available.");
        }
        sensorManager.registerListener(this,
                lightSensor, 2 * 1000 * 1000);
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
