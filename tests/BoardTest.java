import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void hamming() {
        int[][] blocks = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board = new Board(blocks);

        assertEquals(5, board.hamming());
    }

    @Test
    public void manhattan() {
        int[][] blocks = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board = new Board(blocks);

        assertEquals(10, board.manhattan());
    }
}
