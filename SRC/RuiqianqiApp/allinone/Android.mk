LOCAL_PATH:= $(call my-dir)


include $(CLEAR_VARS)


LOCAL_PACKAGE_NAME := com.zccl.ruiqianqi.mind.voice.allinone
LOCAL_PROGUARD_ENABLED := disabled

tools_dir := ../tools
drive_dir := ../drive
plugin_dir := ../plugin

java_path := src/main/java
aidl_path := src/main/aidl
res_path := src/main/res
assets_path := src/main/assets

oem_ext_src_dirs := src/main/$(mic_ver)/$(platform)/java
oem_ext_assets_dirs := src/main/$(mic_ver)/wakeup/$(wakeup)/assets

src_dirs := $(java_path) \
	$(aidl_path) \
    $(tools_dir)/$(java_path) \
	$(tools_dir)/$(aidl_path) \
    $(drive_dir)/$(java_path) \
	$(drive_dir)/$(aidl_path) \
	$(plugin_dir)/$(java_path) \
	oem_ext_src_dirs \
	
	
res_dirs := $(res_path) \
    $(tools_dir)/$(res_path) \
    $(drive_dir)/$(res_path) 
	
	
assets_dirs := $(assets_path) \
    $(tools_dir)/$(assets_path) \
    $(drive_dir)/$(assets_path) \
	oem_ext_assets_dirs
	
	
LOCAL_SRC_FILES := $(call all-java-files-under, $(src_dirs))
LOCAL_RESOURCE_DIR := $(addprefix $(LOCAL_PATH)/, $(res_dirs))
LOCAL_ASSET_DIR := $(addprefix $(LOCAL_PATH)/, $(assets_dirs))

#ʹ��ָ��Ŀ¼�µ�manifest�ļ����������mk�ļ���ͬһĿ¼�Ļ����붨�壩
LOCAL_MANIFEST_FILE := src/main/AndroidManifest.xml

# APT���ɵĴ���
ext_src_dirs := build/generated/source/apt/P_armv7/release \
	build/generated/source/buildConfig/P_armv7/release
LOCAL_SRC_FILES += $(call all-java-files-under, $(ext_src_dirs))


# ������������
LOCAL_AAPT_FLAGS := \
    --auto-add-overlay \
    --extra-packages com.zccl.ruiqianqi.move \ 
    --extra-packages com.zccl.ruiqianqi.tools
	

# ����˽�еĿ�	
LOCAL_PREBUILT_JNI_LIBS:= \
	src/main/libs/armeabi-v7a/libmictest.so	
	

# ����ϵͳ��̬��
LOCAL_JNI_SHARED_LIBRARIES += libalsa-jni libcae libmsc

	
# ��̬jar��
LOCAL_STATIC_JAVA_LIBRARIES := \
    android-support-v4 \
	android-support-v7-appcompat \
    android-support-v13 \
	alsarecorder \
	cae \
	Msc
	

include $(BUILD_PACKAGE)
	

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := \
    alsarecorder:src/main/$(mic_ver)/$(platform)/libs/alsarecorder.jar \
    cae:src/main/$(mic_ver)/$(platform)/libs/cae.jar \
	Msc:src/main/ext_res/$(app_id)/libs/Msc.jar
	
include $(BUILD_MULTI_PREBUILT)	



include $(CLEAR_VARS)
LOCAL_MODULE := libalsa-jni
LOCAL_SRC_FILES_32 := src/main/$(mic_ver)/$(platform)/libs/armeabi-v7a/libalsa-jni.so
LOCAL_MULTILIB := 32
LOCAL_MODULE_CLASS := SHARED_LIBRARIES
LOCAL_MODULE_SUFFIX := .so
include $(BUILD_PREBUILT)


include $(CLEAR_VARS)
LOCAL_MODULE := libcae
LOCAL_SRC_FILES_32 := src/main/$(mic_ver)/$(platform)/libs/armeabi-v7a/libcae.so
LOCAL_MULTILIB := 32
LOCAL_MODULE_CLASS := SHARED_LIBRARIES
LOCAL_MODULE_SUFFIX := .so
include $(BUILD_PREBUILT)


include $(CLEAR_VARS)
LOCAL_MODULE := libmsc
LOCAL_SRC_FILES_32 := src/main/ext_res/$(app_id)/libs/armeabi-v7a/libmsc.so
LOCAL_MULTILIB := 32
LOCAL_MODULE_CLASS := SHARED_LIBRARIES
LOCAL_MODULE_SUFFIX := .so
include $(BUILD_PREBUILT)
