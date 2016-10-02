import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BoardTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void ThrowNPEonNull() {
        // throw a java.lang.NullPointerException if passed a null argument
        thrown.expect(NullPointerException.class);
        new Board(null);
    }

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
                " 0 12  9 13 \n" +
                "15 11 10 14 \n" +
                " 3  7  5  6 \n" +
                " 4  8  2  1 \n", board4.toString());
    }

    @Test
    public void equalsTest() {
        Board boardA = new Board(new int[][]{
                {0}
        });

        Board boardA1 = new Board(new int[][]{
                {0}
        });
        Board boardB = new Board(new int[][]{
                {1, 2},
                {3, 0}
        });
        assertTrue(boardA.equals(boardA));
        assertTrue(boardA.equals(boardA1));
        assertTrue(boardA1.equals(boardA));
        assertFalse(boardA.equals(boardB));
    }


    @Test
    public void neighborsTest() {
        Board board = new Board(new int[][]{
                {1, 2},
                {3, 0}
        });
        Iterable<Board> neighbors = board.neighbors();
        Board[] n = new Board[2];
        int i = 0;
        for (Board neighbor : neighbors) {
            n[i++] = neighbor;
        }
        assertArrayEquals(new Board[]{
                new Board(new int[][]{
                        {1, 2},
                        {0, 3}
                }),
                new Board(new int[][]{
                        {1, 0},
                        {3, 2}
                })
        }, n);
    }

    @Test
    public void isGoalTest() {
        Board emptyBoard = new Board(new int[][]{
        });
        assertTrue(emptyBoard.isGoal());

        Board oneElementBoard = new Board(new int[][]{
                {0}
        });
        assertTrue(oneElementBoard.isGoal());

        Board goalBoard = new Board(new int[][]{
                {1, 2},
                {3, 0}
        });
        assertTrue(goalBoard.isGoal());

        Board nonGoalBoard = new Board(new int[][]{
                {1, 2},
                {0, 3}
        });
        assertFalse(nonGoalBoard.isGoal());
    }
}
