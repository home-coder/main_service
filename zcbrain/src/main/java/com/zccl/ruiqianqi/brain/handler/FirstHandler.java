package com.zccl.ruiqianqi.brain.handler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.zccl.ruiqianqi.brain.R;
import com.zccl.ruiqianqi.brain.eventbus.MindBusEvent;
import com.zccl.ruiqianqi.brain.semantic.flytek.BaseInfo;
import com.zccl.ruiqianqi.brain.semantic.flytek.DisplayBean;
import com.zccl.ruiqianqi.brain.semantic.flytek.GenericBean;
import com.zccl.ruiqianqi.brain.semantic.flytek.MoveBean;
import com.zccl.ruiqianqi.brain.semantic.flytek.SwitchBean;
import com.zccl.ruiqianqi.brain.system.MainBean;
import com.zccl.ruiqianqi.brain.voice.RobotVoice;
import com.zccl.ruiqianqi.domain.model.Robot;
import com.zccl.ruiqianqi.presentation.presenter.GenericPresenter;
import com.zccl.ruiqianqi.presentation.presenter.HttpReqPresenter;
import com.zccl.ruiqianqi.presentation.presenter.MovePresenter;
import com.zccl.ruiqianqi.presentation.presenter.PersistPresenter;
import com.zccl.ruiqianqi.presentation.presenter.ReportPresenter;
import com.zccl.ruiqianqi.presentation.presenter.SocketPresenter;
import com.zccl.ruiqianqi.presentation.presenter.StatePresenter;
import com.zccl.ruiqianqi.presentation.presenter.SystemPresenter;
import com.zccl.ruiqianqi.presentation.presenter.XiriPresenter;
import com.zccl.ruiqianqi.presentation.view.translation.TranslateActivity;
import com.zccl.ruiqianqi.tools.CheckUtils;
import com.zccl.ruiqianqi.tools.JsonUtils;
import com.zccl.ruiqianqi.tools.LogUtils;
import com.zccl.ruiqianqi.tools.MYUIUtils;
import com.zccl.ruiqianqi.tools.MyAppUtils;
import com.zccl.ruiqianqi.tools.StringUtils;
import com.zccl.ruiqianqi.tools.config.MyConfigure;
import com.zccl.ruiqianqi.tools.regex.CmdRegex;
import com.zccl.ruiqianqi.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zccl.ruiqianqi.brain.eventbus.MindBusEvent.TransEvent.TRANS_FAILURE;
import static com.zccl.ruiqianqi.brain.eventbus.MindBusEvent.TransEvent.TRANS_SUCCESS;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_CHAT;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_DICT;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_DISPLAY;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_FAQ;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_GAME;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_GENERIC;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_HABIT;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_HEALTH;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_MOVE;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_MOVIE_INFO;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_MUSIC;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_MUSIC_CTRL;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_MUTE;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_OPEN_QA;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_OPERA;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_SMART_HOME;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_SMS;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_SQUARE_DANCE;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_STORY;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_SWITCH;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_TRANSLATE;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_TRANSLATE_;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_TV_CONTROL;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_VIDEO;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_VIDEO_CTRL;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_WATCH_TV;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.FUNC_YYD_CAHT;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.OP_SHUT_UP;
import static com.zccl.ruiqianqi.brain.semantic.flytek.FuncType.RESULT_ZERO;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_CAMERA;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_GAME;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_HOME;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_LAUNCHER;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_MUSIC;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_PHOTOS;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_PICTURE;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_POLE;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_QRCODE_SCAN;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_SETTINGS;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_SHUTDOWN;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_TOOLS;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_UPDATE_WORDS;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_VIDEO;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_VOICE_DOWN;
import static com.zccl.ruiqianqi.config.MyConfig.OFF_LINE_VOICE_UP;
import static com.zccl.ruiqianqi.plugin.voice.AbstractVoice.RecognizerCallback.OFFLINE_WORD;
import static com.zccl.ruiqianqi.plugin.voice.AbstractVoice.UnderstandCallback.UNDERSTAND_SUCCESS;

/**
 * Created by ruiqianqi on 2017/3/21 0021.
 */

public class FirstHandler extends BaseHandler {

