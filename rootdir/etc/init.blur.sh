# If there is not a persist value, we need to set one
if [ ! -f /data/property/persist.blur.profile ]; then
    setprop persist.blur.profile 0
fi
