package com.scoreit.hockeyscorekeeper;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class RxJavaUnitTest {
    String result="";

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    // Simple subscription to a fix value
    @Test
    public void returnAValue(){
        result = "";
        Observable<String> observer = Observable.just("Hello"); // provides datea
        observer.subscribe(s -> result=s); // Callable as subscriber
        assertEquals("Hello", result);
    }

      @Test
      public void rxJavaCountdown(){
        boolean isPaused = false;
        Long nextValue = 0L;
        Long finalTime = 0L;
          TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
          RxJavaScoreClock countdown = new RxJavaScoreClock();
          countdown.startTimer().subscribe(testSubscriber);
          testSubscriber.assertNoErrors();
          testSubscriber.onNext(nextValue);

          finalTime = countdown.elapsedTime.get();
          assertFalse((nextValue.compareTo(100L) == 0));

      }


    @Test
    public void fromTimer(){
        Observable<Long> observable = Observable.timer(1,TimeUnit.SECONDS);

        TestObserver<Long> to = new TestObserver<>();
        observable.subscribe(to);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        to.assertValueCount(1);
        to.assertValues(0L).assertComplete();

    }




}