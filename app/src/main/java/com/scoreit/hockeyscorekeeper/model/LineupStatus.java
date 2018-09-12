package com.scoreit.hockeyscorekeeper.model;

import java.util.Arrays;
import java.util.List;

public class LineupStatus {
    public static final List<String> Status = Arrays.asList("on_ice", "bench", "penalty_box", "injured");

    public static final String ON_ICE = "on_ice";
    public static final String BENCH = "bench";
    public static final String PENALTY = "penalty";
    public static final String INJURED = "injured";
}
