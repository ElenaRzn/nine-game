package ru.rsreu.nineGame.model.controller;

import ru.rsreu.nineGame.model.controller.utils.IEstimatedFunction;
import ru.rsreu.nineGame.model.controller.utils.LevenstainFunction;
import ru.rsreu.nineGame.model.data.State;

import java.util.*;

/**
 * <b>Поиск наилучшего частичного пути.</b>
 * Поиском в ширину генерируется TREE_LEVEL_DEPTH уровней дерева вариантов,
 * Терминальная (почледнняя) вершина анализируется оценочной ф-ей, выбирается лучшая вершина,
 * из которой поиском в ширину генерируется TREE_LEVEL_DEPTH вершин.
 */
public class PartialBestPathSolver implements ISolver{

    /** глубина поиска. */
    private int D;

    /** длина пути решения.*/
    private int L;

    /** общее число порожденных вершин. */
    private int N;

    private IEstimatedFunction function = new LevenstainFunction();

    private static final int TREE_LEVEL_DEPTH = 3;

    /** Объект для генерации дерева состояний на глубину N*/
    private ISolver width = new WidthSolver();

    @Override
    public List<State> solve(State start, State target, int stepsCount) {

        // полный путь, из которого будем составлять решений.
        Map<State, State> totalRoot = new HashMap<>();
        totalRoot.put(start, null);
        D ++;

        State current = start;

        int step = 0;
        // пока есть шаги или  не нашли целевую ситуацию
//        while(step<stepsCount) {
        while(true) {
            System.out.println(step);
            // строим дерево
            Map<State, State> tree = generateTree(current, TARGET);

            // оцениваем ситуации
            Map<State, Integer> estimatedTree = estimate(tree);
            // выбираем лучшую для нового старта
            State state = chooseBestOne(estimatedTree);

            if(!totalRoot.containsKey(state)) {
                totalRoot.put(state, current);
                D ++;
            }

            step++;
            current = state;

            if(totalRoot.containsKey(target)) {
                System.out.println("break");
                break;
            }
        }

        // проверка - нашли ли.
        checkCouldSolve(totalRoot, stepsCount);
        List<State> result = buildPath(totalRoot);
        L = result.size();

        System.out.println("Глубина поиска D = " + step);
        System.out.println("Длина пути решения L = " + L);
        System.out.println("Общее число порожденных вершин N = " + N);
        System.out.println("M = " + D );
        return result;
    }

    private Map<State, Integer> estimate(Map<State, State> map) {
        Map<State, Integer> result = new HashMap<>();
        for (State state: map.keySet()) {
            result.put(state, function.estimate(state));
        }

        return result;
    }

    private State chooseBestOne(Map<State, Integer> map) {
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

    private Map<State, State> generateTree(State start, State target) {
        LinkedList<State> queue = new LinkedList<>();
        Map<State, State> route = new HashMap<>();
        queue.push(start);
        route.put(start, null);

        int step = 0;
        while(step<TREE_LEVEL_DEPTH) {
            State current = queue.removeLast();
            List<State> possible = current.getPossible();
            N += possible.size();

            for (State state: possible) {
                if(!route.containsKey(state)) {
                    route.put(state, current);
                    queue.push(state);
                }
            }
            step++;

            if(route.containsKey(target)) {
                System.out.println("break");
                break;
            }

        }

        return route;
    }

    @Override
    public int getN() {
        return N;
    }
}
