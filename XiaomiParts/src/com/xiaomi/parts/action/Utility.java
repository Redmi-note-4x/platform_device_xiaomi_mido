package com.xiaomi.parts.action;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Vibrator;

public class Utility
{
    Context context;
    public boolean isSwitchedOn = false;

    public Utility(Context context)
    {
        this.context = context;
    }

    public boolean torchToggle(String str,Context context) throws CameraAccessException {

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null;


            if(cameraManager!=null)
            {
                cameraId = cameraManager.getCameraIdList()[0];
            }

            if(cameraManager!=null)
            {
                if(str.equals("on"))
                {
                    cameraManager.setTorchMode(cameraId,true);
		    v.vibrate(500);
                    isSwitchedOn = true;
                }
                else
                {
                    cameraManager.setTorchMode(cameraId,false);
                    v.vibrate(500);
                    isSwitchedOn = false;
                }
            }
        }
        return isSwitchedOn;
    }
}
