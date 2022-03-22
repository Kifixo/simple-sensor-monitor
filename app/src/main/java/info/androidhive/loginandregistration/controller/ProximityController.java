package info.androidhive.loginandregistration.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Observable;

public class ProximityController extends Observable implements SensorEventListener {

    public static final String VALUE = "PROXIMITY_VALUE";
    private static final String TAG = "ProximityController";
    private SensorManager sensorManager;
    private Sensor proximitySensor;

    public ProximityController(SensorManager sm) {
        this.sensorManager = sm;

        proximitySensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(proximitySensor == null) {
            Log.e(TAG, "Proximity sensor not available.");
        }

        sensorManager.registerListener(this,
                proximitySensor, 2 * 1000 * 1000);

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
