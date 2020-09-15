package com.xiaomi.parts;

final class DiracUtils {

    private final DiracSound mDiracSound;

    DiracUtils() {
        mDiracSound = new DiracSound(0, 0);
    }

    void onBootCompleted() {
        setEnabled(mDiracSound.getMusic() == 1);
        mDiracSound.setHeadsetType(mDiracSound.getHeadsetType());
        setLevel(getLevel());
    }

    void setEnabled(boolean enable) {
        mDiracSound.setEnabled(enable);
        mDiracSound.setMusic(enable ? 1 : 0);
    }

    boolean isDiracEnabled() {
        return mDiracSound.getMusic() == 1;
    }

    private String getLevel() {
        StringBuilder selected = new StringBuilder();
        for (int band = 0; band <= 6; band++) {
            int temp = (int) mDiracSound.getLevel(band);
            selected.append(temp);
            if (band != 6) selected.append(",");
        }
        return selected.toString();
    }

    void setLevel(String preset) {
        String[] level = preset.split("\\s*,\\s*");
        for (int band = 0; band <= level.length - 1; band++) {
            mDiracSound.setLevel(band, Float.valueOf(level[band]));
        }
    }

    void setHeadsetType(int paramInt) {
        mDiracSound.setHeadsetType(paramInt);
    }
}
