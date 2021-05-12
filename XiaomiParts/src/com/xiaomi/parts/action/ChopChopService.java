package com.xiaomi.parts.action;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.os.IBinder;
import android.os.Vibrator;

public class ChopChopService extends Service implements SensorEventListener {

    public final int MIN_TIME_BETWEEN_SHAKES = 1000;
    SensorManager sensorManager = null;

    Vibrator vibrator = null;
    private long lastShakeTime = 0;
    private boolean init;

    private float x1, x2, x3;
    private static final float ERROR = (float) 12.0;
    private boolean isFlashLightOn = false;

    private Float shakeThreshold = 12.0f;

    public ChopChopService(){}

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    Utility utility;

    @Override
    public void onCreate()
    {
        super.onCreate();
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        utility = new Utility(this);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        if(sensorManager!=null)
        {
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            long curTime = System.currentTimeMillis();

            if((curTime - lastShakeTime)>MIN_TIME_BETWEEN_SHAKES)
            {
              float x = sensorEvent.values[0];
              float y = sensorEvent.values[1];
              float z = sensorEvent.values[2];

            if (!init) {
              x1 = x;
              x2 = y;
              x3 = z;
              init = true;
            } else {

              float diffX = Math.abs(x1 - x);
              float diffY = Math.abs(x2 - y);
              float diffZ = Math.abs(x3 - z);

              //Handling ACCELEROMETER Noise
              if (diffX < ERROR) {
                diffX = (float) 0.0;
              }
              if (diffY < ERROR) {
                diffY = (float) 0.0;
              }
              if (diffZ < ERROR) {
                diffZ = (float) 0.0;
             }

            x1 = x;
            x2 = y;
            x3 = z;

            double acceleration = Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2))-SensorManager.GRAVITY_EARTH;

            if (acceleration > shakeThreshold) {
            //Horizontal Shake Detected!
            if (diffX > diffY) {

                lastShakeTime = curTime;
                    if (!isFlashLightOn)
                    {
                        try {
                            isFlashLightOn = utility.torchToggle("on", this);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }else
                        {
                            try {
                                isFlashLightOn = utility.torchToggle("off", this);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }
		   }
                }

            }
        }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
