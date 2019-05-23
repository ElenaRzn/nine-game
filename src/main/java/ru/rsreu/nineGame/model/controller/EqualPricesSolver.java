package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.controller.vo.EstimatedSituation;
import ru.rsreu.nineGame.model.data.State;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>Стратегия равных цен</p>
 * Алгоритм основан на переборе в ширину. Для кадой встретившейся ситуации запоминается кратчайший путь до нее (час.
 * Если при переборе вариантов ситуация встречается повторно, выбирается кратчайший ведущий в нее путь.
 */
public class EqualPricesSolver implements ISolver {
    private int counter;


    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<EstimatedSituation> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();

        queue.push(new EstimatedSituation(start, 1));
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
                }
                EstimatedSituation situation = new EstimatedSituation(state, current.getMark() + 1);
                //в очереди нет такой Ситуации
                EstimatedSituation contained = contain(queue, situation.getState());

                if(contained == null) {
                    queue.push(situation);
                } else if(contained.getMark() > situation.getMark()) {
                    // должны удалить из очереди и пути и поместить новую.
                    queue.remove(contained);
                    route.remove(contained.getState());
                    queue.push(situation);
                    route.put(situation.getState(), current.getState());
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

    private EstimatedSituation contain(LinkedList<EstimatedSituation> queue, State state) {
        for (EstimatedSituation situation: queue) {
            if(situation.getState().equals(state)) {
                return situation;
            }
        }
        return null;
    }
}
