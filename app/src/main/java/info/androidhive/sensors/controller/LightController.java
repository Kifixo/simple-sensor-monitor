package info.androidhive.sensors.controller;

import static java.lang.Math.round;

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.OptionalDouble;

public class LightController extends Observable implements SensorEventListener {

    public static final String VALUE = "LIGHT_VALUE";
    private static final String TAG = "LightController";
    public static final Integer MIN_VALUE_POSITION = 0;
    public static final Integer MAX_VALUE_POSITION = 1;
    public static final Integer AVG_VALUE_POSITION = 2;
    public static final Integer CURR_VALUE_POSITION = 3;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private List<Double> statValues;
    private List<Double> historyValues;


    public LightController(SensorManager sm) {
        this.sensorManager = sm;
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor == null) {
            Log.e(TAG, "Gyroscope sensor not available.");
        }
        sensorManager.registerListener(this,
                lightSensor, 2 * 1000 * 1000);
        historyValues = new ArrayList<>();
        statValues = new ArrayList<>();
        statValues.add(100d);
        statValues.add(0d);
        statValues.add(0d);
        statValues.add(0d);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double lightValue = sensorEvent.values[0];
        historyValues.add(lightValue);
        getMaxValue(lightValue);
        getMinValue(lightValue);
        getAvgValue(lightValue);
        statValues.set(CURR_VALUE_POSITION, lightValue);
        setChanged();
        notifyObservers(new Tuple<>(VALUE, statValues));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void getMaxValue(double currentValue)  {
        if (currentValue > statValues.get(MAX_VALUE_POSITION)) {
            statValues.set(MAX_VALUE_POSITION, Math.round(currentValue*100.0)/100.0);
        }
    }

    private void getMinValue(double currentValue)  {
        if (currentValue < statValues.get(MIN_VALUE_POSITION)) {
            statValues.set(MIN_VALUE_POSITION, Math.round(currentValue*100.0)/100.0);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void getAvgValue(double currentValue)  {
        if (currentValue > statValues.get(AVG_VALUE_POSITION)) {
            OptionalDouble average = historyValues.stream().mapToDouble(a->a).average();
            if (average.isPresent()) {
                double truncate = Math.round(average.getAsDouble()*100.0)/100.0;
                statValues.set(AVG_VALUE_POSITION, truncate);
            }

        }
    }
}
