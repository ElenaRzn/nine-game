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

    private IEstimatedFunction function = new LevenstainFunction();

    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<EstimatedSituation> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        queue.push(new EstimatedSituation(start, function.estimate(start)));
        route.put(start, null);

        int step = 0;
        // пока есть шаги или  не нашли целевую ситуацию
        while(step<stepsCount) {
            //найти с минимальной оценкой - она будет новый current
            EstimatedSituation current = EstimatedSituation.getMin(queue);
            List<State> possible = current.getState().getPossible();

            // оцениваем, кладем в очередь
            for (State state: possible) {
                if(!route.containsKey(state)) {
                    route.put(state, current.getState());
                    queue.push(new EstimatedSituation(state, function.estimate(state) + current.getMark()));
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

//    /**
//     * Поиск ситуации с минимальным суммарным путем и удаление из очереди.
//     * @param queue - оцередь ситуаций
//     * @return ситуация с миимальной оценкой суммарного пути
//     */
//    private EstimatedSituation getMin(LinkedList<EstimatedSituation> queue) {
//        EstimatedSituation min = queue.get(0);
//        for (EstimatedSituation estimatedSituation : queue) {
//            if(min.getMark() > estimatedSituation.getMark()) {
//                min = estimatedSituation;
//            }
//        }
//        //удаление из очереди
//        queue.remove(min);
//        return min;
//    }
}
