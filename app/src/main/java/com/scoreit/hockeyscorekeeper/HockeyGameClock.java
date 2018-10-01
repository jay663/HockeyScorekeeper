package com.scoreit.hockeyscorekeeper;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import android.os.CountDownTimer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HockeyGameClock extends BaseObservable {
    private java.util.Date dt = new Date();

    private int period;
    private boolean isPaused;
    private boolean isCanceled;
    private String clockDisplay;
    private long timeRemaining;
    private long initialCountDownInterval;
    private long initialTimerMilliseconds;

    public CountDownTimer timer;
    private TimerExpiredListener listener;

    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
        String source = Thread.currentThread().getStackTrace()[1].getMethodName();
        notifyPropertyChanged(BR.clockDisplay);

        if(listener != null && timeRemaining == 0){
            listener.onTimerExpired(source, !source.equals("onTick"));
        }
    }

    public void setTimeExpiredListener(TimerExpiredListener listener) {
        this.listener = listener;
    }

    @Bindable
    public String getClockDisplay() {
        dt.setTime(timeRemaining);
        clockDisplay = sdf.format(dt);
        return clockDisplay;
    }

    @Bindable
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
        notifyPropertyChanged(BR.period);
    }


    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public HockeyGameClock(long millisInFuture, long countDownInterval) {
        setPeriod(1);
        isPaused = false;
        isCanceled = false;
        initialCountDownInterval = countDownInterval;
        initialTimerMilliseconds = millisInFuture;
        setTimeRemaining(millisInFuture);
        timer = setupTimer(millisInFuture, countDownInterval);
        this.listener = null;
    }

    protected CountDownTimer setupTimer(final long millisInFuture, final long countDownInterval) {
        return timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPaused || isCanceled)
                {
                    //If the user request to cancel or paused the
                    //CountDownTimer we will cancel the current instance
                    timer.cancel();
                }
                else {
                    setTimeRemaining(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                setTimeRemaining(0);
                int modifiedPeriod = getPeriod() + 1;
                setPeriod(modifiedPeriod);

            }
        };
    }

    public void start(){
        if (isPaused){
            resume();
            return;
        }

        if (isCanceled) {
            restart();
            return;
        }

        timer.start();
    }

    public void resume(){
        isPaused = false;
        timer = setupTimer(timeRemaining, initialCountDownInterval);
        timer.start();
    }

    public void pause(){
        isPaused = true;
    }

    public void stop(){
        isCanceled = true;
        isPaused = false;
        setTimeRemaining(0);
    }

    public void restart() {
        isPaused = false;
        isCanceled = false;
        timer.cancel();
        timer = setupTimer(initialTimerMilliseconds, initialCountDownInterval);
        setTimeRemaining(initialTimerMilliseconds);
        timer.start();
    }

    public void setTimerForNextPeriod() {
        isPaused = true;
        isCanceled = false;
        timer.cancel();
        timer = setupTimer(initialTimerMilliseconds, initialCountDownInterval);
        setTimeRemaining(initialTimerMilliseconds);
    }

    public boolean addMinute(){
        if (!isCanceled && isPaused) {
            long modifiedTime = getTimeRemaining();
            modifiedTime += 60000;
            setTimeRemaining(modifiedTime);
            return true;
        }

        return false;
    }

    public boolean minusMinute(){
        if (!isCanceled && isPaused)
        {
            long modifiedTime = getTimeRemaining();
            // Should never be negative
            if(modifiedTime >= 60000) {
                modifiedTime -= 60000;
            }else{
                modifiedTime = 0;
            }

            setTimeRemaining(modifiedTime);
            return true;
        }

        return false;
    }

    public boolean addSecond(){
        if (!isCanceled && isPaused) {
            long modifiedTime = getTimeRemaining();
            modifiedTime += 1000;
            setTimeRemaining(modifiedTime);
            return true;
        }

        return false;
    }

    public boolean minusSecond(){
        if (!isCanceled && isPaused)
        {
            long modifiedTime = getTimeRemaining();
            // Should never be negative
            if(modifiedTime >= 1000) {
                modifiedTime -= 1000;
            }else{
                modifiedTime = 0;
            }

            setTimeRemaining(modifiedTime);
            return true;
        }

        return false;
    }
}