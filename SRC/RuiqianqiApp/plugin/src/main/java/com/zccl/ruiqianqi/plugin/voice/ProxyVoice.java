package com.zccl.ruiqianqi.plugin.voice;

/**
 * Created by ruiqianqi on 2016/9/28 0028.
 */

public class ProxyVoice extends AbstractVoice{

    /** ��Ƶ������ */
    protected AbstractVoice realVoice;

    /**
     * �����ӣ�����������ʵ�����
     * @param realVoice
     */
    public ProxyVoice(AbstractVoice realVoice){
        this.realVoice = realVoice;
    }

    /**
     * ��ʼ����������
     * MyApplication��OnCreate()�е���
     */
    @Override
    public void initSpeech(){
        realVoice.initSpeech();
    }

    /************************************���������ܽӿڡ�******************************************/

    /**
     * �����Ƿ�ѭ������
     * @param recyclerListen
     */
    @Override
    public void setRecyclerListen(boolean recyclerListen){
        realVoice.setRecyclerListen(recyclerListen);
    }

    /**
     * �л�����
     * @param language
     */
    @Override
    public void switchLanguage(String language){
        realVoice.switchLanguage(language);
    }

    /**
     * �����µķ�����
     * @param speaker
     */
    @Override
    public void setTtsParams(Speaker speaker){
        realVoice.setTtsParams(speaker);
    }

    /************************************�����ѻص��ӿڡ�******************************************/
    /**
     * ���û��Ѻ�Ļص��ӿ�
     * @param wakeupCallback
     */
    @Override
    public void setWakeupCallback(WakeupCallback wakeupCallback){
        realVoice.setWakeupCallback(wakeupCallback);
    }

    /**
     * ���ػ��Ѻ�Ļص��ӿ�
     */
    @Override
    public WakeupCallback getWakeupCallback(){
        return realVoice.getWakeupCallback();
    }

    /**
     * ���ѿ����ӿ�
     */
    @Override
    public void startWakeup(){
        realVoice.startWakeup();
    }

    /**
     * ��Ҫ��⣬���������� ��Ĭ�ϵ������ǲ���Ҫ��
     * @return true ������Ҫ��⣬����������
     *          false ������Ҫ��⣬����Ҫ��������
     */
    @Override
    public boolean reboot(){
        return false;
    }

    /**
     * ���ÿ��Ź���ֵ
     * @param reboot
     */
    @Override
    public void setReboot(boolean reboot){
        realVoice.setReboot(reboot);
    }

    /**
     * �õ����Ź���ֵ
     * @return
     */
    @Override
    public boolean isReboot(){
        return false;
    }

    /************************************�����������ؽӿڡ�**************************************/
    /**
     * �����������ص��ӿڡ����ϡ�
     * @param understandCallback
     */
    @Override
    public void addUnderstandCallback(String key, ProxyVoice.UnderstandCallback understandCallback) {
        realVoice.addUnderstandCallback(key, understandCallback);
    }

    /**
     * ɾ����Ӧ�ص��ӿ�
     * @param key
     */
    @Override
    public void removeUnderstandCallback(String key){
        realVoice.removeUnderstandCallback(key);
    }

    /**
     * ��ʼ�������
     */
    @Override
    public void startUnderstand(){
        realVoice.startUnderstand();
    }

    /**
     * ֹͣ¼�����ϴ�����
     */
    @Override
    public void stopUnderstand(){
        realVoice.stopUnderstand();
    }

    /**
     * ȡ���Ự
     */
    @Override
    public void cancelUnderstand(){
        realVoice.cancelUnderstand();
    }

    /**
     * ֱ�Ӽ������������������
     * @param dataS
     */
    @Override
    public void writeUnderstand(byte[] dataS){
        realVoice.writeUnderstand(dataS);
    }


    /************************************�﷨ʶ����ؽӿ�******************************************/
    /**
     * �����﷨ʶ��ص��ӿڡ����ϡ�
     * @param key
     * @param recognizerCallback
     */
    @Override
    public void addRecognizerCallback(String key, RecognizerCallback recognizerCallback){
        realVoice.addRecognizerCallback(key, recognizerCallback);
    }

    /**
     * ɾ����Ӧ�ص��ӿ�
     * @param key
     */
    @Override
    public void removeRecognizerCallback(String key){
        realVoice.removeRecognizerCallback(key);
    }

    /**
     * ��ʼ�﷨ʶ��
     */
    @Override
    public void startRecognizer(){
        realVoice.startRecognizer();
    }

    /**
     * ��ʼ�﷨ʶ��
     * @param recFlag
     * 0��������д
     * 1�����������
     * 2�����������
     */
    @Override
    public void startRecognizer(int recFlag){
        realVoice.startRecognizer(recFlag);
    }

    /**
     * ֹͣ¼�����ϴ�����
     */
    @Override
    public void stopRecognizer(){
        realVoice.stopRecognizer();
    }

    /**
     * ȡ���Ự
     */
    @Override
    public void cancelRecognizer(){
        realVoice.cancelRecognizer();
    }

    /**
     * ֱ�Ӽ����﷨ʶ����������
     * @param dataS
     */
    @Override
    public void writeRecognizer(byte[] dataS){
        realVoice.writeRecognizer(dataS);
    }

    /***********************************�����������ؽӿڡ�***************************************/
    /**
     * �����ı����ص��ӿ�
     * @param textUnderCallback
     */
    @Override
    public void setTextUnderCallback(TextUnderCallback textUnderCallback){
        realVoice.setTextUnderCallback(textUnderCallback);
    }

    /**
     * ֱ�����ı�����������������
     * @param text
     */
    @Override
    public void startText(String text){
        realVoice.startText(text);
    }

    /************************************�������ϳ���ؽӿڡ�**************************************/
    /**
     * ��ʼ�����ϳ�
     * @param words  -------------------- ����Ҫ��������
     * @param tag    -------------------- ���Я���ı�־
     * @param synthesizerCallback
     */
    @Override
    public void startTTS(String words, String tag, SynthesizerCallback synthesizerCallback){
        realVoice.startTTS(words, tag, synthesizerCallback);
    }

    /**
     * ��ͣ����
     */
    @Override
    public void pauseTTS(){
        realVoice.pauseTTS();
    }

    /**
     * �ָ�����
     */
    @Override
    public void resumeTTS(){
        realVoice.resumeTTS();
    }

    /**
     * ֹͣ����
     */
    @Override
    public void stopTTS(){
        realVoice.stopTTS();
    }

    /***********************************���ű���ع��ܽӿڡ�***************************************/
    /**
     * �������������﷨����    ���ƴ�Ѷ��ʹ�á���������̬������ϵ�ˡ�
     * @param rule             ������
     * @param ruleValue       ����ֵ
     */
    @Override
    public void updateRule(String rule, String ruleValue){
        realVoice.updateRule(rule, ruleValue);
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
    @Override
    public void loadCmdWords(E_LOAD_SCRIPT_TYPE loadScriptType, String grammarPath, String grammarDir){
        realVoice.loadCmdWords(loadScriptType, grammarPath, grammarDir);
    }

    /********************************��ϵͳ״̬�仯ʱ��֪ͨ�ӿڡ�**********************************/
    /**
     * ֪ͨʲô�ı���
     * @param flag
     * @param obj
     */
    @Override
    public void notifyChange(int flag, Object obj){
        realVoice.notifyChange(flag, obj);
    }

}
