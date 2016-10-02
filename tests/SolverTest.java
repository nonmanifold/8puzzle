import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class SolverTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


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
    public void UnSolvable3x3() {
        int[][] blocks = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        };
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        assertFalse("Be unsolvable", solver.isSolvable());
    }

    @Test
    public void UnSolvable4x4() {
        int[][] blocks = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 15, 14, 0}
        };
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        assertFalse("Be unsolvable", solver.isSolvable());
    }

    @Test
    public void ThrowNPEonNull() {
        // throw a java.lang.NullPointerException if passed a null argument
        thrown.expect(NullPointerException.class);
        new Board(null);
    }
}
