package com.csaba79coder.model;

/**
 * Representation of the "3 monks and 3 cannibals" problem state space.
 * It can be generalized for any number of monks and cannibals.
 * Problem: There are 3 monks and 3 cannibals on the left side of a river.
 * The goal is to move all people to the other side using a boat that can carry two people.
 * When the number of cannibals on one side exceeds the number of monks, the cannibals will eat the monks.
 * Initial state:
 * 3 monks on the left side.
 * 3 cannibals on the left side.
 * The boat is on the left side.
 * 0 monks on the right side.
 * 0 cannibals on the right side.
 * This state is represented as (3, 3, 'B', 0, 0).
 * The goal state is (0, 0, 'J', 3, 3).
 * Operators:
 * Op(int monks, int cannibals):
 * Move 'monks' number of monks and 'cannibals' number of cannibals to the other side.
 * Possible parameterizations:
 * Op(1, 0): Move 1 monk to the other side.
 * Op(2, 0): Move 2 monks to the other side.
 * Op(1, 1): Move 1 monk and 1 cannibal to the other side.
 * Op(0, 1): Move 1 cannibal to the other side.
 * Op(0, 2): Move 2 cannibals to the other side.
 */

public class MonksAndCannibalsState extends AbstractState {
    private final int missionaries; // Number of missionaries in total
    private final int cannibals; // Number of cannibals in total
    private int missionariesOnLeft; // Number of missionaries on the left side
    private int cannibalsOnLeft; // Number of cannibals on the left side
    private char boat; // Boat location, 'B' for left, 'J' for right
    private int missionariesOnRight; // Number of missionaries on the right side
    private int cannibalsOnRight; // Number of cannibals on the right side

    public MonksAndCannibalsState(int missionaries, int cannibals) {
        this.missionaries = missionaries;
        this.cannibals = cannibals;
        missionariesOnLeft = missionaries;
        cannibalsOnLeft = cannibals;
        boat = 'B';
        missionariesOnRight = 0;
        cannibalsOnRight = 0;
    }

    @Override
    public boolean isState() {
        return (missionariesOnLeft >= cannibalsOnLeft || missionariesOnLeft == 0) &&
                (missionariesOnRight >= cannibalsOnRight || missionariesOnRight == 0);
    }

    @Override
    public boolean isGoalState() {
        return missionariesOnRight == missionaries && cannibalsOnRight == cannibals;
    }

    private boolean preOp(int missionaries, int cannibals) {
        if (missionaries + cannibals > 2 || missionaries + cannibals < 0 || missionaries < 0 || cannibals < 0) {
            return false;
        }
        if (boat == 'B') {
            return missionariesOnLeft >= missionaries && cannibalsOnLeft >= cannibals;
        } else {
            return missionariesOnRight >= missionaries && cannibalsOnRight >= cannibals;
        }
    }

    private boolean op(int missionaries, int cannibals) {
        if (!preOp(missionaries, cannibals)) {
            return false;
        }

        MonksAndCannibalsState backup = (MonksAndCannibalsState) clone();

        if (boat == 'B') {
            missionariesOnLeft -= missionaries;
            cannibalsOnLeft -= cannibals;
            boat = 'J';
            missionariesOnRight += missionaries;
            cannibalsOnRight += cannibals;
        } else {
            missionariesOnLeft += missionaries;
            cannibalsOnLeft += cannibals;
            boat = 'B';
            missionariesOnRight -= missionaries;
            cannibalsOnRight -= cannibals;
        }

        if (isState()) {
            return true;
        }

        missionariesOnLeft = backup.missionariesOnLeft;
        cannibalsOnLeft = backup.cannibalsOnLeft;
        boat = backup.boat;
        missionariesOnRight = backup.missionariesOnRight;
        cannibalsOnRight = backup.cannibalsOnRight;

        return false;
    }

    @Override
    public int getOperatorCount() {
        return 5;
    }

    @Override
    public boolean isSuperOperator(int i) {
        switch (i) {
            case 0: return op(0, 1);
            case 1: return op(0, 2);
            case 2: return op(1, 1);
            case 3: return op(1, 0);
            case 4: return op(2, 0);
            default: return false;
        }
    }

    @Override
    public String toString() {
        return missionariesOnLeft + "," + cannibalsOnLeft + "," + boat + "," + missionariesOnRight + "," + cannibalsOnRight;
    }

    @Override
    public boolean equals(Object a) {
        MonksAndCannibalsState aa = (MonksAndCannibalsState) a;
        return aa.missionariesOnLeft == missionariesOnLeft && aa.cannibalsOnLeft == cannibalsOnLeft &&
                aa.boat == boat;
    }

    @Override
    public int hashCode() {
        return missionariesOnLeft + cannibalsOnLeft + boat;
    }
}
