package com.zccl.ruiqianqi.plugin.voice;

/**
 * Created by ruiqianqi on 2016/9/28 0028.
 */

public abstract class AbstractVoice {

    /**
     * ��ʼ����������
     */
    public abstract void initSpeech();

    /************************************���������ܽӿڡ�******************************************/
    /**
     * �����Ƿ�ѭ������
     * @param recyclerListen
     */
    public void setRecyclerListen(boolean recyclerListen){
    }

    /**
     * �л�����
     * @param language
     */
    public void switchLanguage(String language){
    }

    /**
     * �����µķ�����
     * @param speaker
     */
    public void setTtsParams(Speaker speaker){
    }

    /************************************��������ؽӿڡ�******************************************/
    /**
     * ������صĻص�
     */
    public interface WakeupCallback{
        // ���ѳɹ�
        void wakeSuccess(WakeInfo wakeInfo);
        // ��Ƶ����
        void onAudio(byte[] audio, int audioLen);
        // ����ʧ��
        void wakeFailure(Throwable e);
        // ���λ��Ѽ�˵��
        void oneShot(String msg);

        // �ǲ��Ǵ�������
        boolean isTouchWake();
    }

    /**
     * ���û��Ѻ�Ļص��ӿڡ�������ȡ���ݡ�
     * @param wakeupCallback
     */
    public void setWakeupCallback(WakeupCallback wakeupCallback){
    }

    /**
     * ���ػ��Ѻ�Ļص��ӿڡ�������ȡ���ݡ�
     */
    public WakeupCallback getWakeupCallback(){
        return null;
    }

    /**
     * �������ѽӿ�
     */
    public void startWakeup(){
    }

    /**
     * ֹͣ���ѽӿ�
     */
    public void stopWakeup(){
    }

    /**
     * ��Ҫ��⣬���������� ��Ĭ�ϵ������ǲ���Ҫ����ʵ�֡�
     * @return true ������Ҫ��⣬����������
     *          false ������Ҫ��⣬����Ҫ��������
     */
    public boolean reboot(){
        return false;
    }

    /**
     * ���ÿ��Ź���ֵ
     * @param reboot
     */
    public void setReboot(boolean reboot){
    }

    /**
     * �õ����Ź���ֵ
     * @return
     */
    public boolean isReboot(){
        return false;
    }

    /************************************�����������ؽӿڡ�**************************************/
    /**
     * �������Ļص�
     */
    public interface UnderstandCallback{

        // ����������
        int UNDERSTAND_FAILURE = 0;
        // �������ɹ�
        int UNDERSTAND_SUCCESS = 1;

        // ������С
        void onVolumeChanged(int volume);
        // ��ʼ˵��
        void onBeginOfSpeech();
        // ����˵��
        void onEndOfSpeech();
        // ���ؽ��
        void onResult(String result);
        // ������
        void onError(Throwable e);
    }

    /**
     * �����������ص��ӿڡ����ϡ�
     * @param understandCallback
     */
    public void addUnderstandCallback(String key, UnderstandCallback understandCallback) {
    }

    /**
     * ɾ����Ӧ�ص��ӿ�
     * @param key
     */
    public void removeUnderstandCallback(String key){
    }

    /**
     * ��ʼ�������
     */
    public void startUnderstand(){
    }

    /**
     * ֹͣ¼�����ϴ�����
     */
    public void stopUnderstand(){
    }

    /**
     * ȡ���Ự
     */
    public void cancelUnderstand(){
    }

    /**
     * ֱ�Ӽ������������������
     * @param dataS
     */
    public void writeUnderstand(byte[] dataS){
    }


    /*********************************���﷨ʶ����ؽӿڡ�*****************************************/
    /**
     * �﷨ʶ��Ļص�
     */
    public interface RecognizerCallback{

        // ��д����
        int LISTEN_ERROR = -1;
        // ������д
        int LISTEN = 0;
        // ���������
        int ONLINE_WORD = 1;
        // ���������
        int OFFLINE_WORD = 2;
        // �������
        int LISTEN_UNDERSTAND = 3;

        // ������С
        void onVolumeChanged(int volume);
        // ��ʼ˵��
        void onBeginOfSpeech();
        // ����˵��
        void onEndOfSpeech();
        // ���ؽ��
        void onResult(String result, int flag);
        // ʶ�����
        void onError(Throwable e);
    }

    /**
     * �����﷨ʶ��ص��ӿڡ����ϡ�
     * @param key
     * @param recognizerCallback
     */
    public void addRecognizerCallback(String key, RecognizerCallback recognizerCallback){
    }

    /**
     * ɾ����Ӧ�ص��ӿ�
     * @param key
     */
    public void removeRecognizerCallback(String key){
    }

    /**
     * ��ʼ�﷨ʶ��
     */
    public void startRecognizer(){
    }

    /**
     * ��ʼ�﷨ʶ��
     * @param recFlag
     * 0��������д
     * 1�����������
     * 2�����������
     */
    public void startRecognizer(int recFlag){
    }

