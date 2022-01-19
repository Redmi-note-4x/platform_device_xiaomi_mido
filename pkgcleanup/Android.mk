LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := RemovePkgs
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_TAGS := optional
# product/app
LOCAL_OVERRIDES_PACKAGES := CarrierMetrics DevicePolicyPrebuilt DiagnosticsToolPrebuilt Drive GoogleCamera PixelWallpapers2021 PrebuiltGoogleTelemetryTvp SoundAmplifierPrebuilt Tycho VZWAPNLib arcore talkback
# product/priv-app
LOCAL_OVERRIDES_PACKAGES += AmbientSensePrebuilt AndroidAutoStubPrebuilt AppDirectedSMSService BetterBug CarrierLocation CarrierServices CarrierWifi CbrsNetworkMonitor ConfigUpdater DCMO ConnMO DMService GCS HelpRtcPrebuilt MaestroPrebuilt MyVerizonServices OemDmTrigger OdadPrebuilt SCONE ScribePrebuilt Showcase SprintDM SprintHM TetheringEntitlement USCCDM VzwOmaTrigger WfcActivation
# system_ext/priv-app
LOCAL_OVERRIDES_PACKAGES += CarrierSetup GoogleFeedback grilservice OBDM_Permissions obdm_stub RilConfigService
LOCAL_UNINSTALLABLE_MODULE := true
LOCAL_CERTIFICATE := platform
LOCAL_SRC_FILES := /dev/null
include $(BUILD_PREBUILT)
