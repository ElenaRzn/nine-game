package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.data.State;

import java.util.*;

public class DepthSolver implements ISolver {

    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<State> stack = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        stack.push(start);
        route.put(start, null);

        while (stack.size() > 0 && stack.size() < stepsCount) {
            State current = stack.getFirst();
            while (stack.size() > 1 && current.getNumber() == current.getPossible().size()-1) {
                stack.removeFirst();
                current = stack.getFirst();
            }
            State next = getBestNextState(current);
            stack.push(next);
            route.put(next, current);
            System.out.println(stack.size());


            if(route.containsKey(target)) {
                System.out.println("break");
                break;
            }
        }

//        if(!route.containsKey(target)) {
//            System.out.println(stack.getLast().toString());
//            throw new RuntimeException("Could not solve in " + stepsCount + " steps");
//        }
        checkCouldSolve(route, stepsCount);


//        List<State> result = new ArrayList<>();
//
//        while (target != null) {
//            result.add(target);
//            target = route.get(target);
//        }
//
//        Collections.reverse(result);
//        return result;
        return buildPath(route);
    }

    protected State getBestNextState(State current) {
        return current.getNextState();

    }
}
