public class Board {
    private final int dimension;
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
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, dimension);
        }
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }


    // number of blocks out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
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

        return sb.toString();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }
}
