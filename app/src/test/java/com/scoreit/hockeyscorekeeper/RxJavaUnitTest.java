package com.scoreit.hockeyscorekeeper;

import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.RosterCount;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

public class RxJavaUnitTest {
    String result="";
    Player players[] =
            {
                    new Player(8, 8, "Joe Granger", "C"),
                    new Player(9, 8, "Zachery Orange", "LW"),
                    new Player(27, 8, "Joshua Noon", "RW"),
                    new Player(47, 8, "Matt Blue", "D"),
                    new Player(88, 8, "Isaac Carr", "D"),
                    new Player(31, 8, "Sergei Green", "G"),
                    new Player(48, 8, "Aaron Cole", "C"),
                    new Player(39, 8, "Harry Hall", "LW"),
                    new Player(89, 8, "Terrance Bicolli", "RW"),
                    new Player(44, 8, "Marc Villangio", "D"),
                    new Player(61, 8, "Dave Red", "D"),
                    new Player(30, 8, "Dominic Mitchell", "G"),

                    new Player(18, 9, "Pierre Lucra", "C"),
                    new Player(9, 9, "Murray Ferris", "LW"),
                    new Player(13, 9, "Jasper Ghost", "RW"),
                    new Player(8, 9, "Manny Carbone", "D"),
                    new Player(3, 9, "Harris Wininger", "D"),
                    new Player(72, 9, "	Randy Grayson", "G"),
                    new Player(10, 9, "Alexander Savardie", "C"),
                    new Player(38, 9, "Tom Partie", "LW"),
                    new Player(26, 9, "Oscar Doever", "RW"),
                    new Player(23, 9, "	Mario Caparzo", "D"),
                    new Player(58, 9, "	Sam Hill", "D"),
                    new Player(70, 9, "Dennis Calverta", "G"),

                    new Player(55, 10, "Gary Slater", "C"),
                    new Player(81, 10, "Kyle Smith", "LW"),
                    new Player(26, 10, "Mick Blake", "RW"),
            };


    RosterCount eligibleRosters[] =
            {
                    new RosterCount(8, 12)
            };


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