    /**
     * ֹͣ¼�����ϴ�����
     */
    public void stopRecognizer(){
    }

    /**
     * ȡ���Ự
     */
    public void cancelRecognizer(){
    }

    /**
     * ֱ�Ӽ����﷨ʶ����������
     * @param dataS
     */
    public void writeRecognizer(byte[] dataS){
    }

    /***********************************�����������ؽӿڡ�***************************************/
    /**
     * ����ʶ��Ļص��ӿ�
     */
    public interface TextUnderCallback{
        // ���������
        void onResult(String result);
        // ����������
        void onError(Throwable e);
    }

    /**
     * �����ı����ص��ӿ�
     * @param textUnderCallback
     */
    public void setTextUnderCallback(TextUnderCallback textUnderCallback){
    }

    /**
     * ֱ�����ı�����������������
     * @param text
     */
    public void startText(String text){
    }

    /************************************�������ϳ���ؽӿڡ�**************************************/
    /**
     * �����ϳɵĻص�
     */
    public interface SynthesizerCallback{
        // �ϳɿ�ʼ
        void OnBegin();
        // �ϳ���ͣ
        void OnPause();
        // �ϳɻָ�
        void OnResume();
        // �ϳ����
        void OnComplete(Throwable throwable, String tag);
    }

    /**
     * ��ʼ�����ϳ�
     * @param words  -------------------- ����Ҫ��������
     * @param tag    -------------------- ���Я���ı�־
     * @param synthesizerCallback
     */
    public void startTTS(String words, String tag, SynthesizerCallback synthesizerCallback){
    }

    /**
     * ��ʼ�����ϳ�
     * @param words  -------------------- ����Ҫ��������
     * @param tag    -------------------- ���Я���ı�־
     * @param runnable
     */
    public void startTTS(String words, String tag, Runnable runnable){
    }

    /**
     * ��ʼ�����ϳ�
     * @param words  -------------------- ����Ҫ��������
     * @param runnable
     */
    public void startTTS(String words, Runnable runnable){
    }

    /**
     * ��ͣ����
     */
    public void pauseTTS(){
    }

    /**
     * �ָ�����
     */
    public void resumeTTS(){
    }

    /**
     * ֹͣ����
     */
    public void stopTTS(){
    }

    /**
     * �ǲ������ڲ���
     * @return
     */
    public boolean isSpeaking(){
        return false;
    }
    /***********************************���ű���ع��ܽӿڡ�***************************************/
    /**
     * �������������﷨����    ���ƴ�Ѷ��ʹ�á���������̬������ϵ�ˡ�
     * @param rule             ������
     * @param ruleValue       ����ֵ
     */
    public void updateRule(String rule, String ruleValue){
    }

    /**
     * ��̬���أ����ߡ���������� ���ƴ�Ѷ��ʹ�á�
     * @param loadScriptType
     * {@link E_LOAD_SCRIPT_TYPE#ONLINE_ASSETS}:  �ӡ�assets���������������
     * {@link E_LOAD_SCRIPT_TYPE#OFFLINE_ASSETS}���ӡ�assets���������������
     * {@link E_LOAD_SCRIPT_TYPE#ONLINE_ABSOLUTE}:  �ӡ�����·�����������������
     * {@link E_LOAD_SCRIPT_TYPE#OFFLINE_ABSOLUTE}���ӡ�����·�����������������
     *
     * @param grammarPath     �����ű�·��
     * @param grammarDir      �﷨�ļ������ɽ���·��
     *
     */
    public void loadCmdWords(E_LOAD_SCRIPT_TYPE loadScriptType, String grammarPath, String grammarDir){
    }


    /********************************��ϵͳ״̬�仯ʱ��֪ͨ�ӿڡ�**********************************/
    // ����״̬�ı���
    public static final int NET_CHANGE = 1;
    // �绰״̬�ı���
    public static final int PHONE_CHANGE = 2;
    // ������״̬�ı���
    public static final int SENSOR_CHANGE = 3;
    // ���״̬�仯��
    public static final int BATTERY_CHANGE = 4;
    // APP״̬�ı���
    public static final int APP_STATUS_CHANGE = 5;
    // ѭ����������
    public static final int RECYCLE_LISTEN_CHANGE = 6;
    // ֹͣ��������
    public static final int STOP_LISTEN_CHANGE = 7;
    // HDMI״̬�ı���
    public static final int HDMI_CHANGE = 8;

    /**
     * ֪ͨʲô�ı���
     * @param flag
     * @param obj   Я����Ϣ
     */
    public void notifyChange(int flag, Object obj){

    }

    /**
     * ���ء����ߡ����ߡ������
     */
    public enum E_LOAD_SCRIPT_TYPE{
        ONLINE_ASSETS,
        OFFLINE_ASSETS,
        ONLINE_ABSOLUTE,
        OFFLINE_ABSOLUTE,
    }
}
