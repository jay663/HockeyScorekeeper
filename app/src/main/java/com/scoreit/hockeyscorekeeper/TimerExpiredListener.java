package com.scoreit.hockeyscorekeeper;

public interface TimerExpiredListener {
    void onTimerExpired(String source, boolean isManual);
}
