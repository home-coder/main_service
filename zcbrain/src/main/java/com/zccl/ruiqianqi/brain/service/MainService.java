package com.zccl.ruiqianqi.brain.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;

import com.zccl.ruiqianqi.brain.R;
import com.zccl.ruiqianqi.brain.eventbus.MindBusEvent;
import com.zccl.ruiqianqi.brain.service.observer.NameObserver;
import com.zccl.ruiqianqi.brain.service.observer.VideoObserver;
//import com.zccl.ruiqianqi.eventbus.MicBusEvent;
import com.zccl.ruiqianqi.brain.system.ISDKCallback;
import com.zccl.ruiqianqi.brain.system.ISDKService;
import com.zccl.ruiqianqi.brain.voice.RobotVoice;
import com.zccl.ruiqianqi.config.MyConfig;
import com.zccl.ruiqianqi.mind.eventbus.MainBusEvent;
import com.zccl.ruiqianqi.mind.service.SystemService;
import com.zccl.ruiqianqi.presentation.presenter.PersistPresenter;
import com.zccl.ruiqianqi.presentation.presenter.LocalPresenter;
import com.zccl.ruiqianqi.presenter.impl.MindPresenter;
import com.zccl.ruiqianqi.tools.LogUtils;
import com.zccl.ruiqianqi.tools.MYUIUtils;
import com.zccl.ruiqianqi.tools.ShareUtils;
import com.zccl.ruiqianqi.tools.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.zccl.ruiqianqi.brain.handler.SDKHandler.RECV_FACE_DETECT;
import static com.zccl.ruiqianqi.brain.service.observer.NameObserver.NAME_URI;
import static com.zccl.ruiqianqi.brain.service.observer.VideoObserver.VIDEO_URI;
import static com.zccl.ruiqianqi.presentation.presenter.PersistPresenter.KEY_ID;
import static com.zccl.ruiqianqi.presentation.presenter.PersistPresenter.KEY_SID;

/**
 * Created by ruiqianqi on 2017/1/14 0014.
 */

public class MainService extends SystemService {

    // 类功能标识
    public static final String TAG = MainService.class.getSimpleName();

    // 人脸识别的结果接收广播
    private static final String FACE_DETECT = "com.yongyida.robot.face.detect";
    // 携带的结果
    private static final String FACE_RESULT = "result";

    /**
     * 启动主服务
     * @param context
     */
    public static void startMyService(Context context) {
        Intent intent = new Intent(context, MainService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);

        /*
        intent = new Intent(context, WsService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);
        */
    }

    /**
     * 关闭主服务
     * @param context
     */
    public static void stopMyService(Context context) {
        Intent intent = new Intent(context, MainService.class);
        context.stopService(intent);
    }


