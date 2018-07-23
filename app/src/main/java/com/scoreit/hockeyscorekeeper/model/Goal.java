package com.scoreit.hockeyscorekeeper.model;

import java.io.Serializable;
import java.sql.Time;

public class Goal implements Serializable {
    public int scoringPlayerId;
    public int assistedPlayerId;
    public int secondaryAssistPlayerId;
    public int teamId;
    public Time timeOfGoal;
}
