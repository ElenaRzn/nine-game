package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.controller.utils.IEstimatedFunction;
import ru.rsreu.nineGame.model.controller.utils.LevenstainFunction;
import ru.rsreu.nineGame.model.controller.vo.EstimatedSituation;
import ru.rsreu.nineGame.model.data.State;

import java.util.*;

/**
 * <b>Стратегия ветвей и границ.</b>
 * Основана на переборе в ширину с параллельной оценкой затрат суммарного частичного пути.
 * Алгоритм движется волной по тем направлениям, по которым суммарные затраты меньше.
 */
public class BranchBoundStrategySolver implements ISolver {

    /** глубина поиска. */
    private int D;

    /** длина пути решения.*/
    private int L;

    /** общее число порожденных вершин. */
    private int N;

    private IEstimatedFunction function = new LevenstainFunction();

    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<EstimatedSituation> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        queue.push(new EstimatedSituation(start, function.estimate(start)));
        D ++;

        route.put(start, null);

        int step = 0;
        // пока есть шаги или  не нашли целевую ситуацию
        while(step<stepsCount) {
            //найти с минимальной оценкой - она будет новый current
            EstimatedSituation current = EstimatedSituation.getMin(queue);
            List<State> possible = current.getState().getPossible();
            N+= possible.size();

            // оцениваем, кладем в очередь
            for (State state: possible) {
                if(!route.containsKey(state)) {
                    route.put(state, current.getState());
                    queue.push(new EstimatedSituation(state, function.estimate(state) + current.getMark()));
                    D ++;
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
