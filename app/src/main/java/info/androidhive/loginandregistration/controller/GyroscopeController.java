package info.androidhive.loginandregistration.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;


public class GyroscopeController extends Observable implements SensorEventListener, Observer {

    public static final String VALUE = "GYROSCOPE_VALUE";
    private static final String TAG = "GyroscopeController";
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    public GyroscopeController(SensorManager sm) {
        this.sensorManager = sm;
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscopeSensor == null) {
            Log.e(TAG, "Gyroscope sensor not available.");
        }
        sensorManager.registerListener(this,
                gyroscopeSensor, 2 * 1000 * 1000);
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


    @Override
    public void update(Observable observable, Object o) {

    }
}
