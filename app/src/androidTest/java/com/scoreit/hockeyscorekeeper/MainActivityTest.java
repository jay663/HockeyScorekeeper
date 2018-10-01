package com.scoreit.hockeyscorekeeper;

import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

public class MainActivityTest {
    private String expected;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    @Test
    public void testScreenContainsPlayGameButton() throws Exception{
        // Arrange - setup the expected result
        Intents.init();
        expected = "Start Game";

        // Act
        // No Action for this just check it
        mActivityTestRule.launchActivity(new Intent());

        // Assert - assume the button text matches Start Game
        onView(withId(R.id.btnStartGame))
                .check(matches(withText(containsString(expected))));

        Intents.release();
    }


    @Test
    public void testStartGameButtonOpensActivity() throws Exception{
        // Arrange - setup the expected result
        Intents.init();

        // Act - click the start game button
        onView(withId(R.id.btnStartGame))
                .perform(click());

        // Assert - assume the select team activity is started
        intended(hasComponent(SelectTeamsActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testTeamsButtonOpensActivity() throws Exception{
        // Arrange - setup the expected result
        Intents.init();

        // Act - click the start game button
        onView(withId(R.id.btnAddTeam))
                .perform(click());

        // Assert - assume the select team activity is started
        intended(hasComponent(ListOfTeamsActivity.class.getName()));
        Intents.release();
    }
}