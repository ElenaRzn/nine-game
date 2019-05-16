package ru.rsreu.nineGame.model.controller.vo;

import ru.rsreu.nineGame.model.data.State;

import java.util.LinkedList;
import java.util.Objects;

public class EstimatedSituation {
    private State state;
    private int mark;

    public EstimatedSituation(State state, int mark) {
        this.mark = mark;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public int getMark() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstimatedSituation that = (EstimatedSituation) o;
        return mark == that.mark &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, mark);
    }

    /**
     * Поиск ситуации с минимальным суммарным путем и удаление из очереди.
     * @param queue - оцередь ситуаций
     * @return ситуация с миимальной оценкой суммарного пути
     */
    public static EstimatedSituation getMin(LinkedList<EstimatedSituation> queue) {
        EstimatedSituation min = queue.get(0);
        for (EstimatedSituation estimatedSituation : queue) {
            if(min.getMark() > estimatedSituation.getMark()) {
                min = estimatedSituation;
            }
        }
        //удаление из очереди
        queue.remove(min);
        return min;
    }
}
