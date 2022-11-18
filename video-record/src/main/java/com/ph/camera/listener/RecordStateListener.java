package com.ph.camera.listener;

public interface RecordStateListener {

    void recordStart();
    void recordEnd(long time);
    void recordCancel();
}
