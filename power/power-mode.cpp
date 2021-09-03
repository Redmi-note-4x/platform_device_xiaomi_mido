/*
 * Copyright (C) 2020 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_TAG "QTI PowerHAL"
#include <android-base/file.h>
#include <log/log.h>

#include <aidl/android/hardware/power/BnPower.h>

#include "power-common.h"

#define GPU_MIN_PWRLEVEL_NODE "/sys/class/kgsl/kgsl-3d0/min_pwrlevel"

using ::aidl::android::hardware::power::Mode;
using ::android::base::WriteStringToFile;

namespace aidl {
namespace android {
namespace hardware {
namespace power {
namespace impl {

bool isDeviceSpecificModeSupported(Mode type, bool* _aidl_return) {
    switch (type) {
        case Mode::EXPENSIVE_RENDERING:
        case Mode::LAUNCH:
            *_aidl_return = true;
            return true;
        default:
            return false;
    }
}

bool setDeviceSpecificMode(Mode type, bool enabled) {
    switch (type) {
        case Mode::EXPENSIVE_RENDERING:
            WriteStringToFile(enabled ? "0" : "6", GPU_MIN_PWRLEVEL_NODE, true);
            return true;
        case Mode::LAUNCH:
            power_hint(POWER_HINT_LAUNCH, enabled ? &enabled : NULL);
            return true;
        default:
            return false;
    }
}

}  // namespace impl
}  // namespace power
}  // namespace hardware
}  // namespace android
}  // namespace aidl
