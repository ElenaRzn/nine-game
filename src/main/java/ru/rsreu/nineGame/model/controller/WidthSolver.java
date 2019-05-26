package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.data.State;

import java.util.*;

public class WidthSolver implements ISolver {

    /** глубина поиска. */
    private int D;

    /** длина пути решения.*/
    private int L;

    /** общее число порожденных вершин. */
    private int N;

    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<State> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        queue.push(start);
        D ++;
        route.put(start, null);

        int step = 0;
        while(step<stepsCount) {
            State current = queue.removeLast();
            List<State> possible = current.getPossible();
            N += possible.size();

            for (State state: possible) {
                if(!route.containsKey(state)) {
                    route.put(state, current);
                    queue.push(state);
                    D++;
                }
            }
            step++;

            if(route.containsKey(target)) {
                System.out.println("break");
                break;
            }

        }

        checkCouldSolve(route, stepsCount);
        List<State> result = buildPath(route);
        L = result.size();

        System.out.println("Глубина поиска D = " + step);
        System.out.println("Длина пути решения L = " + L);
        System.out.println("Общее число порожденных вершин N = " + N);
        System.out.println("M = " + D );
        return result;
    }

    @Override
    public int getN() {
        return N;
    }


}
