import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int dimension;
    private int outOfPlace;
    private int manhattan;
    private int blankR;
    private int blankC;
    private int[][] blocks;
    private static int[][] directions = new int[][]{
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException();
        }
        dimension = blocks.length;
        this.blocks = new int[dimension][];
        // copy blocks to internal state
        for (int i = 0; i < dimension; i++) {
            this.blocks[i] = new int[dimension];
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (isBlockMisplaced(i, j)) {
                    outOfPlace++;
                    int manhattanDistance = computeManhattanDistance(i, j);
                    manhattan += manhattanDistance;
                }
                if (blocks[i][j] == 0) {
                    blankR = i;
                    blankC = j;
                }
            }
        }
    }

    private int computeManhattanDistance(int row, int col) {
        if (isBlank(row, col)) {
            return 0;
        } else {
            int block = blocks[row][col] - 1;
            int deltaRow = row - block / dimension;
            int deltaCol = col - (block - dimension * (block / dimension));
            return Math.abs(deltaRow) + Math.abs(deltaCol);
        }
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
        Board copy = new Board(blocks);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int[] d : directions) {
                    int targetR = i + d[0];
                    int targetC = j + d[1];
                    if (insideRange(targetR, targetC) && !isBlank(i, j) && !isBlank(targetR, targetC)) {
                        copy.swapFromTo(i, j, targetR, targetC);
                        return copy;
                    }
                }
            }
        }

        return copy;
    }

    private void swapFromTo(int row, int col, int targetRow, int targetCol) {
        // here we also update outOfPlace counter
        int numOutOfPlaceBeforeSwap = 0;
        int manhattanSumBefore = 0;
        if (isBlockMisplaced(targetRow, targetCol)) {
            numOutOfPlaceBeforeSwap++;
            manhattanSumBefore += computeManhattanDistance(targetRow, targetCol);
        }
        if (isBlockMisplaced(row, col)) {
            numOutOfPlaceBeforeSwap++;
            manhattanSumBefore += computeManhattanDistance(row, col);
        }

        int old = blocks[targetRow][targetCol];

        blocks[targetRow][targetCol] = blocks[row][col];
        blocks[row][col] = old;

        int numOutOfPlaceAfterSwap = 0;
        int manhattanSumAfter = 0;
        if (isBlockMisplaced(targetRow, targetCol)) {
            numOutOfPlaceAfterSwap++;
            manhattanSumAfter += computeManhattanDistance(targetRow, targetCol);
        }
        if (isBlockMisplaced(row, col)) {
            numOutOfPlaceAfterSwap++;
            manhattanSumAfter += computeManhattanDistance(row, col);
        }
        int outOfPlaceDelta = numOutOfPlaceAfterSwap - numOutOfPlaceBeforeSwap;
        outOfPlace += outOfPlaceDelta;

        int manhattanDelta = manhattanSumAfter - manhattanSumBefore;
        manhattan += manhattanDelta;
    }

    private boolean isBlockMisplaced(int row, int col) {
        int block = blocks[row][col];
        if (block == 0) {
            return false;
        }else {
            return block != 1 + row * dimension + col;
        }
    }

    private boolean isBlank(int row, int col) {
        return blocks[row][col] == 0;
    }

    private boolean insideRange(int targetR, int targetC) {
        return targetR >= 0 && targetR < dimension && targetC >= 0 && targetC < dimension;
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
        if (this.dimension() != that.dimension()) {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        sb.append("\n");

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(String.format("%2d ", blocks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        for (int[] d : directions) {
            addNeighborIfPossible(neighbors, d[0], d[1]);
        }
        return neighbors;
    }

    private void addNeighborIfPossible(Stack<Board> neighbors, int dr, int dc) {
        int blankTargetR = blankR + dr;
        int blankTargetC = blankC + dc;

        if (insideRange(blankTargetR, blankTargetC)) {
            Board copy = new Board(blocks);
            copy.swapBlankTo(blankTargetR, blankTargetC);
            neighbors.push(copy);
        }
    }

    private void swapBlankTo(int blankTargetR, int blankTargetC) {
        swapFromTo(blankR, blankC, blankTargetR, blankTargetC);
        blankR = blankTargetR;
        blankC = blankTargetC;
    }
}
