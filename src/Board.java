import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int dimension;
    private int outOfPlace;
    private int blankR;
    private int blankC;
    private int[][] blocks;

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
                if (blocks[i][j] != 1 + i * dimension + j && !(i == dimension - 1 && j == dimension - 1)) {
                    outOfPlace++;
                }
                if (blocks[i][j] == 0) {
                    blankR = i;
                    blankC = j;
                }
            }
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
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return outOfPlace == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return null;
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
        addNeighborIfPossible(neighbors, 1, 0);
        addNeighborIfPossible(neighbors, -1, 0);
        addNeighborIfPossible(neighbors, 0, 1);
        addNeighborIfPossible(neighbors, 0, -1);
        return neighbors;
    }

    private void addNeighborIfPossible(Stack<Board> neighbors, int dr, int dc) {
        int blankTargetR = blankR + dr;
        int blankTargetC = blankC + dc;

        if (blankTargetR >= 0 && blankTargetR < dimension && blankTargetC >= 0 && blankTargetC < dimension) {
            Board copy = new Board(blocks);
            copy.swapBlankTo(blankTargetR, blankTargetC);
            neighbors.push(copy);
        }
    }

    private void swapBlankTo(int blankTargetR, int blankTargetC) {
        blocks[blankR][blankC] = blocks[blankTargetR][blankTargetC];
        blocks[blankTargetR][blankTargetC] = 0;
        blankR = blankTargetR;
        blankC = blankTargetC;
    }
}