    // 类标志
    private static String TAG = FirstHandler.class.getSimpleName();

    // 安静语
    private String[] muteWords;
    // 用不用讯飞音乐
    private String isUseXiriKey;
    // 讯飞音乐正则匹配
    private CmdRegex mXfMusicRegex;
    // 自有播放器正则匹配
    private CmdRegex mMyMusicRegex;

    // 音乐的时候进不进行其他功能
    private boolean isMusicWithFunc;

    public FirstHandler(Context context, RobotVoice robotVoice){
        super(context, robotVoice);
        muteWords = mContext.getResources().getStringArray(R.array.mute_words);
        isUseXiriKey = mContext.getString(R.string.is_use_xiri);
        mXfMusicRegex = new CmdRegex(mContext);
        mMyMusicRegex = new CmdRegex(mContext);

        mXfMusicRegex.load("xf_music");
        mMyMusicRegex.load("my_music");

        isMusicWithFunc = Boolean.parseBoolean(MyConfigure.getValue("music_with_func"));
    }

    /**
     * 匹配自有播放器
     * 戏曲、故事、广场舞、健康养生、习惯养成
     *
     * @param funcType
     * @return
     */
    private boolean matchMyMusic(String funcType){
        return FUNC_OPERA.equals(funcType) || FUNC_STORY.equals(funcType) ||
                FUNC_SQUARE_DANCE.equals(funcType) || FUNC_HEALTH.equals(funcType)||
                FUNC_HABIT.equals(funcType);
    }

    /**
     * 讯飞视频处理的
     * @param funcType
     * @return
     */
    private boolean matchXfVideo(String funcType){
        return FUNC_VIDEO.equals(funcType) || FUNC_VIDEO_CTRL.equals(funcType) || FUNC_MUSIC_CTRL.equals(funcType) ||
                FUNC_MOVIE_INFO.equals(funcType) || FUNC_SMART_HOME.equals(funcType) || FUNC_TV_CONTROL.equals(funcType) ||
                FUNC_CHAT.equals(funcType) || FUNC_FAQ.equals(funcType) || FUNC_OPEN_QA.equals(funcType) ;
    }

    /**
     * 在视频界面，过滤掉播放动作词
     * @param words
     * @return
     */
    private String catchXfVideo(String words){
        Pattern pat = Pattern.compile(".*[看播放](.*)");
        Matcher mat = pat.matcher(words);
        if(mat.find()){
            try {
                return mat.group(1);
            }catch (Exception e){

            }
        }
        return null;
    }

