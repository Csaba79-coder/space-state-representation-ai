package com.csaba79coder;

import com.csaba79coder.model.*;

public class StateRepresentationApp {

    public static void main(String[] args) {
        Node startNode;
        GraphSearch searcher;

        System.out.println("Solving the Hungry Knight problem on a 4x4 board.");
        startNode = new Node(new HungryKnightState(4));
        System.out.println("Using a depth-limited, memory-based backtrack search with a depth limit of 10.");
        searcher = new BackTrack(startNode, 10, true);
        searcher.printSolution(searcher.search());

        System.out.println("Using depth-first search with cycle detection.");
        searcher = new DepthFirstSearch(startNode, true);
        searcher.printSolution(searcher.search());

        System.out.println("Solving the 3 monks and 3 cannibals problem.");
        startNode = new Node(new MonksAndCannibalsState(3, 3));
        System.out.println("Using a depth-limited, memory-based backtrack search with a depth limit of 15.");
        searcher = new BackTrack(startNode, 15, true);
        searcher.printSolution(searcher.search());

        System.out.println("Using depth-first search with cycle detection.");
        searcher = new DepthFirstSearch(startNode, true);
        searcher.printSolution(searcher.search());
    }
}
