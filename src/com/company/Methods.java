package com.company;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import com.company.GameGrid;


public class Methods
{

    public Methods() {
    }

    public  GameGrid start(Scanner scanner) {
        int n, m;
        while (true) {
            try {
                System.out.println("Enter number of rows (n):");
                n = scanner.nextInt();
                System.out.println("Enter number of columns (m):");
                m = scanner.nextInt();
                if (n > 0 && m > 0) break;
                System.out.println("Invalid grid dimensions. Both n and m must be greater than 0.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter valid integers for grid dimensions.");
                scanner.next();
            }
        }

        GameGrid gameGrid = new GameGrid(n, m);

        int cntColor;
        while (true) {
            try {
                System.out.println("Enter the number of colors:");
                cntColor = scanner.nextInt();
                gameGrid.colorNumber=cntColor;
                if (cntColor > 0 && cntColor <= n * m) break;
                System.out.println("Number of colors must be greater than 0 and less than grid cells.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer for the number of colors.");
                scanner.next();
            }
        }

        Set<Character> uniqueColors = new HashSet<Character>();

        int totalSquares = 0;
        for (int i = 0; i < cntColor; i++) {
            char color;
            while (true) {
                System.out.println("Enter color " + (i + 1) + ":");
                color = scanner.next().charAt(0);
                if (Character.isLetter(color) && !uniqueColors.contains(color) && color != 'B') {
                    uniqueColors.add(color);
                    break;
                }
                System.out.println("Invalid or duplicate color. Please enter a unique character.");
            }
            int size;
            while (true) {
                try {
                    System.out.println("Enter the number of cells for color " + color + ":");
                    size = scanner.nextInt();
                    if (size >0 && totalSquares + size <= n * m-(cntColor-(i+1))) break;
                    System.out.println("Invalid number of cells. Not enough space left for remaining colors.");
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid integer for the number of cells.");
                    scanner.next();
                }
            }
            totalSquares += size;

            for (int j = 0; j < size; j++) {
                while (true) {
                    try {
                        System.out.println("Enter coordinates (x, y) for cell " + (j + 1) + ":");
                        int x = scanner.nextInt();
                        int y = scanner.nextInt();
                        if (x >= 0 && x < n && y >= 0 && y < m && gameGrid.grid[x][y] == 'e' ) {
                            gameGrid.grid[x][y] =color;
                            break;
                        }
                        System.out.println("Invalid or invalid coordinates. Please try again.");
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter valid integers for coordinates.");
                        scanner.next();
                    }
                }
            }
        }

        int cntBlocks;
        while (true) {
            try {
                System.out.println("Enter the number of blocks:");
                cntBlocks = scanner.nextInt();
                if (cntBlocks >= 0 && cntBlocks <= n * m - totalSquares) break;
                System.out.println("Number of blocks must not exceed available empty cells.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer for the number of blocks.");
                scanner.next();
            }
        }

        for (int i = 0; i < cntBlocks; i++) {
            while (true) {
                try {
                    System.out.println("Enter coordinates (x, y) for block " + (i + 1) + ":");
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    if (x >= 0 && x < n && y >= 0 && y < m && gameGrid.grid[x][y] == 'e') {
                        gameGrid.grid[x][y]='B';
                        break;
                    }
                    System.out.println("Invalid or invalid coordinates. Please try again.");
                } catch (InputMismatchException e) {
                    System.out.println("Please enter valid integers for coordinates.");
                    scanner.next();
                }
            }
        }

        return gameGrid;
    }


}
