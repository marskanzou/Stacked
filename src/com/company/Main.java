
package com.company;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Methods method = new Methods();
            GameGrid gameGrid = method.start(scanner);
            System.out.println("Enter the Type (1 for bfs or 2 for dfs or 4 for recursiveDFS  or 5 for UC " +
                    "or 6 for hillClimbing or 7 for A* or 3 for user ):");
            char type = scanner.next().charAt(0);
            if (type == '1') {
                gameGrid.BFS();

            }
            else if (type == '2') {
                gameGrid.DFS();
            }
            else if (type == '4') {
                gameGrid.dfsRec();
            }
            else if (type == '5') {
                boolean success = gameGrid.uniformCostSearch();
                if (success) {
                    System.out.println("Goal Reached!");
                } else {
                    System.out.println("No solution found.");
                }}
            else if (type == '6') {
                boolean success = gameGrid.hillClimbing();
                if (success) {
                    System.out.println("Goal Reached!");
                }else {
                    System.out.println("No solution found.");
                }}
            else if (type == '7') {
                boolean success = gameGrid.aStarSearch();
                if (success) {
                    System.out.println("Goal Reached!");
                }else {
                    System.out.println("No solution found.");
                    }
                }
            else{
            while (true) {
                gameGrid.printGrid();
                System.out.println("Enter direction (a = left, d = right, s = down, w = up, q = quit):");
                char direction = scanner.next().charAt(0);

                if (direction == 'q') {
                    System.out.println("Game Over!");
                    break;
                }

                gameGrid.updateGrid(direction);

                if (gameGrid.checkWinCondition()) {
                    gameGrid.printGrid();
                    System.out.println("Congratulations! You won the game!");
                    break;
                }
            }
            }
            System.out.println("Do you want to play again? (y/n):");
            char playAgain = scanner.next().charAt(0);
            if (playAgain != 'y') {
                System.out.println("Game Over!");
                break;
            }
        }
    }
}




