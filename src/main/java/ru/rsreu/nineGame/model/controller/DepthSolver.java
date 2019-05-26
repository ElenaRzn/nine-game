package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.data.State;

import java.util.*;

public class DepthSolver implements ISolver {
    /** глубина поиска. */
    protected int D;

    /** длина пути решения.*/
    private int L;

    /** общее число порожденных вершин. */
    protected int N;

    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<State> stack = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        stack.push(start);
        D ++;
        route.put(start, null);
        int step = 0;
        while (step < stepsCount) {
            State current = stack.getFirst();
            while (stack.size() > 1 && current.getNumber() == current.getPossible().size()-1) {
                stack.removeFirst();
                current = stack.getFirst();
            }
            State next = getBestNextState(current);
            N ++;
            stack.push(next);
            D ++;
            route.put(next, current);
            System.out.println(stack.size());
            System.out.println(current.toString());

            if(route.containsKey(target)) {
                System.out.println("break");
                break;
            }

            step ++;
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

    protected State getBestNextState(State current) {
        return current.getNextState();

    }

    @Override
    public int getN() {
        return N;
    }
}
