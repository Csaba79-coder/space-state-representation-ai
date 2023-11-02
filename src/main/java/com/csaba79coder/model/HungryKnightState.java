package com.csaba79coder.model;

import java.util.Objects;

/**
 * This is the state representation of the "Hungry Knight" problem.
 * The knight needs to go from the starting position in the top left corner
 * to the canteen, which is in the bottom right corner.
 * The board is represented by a (N+4)*(N+4) matrix.
 * The outer 2 rows and columns are the margin, and the inner part is the board.
 * Using the margin makes it much easier to write the isGoalState predicate.
 * 0 represents empty, and 1 represents the knight's position.
 * For a 3x3 board, the initial state is:
 * 0,0,0,0,0,0,0
 * 0,0,0,0,0,0,0
 * 0,0,1,0,0,0,0
 * 0,0,0,0,0,0,0
 * 0,0,0,0,0,0,0
 * 0,0,0,0,0,0,0
 * 0,0,0,0,0,0,0
 * From the representation above, it's clear that we only need to keep track of the knight's position.
 * So, the initial state (starting from the top left corner) is:
 * x = 2
 * y = 2
 * The goal state (reaching the bottom right corner) is:
 * x = N+1
 * y = N+1
 * Operators:
 * The possible 8 knight's moves.
 */
public class HungryKnightState extends AbstractState {
    // This class represents the state space for the "Hungry Knight" problem.
    // The knight must move from the starting position (top-left corner)
    // to the canteen (bottom-right corner) on an (N+4) x (N+4) chessboard.
    // The external 2-wide border serves as margin, and the inner part is the board.
    // Using the margin makes it easier to implement the isState() predicate.
    // In this representation, 0 means an empty cell, and 1 means the knight is present.
    // For a 3x3 board, the initial state is as follows:
    // 0, 0, 0, 0, 0, 0, 0
    // 0, 0, 0, 0, 0, 0, 0
    // 0, 0, 1, 0, 0, 0, 0
    // 0, 0, 0, 0, 0, 0, 0
    // 0, 0, 0, 0, 0, 0, 0
    // 0, 0, 0, 0, 0, 0, 0
    // 0, 0, 0, 0, 0, 0, 0
    // From the above representation, we can see that we only need to keep track of the knight's position.
    // So, the initial state (starting from the top-left corner) is:
    // x = 2
    // y = 2
    // The goal state (reaching the bottom-right corner) is:
    // x = N+1
    // y = N+1

    // By default, the class represents a 3x3 chessboard.
    private static int N = 3;
    // Fields for internal state representation.
    private int x;
    private int y;

    // Set the initial state (default constructor).
    public HungryKnightState() {
        x = 2; // Starting from the top-left corner (margins included), so (2,2).
        y = 2;
    }

    // Set the initial state and specify the board size (custom constructor).
    public HungryKnightState(int n) {
        x = 2;
        y = 2;
        N = n;
    }

    @Override
    public boolean isGoalState() {
        // The bottom-right corner (margins included) is at (N+1, N+1).
        return x == N + 1 && y == N + 1;
    }

    @Override
    public boolean isState() {
        // The knight is not on the margin.
        return x >= 2 && y >= 2 && x <= N + 1 && y <= N + 1;
    }

    // Check if it's a valid knight move. If not, return false.
    private boolean preKnightMove(int x, int y) {
        return (x * y == 2) || (x * y == -2);
    }

    // This is the operator method. It returns true if it's applicable, false otherwise.
    // Parameters:
    // - x: Movement in the x-direction.
    // - y: Movement in the y-direction.
    // Preconditions check if it's a valid knight move.
    // Postconditions ensure that the state remains on the board.
    // Example:
    // knightMove(1, -2) means moving up 2 squares and right 1 square.
    private boolean knightMove(int x, int y) {
        if (!preKnightMove(x, y)) return false;
        // If the preconditions are met, perform the state transition.
        this.x += x;
        this.y += y;
        // Postconditions always match the isState() predicate.
        if (isState()) return true;
        // If postconditions are not met, revert the state transition.
        this.x -= x;
        this.y -= y;
        return false;
    }

    @Override
    public boolean isSuperOperator(int i) {
        switch (i) {
            // Enumerate all possible 8 knight moves.
            case 0: return knightMove(1, 2);
            case 1: return knightMove(1, -2);
            case 2: return knightMove(-1, 2);
            case 3: return knightMove(-1, -2);
            case 4: return knightMove(2, 1);
            case 5: return knightMove(2, -1);
            case 6: return knightMove(-2, 1);
            case 7: return knightMove(-2, -1);
            default: return false;
        }
    }

    @Override
    public int getOperatorCount() {
        return 8;
    }

    // When printing, subtract the margin width from x and y.
    @Override
    public String toString() {
        return (x - 2) + " : " + (y - 2);
    }

    @Override
    public boolean equals(Object a) {
        HungryKnightState aa = (HungryKnightState) a;
        return aa.x == x && aa.y == y;
    }

    @Override
    public int hashCode() {
        return x + y;
    }
}
