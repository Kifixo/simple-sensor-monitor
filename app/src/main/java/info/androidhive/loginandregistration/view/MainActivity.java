package info.androidhive.loginandregistration.view;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.controller.AccelerometerController;
import info.androidhive.loginandregistration.controller.GyroscopeController;
import info.androidhive.loginandregistration.controller.LightController;
import info.androidhive.loginandregistration.controller.ProximityController;
import info.androidhive.loginandregistration.controller.Tupla;

/**
 * Clase controladora de la ventana de inicio de sesión.
 * @author Martín Gascón
 * @author Eduardo Ruiz
 * @author Daniel Huici
 * @version 1.0
 */
public class MainActivity extends Activity  implements Observer {

    private TextView lightValue;
    private TextView accelerometerValue;
    private TextView gyroscopeValue;
    private TextView proximityValue;

    private GyroscopeController gyroscope;
    private ProximityController proximity;
    private LightController light;
    private AccelerometerController accelerometer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightValue = findViewById(R.id.lightValue);
        accelerometerValue = findViewById(R.id.accelerometerValue);
        gyroscopeValue = findViewById(R.id.gyroscopeValue);
        proximityValue = findViewById(R.id.proximityValue);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        gyroscope = new GyroscopeController(sensorManager);
        gyroscope.addObserver(this);
        proximity = new ProximityController(sensorManager);
        proximity.addObserver(this);
        light = new LightController(sensorManager);
        light.addObserver(this);
        accelerometer = new AccelerometerController(sensorManager);
        accelerometer.addObserver(this);

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

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
