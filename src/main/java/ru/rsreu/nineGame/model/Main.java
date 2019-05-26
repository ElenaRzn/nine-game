package ru.rsreu.nineGame.model;

import ru.rsreu.nineGame.model.controller.*;
import ru.rsreu.nineGame.model.data.State;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        ISolver solver = new GradientSolver();
        ISolver solver = new DepthSolver();
//        ISolver solver = new WidthSolver();
//        ISolver solver = new PartialBestPathSolver();
//        ISolver solver = new BranchBoundStrategySolver();
//        ISolver solver = new EqualPricesSolver();

        long start = System.currentTimeMillis();
        List<State> states = solver.solve(new State(new int[][]{{0, 1, 3},
                {4, 2, 6}, {7, 5, 8}}), new State(State.TARGET));

        long end = System.currentTimeMillis();


        for (State state: states
             ) {
            System.out.println(state.toString());
        }

        double tc = (double) (end - start) / solver.getN();
        System.out.println("Эффективность = " + tc);
    }
}
