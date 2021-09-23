package com.xiaomi.parts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import android.provider.Settings;
import android.content.SharedPreferences;
import com.xiaomi.parts.ambient.SensorsDozeService;
import com.xiaomi.parts.kcal.Utils;
import com.xiaomi.parts.soundcontrol.SoundControlSettings;
import com.xiaomi.parts.soundcontrol.SoundControlFileUtils;
import com.xiaomi.parts.FileUtils;
import com.xiaomi.parts.torch.TorchSettings;
import com.xiaomi.parts.torch.TorchUtils;
import com.xiaomi.parts.touch.TouchSettings;
import com.xiaomi.parts.touch.TouchUtils;
import com.xiaomi.parts.dirac.DiracSettings;
import com.xiaomi.parts.dirac.DiracService;

public class BootReceiver extends BroadcastReceiver implements Utils {

    public void onReceive(final Context context, final Intent intent) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (Settings.Secure.getInt(context.getContentResolver(), PREF_ENABLED, 0) == 1) {
            FileUtils.setValue(KCAL_ENABLE, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_ENABLED, 0));

            String rgbValue = Settings.Secure.getInt(context.getContentResolver(),
                    PREF_RED, RED_DEFAULT) + " " +
                    Settings.Secure.getInt(context.getContentResolver(), PREF_GREEN,
                            GREEN_DEFAULT) + " " +
                    Settings.Secure.getInt(context.getContentResolver(), PREF_BLUE,
                            BLUE_DEFAULT);

            FileUtils.setValue(KCAL_RGB, rgbValue);
            FileUtils.setValue(KCAL_MIN, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_MINIMUM, MINIMUM_DEFAULT));
            FileUtils.setValue(KCAL_SAT, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_GRAYSCALE, 0) == 1 ? 128 :
                    Settings.Secure.getInt(context.getContentResolver(),
                            PREF_SATURATION, SATURATION_DEFAULT) + SATURATION_OFFSET);
            FileUtils.setValue(KCAL_VAL, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_VALUE, VALUE_DEFAULT) + VALUE_OFFSET);
            FileUtils.setValue(KCAL_CONT, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_CONTRAST, CONTRAST_DEFAULT) + CONTRAST_OFFSET);
            FileUtils.setValue(KCAL_HUE, Settings.Secure.getInt(context.getContentResolver(),
                    PREF_HUE, HUE_DEFAULT));
        }

        TorchUtils.setValue(TorchSettings.TORCH_1_BRIGHTNESS_PATH,
                Settings.Secure.getInt(context.getContentResolver(),
                        TorchSettings.KEY_WHITE_TORCH_BRIGHTNESS, 255));
        TorchUtils.setValue(TorchSettings.TORCH_2_BRIGHTNESS_PATH,
                Settings.Secure.getInt(context.getContentResolver(),
                        TorchSettings.KEY_YELLOW_TORCH_BRIGHTNESS, 255));

	// Glove Mode
        boolean GloveEnabled = sharedPrefs.getBoolean(TouchSettings.GLOVE_MODE, false);
        TouchUtils.setValue(TouchSettings.GLOVE_PATH, GloveEnabled);

        // Disable Button
        //boolean ButtonEnabled = sharedPrefs.getBoolean(DeviceSettings.BUTTON_KEY, false);
        //FileUtils.setValue(DeviceSettings.BUTTON_PATH, ButtonEnabled);

	// Sound Control
        int gain = Settings.Secure.getInt(context.getContentResolver(),
                SoundControlSettings.PREF_HEADPHONE_GAIN, 4);
        SoundControlFileUtils.setValue(SoundControlSettings.HEADPHONE_GAIN_PATH, gain + " " + gain);
        SoundControlFileUtils.setValue(SoundControlSettings.MICROPHONE_GAIN_PATH, Settings.Secure.getInt(context.getContentResolver(),
                SoundControlSettings.PREF_MICROPHONE_GAIN, 0));
        SoundControlFileUtils.setValue(SoundControlSettings.SPEAKER_GAIN_PATH, Settings.Secure.getInt(context.getContentResolver(),
                SoundControlSettings.PREF_SPEAKER_GAIN, 0));
	//Dirac
        context.startService(new Intent(context, DiracService.class));
        context.startService(new Intent(context, SensorsDozeService.class));
    }
}
