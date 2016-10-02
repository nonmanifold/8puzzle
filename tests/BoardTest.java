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

    @Test
    public void toStringTest() {
        Board board = new Board(new int[][]{
                {1, 2, 3},
                {4, 5, 0},
                {7, 8, 6}
        });

        assertEquals("3\n" +
                " 1  2  3 \n" +
                " 4  5  0 \n" +
                " 7  8  6 \n", board.toString());


        Board board4 = new Board(new int[][]{
                {0, 12, 9, 13},
                {15, 11, 10, 14},
                {3, 7, 5, 6},
                {4, 8, 2, 1}
        });
        assertEquals("4\n" +
                " 0 12  9 13\n" +
                "15 11 10 14\n" +
                " 3  7  5  6\n" +
                " 4  8  2  1\n", board4.toString());
    }
}
