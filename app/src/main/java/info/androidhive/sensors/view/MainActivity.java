package info.androidhive.sensors.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import info.androidhive.sensors.R;
import info.androidhive.sensors.controller.AccelerometerController;
import info.androidhive.sensors.controller.GyroscopeController;
import info.androidhive.sensors.controller.LightController;
import info.androidhive.sensors.controller.ProximityController;
import info.androidhive.sensors.controller.Tuple;

/**
 * Clase controladora de la ventana de inicio de sesión.
 * @author Martín Gascón
 * @author Eduardo Ruiz
 * @author Daniel Huici
 * @version 1.0
 */
public class MainActivity extends Activity implements Observer {

    private TextView lightValue;
    private TextView accelerometerValue;
    private TextView gyroscopeValue;
    private TextView proximityValue;

    private GyroscopeController gyroscope;
    private ProximityController proximity;
    private LightController light;
    private AccelerometerController accelerometer;

    private Button button;
    private ArrayList<String> valueList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueList = new ArrayList<>();

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

         button = (Button) findViewById(R.id.button);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListActivity.class);
                intent.putExtra("list", valueList);
                startActivity(intent);
             }
         });
    }

    @Override
    public void update(Observable observable, Object o) {
        Tuple<String, Object> tuple = (Tuple<String, Object>) o;
        switch (tuple.a){
            case AccelerometerController.VALUE:
                accelerometerValue.setText((String) tuple.b);
                break;
            case GyroscopeController.VALUE:
                gyroscopeValue.setText((String) tuple.b);
                break;

            case LightController.VALUE:
                lightValue.setText((String) tuple.b);
                valueList.add((String) tuple.b);
                break;

            case ProximityController.VALUE:
                proximityValue.setText((String) tuple.b);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