    /**
     * 处理场景【场景循环监听，就意味着所有的结果都要发给场景，由场景处理之后发循环监听广播】
     * @param json     返回的理解结果
     * @param type     理解成功与失败
     * {@link com.zccl.ruiqianqi.plugin.voice.AbstractVoice.UnderstandCallback#UNDERSTAND_FAILURE}
     * {@link com.zccl.ruiqianqi.plugin.voice.AbstractVoice.UnderstandCallback#UNDERSTAND_SUCCESS}
     *
     * @return true代表处理了，false代表没处理，继续走流程
     */
    @Override
    public boolean handlerScene(String json, int type){
        StatePresenter sp = StatePresenter.getInstance();
        String scene = sp.getScene();
        LogUtils.e(TAG, "handlerScene = " + scene);

        if(UNDERSTAND_SUCCESS == type){
            BaseInfo baseInfo = JsonUtils.parseJson(json, BaseInfo.class);
            if (null == baseInfo) {
                return false;
            }
            String funcType = baseInfo.getServiceType();
            String words = baseInfo.getText();

            //【显示录音文字】
            if(PersistPresenter.getInstance().isShowWords()){
                MYUIUtils.showToast(mContext, baseInfo.getText());
            }

            // 【翻译场景，优先级最高】
            if(SCENE_MY_TRANS.equals(scene)){
                MindBusEvent.TransEvent transEvent = new MindBusEvent.TransEvent();
                if(null != baseInfo){
                    transEvent.setType(TRANS_SUCCESS);
                    transEvent.setText(baseInfo.getText());
                }
                // 返回的数据解析出错，这个可能性不大，一旦出错就意味着讯飞服务器返回出错了
                else {
                    transEvent.setType(TRANS_FAILURE);
                }
                EventBus.getDefault().post(transEvent);
                return true;
            }

            //【音乐场景】
            else if(SCENE_MY_MUSIC.equals(scene)){

                // 用讯飞音乐，我就不处理音乐了
                boolean isUseXiri = SystemProperties.getBoolean(isUseXiriKey, false);
                boolean isFilter;
                if(isUseXiri){
                    //【戏曲、故事、广场舞、健康养生、习惯养成】
                    isFilter = matchMyMusic(funcType);
                }else {
                    //【戏曲、故事、广场舞、健康养生、习惯养成】【音乐、儿歌】
                    isFilter = matchMyMusic(funcType) || FUNC_MUSIC.equals(funcType);
                }

                Bundle bundle = new Bundle();
                // 音乐搜索，【GSON解析】
                if(isFilter){
                    bundle.putString(PLAYER_CATEGORY_KEY, MUSIC_SEARCH);
                    bundle.putString(PLAYER_RESULT_KEY, json);
                    MyAppUtils.sendBroadcast(mContext, ACTION_PLAYER, bundle);
                    return true;
                }
                // 音乐控制，【正则匹配】
                else {
                    String command = mMyMusicRegex.filter(words);
                    if(!StringUtils.isEmpty(command)){
                        //AppUtils.controlMusicPlayer(mContext, words);
                        AppUtils.controlMusicPlayer(mContext, command);
                        return true;
                    }else {
                        // 其他功能也要处理
                        return !isMusicWithFunc;
                    }
                }
            }

            // 【讯飞音乐】
            else if(SCENE_XF_MUSIC.equals(scene)){

                if(FUNC_MUSIC.equals(funcType) || FUNC_MUSIC_CTRL.equals(funcType)){
                    XiriPresenter xiriPresenter = new XiriPresenter();
                    xiriPresenter.flyTekYuDian(json, FUNC_MUSIC);
                    return true;
                }else {
                    String command = mXfMusicRegex.filter(words);
                    if(!StringUtils.isEmpty(command)){
                        XiriPresenter xiriPresenter = new XiriPresenter();
                        xiriPresenter.xfMusicAction(command, words);
                        return true;
                    }else {
                        // 其他功能也要处理
                        return !isMusicWithFunc;
                    }
                }
            }

            // 【讯飞视频】
            else if(SCENE_XF_VIDEO.equals(scene)){
                if(FUNC_VIDEO.equals(funcType) || FUNC_VIDEO_CTRL.equals(funcType)) {
                    XiriPresenter xiriPresenter = new XiriPresenter();
                    xiriPresenter.flyTekYuDian(json, FUNC_VIDEO);
                    return true;
                }else {
                    if(matchXfVideo(funcType) || 0 != baseInfo.getSuccess()){
                        XiriPresenter xiriPresenter = new XiriPresenter();
                        String text = catchXfVideo(baseInfo.getText());
                        if(StringUtils.isEmpty(text)) {
                            xiriPresenter.sendCommand(baseInfo.getText());
                        }else {
                            xiriPresenter.sendCommand(text);
                        }
                        return true;
                    }else {
                        // 其他功能也要处理
                        return !isMusicWithFunc;
                    }
                }
            }

        }else {

            // 【当前是翻译场景】
            if(SCENE_MY_TRANS.equals(scene)){
                MindBusEvent.TransEvent transEvent = new MindBusEvent.TransEvent();
                transEvent.setType(TRANS_FAILURE);
                transEvent.setText(json);
                EventBus.getDefault().post(transEvent);
                return true;
            }
            //【音乐场景】
            else if(SCENE_MY_MUSIC.equals(scene)){
                // 没有识别到任何东西，继续播放
                AppUtils.controlMusicPlayer(mContext, "play");
            }
            // 止错于第一步
            else {
                return true;
            }

        }

        if(null != getSuccessor()){
            return getSuccessor().handlerScene(json, type);
        }
        return false;
    }

