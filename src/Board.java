import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {
    private int dimension = 0;
    private int outOfPlace;
    private int manhattan;
    private int blank;
    private int[] blocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException();
        }
        dimension = blocks.length;
        this.blocks = new int[dimension * dimension];
        // copy blocks to internal state
        int offset = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[offset] = blocks[i][j];
                if (isBlockMisplaced(offset)) {
                    outOfPlace++;
                    int manhattanDistance = computeManhattanDistance(offset);
                    manhattan += manhattanDistance;
                }
                if (blocks[i][j] == 0) {
                    blank = offset;
                }
                offset++;
            }

        }
    }

    private Board(int[] blocks, int dimension, int manhattan, int hamming, int blank) {
        this.blocks = Arrays.copyOf(blocks, blocks.length);
        this.dimension = dimension;
        outOfPlace = hamming;
        this.manhattan = manhattan;
        this.blank = blank;
    }

    private int to1d(int row, int col) {
        return row * dimension + col;
    }

    private int row(int offset) {
        return offset / dimension;
    }

    private int col(int offset) {
        return offset - row(offset) * dimension;
    }

    private int computeManhattanDistance(int offset) {
        int desiredIdx = blocks[offset] - 1;

        int deltaRow = row(offset) - row(desiredIdx);
        int deltaCol = col(offset) - col(desiredIdx);
        return Math.abs(deltaRow) + Math.abs(deltaCol);
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        return outOfPlace;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board copy = new Board(blocks, dimension, manhattan(), hamming(), blank);
        int source = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int d : directions()) {
                    int target = source + d;
                    if (insideRange(source, d) && !isBlank(source) && !isBlank(target)) {
                        copy.swapFromTo(source, target);
                        return copy;
                    }
                }
                source++;
            }
        }

        return copy;
    }

    private int[] directions() {
        return new int[]{
                1,
                dimension,
                -dimension,
                -1,
        };
    }

    private void swapFromTo(int source, int target) {
        // here we also update outOfPlace counter
        int numOutOfPlaceBeforeSwap = 0;
        int manhattanSumBefore = 0;
        if (isBlockMisplaced(target)) {
            numOutOfPlaceBeforeSwap++;
            manhattanSumBefore += computeManhattanDistance(target);
        }
        if (isBlockMisplaced(source)) {
            numOutOfPlaceBeforeSwap++;
            manhattanSumBefore += computeManhattanDistance(source);
        }

        int old = blocks[target];

        blocks[target] = blocks[source];
        blocks[source] = old;

        int numOutOfPlaceAfterSwap = 0;
        int manhattanSumAfter = 0;
        if (isBlockMisplaced(target)) {
            numOutOfPlaceAfterSwap++;
            manhattanSumAfter += computeManhattanDistance(target);
        }
        if (isBlockMisplaced(source)) {
            numOutOfPlaceAfterSwap++;
            manhattanSumAfter += computeManhattanDistance(source);
        }
        int outOfPlaceDelta = numOutOfPlaceAfterSwap - numOutOfPlaceBeforeSwap;
        outOfPlace += outOfPlaceDelta;

        int manhattanDelta = manhattanSumAfter - manhattanSumBefore;
        manhattan += manhattanDelta;
    }

    private boolean isBlockMisplaced(int offset) {
        int block = blocks[offset];
        if (block == 0) {
            return false;
        } else {
            return block != 1 + offset;
        }
    }

    private boolean isBlank(int offset) {
        return blocks[offset] == 0;
    }

    private boolean insideRange(int src, int d) {
        int target = src + d;
        if ((d == -1 || d == 1) && row(src) != row(target)) {
            return false;
        }
        return target >= 0 && target < blocks.length;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) other;
        if (this.dimension != that.dimension) {
            return false;
        }
        int offset = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[offset] != that.blocks[offset]) {
                    return false;
                }
                offset++;
            }
        }
        return true;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        sb.append("\n");
        int offset = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                sb.append(String.format("%2d ", blocks[offset]));
                offset++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        for (int d : directions()) {
            addNeighborIfPossible(neighbors, d);
        }
        return neighbors;
    }

    private void addNeighborIfPossible(Stack<Board> neighbors, int d) {
        int blankTarget = blank + d;
        if (insideRange(blank, d)) {
            Board copy = new Board(blocks, dimension, manhattan, outOfPlace, blank);
            copy.swapBlankTo(blankTarget);
            neighbors.push(copy);
        }
    }

    private void swapBlankTo(int blankTarget) {
        swapFromTo(blank, blankTarget);
        blank = blankTarget;
    }
}
