import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Solver {
    private final Node goalNode;
    private int moves = -1;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        goalNode = compute(initial);

        if (goalNode != null) {
            moves = goalNode.moves;
        }
    }

    private Node compute(Board initial) {
        MinPQ<Node> queueMain = new MinPQ<>();
        queueMain.insert(new Node(initial, -1, null));

        MinPQ<Node> queueTwin = new MinPQ<>();
        queueTwin.insert(new Node(initial.twin(), -1, null));
        int maxMoves = (int) Math.pow(2, initial.dimension() * initial.dimension());
        boolean isMain = true;
        while (!queueMain.isEmpty() && !queueTwin.isEmpty()) {
            isMain = !isMain;
            if (isMain) {
                Node current = queueMain.delMin();
                if (current.board.isGoal()) {
                    return current;
                }
                if (current.moves > maxMoves) {
                    continue;
                }
                for (Board neighbor : current.board.neighbors()) {
                    if (!neighbor.equals(current.board)) {
                        queueMain.insert(new Node(neighbor, current.moves, current));
                    }
                }
            } else {
                Node currentTwin = queueTwin.delMin();
                if (currentTwin.board.isGoal()) {
                    return null;
                }
                if (currentTwin.moves > maxMoves) {
                    continue;
                }
                for (Board neighbor : currentTwin.board.neighbors()) {
                    if (!neighbor.equals(currentTwin.board)) {
                        queueTwin.insert(new Node(neighbor, currentTwin.moves, currentTwin));
                    }
                }
            }
        }
        return null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Node curr = goalNode;

        if (curr != null) {
            Board[] boards = new Board[moves() + 1];
            int i = moves;
            do {
                boards[i] = curr.board;
                i--;
                curr = curr.previous;
            } while (curr != null);
            return Arrays.asList(boards);
        } else {
            return null;
        }
    }

    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final int priority;
        private final Node previous;

        Node(Board board, int moves, Node previous) {
            this.previous = previous;
            this.board = board;
            this.moves = moves + 1;
            this.priority = this.moves + board.manhattan();
        }

        @Override
        public int compareTo(Node o) {
            if (priority == o.priority) {
                return board.manhattan() - o.board.manhattan();
            } else {
                return priority - o.priority;
            }
        }
    }
}
