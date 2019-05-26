package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.data.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ISolver {
    int STEPS_COUNT = 1000000;

    State TARGET = new State(State.TARGET);

    List<State> solve(State start, State target, int stepsCount);

    default List<State> solve(State start, State target) {
        return solve(start, target, STEPS_COUNT);
    }

    default void checkCouldSolve(Map<State, State> route, int stepsCount) {
        if(!route.containsKey(TARGET)) {
            throw new RuntimeException("Could not solve in " + stepsCount + " steps");
        }
    }

    default List<State> buildPath(Map<State, State> route) {
        List<State> result = new ArrayList<>();
        State target = TARGET;

        while (target != null) {
            result.add(target);
            target = route.get(target);
        }

        Collections.reverse(result);
        return result;
    }

    int getN();
}
