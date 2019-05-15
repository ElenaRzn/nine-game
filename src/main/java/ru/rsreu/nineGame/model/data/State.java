package ru.rsreu.nineGame.model.data;

import java.util.ArrayList;
import java.util.List;

public class State {
    private final static int SIZE = 3;

    public static final int[][] TARGET =  {{1,2, 3}, {4,5, 6}, {7, 8, 0}};

    private int[][] data;

    private int number;

    private List<State> possible;

    public State(int[][] data) {
        this.data = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE ; i++) {
            for (int j = 0; j < SIZE ; j++) {
                this.data[i][j]= data[i][j];
            }
        }

//        getPossibleStates();
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public List<State> getPossible() {
        if(possible == null) {
            getPossibleStates();
        }
        return possible;
    }

    public void setPossible(List<State> possible) {
        this.possible = possible;
    }

    public State getNextState() {
        if(possible == null) {
            getPossibleStates();
        }
        State result = possible.get(number);
        number ++;
       return result;
    }

    private void getPossibleStates() {
        List<Point> points = new ArrayList<>();

        points.add(new Point(1, 0));
        points.add(new Point(0, 1));
        points.add(new Point(0, -1));
        points.add(new Point(-1, 0));

        this.possible = new ArrayList<>();

        for (Point point: points) {
            State st = move(point);
            if(st != null) {
                possible.add(st);
            }
        }
    }

    private Point getSpacePlace() {
        for (int i = 0; i < SIZE ; i++) {
            for (int j = 0; j < SIZE ; j++) {
                if(data[i][j] == 0) {
                    return new Point(i, j);
                }
            }
        }
        throw new RuntimeException("Could not find space");
    }


    private State move(Point point) {
        State result = new State(this.data);
        Point space = getSpacePlace();
        if(space.getX() + point.getX() <0 || space.getX() + point.getX() == SIZE) {
            return null;
        }
        if(space.getY() + point.getY() <0 || space.getY() + point.getY() == SIZE) {
            return null;
        }

        result.getData()[space.getX()][space.getY()] = this.getData()[space.getX() + point.getX()][space.getY() + point.getY()];
        result.getData()[space.getX() + point.getX()][space.getY() + point.getY()] = this.getData()[space.getX()][space.getY()];

        return result;

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE ; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        int[][] data = ((State) obj).getData();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE ; j++) {
                if(this.data[i][j] != data[i][j])
                    return false;

            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE ; j++) {
                hash += data[i][j] *i + j;
            }
        }
        return hash;
    }
}
