package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.data.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ISolver {
    public final static int STEPS_COUNT = 50;

    public final static State TARGET = new State(State.TARGET);

    List<State> solve(State start, State target, int stepsCount);

    public default List<State> solve(State start, State target) {
        return solve(start, target, STEPS_COUNT);
    }

    public default void checkCouldSolve(Map<State, State> route, int stepsCount) {
        if(!route.containsKey(TARGET)) {
            throw new RuntimeException("Could not solve in " + stepsCount + " steps");
        }
    }

    public default List<State> buildPath(Map<State, State> route) {
        List<State> result = new ArrayList<>();
        State target = TARGET;

        while (target != null) {
            result.add(target);
            target = route.get(target);
        }

        Collections.reverse(result);
        return result;
    }
}
