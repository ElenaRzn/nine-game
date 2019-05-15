package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.data.State;

import java.util.*;

public class WidthSolver implements ISolver {

    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<State> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        queue.push(start);
        route.put(start, null);

        int step = 0;
        while(step<stepsCount) {
            State current = queue.removeLast();
            List<State> possible = current.getPossible();

            for (State state: possible) {
                if(!route.containsKey(state)) {
                    route.put(state, current);
                    queue.push(state);
                }
            }
            step++;

            if(route.containsKey(target)) {
                System.out.println("break");
                break;
            }

        }

        checkCouldSolve(route, stepsCount);
        return buildPath(route);
    }
}
