package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.data.State;

import java.util.List;

/**
 * <b>Стратегия ветвей и границ.</b>
 * Основана на переборе в ширину с параллельной оценкой затрат суммарного частичного пути.
 * Алгоритм движется волной по тем направлениям, по которым суммарные затраты меньше.
 */
public class BranchBoundStrategySolver implements ISolver {
    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        return null;
    }
}
