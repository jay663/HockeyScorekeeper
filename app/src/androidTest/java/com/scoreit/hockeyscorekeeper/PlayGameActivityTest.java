package com.scoreit.hockeyscorekeeper;

import android.content.Context;
import android.content.Intent;

import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameShots;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PlayGameActivityTest {
    private GameDao mGameDao;
    private GameShotsDao mGameShotsDao;
    private HockeyDatabase mDb;
    private long mGameId;

    @Rule
    public ActivityTestRule<PlayGameActivity> mActivityTestRule = new ActivityTestRule<PlayGameActivity>(PlayGameActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent(InstrumentationRegistry.getContext(), PlayGameActivity.class);
            intent.putExtra("GAME_ID",1L);
            return intent;
        }
    };

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, HockeyDatabase.class).build();
        mGameDao = mDb.getGameDao();
        //mUserDao = mDb.getGameLineupDao();
        mGameShotsDao = mDb.getGameShotsDao();

        Game game =
                new Game(0, 1, "awayTeam",
                        "homeTeam", "2018-09-17 21:32", "arena");
        mGameId = mGameDao.insert(game);
        GameShots gameShots = new GameShots(mGameId);
        mGameShotsDao.insert(gameShots);

    }

    @Test
    public void addAwayGoalIncreasesAwayTotalScore(){
        // Arrange - setup the room db with game teams, game lineup and add game shots
        String expected = "1";
        // Act - click the away team goal button
        onView(withId(R.id.btn_away_team_score))
            .perform(click()
            );
        // Assert - assume the scoring total increased for the away team on the scoreboard
        onView(withText(R.id.away_team_total_goals))
                .check(matches(withText(expected)));
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }
}