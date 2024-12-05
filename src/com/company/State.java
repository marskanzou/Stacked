package com.company;
public class State implements Comparable<State> {
    GameGrid grid;
    int cost;

    public State(GameGrid grid, int cost) {
        this.grid = grid;
        this.cost = cost;

    }

    @Override
    public int compareTo(State other) {
        // Custom comparison based on cost
        if (this.cost < other.cost) {
            return -1;
        } else if (this.cost > other.cost) {
            return 1;
        } else {
            return 0;
        }
    }
}
