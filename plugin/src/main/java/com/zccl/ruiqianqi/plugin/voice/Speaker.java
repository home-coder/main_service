package com.zccl.ruiqianqi.plugin.voice;

/**
 * Created by ruiqianqi on 2016/11/25 0025.
 */

public class Speaker {
    // ���߷�����
    public static final int OFF_LINE_SPEAKER = 0;
    // ���߷�����
    public static final int ON_LINE_SPEAKER = 1;
    // ��Ƿ�����
    public static final int OFF_LINE_PLUS_SPEAKER = 2;

    // ���߻�������
    private int offOnType;
    // ������
    private String speakerName = "jiajia";
    // ��Ƶ�������б�
    private String streamTypeSelected = "3";
    // ����
    private String speechPitchValue = "50";
    // ����
    private String speechRateValue = "50";
    // ����
    private String speechVolumeValue = "100";

    // ��˼�سۣ��ƴ�Ѷ��֮��ǡ�
    // ����������
    private int speakerIndex;
    // ��˼�سۡ�
    // ����������
    private String language;

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerSelected) {
        this.speakerName = speakerSelected;
    }

    public int getOffOnType() {
        return offOnType;
    }

    public void setOffOnType(int offOnType) {
        this.offOnType = offOnType;
    }

    public String getSpeechPitchValue() {
        return speechPitchValue;
    }

    public void setSpeechPitchValue(String speechPitchValue) {
        this.speechPitchValue = speechPitchValue;
    }

    public String getSpeechRateValue() {
        return speechRateValue;
    }

    public void setSpeechRateValue(String speechRateValue) {
        this.speechRateValue = speechRateValue;
    }

    public String getSpeechVolumeValue() {
        return speechVolumeValue;
    }

    public void setSpeechVolumeValue(String speechVolumeValue) {
        this.speechVolumeValue = speechVolumeValue;
    }

    public String getStreamTypeSelected() {
        return streamTypeSelected;
    }

    public void setStreamTypeSelected(String streamTypeSelected) {
        this.streamTypeSelected = streamTypeSelected;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getSpeakerIndex() {
        return speakerIndex;
    }

    public void setSpeakerIndex(int speakerIndex) {
        this.speakerIndex = speakerIndex;
    }
}
