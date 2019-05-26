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
    /** глубина поиска. */
    private int D;

    /** длина пути решения.*/
    private int L;

    /** общее число порожденных вершин. */
    private int N;


    @Override
    public List<State> solve(State start, State target, int stepsCount) {
        LinkedList<EstimatedSituation> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();

        queue.push(new EstimatedSituation(start, 1));
        D++;
        route.put(start, null);

        int step = 0;
        // пока есть шаги или  не нашли целевую ситуацию
        while(step<stepsCount) {
            //найти с минимальной оценкой - она будет новый current
            EstimatedSituation current = EstimatedSituation.getMin(queue);
            List<State> possible = current.getState().getPossible();
            N += possible.size();

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
                    D ++;
                } else if(contained.getMark() > situation.getMark()) {
                    // должны удалить из очереди и пути и поместить новую.
                    queue.remove(contained);
                    route.remove(contained.getState());
                    queue.push(situation);
                    D++;
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
        List<State> result = buildPath(route);
        L = result.size();

        System.out.println("Глубина поиска D = " + step);
        System.out.println("Длина пути решения L = " + L);
        System.out.println("Общее число порожденных вершин N = " + N);
        System.out.println("M = " + D );
        return result;
    }

    private EstimatedSituation contain(LinkedList<EstimatedSituation> queue, State state) {
        for (EstimatedSituation situation: queue) {
            if(situation.getState().equals(state)) {
                return situation;
            }
        }
        return null;
    }

    @Override
    public int getN() {
        return N;
    }
}
