package com.company.DSA_Project_GaleShapley;
import java.util.Arrays;


public class GaleShapley {

    // Returns true if woman 'w' prefers man 'm1' over man 'm'
    private static boolean prefers(int[][] women, int w, int m, int m1) {
        for (int i = 0; i < women[0].length; i++) {
            if (women[w][i] == m1) {
                return true;
            }
            if (women[w][i] == m) {
                return false;
            }
        }
        return false;
    }

    // Prints the matching pairs
    private static int  printMatches(int[] matches,int finalWeight[][]) {
        int x = 0;
        for (int i = 0; i < matches.length; i++) {
            x += finalWeight[matches[i]][i];
        }
        return x;
    }

    // Implements the Gale-Shapley algorithm
    public static int stableMarriage(int[][] men, int[][] women,int [][] finalWeight) {
        int n = men.length;
        int[] matches = new int[n];
        boolean[] menEngaged = new boolean[n];

        Arrays.fill(matches, -1);

        int freeMen = n;

        while (freeMen > 0) {
            int m;
            for (m = 0; m < n; m++) {
                if (!menEngaged[m]) {
                    break;
                }
            }

            for (int i = 0; i < n && !menEngaged[m]; i++) {
                int w = men[m][i];

                if (matches[w - 1] == -1) {
                    matches[w - 1] = m;
                    menEngaged[m] = true;
                    freeMen--;
                }
                else {
                    int m1 = matches[w - 1];

                    if (prefers(women, w - 1, m, m1)) {
                        matches[w - 1] = m;
                        menEngaged[m] = true;
                        menEngaged[m1] = false;
                    }
                }
            }
        }

       return printMatches(matches,finalWeight);
    }

    public static void main(String[] args) {
        int[][] men = {
                {2, 3, 1},
                {2, 1, 3},
                {1, 2, 3}
        };

        int[][] women = {
                {1, 2, 3},
                {2, 3, 1},
                {3, 1, 2}
        };

//        stableMarriage(men, women);
    }
}
