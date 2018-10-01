package com.scoreit.hockeyscorekeeper.repositories;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameLineupDao;
import com.scoreit.hockeyscorekeeper.data.GameScoringDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.data.RosterCountDao;
import com.scoreit.hockeyscorekeeper.data.TeamDao;
import com.scoreit.hockeyscorekeeper.model.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import androidx.lifecycle.MutableLiveData;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameRepositoryTest {
    @Mock
    private GameDao mGameDao;
    @Mock
    private GameLineupDao mGameLineupDao;
    @Mock
    private GameShotsDao mGameShotsDao;
    @Mock
    private TeamDao mTeamDao;
    @Mock
    private RosterCountDao mRosterCountDao;
    @Mock
    private GameScoringDao mGameScoringDao;
    @Mock
    private HockeyDatabase mDB;

    private GameRepository mRepository;

    @Before
    public void setUp() throws Exception {
        Application app = mock(Application.class);
        mRepository = new GameRepository(app);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getGame_ReturnValidGame() {
        // Arrange
        Game expected = new Game(1,2,"awayTeam", "homeTeam",
                "2018-01-01", "arena");
        MutableLiveData<Game> returnedData = new MutableLiveData<Game>();
        returnedData.setValue(expected);
        when(mDB.getGameDao()).thenReturn(mGameDao);
        when(mGameDao.getGame(anyLong())).thenReturn(returnedData);

        // Act
        Game actual = mRepository.getGame(1).getValue();

        // Assert
        assertEquals(expected.getHomeTeam(), actual.getHomeTeam());
    }

    @Test
    public void getGame_ReturnNull() {
        // Arrange
        // How to mock the return of
        // Act
        mRepository.getGame(1);
        // Assert
    }
}