    /**
     * 语义理解的处理
     * @param json         科大讯飞返回的完整数据
     * @param funcType     功能类型
     */
    @Override
    public boolean handleSemantic(String json, String funcType) {

        if(StringUtils.isEmpty(json))
            return false;

        BaseInfo baseInfo = JsonUtils.parseJson(json, BaseInfo.class);
        if (null == baseInfo) {
            return false;
        }

        boolean isUseXiri = SystemProperties.getBoolean(isUseXiriKey, false);
        // 用讯飞
        if(isUseXiri && (FUNC_MUSIC.equals(funcType) || FUNC_VIDEO.equals(funcType))){
            // 传给讯飞语点
            XiriPresenter musicPresenter = new XiriPresenter();
            musicPresenter.flyTekYuDian(json, funcType);
            // 关掉其他功能
            mRobotVoice.stopOtherAppFunc(XiriPresenter.TAG);
            return true;
        }
        // 不用讯飞
        else {
            // 播放音乐、儿歌、
            // 戏曲、故事、广场舞、健康养生、习惯养成
            boolean isFilterMusic;

            // 用讯飞音乐：自有音乐播放器，不处理音乐
            if(isUseXiri){
                isFilterMusic = matchMyMusic(funcType);
            }
            // 自有音乐播放器，处理音乐
            else {
                isFilterMusic = matchMyMusic(funcType) || FUNC_MUSIC.equals(funcType);
            }

            // 音频
            if(isFilterMusic){
                Bundle bundle = new Bundle();
                bundle.putString(PLAYER_CATEGORY_KEY, MUSIC_PLAY);
                bundle.putString(PLAYER_RESULT_KEY, json);
                MyAppUtils.sendBroadcast(mContext, ACTION_PLAYER, bundle);
                // 关掉其他功能
                mRobotVoice.stopOtherAppFunc("MusicPresenter");
                return true;
            }
            // 视频
            else if(FUNC_VIDEO.equals(funcType)){
                MyAppUtils.openApp(mContext, "com.yongyida.robot.videotutarial");
                return true;
            }
        }



        // 没有找到对应的语义
        if(RESULT_ZERO != baseInfo.getSuccess()){

            // 在这里获取薄言豆豆的对话语义
            StatePresenter sp = StatePresenter.getInstance();
            Robot robot = sp.getRobot();
            if(null != robot){
                HttpReqPresenter htp = new HttpReqPresenter(mContext);
                htp.queryBoYan(baseInfo.getText(), robot.getRid(), robot.getRname());
            }
            return true;


            /*
            // 解析完之后，交由Master处理
            HttpReqPresenter htp = new HttpReqPresenter(mContext);
            json = htp.queryByMaster(baseInfo.getText(), "");
            funcType = FUNC_CHAT;
            LogUtils.e(TAG, "RC4 = " + json);
            */
        }


        // 如果是聊天
        if(FUNC_CHAT.equals(funcType)) {
            // 如果是闭嘴
            if (OP_SHUT_UP.equals(baseInfo.getOperation())) {
                funcType = FUNC_MUTE;
            }
        }


        /*
        // 聊天或开放语义
        if(FUNC_CHAT.equals(funcType) || FUNC_OPEN_QA.equals(funcType)){
            // 在这里服务器的对话语义
            StatePresenter sp = StatePresenter.getInstance();
            Robot robot = sp.getRobot();
            if(null != robot){
                HttpReqPresenter htp = new HttpReqPresenter(mContext);
                htp.queryCustomQA(baseInfo.getText(), "", robot.getRid(), robot.getSerial());
            }
            return true;
        }
        */

        // 机器人移动
        if(FUNC_MOVE.equals(funcType)){
            MoveBean moveBean = JsonUtils.parseJson(json, MoveBean.class);
            if(null != moveBean && null != moveBean.semantic && null != moveBean.semantic.slots){
                MovePresenter.getInstance().parseFlytekData(moveBean.semantic.slots.direct);
            }else {
                ReportPresenter.report("");
            }
            return true;
        }

        // 切换版本
        else if(FUNC_SWITCH.equals(funcType)){
            SwitchBean switchBean = JsonUtils.parseJson(json, SwitchBean.class);
            if(null != switchBean && null != switchBean.semantic && null != switchBean.semantic.slots){
                SocketPresenter.getInstance().switchToServer(switchBean.semantic.slots.server_type);
            }else {
                ReportPresenter.report("");
            }
            return true;
        }

        // 是否显示录音文本
        else if(FUNC_DISPLAY.equals(funcType)){
            DisplayBean displayBean = JsonUtils.parseJson(json, DisplayBean.class);
            if(null != displayBean && null != displayBean.semantic && null != displayBean.semantic.slots){
                if(DisplayBean.DISPLAY.equals(displayBean.semantic.slots.display)){
                    PersistPresenter.getInstance().setShowWords(true);
                }else {
                    PersistPresenter.getInstance().setShowWords(false);
                }
            }else {
                ReportPresenter.report("");
            }
            return true;
        }

        // 安静
        else if(FUNC_MUTE.equals(funcType)){

            // 让其他应用安静
            mRobotVoice.stopOtherAppFunc(FUNC_MUTE);

            // 让自己安静
            mRobotVoice.cancelUnderstand();
            mRobotVoice.cancelRecognizer();
            String word = muteWords[CheckUtils.getRandom(muteWords.length)];
            mRobotVoice.startTTS(word, new Runnable() {
                @Override
                public void run() {
                    // 结束发音
                    mRobotVoice.stopTTS();
                }
            });
            return true;
        }

        // 打开未读短信
        else if(FUNC_SMS.equals(funcType)){
            MyAppUtils.openDialRecord(mContext);
            return true;
        }

        // 打开多媒体
        else if(FUNC_WATCH_TV.equals(funcType)){
            return true;
        }

        // 打开游戏
        else if(FUNC_GAME.equals(funcType)){
            GenericPresenter genericPresenter = new GenericPresenter();
            genericPresenter.genericOperator(GenericPresenter.GAME);
            return true;
        }


        /*
        // 打开各类界面
        else if(FUNC_APP.equals(funcType)){
            return true;
        }

        // 红外感应的开关、二维码、打开测试界面
        else if(FUNC_YYD_CHAT.equals(funcType)){
            String op = baseInfo.getOperation();
            if(OP_OPEN_SENSE.equals(op)){

            }
            else if(OP_CLOSE_SENSE.equals(op)){

            }
            else if(OP_BIND_QRCODE.equals(op)){

            }
            else if(OP_DOWNLOAD_QRCODE.equals(op)){

            }
        }
        */

        // 打开、关闭投影
        else if(FUNC_YYD_CAHT.equals(funcType)){
            return true;
        }

        // 翻译【直接打开翻译界面】
        else if(FUNC_TRANSLATE.equals(funcType) || FUNC_TRANSLATE_.equals(funcType)){
            Intent intent = new Intent(mContext, TranslateActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            return true;
        }

        // 开启英业达字典
        else if(FUNC_DICT.equals(funcType)){
            MyAppUtils.openApp(mContext, mContext.getString(R.string.yingyeda));
            return true;
        }

        // 如果是通用功能
        else if(FUNC_GENERIC.equals(funcType)){
            GenericBean genericBean = JsonUtils.parseJson(json, GenericBean.class);
            if(null != genericBean && null != genericBean.semantic && null != genericBean.semantic.slots){
                GenericPresenter genericPresenter = new GenericPresenter();
                genericPresenter.genericOperator(genericBean);
            }else {
                ReportPresenter.report("");
            }
            return true;
        }

        // 其他发送出去
        else {
            if(null != getSuccessor()){
                return getSuccessor().handleSemantic(json, funcType);
            }
        }
        return false;
    }

    /**
     * 语音识别的处理
     * @param asr
     * @param type
     * {@link com.zccl.ruiqianqi.plugin.voice.AbstractVoice.RecognizerCallback#LISTEN_ERROR}
     * {@link com.zccl.ruiqianqi.plugin.voice.AbstractVoice.RecognizerCallback#LISTEN}
     * {@link com.zccl.ruiqianqi.plugin.voice.AbstractVoice.RecognizerCallback#ONLINE_WORD}
     * {@link com.zccl.ruiqianqi.plugin.voice.AbstractVoice.RecognizerCallback#OFFLINE_WORD}
     * {@link com.zccl.ruiqianqi.plugin.voice.AbstractVoice.RecognizerCallback#LISTEN_UNDERSTAND}
     */
    @Override
    public boolean handleAsr(String asr, int type) {

        LogUtils.e(TAG, asr + "");
        if(type == OFFLINE_WORD){

            Map<String, String> offLineMap = JsonUtils.parseJson(asr, Map.class);
            if(null != offLineMap){

                String text = offLineMap.get("text");
                if(!TextUtils.isEmpty(text)){

                    if(text.contains("歪发")){
                        text = text.replace("歪发", "WIFI");
                    }

                    // 显示录音文字
                    if(PersistPresenter.getInstance().isShowWords()){
                        MYUIUtils.showToast(mContext, text);
                    }
                }

                String funcStr = null;
                String funcIndex = "0";

                if(offLineMap.containsKey(OFF_LINE_MUSIC)){
                    funcStr = OFF_LINE_MUSIC;

                }else if(offLineMap.containsKey(OFF_LINE_VIDEO)){
                    funcStr = OFF_LINE_VIDEO;

                }else if(offLineMap.containsKey(OFF_LINE_PICTURE) || offLineMap.containsKey(OFF_LINE_CAMERA)){
                    funcStr = OFF_LINE_CAMERA;

                }else if(offLineMap.containsKey(OFF_LINE_SHUTDOWN)){
                    funcStr = OFF_LINE_SHUTDOWN;

                }else if(offLineMap.containsKey(OFF_LINE_PHOTOS)){
                    funcStr = OFF_LINE_PHOTOS;

                }else if(offLineMap.containsKey(OFF_LINE_GAME)){
                    funcStr = OFF_LINE_GAME;

                }else if(offLineMap.containsKey(OFF_LINE_QRCODE_SCAN)){
                    funcStr = OFF_LINE_QRCODE_SCAN;
                    funcIndex = offLineMap.get(OFF_LINE_QRCODE_SCAN);

                }else if(offLineMap.containsKey(OFF_LINE_SETTINGS)) {
                    funcStr = OFF_LINE_SETTINGS;
                    funcIndex = offLineMap.get(OFF_LINE_SETTINGS);

                }else if(offLineMap.containsKey(OFF_LINE_TOOLS)){
                    funcStr = OFF_LINE_TOOLS;
                    funcIndex = offLineMap.get(OFF_LINE_TOOLS);

                }else if(offLineMap.containsKey(OFF_LINE_LAUNCHER)){
                    funcStr = OFF_LINE_LAUNCHER;
                    funcIndex = offLineMap.get(OFF_LINE_LAUNCHER);

                }else if(offLineMap.containsKey(OFF_LINE_HOME)){
                    funcStr = OFF_LINE_HOME;
                    funcIndex = offLineMap.get(OFF_LINE_HOME);

                }else if(offLineMap.containsKey(OFF_LINE_POLE)){
                    funcStr = OFF_LINE_POLE;
                    funcIndex = offLineMap.get(OFF_LINE_POLE);

                }else if(offLineMap.containsKey(OFF_LINE_VOICE_UP)){
                    funcStr = OFF_LINE_VOICE_UP;

                }else if(offLineMap.containsKey(OFF_LINE_VOICE_DOWN)){
                    funcStr = OFF_LINE_VOICE_DOWN;

                }else if(offLineMap.containsKey(OFF_LINE_UPDATE_WORDS)){
                    funcStr = OFF_LINE_UPDATE_WORDS;
                    funcIndex = offLineMap.get(OFF_LINE_UPDATE_WORDS);

                }

                try {
                    type = Integer.parseInt(funcIndex);
                }catch (NumberFormatException e){
                    type = 0;
                }
                if(null != getSuccessor()){
                    return getSuccessor().handleAsr(funcStr, type);
                }
            }

        }else {
            if(null != getSuccessor()){
                return getSuccessor().handleAsr(asr, type);
            }
        }
        return false;
    }

    /**
     * 根据传入的数据，直接处理相应的功能
     * @param func
     */
    @Override
    public boolean handlerFunc(String func) {
        if(null != getSuccessor()){
            return getSuccessor().handlerFunc(func);
        }
        return false;
    }

}
