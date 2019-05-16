package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.controller.utils.IEstimatedFunction;
import ru.rsreu.nineGame.model.controller.utils.LevenstainFunction;
import ru.rsreu.nineGame.model.data.State;

import java.util.*;

/**
 * <b>Стратегия ветвей и границ.</b>
 * Основана на переборе в ширину с параллельной оценкой затрат суммарного частичного пути.
 * Алгоритм движется волной по тем направлениям, по которым суммарные затраты меньше.
 */
public class BranchBoundStrategySolver implements ISolver {

    private IEstimatedFunction function = new LevenstainFunction();

    private class Mark implements Comparable<Mark>{
        State state;
        int mark;

        Mark(State state, int mark) {
            this.mark = mark;
            this.state = state;
        }

        public int compareTo(Mark m) {
            return this.mark - m.mark;
        }
    }

    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<Mark> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        queue.push(new Mark(start, function.estimate(start)));
        route.put(start, null);

        int step = 0;
        // пока есть шаги или  не нашли целевую ситуацию
        while(step<stepsCount) {
            //найти с минимальной оценкой - она будет новый current
            Mark current = getMin(queue);
            List<State> possible = current.state.getPossible();

            // оцениваем, кладем в очередь
            for (State state: possible) {
                if(!route.containsKey(state)) {
                    route.put(state, current.state);
                    queue.push(new Mark(state, function.estimate(state) + current.mark));
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

    /**
     * Поиск ситуации с минимальным суммарным путем и удаление из очереди.
     * @param queue - оцередь ситуаций
     * @return ситуация с миимальной оценкой суммарного пути
     */
    private Mark getMin(LinkedList<Mark> queue) {
        Mark min = queue.get(0);
        for (Mark mark: queue) {
            if(min.mark > mark.mark) {
                min = mark;
            }
        }
        //удаление из очереди
        queue.remove(min);
        return min;
    }
}