    /**********************************************************************************************/
    // SDK统一回调接口
    private ISDKCallback mSDKCallback;
    // 人脸识别的结果接收广播
    private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(FACE_DETECT.equals(intent.getAction())){
                if(null != mSDKCallback){
                    String result = intent.getStringExtra(FACE_RESULT);
                    LogUtils.e(TAG, "FACE_RESULT = " + result);
                    if(!TextUtils.isEmpty(result)) {
                        try {
                            mSDKCallback.onReceive(RECV_FACE_DETECT, result);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    /**********************************************************************************************/
    // 机器人名字数据库观察者
    private NameObserver mNameObserver;
    // 视频配置数据库观察者
    private VideoObserver mVideoObserver;
    // 本地服务器数据处理中心
    private LocalPresenter mLocalPresenter;

    // 悬浮监听按钮处理类
    private FloatListen mFloatBtnView;

    @Override
    public void onCreate() {

        // 有动态注册的广播
        super.onCreate();

        // 注册远程Media控制广播
        registers.registerMediaButton();
        // 注册耳机及远程Media控制广播
        registers.registerHeadsetPlugReceiver();

        // 观察机器人名字数据库
        mNameObserver = new NameObserver(this, null);
        Uri uriName = Uri.parse(NAME_URI);
        getContentResolver().registerContentObserver(uriName, true, mNameObserver);

        // 观察视频配置数据库
        mVideoObserver = new VideoObserver(this, null);
        Uri uriVideo = Uri.parse(VIDEO_URI);
        getContentResolver().registerContentObserver(uriVideo, true, mVideoObserver);

        // 初始化一些机器人配置
        PersistPresenter.getInstance().initSome();

        // 开启本地服务器
        mLocalPresenter = new LocalPresenter();
        mLocalPresenter.initSome();

        // 初始化悬浮监听按钮
        mFloatBtnView = new FloatListen(this);
        mFloatBtnView.addView();

        // 注册事件总线
        EventBus.getDefault().register(this);

        // 注册动态广播
        IntentFilter filter = new IntentFilter(FACE_DETECT);
        getApplicationContext().registerReceiver(dynamicReceiver, filter);


        LogUtils.f("mainservice", System.currentTimeMillis() + "：onCreate\n");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 注销远程Media控制广播
        registers.unregisterMediaButton();
        // 注销耳机及远程Media控制广播
        registers.registerHeadsetPlugReceiver();

        // 服务已死，取消观察机器人名字变化
        getContentResolver().unregisterContentObserver(mNameObserver);
        // 服务已死，取消观察视频状态
        getContentResolver().unregisterContentObserver(mVideoObserver);

        // 关闭本地服务器
        mLocalPresenter.release();

        // 移除悬浮按钮
        mFloatBtnView.removeView();

        // 注销事件总线
        EventBus.getDefault().unregister(this);

        // 注销动态广播
        getApplicationContext().unregisterReceiver(dynamicReceiver);

        // 死了，继续重启
        MainService.startMyService(getApplicationContext());


        LogUtils.f("mainservice", System.currentTimeMillis() + "：onDestroy\n");
    }


    /**********************************【事件总线的处理】******************************************/
    /**
     * 监听事件处理
     * @param voiceFloatEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 10)
    public void OnListenEvent(MindBusEvent.VoiceFloatEvent voiceFloatEvent){
        if(MindBusEvent.VoiceFloatEvent.START == voiceFloatEvent.getType()){
            mFloatBtnView.start();
        }
        else if(MindBusEvent.VoiceFloatEvent.GOING == voiceFloatEvent.getType()){
            mFloatBtnView.going(voiceFloatEvent.getVolume());
        }
        else if(MindBusEvent.VoiceFloatEvent.END == voiceFloatEvent.getType()){
            mFloatBtnView.end();
        }
    }

    /**
     * 打开和关闭五麦事件
     * @param operator5MicEvent
     */
    /*
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 10)
    public void On5MICEvent(MicBusEvent.Operator5MicEvent operator5MicEvent){
        LogUtils.e(TAG, "5MicEvent = " + operator5MicEvent.getStatus());

        MainBusEvent.SensorEvent sensorEvent = new MainBusEvent.SensorEvent();
        // 关闭五麦
        if(MicBusEvent.Operator5MicEvent.CLOSE_5_MIC == operator5MicEvent.getStatus()){
            sensorEvent.setText("5micoff");
            EventBus.getDefault().post(sensorEvent);
        }
        // 打开五麦
        else if(MicBusEvent.Operator5MicEvent.OPEN_5_MIC == operator5MicEvent.getStatus()){
            sensorEvent.setText("5micon");
            EventBus.getDefault().post(sensorEvent);
        }
    }
    */


    /**
     * 这个是服务体，是服务具体实现的地方，在这边没有通讯相关的了，通讯已经过来了
     */
    private final ISDKService.Stub mBinder = new ISDKService.Stub() {
        @Override
        public void setCallback(ISDKCallback callback, boolean isUseSDK) throws RemoteException {
            RobotVoice robotVoice = (RobotVoice) MindPresenter.getInstance().getVoiceDevice();
            if(null != robotVoice){
                if(!isUseSDK) {
                    callback = null;
                }
                mSDKCallback = callback;
                robotVoice.setSDKCallback(callback);
            }
        }

        @Override
        public String getRobotID() throws RemoteException {
            String id = ShareUtils.getP(getApplicationContext()).getString(KEY_ID, MyConfig.STATE_DEFAULT_ID);
            String sid = ShareUtils.getP(getApplicationContext()).getString(KEY_SID, MyConfig.STATE_DEFAULT_SID);
            return id + sid;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
