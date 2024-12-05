
package com.company;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameGrid {
    public int n, m;
    public char[][] grid;
    int colorNumber = 0;

    public GameGrid(GameGrid other) {
        this.n = other.n;
        this.m = other.m;
        this.colorNumber = other.colorNumber;

        this.grid = new char[other.n][other.m];
        for (int i = 0; i < other.n; i++) {
            this.grid[i] = Arrays.copyOf(other.grid[i], other.m);
        }
    }

    public GameGrid(int n, int m) {
        this.n = n;
        this.m = m;
        this.grid = new char[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(grid[i], 'e');
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GameGrid gameGrid = (GameGrid) obj;
        return n == gameGrid.n && m == gameGrid.m && Arrays.deepEquals(grid, gameGrid.grid);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }


    public void printGrid() {
        System.out.println("Grid:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void updateGrid(char direction) {
        switch (direction) {
            case 'a':
                moveLeft();
                break;
            case 'd':
                moveRight();
                break;
            case 's':
                moveDown();
                break;
            case 'w':
                moveUp();
                break;
            default:
                System.out.println("Invalid input. Use 'a', 'd', 's', or 'w'.");
                break;
        }
    }


    private void moveLeft() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 'B' || grid[i][j] == 'e') {
                    continue;
                } else {
                    char cur_color = grid[i][j];
                    grid[i][j] = 'e';
                    while (j > 0 && grid[i][j - 1] == 'e') {
                        j--;
                    }
                    if (j == 0 || cur_color != grid[i][j - 1]) {
                        grid[i][j] = cur_color;
                    }
                }
            }
        }
    }

    private void moveRight() {
        for (int i = 0; i < n; i++) {
            for (int j = m - 1; j >= 0; j--) {
                if (grid[i][j] == 'B' || grid[i][j] == 'e') {
                    continue;
                } else {
                    char cur_color = grid[i][j];
                    grid[i][j] = 'e';
                    while (j < m - 1 && grid[i][j + 1] == 'e') {
                        j++;
                    }
                    if (j == m - 1 || cur_color != grid[i][j + 1]) {
                        grid[i][j] = cur_color;
                    }
                }
            }
        }
    }

    private void moveDown() {
        for (int j = 0; j < m; j++) {
            for (int i = n - 1; i >= 0; i--) {
                if (grid[i][j] == 'B' || grid[i][j] == 'e') {
                    continue;
                } else {
                    char cur_color = grid[i][j];
                    grid[i][j] = 'e';
                    while (i < n - 1 && grid[i + 1][j] == 'e') {
                        i++;
                    }
                    if (i == n - 1 || cur_color != grid[i + 1][j]) {
                        grid[i][j] = cur_color;
                    }
                }
            }
        }
    }

    private void moveUp() {
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (grid[i][j] == 'B' || grid[i][j] == 'e') {
                    continue;
                } else {
                    char cur_color = grid[i][j];
                    grid[i][j] = 'e';
                    while (i > 0 && grid[i - 1][j] == 'e') {
                        i--;
                    }
                    if (i == 0 || cur_color != grid[i - 1][j]) {
                        grid[i][j] = cur_color;
                    }
                }
            }
        }
    }


    public boolean checkWinCondition() {
        int colorCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != 'e' && grid[i][j] != 'B') {
                    colorCount++;
                }
            }
        }
        return colorNumber == colorCount;
    }


    void BFS() {
        long startTime = System.currentTimeMillis();

        GameGrid initial = new GameGrid(this);
        Deque<GameGrid> dq = new ArrayDeque<GameGrid>();
        dq.add(initial);
        Map<GameGrid, Pair<GameGrid, Character>> parent = new HashMap<GameGrid, Pair<GameGrid, Character>>();
        parent.put(initial, new Pair<GameGrid, Character>(null, null));

        Set<GameGrid> visited = new HashSet<GameGrid>();
        ArrayList<GameGrid> target = new ArrayList<GameGrid>();
        ArrayList<Character> directions = new ArrayList<Character>();

        int j=0 ;
        Boolean f=false;
        while (!dq.isEmpty()) {
            j++;
            GameGrid cur = dq.pollFirst();

            if (cur.checkWinCondition()) {
                int t = 0;
                f =true ;
                while (cur != null) {
                    target.add(new GameGrid(cur));
                    directions.add(parent.get(cur).getValue());
                    cur = parent.get(cur).getKey();
                    t++;
                }

                System.out.println("Total steps of win : " + (t - 1));


                for (int i = target.size() - 1; i >= 0; i--) {
                    target.get(i).printGrid();
                    if (i != 0) {
                        System.out.println("Move: " + directions.get(i-1));
                    }
                    System.out.println();
                }
                break;
            }

            GameGrid g1 = new GameGrid(cur);
            GameGrid g2 = new GameGrid(cur);
            GameGrid g3 = new GameGrid(cur);
            GameGrid g4 = new GameGrid(cur);

            visited.add(cur);

            g1.updateGrid('a');
            g2.updateGrid('d');
            g3.updateGrid('s');
            g4.updateGrid('w');

            if (!visited.contains(g1) && !parent.containsKey(g1)) {
                dq.addLast(g1);
                parent.put(g1, new Pair<GameGrid, Character>(cur, 'a'));
            }
            if (!visited.contains(g2) && !parent.containsKey(g2)) {
                dq.addLast(g2);
                parent.put(g2, new Pair<GameGrid, Character>(cur, 'd'));
            }
            if (!visited.contains(g3) && !parent.containsKey(g3)) {
                dq.addLast(g3);
                parent.put(g3, new Pair<GameGrid, Character>(cur, 's'));
            }
            if (!visited.contains(g4) && !parent.containsKey(g4)) {
                dq.addLast(g4);
                parent.put(g4, new Pair<GameGrid, Character>(cur, 'w'));
            }
        }
        long endTime = System.currentTimeMillis(); // End timing

        if(f ==false ) {
            System.out.println("There is no solution for this grid ");
        }
        System.out.println("Total steps of grid : " + j);
        System.out.println("Time to reach the goal: " + (endTime - startTime) + " ms");

    }



    void DFS() {
        long startTime = System.currentTimeMillis();

        GameGrid initial = new GameGrid(this);
        Stack<GameGrid> st= new Stack<GameGrid>();
        st.add(initial);
        Map<GameGrid, Pair<GameGrid, Character>> parent = new HashMap<GameGrid, Pair<GameGrid, Character>>();
        parent.put(initial, new Pair<GameGrid, Character>(null, null));

        Set<GameGrid> visited = new HashSet<GameGrid>();
        ArrayList<GameGrid> target = new ArrayList<GameGrid>();
        ArrayList<Character> directions = new ArrayList<Character>();

        int j=0 ;
        Boolean f=false;
        while (!st.isEmpty()) {
            j++;
            GameGrid cur = st.pop();

            if (cur.checkWinCondition()) {
                int t = 0;
                f =true ;
                while (cur != null) {
                    target.add(new GameGrid(cur));
                    directions.add(parent.get(cur).getValue());
                    cur = parent.get(cur).getKey();
                    t++;
                }

                System.out.println("Total steps of win : " + (t - 1));


                for (int i = target.size() - 1; i >= 0; i--) {
                    target.get(i).printGrid();
                    if (i != 0) {
                        System.out.println("Move: " + directions.get(i-1));
                    }
                    System.out.println();
                }
                break;
            }

            GameGrid g1 = new GameGrid(cur);
            GameGrid g2 = new GameGrid(cur);
            GameGrid g3 = new GameGrid(cur);
            GameGrid g4 = new GameGrid(cur);

            visited.add(cur);

            g1.updateGrid('a');
            g2.updateGrid('d');
            g3.updateGrid('s');
            g4.updateGrid('w');

            if (!visited.contains(g1) && !parent.containsKey(g1)) {
                st.add(g1);
                parent.put(g1, new Pair<GameGrid, Character>(cur, 'a'));
            }
            if (!visited.contains(g2) && !parent.containsKey(g2)) {
                st.add(g2);
                parent.put(g2, new Pair<GameGrid, Character>(cur, 'd'));
            }
            if (!visited.contains(g3) && !parent.containsKey(g3)) {
                st.add(g3);
                parent.put(g3, new Pair<GameGrid, Character>(cur, 's'));
            }
            if (!visited.contains(g4) && !parent.containsKey(g4)) {
                st.add(g4);
                parent.put(g4, new Pair<GameGrid, Character>(cur, 'w'));
            }
        }
        long endTime = System.currentTimeMillis(); // End timing

        if(f ==false ) {
            System.out.println("There is no solution for this grid ");
        }
        System.out.println("Total steps of grid : " + j);
        System.out.println("Time to reach the goal: " + (endTime - startTime) + " ms");

    }

    boolean recursive(GameGrid initial , Stack<GameGrid> st ,Map<GameGrid, Pair<GameGrid, Character>> parent, Set<GameGrid> visited,ArrayList<Character> directions,AtomicInteger steps) {
        steps.incrementAndGet();
        GameGrid cur = st.pop();
             Boolean f= false;
        if (cur.checkWinCondition()) {
            ArrayList<GameGrid> target = new ArrayList<GameGrid>();
            int t = 0;
            f =true ;
            while (cur != null) {
                target.add(new GameGrid(cur));
                directions.add(parent.get(cur).getValue());
                cur = parent.get(cur).getKey();
                t++;
            }
            System.out.println("Total steps of win : " + (t - 1));

            for (int i = target.size() - 1; i >= 0; i--) {
                target.get(i).printGrid();
                if (i != 0) {
                    System.out.println("Move: " + directions.get(i-1));
                }
                System.out.println();
            }
            return true;
        }

        GameGrid g1 = new GameGrid(cur);
        GameGrid g2 = new GameGrid(cur);
        GameGrid g3 = new GameGrid(cur);
        GameGrid g4 = new GameGrid(cur);

        visited.add(cur);
        g1.updateGrid('w');
        g2.updateGrid('s');
        g3.updateGrid('a');
        g4.updateGrid('d');


        if (!visited.contains(g1)) {
            st.add(g1);
            parent.put(g1, new Pair<GameGrid, Character>(cur, 'w'));
          if(recursive(g1,st , parent,visited,directions,steps))
              return true;
        }
        if (!visited.contains(g2)) {
            st.add(g2);
            parent.put(g2, new Pair<GameGrid, Character>(cur, 's'));
            if(recursive(g2,st , parent,visited,directions, steps))
                return true;
        }
        if (!visited.contains(g3)) {
            st.add(g3);
            parent.put(g3, new Pair<GameGrid, Character>(cur, 'a'));
            if(recursive(g3,st , parent,visited,directions, steps))
                return true;
        }
        if (!visited.contains(g4)) {
            st.add(g4);
            parent.put(g4, new Pair<GameGrid, Character>(cur, 'd'));
            if(recursive(g4,st , parent,visited,directions, steps))
                return true;
        }

            return false;

        }

    void dfsRec() {
        long startTime = System.currentTimeMillis();

        GameGrid initial = new GameGrid(this);
        Stack<GameGrid> st = new Stack<GameGrid>();
        st.add(initial);
        Map<GameGrid, Pair<GameGrid, Character>> parent = new HashMap<GameGrid, Pair<GameGrid, Character>>();
        parent.put(initial, new Pair<GameGrid, Character>(null, null));

        Set<GameGrid> visited = new HashSet<GameGrid>();
        ArrayList<Character> directions = new ArrayList<Character>();
        AtomicInteger steps = new AtomicInteger(0);
        if(recursive(initial, st, parent , visited, directions, steps)){
            System.out.println("Total steps of grid : " + steps.get());

        }else{
            System.out.println("There is no solution for this grid ");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time to reach the goal: " + (endTime - startTime) + " ms");

    }

        private static final Map<Character, Integer> directionCosts = new HashMap<Character, Integer>() {{
            put('d', 1);
            put('a', 2);
            put('w', 3);
            put('s', 4);
        }};


        public boolean uniformCostSearch() {
            long startTime = System.currentTimeMillis();

            GameGrid initial = new GameGrid(this);
            PriorityQueue<State> pq = new PriorityQueue<State>();
            pq.add(new State(initial, 0));

            Map<GameGrid, Pair<GameGrid, Character>> parent = new HashMap<GameGrid, Pair<GameGrid, Character>>();
            parent.put(initial, new Pair<GameGrid, Character>(null, null));

            Set<GameGrid> visited = new HashSet<GameGrid>();
            ArrayList<GameGrid> target = new ArrayList<GameGrid>();
            ArrayList<Character> directions = new ArrayList<Character>();

            int totalGrids = 0;
            boolean found = false;

            while (!pq.isEmpty()) {
                totalGrids++;
                State currentState = pq.poll();
                GameGrid cur = currentState.grid;

               // cur.printGrid();

                if (cur.checkWinCondition()) {

                    int t = 0;
                    found = true;
                    while (cur != null) {
                        target.add(new GameGrid(cur));
                        directions.add(parent.get(cur).getValue());
                        cur = parent.get(cur).getKey();
                        t++;
                    }

                    System.out.println("Total steps of win: " + (t - 1));

                    for (int i = target.size() - 1; i >= 0; i--) {
                        target.get(i).printGrid();
                        if (i != 0) {
                            System.out.println("Move: " + directions.get(i - 1));
                        }
                        System.out.println();
                    }
                    break;
                }

                visited.add(cur);

                for (char direction : directionCosts.keySet()) {
                    GameGrid newGrid = new GameGrid(cur);
                    newGrid.updateGrid(direction);
                    int newCost = currentState.cost + directionCosts.get(direction);

                    if (!visited.contains(newGrid) && !parent.containsKey(newGrid)) {
                        pq.add(new State(newGrid, newCost));
                        parent.put(newGrid, new Pair<GameGrid, Character>(cur, direction));
                    }
                }
            }
            long endTime = System.currentTimeMillis(); // End timing

            if (!found) {
                System.out.println("There is no solution for this grid.");
            }

            System.out.println("Total steps of grid: " + totalGrids);
            System.out.println("Time to reach the goal: " + (endTime - startTime) + " ms");
            return found;
        }


    public boolean hillClimbing() {
        long startTime = System.currentTimeMillis();

        GameGrid initial = new GameGrid(this);
        PriorityQueue<State> pq = new PriorityQueue<State>();
        pq.add(new State(initial, calculateHeuristic(initial)));

        Map<GameGrid, Pair<GameGrid, Character>> parent = new HashMap<GameGrid, Pair<GameGrid, Character>>();
        parent.put(initial, new Pair<GameGrid, Character>(null, null));

        Set<GameGrid> visited = new HashSet<GameGrid>();
        ArrayList<GameGrid> target = new ArrayList<GameGrid>();
        ArrayList<Character> directions = new ArrayList<Character>();

        int totalGrids = 0;
        boolean found = false;

        while (!pq.isEmpty()) {
            totalGrids++;
            State currentState = pq.poll();
            GameGrid cur = currentState.grid;
            if (cur.checkWinCondition()) {
                found = true;

                int t = 0;
                while (cur != null) {
                    target.add(new GameGrid(cur));
                    directions.add(parent.get(cur).getValue());
                    cur = parent.get(cur).getKey();
                    t++;
                }

                System.out.println("Total steps of win: " + (t - 1));
                for (int i = target.size() - 1; i >= 0; i--) {
                    target.get(i).printGrid();
                    if (i != 0) {
                        System.out.println("Move: " + directions.get(i - 1));
                    }
                    System.out.println();
                }
                break;
            }

            visited.add(cur);

            PriorityQueue<State> neighbors = new PriorityQueue<State>();
            for (char direction : directionCosts.keySet()) {
                GameGrid newGrid = new GameGrid(cur);
                newGrid.updateGrid(direction);

                if (!visited.contains(newGrid) && !parent.containsKey(newGrid)) {
                    int heuristic = calculateHeuristic(newGrid);
                    neighbors.add(new State(newGrid, heuristic));
                    parent.put(newGrid, new Pair<GameGrid, Character>(cur, direction));
                }
            }
            while (!neighbors.isEmpty()) {
                pq.add(neighbors.poll());
            }
        }
        long endTime = System.currentTimeMillis();
        if (!found) {
            System.out.println("There is no solution for this grid.");
        }

        System.out.println("Total grids evaluated: " + totalGrids);
        System.out.println("Time to reach the goal: " + (endTime - startTime) + " ms");
        return found;
    }
    public int calculateHeuristic(GameGrid grid) {
        int movableCells = 0;

        for (int i = 0; i < grid.n; i++) {
            for (int j = 0; j < grid.m; j++) {
                char cell = grid.grid[i][j];
                if (cell != 'B' && cell != 'e') {
                    if (grid.canMove(i, j)) {
                        movableCells++;
                    }
                }
            }
        }
        return movableCells;
    }


    public boolean canMove(int row, int col) {

        if (row < 0 || row >= n || col < 0 || col >= m || grid[row][col] == 'B') {
            return false;
        }

        if (row > 0 && grid[row - 1][col] == 'e') return true;
        if (row < n - 1 && grid[row + 1][col] == 'e') return true; // Down
        if (col > 0 && grid[row][col - 1] == 'e') return true; // Left
        if (col < m - 1 && grid[row][col + 1] == 'e') return true; // Right

        return false;
    }


    public boolean aStarSearch() {
        long startTime = System.currentTimeMillis();

        GameGrid initial = new GameGrid(this);
        PriorityQueue<State> pq = new PriorityQueue<State>();
        pq.add(new State(initial, 0));

        Map<GameGrid, Pair<GameGrid, Character>> parent = new HashMap<GameGrid, Pair<GameGrid, Character>>();
        parent.put(initial, new Pair<GameGrid, Character>(null, null));

        Map<GameGrid, Integer> costSoFar = new HashMap<GameGrid, Integer>();
        costSoFar.put(initial, 0);

        ArrayList<GameGrid> target = new ArrayList<GameGrid>();
        ArrayList<Character> directions = new ArrayList<Character>();
        Set<GameGrid> visited = new HashSet<GameGrid>();

        int totalGrids = 0;
        boolean found = false;

        while (!pq.isEmpty()) {
            totalGrids++;
            State currentState = pq.poll();
            GameGrid cur = currentState.grid;

            if (cur.checkWinCondition()) {
                found = true;

                int t = 0;
                while (cur != null) {
                    target.add(new GameGrid(cur));
                    directions.add(parent.get(cur).getValue());
                    cur = parent.get(cur).getKey();
                    t++;
                }

                System.out.println("Total steps of win: " + (t - 1));
                for (int i = target.size() - 1; i >= 0; i--) {
                    target.get(i).printGrid();
                    if (i != 0) {
                        System.out.println("Move: " + directions.get(i - 1));
                    }
                    System.out.println();
                }
                break;
            }

            visited.add(cur);

            for (char direction : directionCosts.keySet()) {
                GameGrid newGrid = new GameGrid(cur);
                newGrid.updateGrid(direction);

                int gCost = costSoFar.get(cur) + directionCosts.get(direction);
                int hCost = calculateHeuristic(newGrid);
                int fCost = gCost + hCost;

                if (!costSoFar.containsKey(newGrid)) {
                    costSoFar.put(newGrid, gCost);
                    pq.add(new State(newGrid, fCost));
                    parent.put(newGrid, new Pair<GameGrid, Character>(cur, direction));
                }
            }
        }

        long endTime = System.currentTimeMillis();
        if (!found) {
            System.out.println("There is no solution for this grid.");
        }

        System.out.println("Total grids evaluated: " + totalGrids);
        System.out.println("Time to reach the goal: " + (endTime - startTime) + " ms");
        return found;
    }



}


























