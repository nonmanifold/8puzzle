import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class SolverTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void Solved04() {
        int[][] blocks = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        assertTrue("Be solvable", solver.isSolvable());
        assertEquals("Minimum number of moves = 0", 0, solver.moves());
    }

    @Test
    public void Solvable04() {
        int[][] blocks = new int[][]{
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        assertTrue("Be solvable", solver.isSolvable());
        assertEquals("Minimum number of moves = 4", 4, solver.moves());
    }


    @Test
    public void solutionTest() {
        Board initial = new Board(new int[][]{
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        });
        Solver solver = new Solver(initial);
        assertTrue("Be solvable", solver.isSolvable());
        assertEquals("Minimum number of moves = 4", 4, solver.moves());
        int boards = 0;
        Board last = null;
        Board first = null;
        for (Board b : solver.solution()) {
            boards++;
            if (first == null) {
                first = b;
            }
            last = b;
        }
        assertEquals(5, boards);
        assertEquals(first, initial);
        assertTrue(last.isGoal());
    }

    @Test
    public void UnSolvable3x3() {
        int[][] blocks = new int[][]{
                {8, 6, 7},
                {2, 5, 4},
                {1, 3, 0}

        };
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        assertFalse("Be unsolvable", solver.isSolvable());
        int boards = 0;
        for (Board b : solver.solution()) {
            boards++;
        }
        assertEquals(0, boards);
    }

    @Test
    public void UnSolvable4x4() {
        int[][] blocks = new int[][]{
                {3, 2, 4, 8},
                {1, 6, 0, 12},
                {5, 10, 7, 11},
                {9, 13, 14, 15}
        };
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        assertFalse("Be unsolvable", solver.isSolvable());
    }

    @Test
    public void ThrowNPEonNull() {
        // throw a java.lang.NullPointerException if passed a null argument
        thrown.expect(NullPointerException.class);
        new Solver(null);
    }
}
