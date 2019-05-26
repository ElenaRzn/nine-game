package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.controller.utils.IEstimatedFunction;
import ru.rsreu.nineGame.model.controller.utils.LevenstainFunction;
import ru.rsreu.nineGame.model.data.State;

import java.util.*;

/**
 * <b>Поиск по градиенту</b>.
 * Основан на поиске в глубину. В качестве оценочной функции используется Расстояние Левенштейна {@link LevenstainFunction}
 */
public class GradientSolver extends   DepthSolver {

    private IEstimatedFunction function = new LevenstainFunction();

    @Override
    protected State getBestNextState(State current) {
        List<State> possible = current.getPossible();
        N += possible.size();
        Map<State, Integer> map = new HashMap<>();
        for (State state: possible) {
            map.put(state, function.estimate(state));
        }

        State result = (State) map.keySet().toArray()[0];
        int min = (int) map.values().toArray()[0];
        for (Map.Entry<State, Integer> entry: map.entrySet()) {
            if(min > entry.getValue()) {
                result = entry.getKey();
                min = entry.getValue();
            }
        }

        return result;
    }
}
