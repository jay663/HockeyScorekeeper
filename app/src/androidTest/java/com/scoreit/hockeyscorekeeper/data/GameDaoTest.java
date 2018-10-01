package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class GameDaoTest {

    private HockeyDatabase mDatabase;
    private GameDao gamesDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                HockeyDatabase.class)
                .allowMainThreadQueries()
                .build();

        gamesDao = mDatabase.getGameDao();
    }

    @After
    public void tearDown() throws Exception {
        mDatabase.close();
    }

    @Test
    public void insertGame_VerifyGameIsAdded() throws InterruptedException {
        // Arrange
        String expected = "AwwayTeam";
        Game game = new Game(1, 2, "AwwayTeam", "HomeTeam",
                "2018-09-19", "ArenaName");

        // Act
        gamesDao.insert(game);

        // Assert
        Game actual;
        List<Game> games = gamesDao.getAll().getValue();
        actual = games.get(1);

        assertEquals(actual.getAwayTeam(), expected);
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getGame() {
    }

    @Test
    public void delete() {
    }
}