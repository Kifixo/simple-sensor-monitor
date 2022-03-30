package info.androidhive.sensors.view;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import info.androidhive.sensors.R;
import info.androidhive.sensors.controller.AccelerometerController;
import info.androidhive.sensors.controller.GyroscopeController;
import info.androidhive.sensors.controller.LightController;
import info.androidhive.sensors.controller.ProximityController;
import info.androidhive.sensors.controller.RotationController;
import info.androidhive.sensors.controller.Tupla;


public class MainActivity extends Activity  implements Observer {

    private TextView lightValue;
    private TextView accelerometerValue;
    private TextView gyroscopeValue;
    private TextView proximityValue;
    private TextView temperatureValue;

    private GyroscopeController gyroscope;
    private ProximityController proximity;
    private LightController light;
    private AccelerometerController accelerometer;
    private RotationController temperature;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightValue = findViewById(R.id.lightValue);
        accelerometerValue = findViewById(R.id.accelerometerValue);
        gyroscopeValue = findViewById(R.id.gyroscopeValue);
        proximityValue = findViewById(R.id.proximityValue);
        temperatureValue = findViewById(R.id.rotationValue);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        gyroscope = new GyroscopeController(sensorManager);
        gyroscope.addObserver(this);
        proximity = new ProximityController(sensorManager);
        proximity.addObserver(this);
        light = new LightController(sensorManager);
        light.addObserver(this);
        accelerometer = new AccelerometerController(sensorManager);
        accelerometer.addObserver(this);
        temperature = new RotationController(sensorManager);
        temperature.addObserver(this);

    }

 


    @Override
    public void update(Observable observable, Object o) {
        Tupla<String, Object> tupla = (Tupla<String, Object>) o;
        switch (tupla.a){
            case AccelerometerController.VALUE:
                accelerometerValue.setText((String) tupla.b);
                break;
            case GyroscopeController.VALUE:
                gyroscopeValue.setText((String) tupla.b);
                break;

            case LightController.VALUE:
                lightValue.setText((String) tupla.b);
                break;

            case ProximityController.VALUE:
                proximityValue.setText((String) tupla.b);
                break;

            case RotationController.VALUE:
                temperatureValue.setText((String) tupla.b);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
